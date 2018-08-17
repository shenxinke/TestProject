package com.tencent.qcloud.tlslibrary.service;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tencent.qcloud.tlslibrary.helper.Util;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSStrAccRegListener;
import tencent.tls.platform.TLSUserInfo;

/**
 * @author dgy
 * @version 1.0.0
 * @date 15/8/13
 */
public class AccountRegisterService {

    private final static String TAG = "AccountRegisterService";

    private Context context;
    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtRepassword;
    private Button btnRegister;

    private TLSService tlsService;
    private StrAccRegListener strAccRegListener;

    private String username;
    private String password;
    private int eight = 8;

    public AccountRegisterService(Context context,
                               EditText txtUsername,
                               EditText txtPassword,
                               EditText txtRepassword,
                               Button btnRegister) {
        this.context = context;
        this.txtUsername = txtUsername;
        this.txtPassword = txtPassword;
        this.txtRepassword = txtRepassword;
        this.btnRegister = btnRegister;

        tlsService = TLSService.getInstance();
        strAccRegListener = new StrAccRegListener();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = AccountRegisterService.this.txtUsername.getText().toString();
                password = AccountRegisterService.this.txtPassword.getText().toString();
                String tmp = AccountRegisterService.this.txtRepassword.getText().toString();

                if (username.length() == 0 || password.length() == 0 || tmp.length() == 0) {
                    Util.showToast(AccountRegisterService.this.context, "用户名密码不能为空");
                    return;
                }

                if (!password.equals(tmp)) {
                    Util.showToast(AccountRegisterService.this.context, "两次输入的密码不一致");
                    return;
                }

                if (password.length() < eight) {
                    Util.showToast(AccountRegisterService.this.context, "密码的长度不能小于8个字符");
                }

                int result = tlsService.TLSStrAccReg(username, password, strAccRegListener);
                if (result == TLSErrInfo.INPUT_INVALID) {
                    Util.showToast(AccountRegisterService.this.context, "帐号不合法");
                }
            }
        });
    }

    class StrAccRegListener implements TLSStrAccRegListener {
        @Override
        public void OnStrAccRegSuccess(TLSUserInfo userInfo) {
            Util.showToast(context, "成功注册 " + userInfo.identifier);
            TLSService.setLastErrno(0);
            StrAccountLogin login = new StrAccountLogin(context);
            login.doStrAccountLogin(username, password);
        }

        @Override
        public void OnStrAccRegFail(TLSErrInfo errInfo) {
            Util.notOK(context, errInfo);
        }

        @Override
        public void OnStrAccRegTimeout(TLSErrInfo errInfo) {
            Util.notOK(context, errInfo);
        }
    }
}
