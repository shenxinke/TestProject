package com.yst.onecity.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;
import com.yst.onecity.R;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.security.PreferenceUtil;

/**
 * 启动页
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public class SplashActivity extends AppCompatActivity {
    /**
     * 默认是第一次打开程序
     */
    private boolean isFirst = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT < Const.INTEGER_23) {
            ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.white).statusBarAlpha(0.2f).init();
        } else if (Build.VERSION.SDK_INT >= Const.INTEGER_23) {
            ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();
        }
        setContentView(R.layout.activity_splash);

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isFirst = PreferenceUtil.getBoolean("isFrist", true);
                if (isFirst) {
                    JumpIntent.jump(SplashActivity.this, GuideActivity.class);
                } else {
                    JumpIntent.jump(SplashActivity.this, MainActivity.class);
                }
                SplashActivity.this.finish();
            }
        }, 2500);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
