package com.yst.onecity.utils;

import android.text.TextUtils;

import com.yst.onecity.TianyiApplication;
import com.yst.onecity.bean.AppCommonBean;
import com.yst.onecity.utils.security.PreferenceUtil;


/**
 *  用户基本信息
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class AppCommonManager {

    public static AppCommonManager appCommonManager;
    public static String SPNAME = "HeadHunter";
    public static String SPUSER_ACCOUNT = "user_account";
    public static String SPUSER_PASSWORD = "user_password";
    public static String SPISLOGIN = "isLogin";
    public static String FIRSTINTO = "first";
    public static String SEARCHHISTORY = "searchhistory";
    public static String LOGINTYPE = "logintype";
    public static String PASSWORDLOGINTYPE = "0";
    public static String CODELOGINTYPE = "1";

    public static String SPUSER_UUID = "uuid";
    public static String SPUSER_HUNTER_ID = "hunter_id";
    public static String SPUSER_IM_ID = "im_id";
    public static String SPUSER_ID = "id";
    public static String SPUSER_IDENT = "ident";
    public static String SPUSER_IM_PASSWORD = "im_password";
    public static String SPUSER_IMSIGN = "ImSign";
    public static String SPUSER_NICKNAME = "nickname";
    public static String SPUSER_TOKEN = "token";
    public static String USER_TYPE = "userType";
    public static String ADDRESS = "address";
    public static String SPUSER_NEWYETAI = "newyetai";


    private AppCommonManager() {
        TianyiApplication.appCommonBean = new AppCommonBean();
    }

    public static AppCommonManager getInstance() {
        if (appCommonManager == null) {
            appCommonManager = new AppCommonManager();
        }
        return appCommonManager;
    }

    public void saveUserInfo(AppCommonBean appCommonBean) {
        if (!TextUtils.isEmpty(appCommonBean.getPhone())) {
            PreferenceUtil.put(SPUSER_ACCOUNT, appCommonBean.getPhone());
            TianyiApplication.appCommonBean.setPhone(appCommonBean.getPhone());
        }
        if (!TextUtils.isEmpty(appCommonBean.getPassword())) {
            PreferenceUtil.put(SPUSER_PASSWORD, appCommonBean.getPassword());
            TianyiApplication.appCommonBean.setPassword(appCommonBean.getPassword());
        }
        PreferenceUtil.put(SPUSER_IDENT, appCommonBean.getIdent());
        TianyiApplication.appCommonBean.setId(appCommonBean.getId());
        TianyiApplication.appCommonBean.setIdent(appCommonBean.getIdent());
        TianyiApplication.appCommonBean.setHunter_id(appCommonBean.getHunter_id());
        TianyiApplication.appCommonBean.setIm_id(appCommonBean.getIm_id());
        TianyiApplication.appCommonBean.setIm_password(appCommonBean.getIm_password());
        TianyiApplication.appCommonBean.setAddress(appCommonBean.getAddress());
        TianyiApplication.appCommonBean.setNickname(appCommonBean.getNickname());
        TianyiApplication.appCommonBean.setUuid(appCommonBean.getUuid());
        TianyiApplication.appCommonBean.setName(appCommonBean.getName());
        TianyiApplication.appCommonBean.setCard(appCommonBean.getCard());
        TianyiApplication.appCommonBean.setImSign(appCommonBean.getImSign());
        TianyiApplication.appCommonBean.setToken(appCommonBean.getToken());
        TianyiApplication.appCommonBean.setUserType(appCommonBean.getUserType());
        TianyiApplication.appCommonBean.setIsNewYeTai(appCommonBean.getIsNewYeTai());
        TianyiApplication.appCommonBean.setIsCertification(appCommonBean.getIsCertification());
    }

    public void saveCodeLoginInfo(AppCommonBean appCommonBean) {
        PreferenceUtil.put(SPUSER_UUID, appCommonBean.getUuid());
        PreferenceUtil.put(USER_TYPE, appCommonBean.getUserType());
        PreferenceUtil.put(SPUSER_HUNTER_ID, appCommonBean.getHunter_id());
        PreferenceUtil.put(SPUSER_IM_ID, appCommonBean.getIm_id());
        PreferenceUtil.put(SPUSER_ID, appCommonBean.getId());
        PreferenceUtil.put(SPUSER_IDENT, appCommonBean.getIdent());
        PreferenceUtil.put(SPUSER_IM_PASSWORD, appCommonBean.getIm_password());
        PreferenceUtil.put(SPUSER_IMSIGN, appCommonBean.getImSign());
        PreferenceUtil.put(SPUSER_NICKNAME, appCommonBean.getNickname());
        PreferenceUtil.put(SPUSER_TOKEN, appCommonBean.getToken());
        PreferenceUtil.put(SPUSER_NEWYETAI, appCommonBean.getIsNewYeTai());
    }

    public void setLoginStaus() {
        PreferenceUtil.put(SPISLOGIN, true);
    }

    public void setLoginType(String type) {
        PreferenceUtil.put(LOGINTYPE, type);
    }

    public void setLoginStausFalse() {
        PreferenceUtil.put(SPISLOGIN, false);
    }

    public void saveState() {
        PreferenceUtil.put(FIRSTINTO, false);
    }

    public void quitLogin() {
        TianyiApplication.appCommonBean.setId("");
        TianyiApplication.appCommonBean.setIdent("");
        TianyiApplication.appCommonBean.setHunter_id("");
        TianyiApplication.appCommonBean.setIm_id("");
        TianyiApplication.appCommonBean.setIm_password("");
        TianyiApplication.appCommonBean.setAddress("");
        TianyiApplication.appCommonBean.setNickname("");
        TianyiApplication.appCommonBean.setUuid("");
        TianyiApplication.appCommonBean.setToken("");
        TianyiApplication.appCommonBean.setUserType(-1);
        TianyiApplication.appCommonBean.setIsNewYeTai("");
        PreferenceUtil.put(SPISLOGIN, false);
        TianyiApplication.isLogin = false;
    }
}
