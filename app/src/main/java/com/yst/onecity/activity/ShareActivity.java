package com.yst.onecity.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.BitmapUtil;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 分享界面
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public class ShareActivity extends BaseActivity {

    private String title = "";
    private String content = "";
    private Handler mHandler;
    private String readSDPermission = Manifest.permission.READ_EXTERNAL_STORAGE;
    private String shareConsultUrl = Constants.SHARE_CONSULT;
    private String shareServerMember = Constants.SHARE_SERVER_MEMBER;
    private String shareUrl;
    public static final int SHARE_CONSULT = 1;
    public static final int SHARE_SERVER_MEMBER = 2;
    public static final int SHARE_INVITATION = 3;

    private int type;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    private String id;

    private static class MyHandler extends Handler {
        private final WeakReference<Activity> mActivity;

        public MyHandler(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null) {
                return;
            }
            if (msg.what == 1) {
                ToastUtils.show("分享成功");
            } else if (msg.what == Const.INTEGER_2) {
                ToastUtils.show("分享失败");
            } else {
                ToastUtils.show("分享取消");
            }
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_share;
    }

    @Override
    public void initData() {
        initTitleBar(getResources().getString(R.string.share));
        mHandler = new MyHandler(this);
        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", 0);
            switch (type) {
                case SHARE_CONSULT:
                    id = getIntent().getStringExtra("consultId");
                    shareUrl = shareConsultUrl + "ty_xq.html" + "?id=" + id;
//                    + "&memberid=" + TianyiApplication.appCommonBean.getId()
                    title = "分享资讯";
                    content = "分享资讯";
                    MyLog.e("share", shareUrl);
                    break;
                case SHARE_SERVER_MEMBER:
                    String hunterId = getIntent().getStringExtra("hunterId");
                    shareUrl = shareServerMember + "?hId=" + hunterId;
                    title = "分享服务专员";
                    content = "分享服务专员";
                    MyLog.e("share", shareUrl);
                    break;
                case SHARE_INVITATION:
                    title = "邀请下载App";
                    content = "邀请下载App";
                    break;
                default:
                    break;
            }
        }

    }

    @OnClick({R.id.ll_back, R.id.share_qq, R.id.share_weixin, R.id.share_weibo})
    public void onViewClicked(View view) {
        if (EasyPermissions.hasPermissions(this, readSDPermission)) {

            switch (view.getId()) {
                case R.id.share_qq:
                    if (!Utils.isAvilible(context, Const.CONS_STR_COM_TECENT_QQ)) {
                        ToastUtils.show("还没有安装QQ");
                        return;
                    }
                    share(SHARE_MEDIA.QQ, type);
                    break;
                case R.id.share_weixin:
                    if (!Utils.isAvilible(context, Const.CONS_STR_COM_TECENT_MM)) {
                        ToastUtils.show("还没有安装微信");
                        return;
                    } else {
                        share(SHARE_MEDIA.WEIXIN, type);
                    }
                    break;
                case R.id.share_weibo:
                    if (!Utils.isAvilible(context, Const.CONS_STR_COM_SINA_WEIBO)) {
                        ToastUtils.show("还没有安装新浪微博");
                        return;
                    } else {
                        share(SHARE_MEDIA.SINA, type);
                    }
                    break;
                default:
                    break;
            }
        } else {
            EasyPermissions.requestPermissions(this, "请打开读写内存卡权限", 100, readSDPermission);
        }
    }

    private void share(SHARE_MEDIA qq, int type) {
        UMImage imageR;
        if (type == Const.INTEGER_3) {
            String uri = getIntent().getStringExtra("url");
            //二维码
            imageR = new UMImage(ShareActivity.this, BitmapUtil.createQRImage(uri, 300,
                    BitmapFactory.decodeResource(getResources(), R.drawable.icon_logo)));
            //开启自定义分享页面
            new ShareAction(this)
                    .setPlatform(qq)
                    .setCallback(umShareListener)
                    .withTitle(title)
                    .withMedia(imageR)
                    .share();
        } else {
            //logo
            imageR = new UMImage(ShareActivity.this, R.drawable.icon_logo);
            //开启自定义分享页面
            new ShareAction(this)
                    .setPlatform(qq)
                    .setCallback(umShareListener)
                    .withTitle(title)
                    .withText(content)
                    .withTargetUrl(shareUrl)
                    .withMedia(imageR)
                    .share();
        }

    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {

            MyLog.d("sss", "platform" + platform);
            if (Const.CONS_STR_WEIXIN_FAVORITE.equals(platform.name())) {
                Toast.makeText(context, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                requestShareSuccessNet();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(context, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                com.umeng.socialize.utils.Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 分享成功的接口
     */
    private void requestShareSuccessNet() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "consultId", id,
                "timestamp", timestamp);
        OkHttpUtils
                .post()
                .url(Constants.SHARE_SUCCESS_UPDATE)
                .addParams("consultId", id)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                MyLog.e("sss", e.toString());
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("sss", response);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }
}
