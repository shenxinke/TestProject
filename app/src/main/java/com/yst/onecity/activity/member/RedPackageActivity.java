package com.yst.onecity.activity.member;

import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.order.OrderRewardBean;
import com.yst.onecity.callbacks.AbstractOrderRewardCallBack;
import com.yst.onecity.config.Constants;
import com.yst.onecity.dialog.RedPackgetDialog;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MusicUtils;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 开红包
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/12/18
 */
public class RedPackageActivity extends BaseActivity {

    private RedPackgetDialog mRedPackgetDialog;
    private MusicUtils musicUtils;
    private String id;
    private boolean isOpen = false;

    @Override
    public int bindLayout() {
        return R.layout.activity_red_package;
    }

    @Override
    public void initData() {
        initTitleBar("开红包");
        id = getIntent().getExtras().getString("id");
        mRedPackgetDialog = new RedPackgetDialog(this, new RedPackgetDialog.RedPackageCallBack() {
            @Override
            public void toOpen() {
                if (isOpen) {
                    mRedPackgetDialog.setEnable(false);
                } else {
                    if (ConstUtils.isClickable()) {
                        openRedPackage();
                    }
                }
            }

            @Override
            public void toCancle() {
                RedPackageActivity.this.finish();
            }
        });
        mRedPackgetDialog.showCartDialog();
    }

    /**
     * 开红包
     */
    private void openRedPackage() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "id", id,
                "type", "1",
                "memberId", TianyiApplication.appCommonBean.getId(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.ORDER_REWARD)
                .addParams("id", id)
                //1开红包，0天天返
                .addParams("type", "1")
                .addParams("memberId", TianyiApplication.appCommonBean.getId())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new AbstractOrderRewardCallBack() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.app_on_request_error_msg));
            }

            @Override
            public void onResponse(OrderRewardBean response, int id) {
                if (response.getCode() == 1) {
                    musicUtils = new MusicUtils(RedPackageActivity.this);
                    musicUtils.initMediaPlayer();
                    musicUtils.playBeepSoundAndVibrate();
                    mRedPackgetDialog.setImageStyle(R.mipmap.hongbao_open);
                    mRedPackgetDialog.setMoney(response.getContent());
                    isOpen = true;
                    mRedPackgetDialog.setCancleText("确定");
                } else {
                    ToastUtils.show(response.getMsg());
                }
            }
        });
    }

}
