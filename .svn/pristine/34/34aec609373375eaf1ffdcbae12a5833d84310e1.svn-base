package com.yst.onecity.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.tencent.TIMManager;
import com.tencent.TIMUserStatusListener;
import com.umeng.analytics.MobclickAgent;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.MainActivity;
import com.yst.onecity.activity.VideoPlayerActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.config.Const;
import com.yst.onecity.dialog.CommonProgressDialog;
import com.yst.onecity.utils.AppCommonManager;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.WindowUtils;
import com.yst.onecity.view.MyProcessDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * activity基类
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Context context;
    private LinearLayout llBack;
    protected TextView tvMainTitle;
    protected TextView tvRight;
    private List<View> views;
    /**
     * 显示界面等待条
     */
    private MyProcessDialog mInfoProgressDialog;
    private CommonProgressDialog onLineDialog;
    /**
     * 登出时弹出取消对话框
     */
    private PopupWindow mCancelPopWindow;
    public ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //所有都为竖屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mImmersionBar = ImmersionBar.with(this);
        if (VideoPlayerActivity.class.isInstance(this)) {
            if (Build.VERSION.SDK_INT < Const.INTEGER_23) {
                mImmersionBar.statusBarDarkFont(true).statusBarColor(R.color.color_99000000).barAlpha(0.4f).init();
            } else if (Build.VERSION.SDK_INT >= Const.INTEGER_23) {
                mImmersionBar.statusBarDarkFont(true).statusBarColor(R.color.color_99000000).barAlpha(0.4f).init();
            }
        }
        else {
            if (Build.VERSION.SDK_INT < Const.INTEGER_23) {
                mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.white).statusBarAlpha(0.2f).init();
            } else if (Build.VERSION.SDK_INT >= Const.INTEGER_23) {
                mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();
            }
        }

        TianyiApplication.instance.addActivity(this);
        context = this;
        views = new ArrayList<>();
        views.add(new EditText(context));

        setContentView(bindLayout());
        ButterKnife.bind(this);
        initData();
        initControll();
        setListener();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(context);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(context);
    }

    /**
     * 布局id
     *
     * @return layout布局
     */
    public abstract int bindLayout();

    /**
     * 初始化页面数据
     */
    public abstract void initData();

    public void initControll() {
    }

    public void setListener() {
    }

    protected void setTitleBarTitle(String title) {
        if (tvMainTitle != null) {
            tvMainTitle.setText(title);
        } else {
            tvMainTitle = (TextView) findViewById(R.id.tv_main_title);
            tvMainTitle.setText(title);
        }
    }

    protected void initTitleBar(String title) {
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        tvMainTitle = (TextView) findViewById(R.id.tv_main_title);
        tvRight = (TextView) findViewById(R.id.tv_right);
        llBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitleBarTitle(title);
    }

    protected void setRightTextVisibility(int visible) {
        if (tvRight != null) {
            tvRight.setVisibility(visible);
        } else {
            tvRight = (TextView) findViewById(R.id.tv_right);
            tvRight.setVisibility(visible);
        }
    }

    protected void setRightText(String title) {
        setRightTextVisibility(View.VISIBLE);
        if (tvRight != null) {
            tvRight.setText(title);
        } else {
            tvRight = (TextView) findViewById(R.id.tv_right);
            tvRight.setText(title);
        }
    }

    protected void setRightText(String title, boolean isEnable) {
        if (tvRight != null) {
            tvRight.setText(title);
        } else {
            tvRight = (TextView) findViewById(R.id.tv_right);
            tvRight.setText(title);
        }
        tvRight.setEnabled(isEnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TianyiApplication.activitys.remove(this);
        views.removeAll(views);
        views = null;
        context = null;
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }

    }

    public void showInfoProgressDialog() {
        if (mInfoProgressDialog == null) {
            mInfoProgressDialog = new MyProcessDialog(this);
        }
        if (!this.isFinishing()) {
            try {
                mInfoProgressDialog.show();
            } catch (Exception e) {

            }
        }
    }

    /**
     * 关闭进度条
     */
    public void dismissInfoProgressDialog() {
        if (mInfoProgressDialog != null) {
            mInfoProgressDialog.dismiss();
        }
    }

    /**
     * 显示界面等待条
     */
    public void showProgressDialog() {
        if (null == onLineDialog) {
            onLineDialog = CommonProgressDialog.showDialog(BaseActivity.this);
        }
        if (!BaseActivity.this.isFinishing()) {
            try {
                onLineDialog.show();
            } catch (Exception e) {

            }
        }
    }

    /**
     * 关闭进度条
     */
    public void dismissProgressDialog() {
        if (null != onLineDialog) {
            onLineDialog.dismiss();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        WindowUtils.hideInputWhenTouchOtherView(this, ev, views);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 这个函数在Activity创建完成之后会调用。购物车悬浮窗需要依附在Activity上，如果Activity还没有完全建好就去
     * 调用showCartFloatView()，则会抛出异常
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
                @Override
                public void onForceOffline() {
                    MyLog.e("mainactivity", "onForceOffline onForceOffline === ");
                    //被踢下线
                    showCancelDialog();
                    TianyiApplication.mAppCommonManager.quitLogin();
                    TianyiApplication.mAppCommonManager.setLoginType(AppCommonManager.PASSWORDLOGINTYPE);
                    TIMManager.getInstance().logout();
                }

                @Override
                public void onUserSigExpired() {
                    //票据过期，需要换票后重新登录
                    MyLog.e("mainactivity", "onUserSigExpired onUserSigExpired === ");
                }
            });
        }
    }

    /**
     * 退出提示框
     */
    private void showCancelDialog() {
        final View mCancelView = getLayoutInflater().inflate(R.layout.activity_logout_alert_view, null);
        mCancelPopWindow = new PopupWindow(mCancelView, RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT, true);
        TextView mLogoutTv = (TextView) mCancelView.findViewById(R.id.activity_logout_cancel_tv);
        TextView mCancelTv = (TextView) mCancelView.findViewById(R.id.activity_logout_tv);
        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleMyPopWindow();
            }
        });

        // 退出确定按钮
        mLogoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserLogoutMyPopWindow();
            }
        });
        mCancelPopWindow.setAnimationStyle(R.style.AnimTop2);
        mCancelPopWindow.update();
        // 设置可点击
        mCancelPopWindow.setTouchable(true);
        // 设置popupwindow外部可点击
        mCancelPopWindow.setOutsideTouchable(true);
        // 如果有edittext，阻挡输入法遮挡
        mCancelPopWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mCancelPopWindow.showAtLocation(mCancelView, Gravity.CENTER, 0, 0);
        mCancelPopWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mCancelPopWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 关闭弹框提示，并跳转到登录页面
     */
    private void cancleMyPopWindow() {
        if (mCancelPopWindow != null && mCancelPopWindow.isShowing()) {
            mCancelPopWindow.dismiss();
        }
        TianyiApplication.mAppCommonManager.quitLogin();
        TIMManager.getInstance().logout();
        Intent intent = new Intent(MainActivity.ChangePage);
        LocalBroadcastManager.getInstance(BaseActivity.this).sendBroadcast(intent);
        Bundle bundle = new Bundle();
        bundle.putString("register", "register");
        JumpIntent.jump(BaseActivity.this, LoginActivity.class, bundle);
    }


    /**
     * 关闭弹框提示，并跳转到登录页面
     */
    private void mUserLogoutMyPopWindow() {
        if (mCancelPopWindow != null && mCancelPopWindow.isShowing()) {
            mCancelPopWindow.dismiss();
        }
        TianyiApplication.mAppCommonManager.quitLogin();
        TIMManager.getInstance().logout();
        Intent intent = new Intent(MainActivity.ChangePage);
        LocalBroadcastManager.getInstance(BaseActivity.this).sendBroadcast(intent);
        Bundle bundle = new Bundle();
        bundle.putString("register", "register");
        JumpIntent.jump(BaseActivity.this, MainActivity.class, bundle);
    }
}
