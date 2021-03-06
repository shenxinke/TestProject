package com.yst.onecity.wxapi;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yst.onecity.R;
import com.yst.onecity.activity.member.MyOrderActivity;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @version 4.0.0
 * @author WangJingWei
 * @date 2018/4/11.
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    @BindView(R.id.iv_pay_image)
    ImageView ivPayImage;
    @BindView(R.id.tv_pay_state)
    TextView tvPayState;
    @BindView(R.id.tv_auto_jump)
    TextView tvAutoJump;
    private IWXAPI api;
    private Handler handler = new Handler();
    private int time = 3;
    private String paySuccess = "支付成功";

    @Override
    public int bindLayout() {
        return R.layout.activity_wxpay_entry;
    }

    @Override
    public void initData() {
        setTitleBarTitle("支付结果");
        api = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        MyLog.e("sss","--code: "+baseResp.errCode);
        if (baseResp.errCode == 0) {
            tvPayState.setText("支付成功");
            ivPayImage.setImageResource(R.mipmap.chenggong);
            jump();
        } else if (baseResp.errCode == -Const.INTEGER_2) {
            ivPayImage.setImageResource(R.mipmap.shibai);
            tvPayState.setText("支付取消");
            jump();
        } else {
            tvAutoJump.setEnabled(true);
            tvAutoJump.setText("再次尝试");
            ivPayImage.setImageResource(R.mipmap.shibai);
            tvPayState.setText("支付失败");
        }
    }

    @OnClick({R.id.ll_back, R.id.tv_auto_jump})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                jumpOrderDetail();
                break;
            case R.id.tv_auto_jump:
                WXPayEntryActivity.this.finish();
                break;
            default:
                break;
        }
    }

    private void jump() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                time--;
                if (time == -1) {
                    if (paySuccess.equals(tvPayState.getText().toString())) {
                        EventBean bean = new EventBean("1", "", "", "", "");
                        EventBus.getDefault().post(bean);
                        jumpOrderDetail();
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putInt("flag", 0);
                        bundle.putString("pay", "pay");
                        JumpIntent.jump(WXPayEntryActivity.this, MyOrderActivity.class, bundle);
                        WXPayEntryActivity.this.finish();
                    }
                    return;
                }
                tvAutoJump.setText("自动跳转\t" + time + "s");
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void jumpOrderDetail() {
        if (paySuccess.equals(tvPayState.getText().toString())) {
            Bundle bundle = new Bundle();
            bundle.putInt("flag", Constants.ORDER_ISMEMBER);
            bundle.putString("pay", "success");
            JumpIntent.jump(WXPayEntryActivity.this, MyOrderActivity.class, bundle);
            WXPayEntryActivity.this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            jumpOrderDetail();
        }
        return super.onKeyDown(keyCode, event);
    }

}
