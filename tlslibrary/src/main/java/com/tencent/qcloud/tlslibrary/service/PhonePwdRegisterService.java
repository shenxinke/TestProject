package com.tencent.qcloud.tlslibrary.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tencent.qcloud.tlslibrary.activity.PhonePwdLoginActivity;
import com.tencent.qcloud.tlslibrary.helper.Util;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSPwdRegListener;
import tencent.tls.platform.TLSUserInfo;

/**
 * @author wangjingwei
 * @version 1.0.0
 * @date 18/04/11
 */
public class PhonePwdRegisterService {

    private final static String TAG = "PhonePwdRegisterService";

    private Context context;
    private EditText txtCountryCode;
    private EditText txtPhoneNumber;
    private EditText txtCheckCode;
    private Button btnRequireCheckCode;
    private Button btnVerify;

    private String countryCode;
    private String phoneNumber;
    private String checkCode;

    private PwdRegListener pwdRegListener;
    private TLSService tlsService;

    public PhonePwdRegisterService(Context context,
                                   EditText txtCountryCode,
                                   EditText txtPhoneNumber,
                                   EditText txtCheckCode,
                                   Button btnRequireCheckCode,
                                   Button btnVerify) {
        this.context = context;
        this.txtCountryCode = txtCountryCode;
        this.txtPhoneNumber = txtPhoneNumber;
        this.txtCheckCode = txtCheckCode;
        this.btnRequireCheckCode = btnRequireCheckCode;
        this.btnVerify = btnVerify;

        tlsService = TLSService.getInstance();
        pwdRegListener = new PwdRegListener();

        btnRequireCheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryCode = PhonePwdRegisterService.this.txtCountryCode.getText().toString();
                // 解析国家码
                countryCode = countryCode.substring(countryCode.indexOf('+') + 1);
                phoneNumber = PhonePwdRegisterService.this.txtPhoneNumber.getText().toString();

                if (!Util.validPhoneNumber(countryCode, phoneNumber)) {
                    Util.showToast(PhonePwdRegisterService.this.context, "请输入有效的手机号");
                    return;
                }

                Log.e(TAG, Util.getWellFormatMobile(countryCode, phoneNumber));

                tlsService.TLSPwdRegAskCode(countryCode, phoneNumber, pwdRegListener);
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryCode = PhonePwdRegisterService.this.txtCountryCode.getText().toString();
                // 解析国家码
                countryCode = countryCode.substring(countryCode.indexOf('+') + 1);
                phoneNumber = PhonePwdRegisterService.this.txtPhoneNumber.getText().toString();
                checkCode = PhonePwdRegisterService.this.txtCheckCode.getText().toString();

                if (!Util.validPhoneNumber(countryCode, phoneNumber)) {
                    Util.showToast(PhonePwdRegisterService.this.context, "请输入有效的手机号");
                    return;
                }

                if (checkCode.length() == 0) {
                    Util.showToast(PhonePwdRegisterService.this.context, "请输入验证码");
                    return;
                }

                Log.e(TAG, Util.getWellFormatMobile(countryCode, phoneNumber));

                tlsService.TLSPwdRegVerifyCode(checkCode, pwdRegListener);
            }
        });
    }

    public class PwdRegListener implements TLSPwdRegListener {
        @Override
        public void OnPwdRegAskCodeSuccess(int reaskDuration, int expireDuration) {
            Util.showToast(context, "请求下发短信成功,验证码" + expireDuration / 60 + "分钟内有效");

            // 在获取验证码按钮上显示重新获取验证码的时间间隔
            Util.startTimer(btnRequireCheckCode, "获取验证码", "重新获取", reaskDuration, 1);
        }

        @Override
        public void OnPwdRegReaskCodeSuccess(int reaskDuration, int expireDuration) {
            Util.showToast(context, "注册短信重新下发,验证码" + expireDuration / 60 + "分钟内有效");
            Util.startTimer(btnRequireCheckCode, "获取验证码", "重新获取", reaskDuration, 1);
        }

        @Override
        public void OnPwdRegVerifyCodeSuccess() {
            Util.showToast(context, "注册验证通过，准备获取号码");
            Intent intent = new Intent(context, PhonePwdLoginActivity.class);
            intent.putExtra(Constants.EXTRA_PHONEPWD_REG_RST, Constants.PHONEPWD_REGISTER);
            intent.putExtra(Constants.COUNTRY_CODE, txtCountryCode.getText().toString());
            intent.putExtra(Constants.PHONE_NUMBER, txtPhoneNumber.getText().toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            context.startActivity(intent);
            ((Activity)context).finish();
        }

        @Override
        public void OnPwdRegCommitSuccess(TLSUserInfo userInfo) {}

        @Override
        public void OnPwdRegFail(TLSErrInfo errInfo) {
            Util.notOK(context, errInfo);
        }

        @Override
        public void OnPwdRegTimeout(TLSErrInfo errInfo) {
            Util.notOK(context, errInfo);
        }
    }
}
