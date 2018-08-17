package com.yst.onecity.activity.servermember;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMManager;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.MainActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.AppCommonManager;
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
import okhttp3.Request;

/**
 * 服务专员(会员)修改登录密码页面
 *
 * @author Chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class ModifyLoginPasswordActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_register_phone)
    TextView tvRegisterPhone;
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.tv_password_phone)
    TextView tvPasswordPhone;
    @BindView(R.id.et_password_phone)
    EditText etPasswordPhone;
    @BindView(R.id.tv_code_phone)
    TextView tvCodePhone;
    @BindView(R.id.et_code_phone)
    EditText etCodePhone;
    @BindView(R.id.btn_code_phone)
    TextView btnCodePhone;
    @BindView(R.id.tv_modify_pass)
    TextView tvModifyPass;

    @Override
    public int bindLayout() {
        return R.layout.activity_modify_login_password;
    }

    @Override
    public void initData() {
        initTitleBar("修改登录密码");
    }

    @OnClick({R.id.tv_modify_pass, R.id.btn_code_phone})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //确认修改]
            case R.id.tv_modify_pass:
                goModifyLoginPassword();
                break;
            //发送验证码
            case R.id.btn_code_phone:
                MyLog.e("modeifyLoginpass", "getCode____" + TianyiApplication.appCommonBean.getPhone() + "___uuid___" + TianyiApplication.appCommonBean.getUuid());
                getCode();
                break;
            default:
                break;
        }
    }

    /**
     * 确认修改
     */
    private void goModifyLoginPassword() {
        String code = etCodePhone.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastUtils.show("请输入您的验证码");
            return;
        }
        String pass = etRegisterPhone.getText().toString().trim();
        if (TextUtils.isEmpty(pass)) {
            ToastUtils.show("请输入您的密码");
            return;
        }
        if (pass.length() < Const.INTEGER_6 || pass.length() > Const.INTEGER_12) {
            ToastUtils.show("密码长度应在6-12之间");
            return;
        }
        String surepass = etPasswordPhone.getText().toString().trim();
        if (TextUtils.isEmpty(surepass)) {
            ToastUtils.show("请输入您的确认密码");
            return;
        }
        if (surepass.length() < Const.INTEGER_6 || surepass.length() > Const.INTEGER_12) {
            ToastUtils.show("确认密码长度应在6-12之间");
            return;
        }
        if (!pass.equals(surepass)) {
            ToastUtils.show("两次密码输入不一致");
            return;
        }
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "newPassword", pass,
                "validCode", code,
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.CHANGE_LOGIN_PASSWORD)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("newPassword", pass)
                .addParams("validCode", code)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
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
                MyLog.e("modeifyLoginpass", "onerror___" + e);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("modeifyLoginpass", "response___" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has(Const.CONS_STR_CODE) && jsonObject.getInt(Const.CONS_STR_CODE) == 1) {
                        TianyiApplication.mAppCommonManager.quitLogin();
                        TianyiApplication.mAppCommonManager.setLoginType(AppCommonManager.PASSWORDLOGINTYPE);
                        TIMManager.getInstance().logout();
                        Intent intent = new Intent(MainActivity.ChangePage);
                        LocalBroadcastManager.getInstance(ModifyLoginPasswordActivity.this).sendBroadcast(intent);
                        Bundle bundle = new Bundle();
                        bundle.putString("register", "register");
                        bundle.putString("isModify", "isModify");
                        RxBus.get().send(RxCode.CODE_SETTING_PWD_FINISH);
                        JumpIntent.jump(ModifyLoginPasswordActivity.this, LoginActivity.class, bundle);
                        finish();
                    }
                    ToastUtils.show(jsonObject.getString("msg"), Toast.LENGTH_SHORT);
                } catch (JSONException e) {
                    ToastUtils.show(R.string.app_on_request_exception_msg, Toast.LENGTH_SHORT);
                }
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.ACCOUNT_SAFE_GET_CAODE)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
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
                MyLog.e("register", "getcode__onError___" + e);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("register", "getcode__onResponse___" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has(Const.CONS_STR_CODE) && jsonObject.getInt(Const.CONS_STR_CODE) == 1) {
                        ConstUtils.startCountDown(btnCodePhone, R.drawable.shape_gray_180dp_bg, R.color.white, R.drawable.shape_gray_180dp_hui_bg, R.color.white);
                    }
                    ToastUtils.show(jsonObject.getString("msg"), Toast.LENGTH_SHORT);
                } catch (JSONException e) {
                    ToastUtils.show(R.string.app_on_request_exception_msg, Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
