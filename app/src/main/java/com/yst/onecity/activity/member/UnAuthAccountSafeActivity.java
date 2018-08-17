package com.yst.onecity.activity.member;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.activity.servermember.ModifyLoginPasswordActivity;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员未认证账户安全页面
 *
 * @author Chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class UnAuthAccountSafeActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ll_login_pass)
    LinearLayout llLoginPass;
    @BindView(R.id.tv_authentication)
    TextView tvAuthentication;

    @Override
    public int bindLayout() {
        return R.layout.activity_un_auth_account_safe;
    }

    @Override
    public void initData() {
        initTitleBar("账户安全");
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.ll_login_pass, R.id.tv_authentication ,R.id.ll_payment_code})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //登录密码
            case R.id.ll_login_pass:
                JumpIntent.jump(this, ModifyLoginPasswordActivity.class);
                break;
            //实名认证
            case R.id.tv_authentication:
                JumpIntent.jump(this, RealNameAuthenticationActivity.class);
                break;
            //设置支付密码
            case R.id.ll_payment_code:
                JumpIntent.jump(this, SetPaymentPasswordActivity.class);
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (Const.CONS_STR_AUTHENTICATION.equals(event.getMsg())) {
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
