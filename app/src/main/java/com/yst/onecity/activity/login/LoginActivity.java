package com.yst.onecity.activity.login;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMUser;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.sdk.Constant;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.MainActivity;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.AppCommonBean;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.bean.LoginBean;
import com.yst.onecity.callbacks.AbstractCodeMsgCallback;
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

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import gorden.rxbus2.RxBus;
import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;
import okhttp3.Call;
import okhttp3.Request;

import static com.yst.onecity.R.id.tv_pass_count;

/**
 * 登录页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.txt_password)
    TextView txtPassword;
    @BindView(R.id.txt_password_line)
    TextView txtPasswordLine;
    @BindView(R.id.rel_password)
    RelativeLayout relPassword;
    @BindView(R.id.txt_phone_code)
    TextView txtPhoneCode;
    @BindView(R.id.txt_phone_code_line)
    TextView txtPhoneCodeLine;
    @BindView(R.id.rel_code)
    RelativeLayout relCode;
    @BindView(tv_pass_count)
    EditText tvPassCount;
    @BindView(R.id.tv_pass_delete)
    ImageView tvPassDelete;
    @BindView(R.id.tv_pass_pass)
    EditText tvPassPass;
    @BindView(R.id.llayout_password_login)
    LinearLayout llayoutPasswordLogin;
    @BindView(R.id.tv_code_count)
    EditText tvCodeCount;
    @BindView(R.id.tv_code_delete)
    ImageView tvCodeDelete;
    @BindView(R.id.tv_code_pass)
    EditText tvCodePass;
    @BindView(R.id.btn_code_phone)
    TextView btnCodePhone;
    @BindView(R.id.llayout_code_login)
    LinearLayout llayoutCodeLogin;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.activity_login)
    LinearLayout activityLogin;
    /**
     * 1密码登录2验证码登录
     */
    private int logintType = 1;
    private String isRegister;
    private String isModify;
    private String register = "register";
    private String isModify1 = "isModify";
    /**
     * 退出登录的时间
     */
    private long time = 0;
    private String addcart;
    private String addcartDetails;
    private String mAddcart = "addcart";
    private String mAddcartDetails = "addcartDetails";

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        RxBus.get().register(this);
        setTitleBarTitle("登录");
        setRightTextVisibility(View.VISIBLE);
        setRightText("注册");
        if (getIntent().getExtras() != null) {
            isModify = getIntent().getExtras().getString("isModify");
            addcart = getIntent().getExtras().getString("addcart");
            addcartDetails = getIntent().getExtras().getString("addcartDetails");

            MyLog.e("sss", "addcart:" + addcart);
            MyLog.e("sss", "addcartDetails:" + addcartDetails);

        }
        MyLog.e("login", "initData___isRegister___" + isRegister);

    }

    @OnClick({R.id.ll_back, R.id.tv_right, R.id.rel_password, R.id.rel_code, R.id.tv_login, R.id.btn_code_phone, R.id.tv_pass_delete, R.id.tv_code_delete})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //返回
            case R.id.ll_back:
                if (!TextUtils.isEmpty(isModify) && isModify.equals(isModify)) {
                    TianyiApplication.exit();
                } else {
                    finish();
                }
                break;
            //注册
            case R.id.tv_right:
                JumpIntent.jump(this, RegisterActivity.class);
                break;
            //密码登录
            case R.id.rel_password:
                logintType = 1;
                txtPassword.setTextColor(ContextCompat.getColor(this, R.color.color_ffbb03));
                txtPasswordLine.setVisibility(View.VISIBLE);
                txtPhoneCode.setTextColor(ContextCompat.getColor(this, R.color.color_8c8c8c));
                txtPhoneCodeLine.setVisibility(View.INVISIBLE);
                llayoutPasswordLogin.setVisibility(View.VISIBLE);
                llayoutCodeLogin.setVisibility(View.GONE);

                break;
            //验证码登录
            case R.id.rel_code:
                logintType = 2;
                txtPassword.setTextColor(ContextCompat.getColor(this, R.color.color_8c8c8c));
                txtPasswordLine.setVisibility(View.INVISIBLE);
                txtPhoneCode.setTextColor(ContextCompat.getColor(this, R.color.color_ffbb03));
                txtPhoneCodeLine.setVisibility(View.VISIBLE);
                llayoutPasswordLogin.setVisibility(View.GONE);
                llayoutCodeLogin.setVisibility(View.VISIBLE);

                break;
            //发送验证码
            case R.id.btn_code_phone:
                getCode();
                break;
            //删除密码登录账号
            case R.id.tv_pass_delete:
                tvPassCount.setText("");
                break;
            //删除验证码登录账号
            case R.id.tv_code_delete:
                tvCodeCount.setText("");
                break;
            //登录
            case R.id.tv_login:
                if (logintType == 1) {
                    //密码登录
                    goPasswordLogin();
                } else if (logintType == Const.INTEGER_2) {
                    //验证码登录
                    goCodeLogin();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String name = tvCodeCount.getText().toString().trim();
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
                .build().execute(new AbstractCodeMsgCallback() {
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
                MyLog.e("login", "getcode__onError___" + e);
            }

            @Override
            public void onResponse(CodeMsgBean response, int id) {
                MyLog.e("login", "getcode__onResponse___" + response.getMsg());
                if (response != null && response.getCode() == 1) {
                    ConstUtils.startCountDown(btnCodePhone, R.drawable.shape_gray_180dp_bg, R.color.white, R.drawable.shape_gray_180dp_hui_bg, R.color.white);
                }
                ToastUtils.show(response.getMsg());
            }

        });
    }

    /**
     * 验证码登录
     */
    private void goCodeLogin() {
        final String name = tvCodeCount.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.show("请输入您的手机号");
            return;
        }
        if (!ConstUtils.isMobileNO(name)) {
            ToastUtils.show("请输入正确的手机号");
            return;
        }
        String code = tvCodePass.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastUtils.show("请输入您的验证码");
            return;
        }
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "phone", name,
                "code", code
        );
        OkHttpUtils.post().url(Constants.LOGIN_WITH_CODE)
                .addParams("phone", name)
                .addParams("code", code)
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
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
                                         MyLog.e("login", "goCodeLogin____onError___" + e);
                                     }

                                     @Override
                                     public void onResponse(String s, int id) {
                                         MyLog.e("login", "goCodeLogin____onResponse___" + s);
                                         Gson gson = new Gson();
                                         LoginBean loginBean = gson.fromJson(s, LoginBean.class);
                                         if (loginBean != null && loginBean.getCode() == 1) {
                                             if (mAddcart.equals(addcart)) {
                                                 MyLog.e("sss", "EventBus");
                                                 RxBus.get().send(RxCode.ADD_CART);
                                             } else if (mAddcartDetails.equals(addcartDetails)) {
                                                 MyLog.e("sss", "EventBus2");
                                                 RxBus.get().send(RxCode.ADD_CART_DETAILS);
                                             } else if (Const.ISDETAILJUMLP) {
                                                 Const.ISDETAILJUMLP = false;
                                                 RxBus.get().send(RxCode.CODE_INFORMATIONDETAIL_LOGIN);
                                             }
                                             List<LoginBean.ContentBean> appCommonBeanInfo = loginBean.getContent();
                                             AppCommonBean appCommonBean = new AppCommonBean();
                                             for (int i = 0; i < appCommonBeanInfo.size(); i++) {
                                                 if (i == 0) {
                                                     if (appCommonBeanInfo.get(i).getIs_newyetai() != null) {
                                                         appCommonBean.setIsNewYeTai(appCommonBeanInfo.get(i).getIs_newyetai());
                                                     }
                                                     if (appCommonBeanInfo.get(i).getHunter_id() != null) {
                                                         appCommonBean.setHunter_id(appCommonBeanInfo.get(i).getHunter_id());
                                                     }
                                                     if (appCommonBeanInfo.get(i).getAddress() != null) {
                                                         appCommonBean.setAddress(appCommonBeanInfo.get(i).getAddress());
                                                     }
                                                     if (appCommonBeanInfo.get(i).getIMpassword() != null) {
                                                         appCommonBean.setIm_password(appCommonBeanInfo.get(i).getIMpassword());
                                                     }
                                                     if (appCommonBeanInfo.get(i).getNickname() != null) {
                                                         appCommonBean.setNickname(appCommonBeanInfo.get(i).getNickname());
                                                     }
                                                     if (appCommonBeanInfo.get(i).getName() != null) {
                                                         appCommonBean.setName(appCommonBeanInfo.get(i).getName());
                                                     }
                                                     if (appCommonBeanInfo.get(i).getId() != null) {
                                                         appCommonBean.setId(appCommonBeanInfo.get(i).getId());
                                                     }
                                                     if (appCommonBeanInfo.get(i).getIMid() != null) {
                                                         appCommonBean.setIm_id(appCommonBeanInfo.get(i).getIMid());
                                                     }
                                                     if (appCommonBeanInfo.get(i).getUuid() != null) {
                                                         appCommonBean.setUuid(appCommonBeanInfo.get(i).getUuid());
                                                     }
                                                     if (appCommonBeanInfo.get(i).getImSign() != null) {
                                                         appCommonBean.setImSign(appCommonBeanInfo.get(i).getImSign());
                                                     }
                                                     if (appCommonBeanInfo.get(i).getIsCertification() != null) {
                                                         appCommonBean.setIsCertification(appCommonBeanInfo.get(i).getIsCertification());
                                                     }

                                                     appCommonBean.setUserType(appCommonBeanInfo.get(i).getRole());
                                                 }
                                                 if (i == 1) {
                                                     if (appCommonBeanInfo.get(i).getIdent() != null) {
                                                         appCommonBean.setIdent(appCommonBeanInfo.get(i).getIdent());
                                                     }
                                                 }
                                             }
                                             appCommonBean.setPhone(name);
                                             TianyiApplication.mAppCommonManager.saveUserInfo(appCommonBean);
                                             TianyiApplication.mAppCommonManager.setLoginStaus();
                                             TianyiApplication.mAppCommonManager.setLoginType(AppCommonManager.CODELOGINTYPE);
                                             TianyiApplication.mAppCommonManager.saveCodeLoginInfo(appCommonBean);
                                             TIMManager.getInstance().init(LoginActivity.this);
                                             //初始化TLS
                                             TlsBusiness.init(LoginActivity.this);
                                             //设置刷新监听
                                             RefreshEvent.getInstance();
                                             TianyiApplication.isLogin = true;
                                             String identifier = appCommonBean.getIm_id();
                                             String userSig = appCommonBean.getImSign();
                                             TIMUser user = new TIMUser();
                                             user.setIdentifier(identifier);
                                             if (identifier != null && userSig != null) {
                                                 TIMManager.getInstance().login(Constant.SDK_APPID, user, userSig, new TIMCallBack() {
                                                     @Override
                                                     public void onError(int i, String s) {
                                                         MyLog.e("mainactivity", "onError onError === " + i);
                                                         MyLog.e("mainactivity", "onError onError === " + s);
                                                     }

                                                     @Override
                                                     public void onSuccess() {
                                                         MyLog.e("mainactivity", "onSuccess onSuccess === ");

                                                     }
                                                 });
                                             }

                                             if (!TextUtils.isEmpty(isRegister) && register.equals(isRegister)) {
                                                 startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                             } else {
                                                 finish();
                                             }
                                             ToastUtils.show(loginBean.getMsg());
                                         }else{
                                             ToastUtils.show(loginBean.getMsg());
                                         }
                                     }
                                 }
        );
    }

    /**
     * 密码登录
     */

    private void goPasswordLogin() {
        MyLog.e("login", "goPasswordLogin____isRegister___" + isRegister);
        final String name = tvPassCount.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.show("请输入您的手机号");
            return;
        }
        if (!ConstUtils.isMobileNO(name)) {
            ToastUtils.show("请输入正确的手机号");
            return;
        }
        final String pass = tvPassPass.getText().toString().trim();
        if (TextUtils.isEmpty(pass)) {
            ToastUtils.show("请输入您的密码");
            return;
        }
        if (pass.length() < Const.INTEGER_6 || pass.length() > Const.INTEGER_12) {
            ToastUtils.show("密码长度应在6-12位之间");
            return;
        }
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "phone", name,
                "password", pass
        );
        OkHttpUtils.post().url(Constants.LOGIN_WITH_PASSWORD)
                .addParams("phone", name)
                .addParams("password", pass)
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .build().execute(new StringCallback() {
            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissProgressDialog();
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(R.string.app_on_request_error_msg, Toast.LENGTH_SHORT);
                MyLog.e("login", "goPasswordLogin____onError___" + e);
                dismissInfoProgressDialog();
            }

            @Override
            public void onResponse(String s, int id) {
                MyLog.e("login", "goPasswordLogin____onResponse___" + s);
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(s, LoginBean.class);
                if (loginBean != null && loginBean.getCode() == 1) {
                    if (mAddcart.equals(addcart)) {
                        RxBus.get().send(RxCode.ADD_CART);
                    } else if (mAddcartDetails.equals(addcartDetails)) {
                        MyLog.e("sss", "EventBus2");
                        RxBus.get().send(RxCode.ADD_CART_DETAILS);
                    } else if (Const.ISDETAILJUMLP) {
                        Const.ISDETAILJUMLP = false;
                        RxBus.get().send(RxCode.CODE_INFORMATIONDETAIL_LOGIN);
                    }
                    List<LoginBean.ContentBean> appCommonBeanInfo = loginBean.getContent();
                    AppCommonBean appCommonBean = new AppCommonBean();
                    for (int i = 0; i < appCommonBeanInfo.size(); i++) {
                        if (i == 0) {
                            if (appCommonBeanInfo.get(i).getIs_newyetai() != null) {
                                appCommonBean.setIsNewYeTai(appCommonBeanInfo.get(i).getIs_newyetai());
                            }
                            if (appCommonBeanInfo.get(i).getHunter_id() != null) {
                                appCommonBean.setHunter_id(appCommonBeanInfo.get(i).getHunter_id());
                            }
                            if (appCommonBeanInfo.get(i).getAddress() != null) {
                                appCommonBean.setAddress(appCommonBeanInfo.get(i).getAddress());
                            }
                            if (appCommonBeanInfo.get(i).getIMpassword() != null) {
                                appCommonBean.setIm_password(appCommonBeanInfo.get(i).getIMpassword());
                            }
                            if (appCommonBeanInfo.get(i).getNickname() != null) {
                                appCommonBean.setNickname(appCommonBeanInfo.get(i).getNickname());
                            }
                            if (appCommonBeanInfo.get(i).getName() != null) {
                                appCommonBean.setName(appCommonBeanInfo.get(i).getName());
                            }
                            if (appCommonBeanInfo.get(i).getId() != null) {
                                appCommonBean.setId(appCommonBeanInfo.get(i).getId());
                            }
                            if (appCommonBeanInfo.get(i).getIMid() != null) {
                                appCommonBean.setIm_id(appCommonBeanInfo.get(i).getIMid());
                            }
                            if (appCommonBeanInfo.get(i).getUuid() != null) {
                                appCommonBean.setUuid(appCommonBeanInfo.get(i).getUuid());
                            }
                            if (appCommonBeanInfo.get(i).getImSign() != null) {
                                appCommonBean.setImSign(appCommonBeanInfo.get(i).getImSign());
                            }
                            if (appCommonBeanInfo.get(i).getIsCertification() != null) {
                                appCommonBean.setIsCertification(appCommonBeanInfo.get(i).getIsCertification());
                            }

                            appCommonBean.setUserType(appCommonBeanInfo.get(i).getRole());
                        }
                        if (i == 1) {
                            if (appCommonBeanInfo.get(i).getIdent() != null) {
                                appCommonBean.setIdent(appCommonBeanInfo.get(i).getIdent());
                            }
                        }
                    }
                    appCommonBean.setPhone(name);
                    appCommonBean.setPassword(pass);
                    TianyiApplication.isLogin = true;
                    TianyiApplication.mAppCommonManager.saveUserInfo(appCommonBean);
                    TianyiApplication.mAppCommonManager.setLoginStaus();
                    TianyiApplication.mAppCommonManager.setLoginType(AppCommonManager.PASSWORDLOGINTYPE);

                    TianyiApplication.isLogin = true;

                    TIMManager.getInstance().init(LoginActivity.this);
                    //初始化TLS
                    TlsBusiness.init(LoginActivity.this);
                    //设置刷新监听
                    RefreshEvent.getInstance();

                    String identifier = appCommonBean.getIm_id();
                    String userSig = appCommonBean.getImSign();
                    TIMUser user = new TIMUser();
                    user.setIdentifier(identifier);
                    if (identifier != null && userSig != null) {
                        TIMManager.getInstance().login(Constant.SDK_APPID, user, userSig, new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                MyLog.e("mainactivity", "onError onError === " + i);
                                MyLog.e("mainactivity", "onError onError === " + s);
                            }

                            @Override
                            public void onSuccess() {
                                MyLog.e("mainactivity", "onSuccess onSuccess === ");
                            }
                        });
                    }

                    if (!TextUtils.isEmpty(isRegister) && register.equals(isRegister)) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        finish();
                    }
                }
                ToastUtils.show(loginBean.getMsg());
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!TextUtils.isEmpty(isModify) && isModify.equals(isModify)) {
                if (System.currentTimeMillis() - time > Const.INTEGER_2000) {
                    ToastUtils.show(getString(R.string.main_activity_exit_msg));
                    time = System.currentTimeMillis();
                } else {
                    TianyiApplication.exit();
                }
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(code = RxCode.CODE_REGISTER_SEND, threadMode = ThreadMode.MAIN)
    public void getRegisterValue(String str) {
        isRegister = str;
    }

}
