package com.tencent.qcloud.tlslibrary.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.tencent.qcloud.tlslibrary.helper.Const;
import com.tencent.qcloud.tlslibrary.helper.MyResource;
import com.tencent.qcloud.tlslibrary.helper.ThreadPoolHelper;
import com.tencent.qcloud.tlslibrary.helper.Util;
import com.tencent.qcloud.tlslibrary.service.Constants;
import com.tencent.qcloud.tlslibrary.service.TLSService;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSPwdRegListener;
import tencent.tls.platform.TLSPwdResetListener;
import tencent.tls.platform.TLSUserInfo;

/**
 * @author wangjingwei
 * @version 1.0.0
 * @date 18/04/11
 */
public class PhonePwdLoginActivity extends Activity {

    public final static String TAG = "PhonePwdLoginActivity";

    private AlertDialog dialog;

    private TLSService tlsService;
    private int loginWay = Constants.PHONEPWD_LOGIN;
    private String countryCode;
    private String phoneNumber;
    final static int SMS_REG_REQUEST = 10004;
    final static int SMS_RESET_REQUEST = 10005;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(MyResource.getIdByName(getApplication(), "layout", "tencent_tls_ui_activity_phone_pwd_login"));

        Intent intent = getIntent();
        if (Constants.thirdappPackageNameSucc == null) {
            Constants.thirdappPackageNameSucc = intent.getStringExtra(Constants.EXTRA_THIRDAPP_PACKAGE_NAME_SUCC);
        }
        if (Constants.thirdappClassNameSucc == null) {
            Constants.thirdappClassNameSucc = intent.getStringExtra(Constants.EXTRA_THIRDAPP_CLASS_NAME_SUCC);
        }
        if (Constants.thirdappPackageNameFail == null) {
            Constants.thirdappPackageNameFail = intent.getStringExtra(Constants.EXTRA_THIRDAPP_PACKAGE_NAME_FAIL);
        }
        if (Constants.thirdappClassNameFail == null) {
            Constants.thirdappClassNameFail = intent.getStringExtra(Constants.EXTRA_THIRDAPP_CLASS_NAME_FAIL);
        }

        tlsService = TLSService.getInstance();

        /** 手机号密码登录*/
        if ((loginWay & Constants.PHONEPWD_LOGIN) != 0) {
            initPhonePwdService();
        }
        /** QQ登录*/
        if ((loginWay & Constants.QQ_LOGIN) != 0) {
            tlsService.initQQLoginService(this,
                    (Button) findViewById(MyResource.getIdByName(getApplication(), "id", "btn_qqlogin")));
        }

        /**微信登录*/
        if ((loginWay & Constants.WX_LOGIN) != 0) {
            tlsService.initWXLoginService(this,
                    (Button) findViewById(MyResource.getIdByName(getApplication(), "id", "btn_wxlogin")));
        }

        SharedPreferences settings = getSharedPreferences(Constants.TLS_SETTING, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(Constants.SETTING_LOGIN_WAY, Constants.PHONEPWD_LOGIN);
        editor.commit();
    }

