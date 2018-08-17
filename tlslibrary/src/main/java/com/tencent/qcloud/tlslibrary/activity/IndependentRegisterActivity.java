package com.tencent.qcloud.tlslibrary.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tencent.qcloud.tlslibrary.helper.MyResource;
import com.tencent.qcloud.tlslibrary.service.TLSService;

/**
 * @author wangjingwei
 * @version 1.0.0
 * @date 18/04/11
 */
public class IndependentRegisterActivity extends Activity {

    public final static String TAG = "IndependentRegisterActivity";
    private TLSService tlsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(MyResource.getIdByName(getApplication(), "layout", "tencent_tls_ui_activity_independent_register"));

        tlsService = TLSService.getInstance();
        tlsService.initAccountRegisterService(this,
                (EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "username")),
                (EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "password")),
                (EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "repassword")),
                (Button) findViewById(MyResource.getIdByName(getApplication(), "id", "btn_register"))
        );

        // 设置返回按钮
        findViewById(MyResource.getIdByName(getApplication(), "id", "returnIndependentLoginActivity"))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IndependentRegisterActivity.this.onBackPressed();
                    }
                });
    }
}
