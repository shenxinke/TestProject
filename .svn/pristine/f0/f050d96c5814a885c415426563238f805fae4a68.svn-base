package com.yst.onecity.activity.member;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.servermember.ModifyLoginPasswordActivity;
import com.yst.onecity.activity.servermember.NameAuthenticationInfoActivity;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
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
import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 会员已经认证账户安全页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class AuthAccountSafeActivity extends BaseActivity {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.ll_login_pass)
    LinearLayout llLoginPass;
    @BindView(R.id.ll_identity_authentication)
    LinearLayout llIdentityAuthentication;
    @BindView(R.id.activity_account_security)
    LinearLayout activityAccountSecurity;


    @Override
    public int bindLayout() {
        return R.layout.activity_auth_account_safe;
    }

    @Override
    public void initData() {
            initTitleBar("账户安全");
        RxBus.get().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }

    @OnClick({R.id.ll_login_pass, R.id.ll_identity_authentication,R.id.ll_payment_code})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //登录密码
            case R.id.ll_login_pass:
                JumpIntent.jump(this, ModifyLoginPasswordActivity.class);
                break;
            //身份认证信息
            case R.id.ll_identity_authentication:
                getAuthencitationInfo();

                break;
            //支付密码
            case R.id.ll_payment_code:
                JumpIntent.jump(this, SetPaymentPasswordActivity.class);
                break;
            default:
                break;
        }
    }

 @Subscribe(code = RxCode.CODE_SETTING_PWD_FINISH,threadMode = ThreadMode.MAIN)
 public void receiveFinish(){
     finish();
 }

    /**
     * 获取个人认证信息
     */
    private void getAuthencitationInfo() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "phone", TianyiApplication.appCommonBean.getPhone()
        );
        OkHttpUtils.post().url(Constants.AUTHCATIONINFO)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                dismissInfoProgressDialog();
            }
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(R.string.app_on_request_error_msg, Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String s, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.has(Const.CONS_STR_CODE) && jsonObject.getInt(Const.CONS_STR_CODE) == 1) {
                        JumpIntent.jump(AuthAccountSafeActivity.this, NameAuthenticationInfoActivity.class);
                    } else {
                        JumpIntent.jump(AuthAccountSafeActivity.this, RealNameAuthenticationActivity.class);
                    }
                } catch (JSONException e) {
                    ToastUtils.show(R.string.app_on_request_exception_msg, Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
