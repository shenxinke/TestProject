package com.tencent.qcloud.tlslibrary.service;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * @author wangjingwei
 * @version 1.0.0
 * @date 18/04/11
 */
public class WXLoginService {
    private IWXAPI iwxapi;
    private Context context;

    public WXLoginService(Context context, Button btnWxlogin) {
        this.context = context;

        btnWxlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wxLogin();
            }
        });
    }

    private void wxLogin() {
        iwxapi = WXAPIFactory.createWXAPI(this.context, TLSConfiguration.WX_APP_ID, true);
        iwxapi.registerApp(TLSConfiguration.WX_APP_ID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "tencent_tls_ui_wxlogin";
        iwxapi.sendReq(req);
    }
}
