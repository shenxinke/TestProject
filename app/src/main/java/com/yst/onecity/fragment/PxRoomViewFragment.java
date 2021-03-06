package com.yst.onecity.fragment;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.commissioner.CommissionerHomePageActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.activity.video.VideoRecoderActivity;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.PxVideoBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.fragment.popfragment.PxOnLineCommentDialog;
import com.yst.onecity.fragment.popfragment.ShareCommonDialog;
import com.yst.onecity.utils.CircleTransFrom;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.RxCode;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import gorden.rxbus2.RxBus;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 品秀视频覆盖层页面
 *
 * @author WangJingWei
 */
public class PxRoomViewFragment extends BaseFragment {

    @BindView(R.id.tv_addVideo)
    TextView tvAddVideo;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_dz)
    TextView tvDz;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_desc)
    TextView tvDesc;

    private ShareCommonDialog mShareCommonDialog;
    private String videoUrl = "";
    private String videoTitle = "品秀";
    private String videoId = "";
    /**
     * 0未点赞 1已赞
     */
    private int isFabulous;

    private String productName;
    private String productDesc;
    private String nickName;
    private String headImg;
    private String commentNum;
    private int fabulousNum;
    private String shareNum;
    private boolean isNoFirst;

    private PxVideoBean.ContentBean mContentBean = new PxVideoBean.ContentBean();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Const.INTEGER_1000:
                    setView();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void setView() {
        tvProductName.setText(ConstUtils.getStringNoEmpty(productName));
        if (!TextUtils.isEmpty(nickName) && nickName.length() > Const.INTEGER_5) {
            tvNickname.setText(nickName.substring(0, 5) + "...");
        } else {
            tvNickname.setText(ConstUtils.getStringNoEmpty(nickName));
        }

        tvDesc.setText(ConstUtils.getStringNoEmpty(productDesc));
        tvShare.setText(ConstUtils.changeEmptyStringToZero(shareNum));
        tvComment.setText(ConstUtils.changeEmptyStringToZero(commentNum));
        tvDz.setText(String.valueOf(fabulousNum));

        Glide.with(TianyiApplication.getInstance()).load(ConstUtils.matchingImageUrl(headImg)).asBitmap()
                .transform(new CircleTransFrom(TianyiApplication.getInstance())).error(R.mipmap.head_2).into(ivHead);
        mHandler.removeMessages(Const.INTEGER_1000);
    }

    private String[] videoPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
    private PxOnLineCommentDialog pxOnLineCommentDialog;

    @OnClick(R.id.tv_addVideo)
    public void addVideo() {
        if (!TianyiApplication.isLogin) {
            JumpIntent.jump((Activity) context, LoginActivity.class);
            return;
        }
        if (TextUtils.isEmpty(TianyiApplication.appCommonBean.getIsCertification())) {
            ToastUtils.show("未认证用户不可添加");
            return;
        }

        if (EasyPermissions.hasPermissions(getActivity(), videoPermission)) {
            Bundle bundle = new Bundle();
            bundle.putInt(Const.VIDEORECODER_FROM, Const.INTEGER_0);
            JumpIntent.jump(getActivity(), VideoRecoderActivity.class, bundle);

        } else {
            EasyPermissions.requestPermissions(this, "请打开相机权限", 100, videoPermission);
        }
    }

    @OnClick(R.id.tv_share)
    public void shareVideo() {
        if (mShareCommonDialog == null) {
            mShareCommonDialog = ShareCommonDialog.openShareDialog(getActivity(), "普济一城", videoTitle, videoUrl, "");
            mShareCommonDialog.setSharePageType(Const.SHARE_PAGETYPE_PX);
            mShareCommonDialog.setShareContentType(Const.SHARE_CONTENTTYPE_VIDEO);
        }
        mShareCommonDialog.show(getActivity().getFragmentManager(), ShareCommonDialog.class.toString());
    }

    @OnClick(R.id.tv_comment)
    public void commentVideo() {
        if (!TianyiApplication.isLogin) {
            JumpIntent.jump((Activity) context, LoginActivity.class);
            return;
        }

        if (pxOnLineCommentDialog == null) {
            pxOnLineCommentDialog = PxOnLineCommentDialog.newInstance(videoId);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("showId", videoId);
            pxOnLineCommentDialog.setArguments(bundle);
        }
        pxOnLineCommentDialog.setPxData(mContentBean);
        pxOnLineCommentDialog.show(getActivity().getSupportFragmentManager(), PxOnLineCommentDialog.class.toString());
    }

    @OnClick(R.id.tv_dz)
    public void dzVideo() {
        if (!Utils.isClickable()) {
            return;
        }

        if (!TianyiApplication.isLogin) {
            JumpIntent.jump((Activity) context, LoginActivity.class);
            return;
        }
        videoGoodLike();
    }


    @OnClick(R.id.iv_head)
    public void toZY() {
        if (!Utils.isClickable()) {
            return;
        }
        Bundle bundle = new Bundle();
        MyLog.e("sss", "--px-hunterId: " + mContentBean.getHunterId());
        bundle.putString("hid", mContentBean.getHunterId());
        bundle.putString("from", Const.CONS_STR_PX);
        JumpIntent.jump(getActivity(), CommissionerHomePageActivity.class, bundle);
    }

    public static PxRoomViewFragment newInstance() {
        PxRoomViewFragment fragment = new PxRoomViewFragment();
        return fragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_px_video_roomview;
    }

    @Override
    public void initData() {
        MyLog.e("sss", "initData");
    }

    @Override
    public void onResume() {
        super.onResume();
        MyLog.e("sss", "onResume");
    }

    public void setFragmentAuguments(PxVideoBean.ContentBean contentBean) {
        mContentBean = contentBean;

        MyLog.e("sss", "setFragmentAuguments");
        if (contentBean != null) {
            videoUrl = Constants.SHARE_CONSULT + "share_video.html" + "?id=" + String.valueOf(mContentBean.getId()) + "&type=0";
            MyLog.e("aaa", "videoUrl_______" + videoUrl + String.valueOf(mContentBean.getId()));
            videoId = String.valueOf(mContentBean.getId());
            productName = mContentBean.getProductName();
            productDesc = mContentBean.getContent();
            nickName = mContentBean.getNickname();
            headImg = mContentBean.getHeadImg();
            commentNum = String.valueOf(mContentBean.getCommentNum());
            fabulousNum = mContentBean.getFabulousNum();
            shareNum = String.valueOf(mContentBean.getShareNum());
            isFabulous = mContentBean.getIsFabulous();

            if (!isNoFirst) {
                isNoFirst = true;
                mHandler.sendEmptyMessageDelayed(Const.INTEGER_1000, Const.INTEGER_50);
            } else {
                setView();
            }
        }
    }

    /**
     * 品秀视频点赞接口
     */
    private void videoGoodLike() {

        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "showId", videoId,
                "timestamp", timestamp);

        if (TextUtils.isEmpty(sign)) {
            return;
        }

        OkHttpUtils.post()
                .url(Constants.VIDEO_PX_GOODLIEK)
                .addParams("showId", videoId)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(TianyiApplication.getInstance().getString(R.string.str_net_error_message));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (Const.INTEGER_1 == jsonObject.getInt(Const.CONS_STR_CODE)) {
                                if (jsonObject.has(Const.CONS_STR_CONTENT)) {
                                    int type = jsonObject.getInt(Const.CONS_STR_CONTENT);
                                    switch (type) {
                                        case Const.INTEGER_0:
                                            ToastUtils.show("取消成功");
                                            mContentBean.setFabulousNum(mContentBean.getFabulousNum() - 1);

                                            break;
                                        case Const.INTEGER_1:
                                            ToastUtils.show("点赞成功");
                                            mContentBean.setFabulousNum(mContentBean.getFabulousNum() + 1);
                                            break;
                                        default:
                                            break;
                                    }
                                    RxBus.get().send(RxCode.CODE_PX_DZ, mContentBean);
                                }
                            } else {
                                ToastUtils.show(jsonObject.getString(Const.CONS_STR_MESSAGE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void dismissShareDialog() {
        if (mShareCommonDialog != null) {
            mShareCommonDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacksAndMessages(null);
    }

}

