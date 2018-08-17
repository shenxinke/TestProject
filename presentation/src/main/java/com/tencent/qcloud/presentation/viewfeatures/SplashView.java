package com.tencent.qcloud.presentation.viewfeatures;

/**
 * 闪屏界面控制接口
 *
 * @author wangjingwei
 * @version 1.0.0
 * @date 18/04/11
 */
public interface SplashView extends MvpView {

    /**
     * 跳转到主界面
     */
    void navToHome();


    /**
     * 跳转到登录界面
     */
    void navToLogin();

    /**
     * 是否已有用户登录
     * @return
     */
    boolean isUserLogin();

}
