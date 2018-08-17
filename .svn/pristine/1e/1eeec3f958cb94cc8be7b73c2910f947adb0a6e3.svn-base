package com.yst.onecity.activity.member;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 设置支付密码
 *
 * @author Shenxinke
 * @version 4.0.0
 * @data 2018/3/20
 */

public class SetPaymentPasswordActivity extends BaseActivity {


    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.et_password_phone)
    EditText etPasswordPhone;
    @BindView(R.id.et_code_phone)
    EditText etCodePhone;
    @BindView(R.id.btn_code_phone)
    TextView btnCodePhone;
    private int passWordLength = 6;


    @Override
    public int bindLayout() {
        return R.layout.activity_set_payment_password;
    }

    @Override
    public void initData() {
        isSetPaymentPassword();
    }

    @OnClick({R.id.tv_modify_pass, R.id.btn_code_phone})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //确认修改]
            case R.id.tv_modify_pass:
                goChangePaymentPassword();
                break;
            //发送验证码
            case R.id.btn_code_phone:
                getCode();
                break;
            default:
                break;
        }
    }

    /**
     * 确认修改
     */
    private void goChangePaymentPassword() {
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
        if (pass.length() > passWordLength || pass.length() < passWordLength ) {
            ToastUtils.show("密码长度应为6位");
            return;
        }
        String surepass = etPasswordPhone.getText().toString().trim();
        if (TextUtils.isEmpty(surepass)) {
            ToastUtils.show("请输入您的确认密码");
            return;
        }
        if (surepass.length() < passWordLength || surepass.length() > passWordLength) {
            ToastUtils.show("确认密码长度为6位");
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
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "smsCode",code,
                "client_type", "A",
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.PAYMENT_CODE)
                .addParams("phone", TianyiApplication.appCommonBean.getPhone())
                .addParams("newPassword", pass)
                .addParams("uuid", TianyiApplication.appCommonBean.getUuid())
                .addParams("timestamp", timestamp)
                .addParams("smsCode", code)
                .addParams("client_type", "A")
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
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("modeifyLoginpass", "response___" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has(Const.CONS_STR_CODE) && jsonObject.getInt(Const.CONS_STR_CODE) == 1) {
                        ToastUtils.show(jsonObject.getString("msg"), Toast.LENGTH_SHORT);
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
        OkHttpUtils.post().url(Constants.CHANGE_SHORT_MESSAGE)
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

    /**
     * 验证是否设置过支付密码
     */
    private void isSetPaymentPassword(){
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", TianyiApplication.appCommonBean.getPhone(),
                "uuid", TianyiApplication.appCommonBean.getUuid(),
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.IS_SET_PASSWORD)
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
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("register", "getcode__onResponse___" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has(Const.CONS_STR_CODE) && jsonObject.getInt(Const.CONS_STR_CONTENT) == 1) {
                        initTitleBar("设置支付密码");
                    }else{
                        initTitleBar("修改支付密码");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.show(R.string.app_on_request_exception_msg, Toast.LENGTH_SHORT);
                }
            }
        });
    }

}
