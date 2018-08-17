package com.tencent.qcloud.tlslibrary.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tencent.qcloud.tlslibrary.helper.MyResource;
import com.tencent.qcloud.tlslibrary.helper.SmsContentObserver;
import com.tencent.qcloud.tlslibrary.service.TLSService;

/**
 * 手机号、密码注册
 *
 * @author lixiangchao
 * @date 2018-03-23
 * @version 4.0.0
 */
public class PhonePwdRegisterActivity extends Activity {

    private final static String TAG = "PhonePwdRegisterActivity";

    private TLSService tlsService;
    private SmsContentObserver smsContentObserver = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(MyResource.getIdByName(getApplication(), "layout", "tencent_tls_ui_activity_phone_pwd_register"));

        // 设置返回按钮
        findViewById(MyResource.getIdByName(getApplication(), "id", "btn_back"))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PhonePwdRegisterActivity.this.onBackPressed();
                    }
                });

        tlsService = TLSService.getInstance();
        tlsService.initPhonePwdRegisterService(this,
                (EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "selectCountryCode")),
                (EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "phone")),
                (EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "txt_checkcode")),
                (Button) findViewById(MyResource.getIdByName(getApplication(), "id", "btn_requirecheckcode")),
                (Button) findViewById(MyResource.getIdByName(getApplication(), "id", "btn_verify"))
        );

/*        smsContentObserver = new SmsContentObserver(new Handler(),
                this,
                (EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "txt_checkcode")),
                Constants.PHONEPWD_REGISTER_SENDER);

        //注册短信变化监听
        this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsContentObserver);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsContentObserver != null) {
            this.getContentResolver().unregisterContentObserver(smsContentObserver);
        }
    }
}