    private void initPhonePwdService() {
        tlsService.initPhonePwdLoginService(this,
                (EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "selectCountryCode")),
                (EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "phone")),
                (EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "password")),
                (Button) findViewById(MyResource.getIdByName(getApplication(), "id", "btn_login"))
        );

        // 设置点击"注册新用户"事件
        findViewById(MyResource.getIdByName(getApplication(), "id", "registerNewUser"))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PhonePwdLoginActivity.this, PhonePwdRegisterActivity.class);
                        startActivityForResult(intent, SMS_REG_REQUEST);
                    }
                });

        // 设置点击"重置密码"事件
        findViewById(MyResource.getIdByName(getApplication(), "id", "resetPassword"))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PhonePwdLoginActivity.this, ResetPhonePwdActivity.class);
                        startActivityForResult(intent, SMS_RESET_REQUEST);
                    }
                });
    }

    /**
     * 应用调用Andriod_SDK接口时，使能成功接收到回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SMS_REG_REQUEST) {
            if (RESULT_OK == resultCode) {
                setResult(RESULT_OK, data);
                finish();
            }

        } else if (requestCode == SMS_RESET_REQUEST) {
            setResult(RESULT_OK, data);
            finish();
        } else {
            if (requestCode == com.tencent.connect.common.Constants.REQUEST_API) {
//                if (resultCode == com.tencent.connect.common.Constants.RESULT_LOGIN) {
//                    tlsService.onActivityResultForQQLogin(requestCode, requestCode, data);
//                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        // 判断是否是从微信登录界面返回的
        int wxLogin = intent.getIntExtra(Constants.EXTRA_WX_LOGIN, Constants.WX_LOGIN_NON);
        if (wxLogin != Constants.WX_LOGIN_NON) {
            if (wxLogin == Constants.WX_LOGIN_SUCCESS) {
                Intent data = new Intent();
                data.putExtra(Constants.EXTRA_LOGIN_WAY, Constants.WX_LOGIN);
                data.putExtra(Constants.EXTRA_WX_LOGIN, Constants.WX_LOGIN_SUCCESS);
                data.putExtra(Constants.EXTRA_WX_OPENID, intent.getStringExtra(Constants.EXTRA_WX_OPENID));
                data.putExtra(Constants.EXTRA_WX_ACCESS_TOKEN, intent.getStringExtra(Constants.EXTRA_WX_ACCESS_TOKEN));
                if (Constants.thirdappPackageNameSucc != null && Constants.thirdappClassNameSucc != null) {
                    data.setClassName(Constants.thirdappPackageNameSucc, Constants.thirdappClassNameSucc);
                    startActivity(data);
                } else {
                    setResult(RESULT_OK, data);
                }
                finish();
            }
            return;
        }

        /**判断是从注册界面还是重置密码界面返回*/
        int where = intent.getIntExtra(Constants.EXTRA_PHONEPWD_REG_RST, Constants.PHONEPWD_NON);
        countryCode= intent.getStringExtra(Constants.COUNTRY_CODE);
        phoneNumber = intent.getStringExtra(Constants.PHONE_NUMBER);
        /**从注册界面返回*/
        if (where == Constants.PHONEPWD_REGISTER) {

            if (countryCode != null && phoneNumber != null) {
                /**弹出填写密码的对话框*/
                setPassword(1);
            }
            return;
            /**从重置密码界面返回*/
        } else if (where == Constants.PHONEPWD_RESET) {

            if (countryCode != null && phoneNumber != null) {
                // 弹出填写密码的对话框
                setPassword(2);
            }

            return;
        }
    }

    public void setPassword(final int type) {

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(MyResource.getIdByName(getApplication(), "layout", "tencent_tls_ui_dialog"), null);

        final EditText editText = (EditText)view.findViewById(MyResource.getIdByName(getApplication(), "id", "password"));
        Button btnConfirm = (Button)view.findViewById(MyResource.getIdByName(getApplication(), "id", "btn_confirm"));
        Button btnCancel = (Button)view.findViewById(MyResource.getIdByName(getApplication(), "id", "btn_cancel"));

        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).setCancelable(false).create();

        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String regPassword = editText.getText().toString();
                if (regPassword.length() == 0) {
                    Util.showToast(PhonePwdLoginActivity.this, "密码不能为空");
                    return;
                }

                // 设置密码
                if (type == 1) {
                    tlsService.TLSPwdRegCommit(regPassword, new TLSPwdRegListener() {
                        @Override
                        public void OnPwdRegAskCodeSuccess(int reaskDuration, int expireDuration) {}

                        @Override
                        public void OnPwdRegReaskCodeSuccess(int reaskDuration, int expireDuration) {}

                        @Override
                        public void OnPwdRegVerifyCodeSuccess() {}

                        @Override
                        public void OnPwdRegCommitSuccess(TLSUserInfo userInfo) {
                            Util.showToast(PhonePwdLoginActivity.this, "注册成功");
                            ((EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "selectCountryCode"))).setText(countryCode);
                            ((EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "phone"))).setText(phoneNumber);
                            ((EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "password"))).setText(regPassword);

                            findViewById(MyResource.getIdByName(getApplication(), "id", "btn_login")).performClick();
                        }

                        @Override
                        public void OnPwdRegFail(TLSErrInfo errInfo) {
                            Util.notOK(PhonePwdLoginActivity.this, errInfo);
                        }

                        @Override
                        public void OnPwdRegTimeout(TLSErrInfo errInfo) {
                            Util.notOK(PhonePwdLoginActivity.this, errInfo);
                        }
                    });
                }

                // 重置密码
                if (type == Const.INTEGER_2) {
                    tlsService.TLSPwdResetCommit(regPassword, new TLSPwdResetListener() {
                        @Override
                        public void OnPwdResetAskCodeSuccess(int reaskDuration, int expireDuration) {}

                        @Override
                        public void OnPwdResetReaskCodeSuccess(int reaskDuration, int expireDuration) {}

                        @Override
                        public void OnPwdResetVerifyCodeSuccess() {}

                        @Override
                        public void OnPwdResetCommitSuccess(TLSUserInfo userInfo) {
                            Util.showToast(PhonePwdLoginActivity.this, "重置密码成功");
                            ((EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "selectCountryCode"))).setText(countryCode);
                            ((EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "phone"))).setText(phoneNumber);
                            ((EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "password"))).setText(regPassword);

                            findViewById(MyResource.getIdByName(getApplication(), "id", "btn_login")).performClick();
                        }

                        @Override
                        public void OnPwdResetFail(TLSErrInfo errInfo) {
                            Util.notOK(PhonePwdLoginActivity.this, errInfo);
                        }

                        @Override
                        public void OnPwdResetTimeout(TLSErrInfo errInfo) {
                            Util.notOK(PhonePwdLoginActivity.this, errInfo);
                        }
                    });
                }

                dialog.dismiss();
            }
        });

        showSoftInput(getApplicationContext());
    }

    private static void showSoftInput(final Context ctx) {

        ThreadPoolHelper.executeTaskDelay(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        },0,200,TimeUnit.MILLISECONDS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThreadPoolHelper.shutdown();
    }
}
