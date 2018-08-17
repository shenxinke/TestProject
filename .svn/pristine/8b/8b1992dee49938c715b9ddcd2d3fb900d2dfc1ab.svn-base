package com.yst.onecity.activity.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.dialog.AbstractVerifyCodeDialog;
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

import static com.yst.onecity.config.Constants.URL_ROOT;

/**
 * 注册页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class RegisterActivity extends BaseActivity {

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
    @BindView(R.id.tv_code_phone)
    TextView tvCodePhone;
    @BindView(R.id.et_code_phone)
    EditText etCodePhone;
    @BindView(R.id.btn_code_phone)
    TextView btnCodePhone;
    @BindView(R.id.tv_password_phone)
    TextView tvPasswordPhone;
    @BindView(R.id.et_password_phone)
    EditText etPasswordPhone;
    @BindView(R.id.tv_sure_password)
    TextView tvSurePassword;
    @BindView(R.id.et_sure_password_phone)
    EditText etSurePasswordPhone;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.iv_check_agree)
    ImageView ivCheckAgree;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    private int agreeTag = 2;
    private AbstractVerifyCodeDialog dialog;
    private int numcode;

    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initData() {
        initTitleBar("注册");
    }

    @OnClick({R.id.iv_check_agree, R.id.tv_register, R.id.btn_code_phone, R.id.tv_xieyi})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //同意协议
            case R.id.iv_check_agree:
                if (agreeTag == 1) {
                    ivCheckAgree.setImageResource(R.mipmap.registered_agreement_s);
                    agreeTag = 2;
                } else if (agreeTag == Const.INTEGER_2) {
                    ivCheckAgree.setImageResource(R.mipmap.registered_agreement_none);
                    agreeTag = 1;
                }
                break;
            //发送验证码
            case R.id.btn_code_phone:
                String name = etRegisterPhone.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.show("请输入您的手机号");
                    return;
                }
                if (!ConstUtils.isMobileNO(name)) {
                    ToastUtils.show("请输入正确的手机号");
                    return;
                }
                imageVerifyCode();
//                getCode();
                break;
            //注册
            case R.id.tv_register:
                goRegister();
                break;
            case R.id.tv_xieyi:
                JumpIntent.jump(RegisterActivity.this, UserAgreementActivity.class);
                break;
            default:
                break;
        }
    }


    /**
     * 图形验证码
     */
    private void imageVerifyCode() {
        numcode = (int) ((Math.random() * 9 + 1) * 100000);
        MyLog.e("register_verify", "numcode____" + numcode);
        final String name = etRegisterPhone.getText().toString().trim();
        dialog = new AbstractVerifyCodeDialog(RegisterActivity.this) {
            @Override
            protected void flushClick() {
                numcode = (int) ((Math.random() * 9 + 1) * 100000);
                MyLog.e("register_verify", "numcode____" + numcode);
                setUrls(numcode);
            }

            @Override
            public void onSureClick(String verifyCode) {
                if (TextUtils.isEmpty(verifyCode)) {
                    ToastUtils.show("请输入验证码");
                    return;
                }
                String timestamp = SignUtils.getTimeStamp();
                String sign = Utils.getSign(
                        "code", verifyCode,
                        "phone", name,
                        "uniquenessCode", String.valueOf(numcode),
                        "timestamp", timestamp);
                OkHttpUtils.post().url(Constants.VERIFY_IMGVERIFYCODE)
                        .addParams("code", verifyCode)
                        .addParams("uniquenessCode", String.valueOf(numcode))
                        .addParams("phone", name)
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
                        MyLog.e("register_verify", "getcode__onError___" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyLog.e("register_verify", "getcode__onResponse___" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has(Const.CONS_STR_CODE) && jsonObject.getInt(Const.CONS_STR_CODE) == 1) {
                                ConstUtils.startCountDown(btnCodePhone, R.drawable.shape_gray_180dp_bg, R.color.white, R.drawable.shape_gray_180dp_hui_bg, R.color.white);
                                dismissDialog();
                            } else {
                                numcode = (int) ((Math.random() * 9 + 1) * 100000);
                                MyLog.e("register_verify", "numcode____" + numcode);
                                setUrls(numcode);
                            }
                            ToastUtils.show(jsonObject.getString("msg"), Toast.LENGTH_SHORT);
                        } catch (JSONException e) {
                            ToastUtils.show(R.string.app_on_request_exception_msg, Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        };
        dialog.setUrl(URL_ROOT + "mobile/view/checkimagecount/securitycodeimg/11?uniquenessCode=" + numcode + "&client_type=A");
        dialog.showDialog();
    }

    private void setUrls(int num) {
        if (dialog == null) {
            dialog = new AbstractVerifyCodeDialog(RegisterActivity.this) {
                @Override
                protected void flushClick() {

                }

                @Override
                public void onSureClick(String verifyCode) {

                }
            };
        } else {
            dialog.setUrl(URL_ROOT + "mobile/view/checkimagecount/securitycodeimg/11?uniquenessCode=" + num + "&client_type=A");
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String name = etRegisterPhone.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.show("请输入您的手机号");
            return;
        }
        if (!ConstUtils.isMobileNO(name)) {
            ToastUtils.show("请输入正确的手机号");
            return;
        }
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", name,
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.LOGIN_GET_CODE)
                .addParams("phone", name)
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
     * 注册
     */
    private void goRegister() {
        String name = etRegisterPhone.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.show("请输入您的手机号");
            return;
        }
        if (!ConstUtils.isMobileNO(name)) {
            ToastUtils.show("请输入正确的手机号");
            return;
        }
        String code = etCodePhone.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastUtils.show("请输入您的验证码");
            return;
        }
        String pass = etPasswordPhone.getText().toString().trim();
        if (TextUtils.isEmpty(pass)) {
            ToastUtils.show("请输入您的密码");
            return;
        }
        if (pass.length() < Const.INTEGER_6 || pass.length() > Const.INTEGER_12) {
            ToastUtils.show("密码长度应在6-12之间");
            return;
        }
        String surepass = etSurePasswordPhone.getText().toString().trim();
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
        if (agreeTag == 1) {
            ToastUtils.show("请阅读并确认普济一城用户协议");
            return;
        }
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "phone", name,
                "password", pass,
                "imageCode", code,
                "timestamp", timestamp
        );

        if (TextUtils.isEmpty(sign)) {
            return;
        }

        OkHttpUtils.post().url(Constants.REGISTER)
                .addParams("phone", name)
                .addParams("password", pass)
                .addParams("imageCode", code)
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addParams("client_type", "A")
                .build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                dismissProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(R.string.app_on_request_error_msg, Toast.LENGTH_SHORT);
                MyLog.e("register", "register____onError___" + e);
            }

            @Override
            public void onResponse(String response, int id) {
                MyLog.e("register", "register____onResponse___" + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject != null) {
                        if (jsonObject.has(Const.CONS_STR_MESSAGE)) {
                            ToastUtils.show(jsonObject.getString("msg"));
                        }

                        if (jsonObject.has(Const.CONS_STR_CODE) && Const.INTEGER_1 == jsonObject.getInt(Const.CONS_STR_CODE)) {
                            RxBus.get().send(RxCode.CODE_REGISTER_SEND, "register");
                            finish();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismissDialog();
        }
    }
}