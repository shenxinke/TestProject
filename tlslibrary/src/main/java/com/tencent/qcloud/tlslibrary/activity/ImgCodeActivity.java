package com.tencent.qcloud.tlslibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.tencent.qcloud.tlslibrary.helper.MyResource;
import com.tencent.qcloud.tlslibrary.service.AccountLoginService;
import com.tencent.qcloud.tlslibrary.service.Constants;
import com.tencent.qcloud.tlslibrary.service.PhonePwdLoginService;
import com.tencent.qcloud.tlslibrary.service.TLSService;

/**
 * @author wangjingwei
 * @version 1.0.0
 * @date 18/04/11
 */
public class ImgCodeActivity extends Activity implements View.OnClickListener{

    private final static String TAG = "ImgCodeActivity";
    private static ImageView imgcodeView;

    private EditText imgcodeEdit;
    private TLSService tlsService;
    private int loginWay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(MyResource.getIdByName(getApplication(), "layout", "tencent_tls_ui_activity_img_code"));

        tlsService = TLSService.getInstance();

        imgcodeEdit = (EditText) findViewById(MyResource.getIdByName(getApplication(), "id", "txt_checkcode"));
        imgcodeView = (ImageView) findViewById(MyResource.getIdByName(getApplication(), "id", "imagecode"));
        imgcodeView.setOnClickListener(this);

        Intent intent = getIntent();
        byte[] picData = intent.getByteArrayExtra(Constants.EXTRA_IMG_CHECKCODE);
        loginWay = intent.getIntExtra(Constants.EXTRA_LOGIN_WAY, Constants.NON_LOGIN);

        fillImageview(picData);
        findViewById(MyResource.getIdByName(getApplication(), "id", "btn_verify")).setOnClickListener(this);
        findViewById(MyResource.getIdByName(getApplication(), "id", "btn_cancel")).setOnClickListener(this);
        findViewById(MyResource.getIdByName(getApplication(), "id", "refreshImageCode")).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String id = "id";
        String btnVerify = "btn_verify";
        String imagecode = "imagecode";
        String btnCancel = "btn_cancel";
        if (v.getId() == MyResource.getIdByName(getApplication(), id, btnVerify)) {
            String imgcode = imgcodeEdit.getText().toString();
            if (loginWay == Constants.PHONEPWD_LOGIN) {
                tlsService.TLSPwdLoginVerifyImgcode(imgcode, PhonePwdLoginService.pwdLoginListener);
            } else if (loginWay == Constants.USRPWD_LOGIN) {
                tlsService.TLSPwdLoginVerifyImgcode(imgcode, AccountLoginService.pwdLoginListener);
            }
            finish();
        } else if (v.getId() == MyResource.getIdByName(getApplication(), id, imagecode)
                || v.getId() == MyResource.getIdByName(getApplication(), id, "refreshImageCode")) {
            // 刷新验证码
            tlsService.TLSPwdLoginReaskImgcode(AccountLoginService.pwdLoginListener);
        }
        if (v.getId() == MyResource.getIdByName(getApplication(), id, btnCancel)) {
            finish();
        }
    }

    public static void fillImageview(byte[] picData) {
        if (picData == null) {
            return;
        }
        Bitmap bm = BitmapFactory.decodeByteArray(picData, 0, picData.length);
        Log.e(TAG, "w " + bm.getWidth() + ", h " + bm.getHeight());
        imgcodeView.setImageBitmap(bm);
    }
}
