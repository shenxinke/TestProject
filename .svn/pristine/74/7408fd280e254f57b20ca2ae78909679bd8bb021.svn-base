package com.yst.onecity.activity.chat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.TIMCallBack;
import com.tencent.TIMLogLevel;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushToken;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.presentation.presenter.SplashPresenter;
import com.tencent.qcloud.presentation.viewfeatures.SplashView;
import com.tencent.qcloud.tlslibrary.activity.HostLoginActivity;
import com.tencent.qcloud.tlslibrary.service.TLSService;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.tencent.qcloud.ui.NotifyDialog;
import com.yst.onecity.R;
import com.yst.onecity.activity.MainActivity;
import com.yst.onecity.bean.chatmodel.UserInfo;
import com.yst.onecity.utils.MyLog;

/**
 * 腾讯IM自带的启动页面（目前没有使用到）
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/07/06
 */
public class SplashActivity extends AppCompatActivity implements SplashView,TIMCallBack {

    SplashPresenter presenter;
    private int loginResultCode = 100;
    private int googlePlayResultCode = 200;
    private final int requestPhonePermissions = 0;
    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout_item.activity_splash);
        init();
    }

    @Override
    public void onError(int i, String s) {
        switch (i) {
            case 6208:
                //离线状态下被其他终端踢下线
                NotifyDialog dialog = new NotifyDialog();
                dialog.show(getString(R.string.kick_logout), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        navToHome();
                    }
                });
                break;
            case 6200:
                Toast.makeText(this,getString(R.string.login_error_timeout),Toast.LENGTH_SHORT).show();
                navToLogin();
                break;
            default:
                Toast.makeText(this,getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                navToLogin();
                break;
        }
    }

    @Override
    public void onSuccess() {
        ///初始化程序后台后消息推送
//        PushUtil.getInstance();
        //初始化消息监听
        MessageEvent.getInstance();
        String deviceMan = android.os.Build.MANUFACTURER;
        ///注册小米和华为推送
//        if (deviceMan.equals("Xiaomi") && shouldMiInit()){
//            MiPushClient.registerPush(getApplicationContext(), "2882303761517480335", "5411748055335");
//        }else if (deviceMan.equals("HUAWEI")){
//            PushManager.requestToken(this);
//        }
//        LoggerInterface newLogger = new LoggerInterface() {
//            @Override
//            public void setTag(String tag) {
//                // ignore
//            }
//            @Override
//            public void log(String content, Throwable t) {
//                MyLog.d(TAG, content, t);
//            }
//            @Override
//            public void log(String content) {
//                MyLog.d(TAG, content);
//            }
//        };
//        Logger.setLogger(this, newLogger);

//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        String refreshedToken = "";
        MyLog.d(TAG, "refreshed token: " + refreshedToken);

        if(!TextUtils.isEmpty(refreshedToken)) {
            TIMOfflinePushToken param = new TIMOfflinePushToken();
            param.setToken(refreshedToken);
            param.setBussid(169);
            TIMManager.getInstance().setOfflinePushToken(param);
        }
///        MiPushClient.clearNotification(getApplicationContext());
        MyLog.d(TAG, "imsdk env " + TIMManager.getInstance().getEnv());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void navToHome() {
        FriendshipEvent.getInstance().init();
        GroupEvent.getInstance().init();
        LoginBusiness.loginIm(UserInfo.getInstance().getId(), UserInfo.getInstance().getUserSig(), this);
    }

    @Override
    public void navToLogin() {
        Intent intent = new Intent(getApplicationContext(), HostLoginActivity.class);
        startActivityForResult(intent, loginResultCode);
    }

    @Override
    public boolean isUserLogin() {
        return UserInfo.getInstance().getId()!= null && (!TLSService.getInstance().needLogin(UserInfo.getInstance().getId()));
    }

    private void init(){
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        int loglvl = pref.getInt("loglvl", TIMLogLevel.DEBUG.ordinal());
        //初始化IMSDK
        InitBusiness.start(getApplicationContext(),loglvl);
        //初始化TLS
        TlsBusiness.init(getApplicationContext());
        //设置刷新监听
        RefreshEvent.getInstance();
        String id =  TLSService.getInstance().getLastUserIdentifier();
        UserInfo.getInstance().setId(id);
        UserInfo.getInstance().setUserSig(TLSService.getInstance().getUserSig(id));

        MyLog.e("sss", "id is === " + id);

        presenter = new SplashPresenter(this);
        presenter.start();
    }
}
