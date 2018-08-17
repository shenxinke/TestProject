package com.yst.onecity.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMUser;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.sdk.Constant;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.AppCommonBean;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.LoginBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.dialog.AbstractVersionCheckDialog;
import com.yst.onecity.dialog.TipsDialog;
import com.yst.onecity.eventbus.GoMallEvent;
import com.yst.onecity.eventbus.GoServermberEvent;
import com.yst.onecity.eventbus.UpdateEvent;
import com.yst.onecity.fragment.CartFragment;
import com.yst.onecity.fragment.HomeFragment;
import com.yst.onecity.fragment.MyFragment;
import com.yst.onecity.fragment.PromotionDivisionFragment;
import com.yst.onecity.fragment.ShoppingMallFragment;
import com.yst.onecity.fragment.mall.MallFragment;
import com.yst.onecity.service.DownloadService;
import com.yst.onecity.utils.AppCommonManager;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.FileUtil;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.PermissionUtil;
import com.yst.onecity.utils.RxCode;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.utils.WindowUtils;
import com.yst.onecity.utils.security.PreferenceUtil;
import com.yst.onecity.view.FlowCheckLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;
import gorden.rxbus2.RxBus;
import gorden.rxbus2.ThreadMode;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Request;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 主页面
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/12/18
 */
public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindArray(R.array.main_tab_name_arr)
    String[] tabNameStrArr;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.flowlayout)
    FlowCheckLayout flowlayout;
    @BindView(R.id.llayout_fragmentRoot)
    FrameLayout llayoutFragmentRoot;
    @BindView(R.id.img_home)
    ImageView imgHome;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.image_pro)
    ImageView imagePro;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.ll_tuiguangshi)
    LinearLayout llTuiguangshi;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.tv_near)
    TextView tvNear;
    @BindView(R.id.ll_shopcar)
    LinearLayout llShopcar;
    @BindView(R.id.img_mine)
    ImageView imgMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    @BindView(R.id.ll_mine)
    LinearLayout llMine;
    @BindView(R.id.rightTitleTV)
    TextView rightTitleTV;
    @BindView(R.id.main_activity_bottom_layout)
    LinearLayout mainActivityBottomLayout;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.image_mall)
    ImageView imageMall;
    @BindView(R.id.tv_mall)
    TextView tvMall;
    @BindView(R.id.ll_mall)
    LinearLayout llMall;
    /**
     * 页面标签的标志位
     */
    private int tagPosition = 0;
    /**
     * 页面跳转控制器
     */
    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;
    private PromotionDivisionFragment promotionDivisionFragment;
    private CartFragment cartFragment;
    private MyFragment myFragment;
    private OnRightItemCheckListener rightItemCheckListener;
    /**
     * 退出登录的时间
     */
    private long time = 0;
    public static boolean isCart = false;
    private String phone = "";
    private boolean isLogin;
    private String loginType = "";
    private String password = "";
    public static String ChangePage = "android.intent.action.SELECT_BROADCAST";
    LocalBroadcastManager broadcastManager;
    BroadcastReceiver mItemViewListClickReceiver;
    /**
     * 进度数
     */
    public static int loadingProgress;
    private File file;
    private Handler mHandler = new Handler();
    /**
     * 版本更新显示界面
     */
    private TipsDialog tipsDialog;
    /**
     * 更新时用到的进度条等
     */
    private ProgressBar progressBar;
    /**
     * 显示进度的view
     */
    private TextView progressText;

    private MallFragment mallFragment;

    private ShoppingMallFragment shoppingMallFragment;

    private String writePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void initData() {
        RxBus.get().register(this);
        getNewVersionApk();
        EventBus.getDefault().register(this);
        fragmentManager = getSupportFragmentManager();
        setTabSelection(tagPosition);
        initDrawLayout();
        flowlayout.setOnItemListener(new FlowCheckLayout.OnItemClickListener() {
            @Override
            public void onItemClick(String str, int pos) {
                drawerLayout.closeDrawer(Gravity.RIGHT);
                rightItemCheckListener.onItemCheck(pos);
            }
        });
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ChangePage);
        mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case "android.intent.action.SELECT_BROADCAST":
                        MyLog.i("User", "Action = " + intent.getAction());
                        setTabSelection(0);
                        break;
                    default:
                        break;
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }

    @Subscribe
    public void onEventMainThread(GoServermberEvent goServermberEvent) {
        setTabSelection(1);
    }

    @Subscribe
    public void onEventCartThread(GoMallEvent goMallEvent) {
        setTabSelection(1);
    }

    /**
     * 设置当前选中的标签状态和对应的内容展示
     *
     * @param position 位置
     */
    private void setTabSelection(int position) {
        // TODO Auto-generated method stub
        this.tagPosition = position;
        // 更改底部导航栏按钮状态
        FragmentTransaction ftn = fragmentManager.beginTransaction();
        // 想要显示一个fragment,先隐藏所有fragment，防止重叠
        hideFragments(ftn);
        refresh();
        switch (tagPosition) {
            case 0:
                MyLog.e("sss", "homeFragment is null ===" + (homeFragment == null));
                imgHome.setSelected(true);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    ftn.add(R.id.llayout_fragmentRoot, homeFragment, "HomeFragment");
                } else {
                    ftn.show(homeFragment);
                }
                break;
            case 1:
                imageMall.setSelected(true);
                if (shoppingMallFragment == null) {
                    shoppingMallFragment = new ShoppingMallFragment();
                    ftn.add(R.id.llayout_fragmentRoot, shoppingMallFragment, "shoppingMallFragment");
                } else {
                    ftn.show(shoppingMallFragment);
                }
                break;
            case 2:
                MyLog.e("sss", "cartFragment is null ===" + (cartFragment == null));
                imgCart.setSelected(true);
                if (cartFragment == null) {
                    cartFragment = new CartFragment();
                    ftn.add(R.id.llayout_fragmentRoot, cartFragment, "cartFragment");
                } else {
                    ftn.show(cartFragment);
                }
                break;
            case 3:
                MyLog.e("sss", "MyFragment is null ===" + (myFragment == null));
                imgMine.setSelected(true);
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    ftn.add(R.id.llayout_fragmentRoot, myFragment, "myFragment");
                } else {
                    ftn.show(myFragment);
                }
                break;

            default:
                break;
        }
        ftn.commitAllowingStateLoss();
    }

    /**
     * 当fragment已被实例化，就隐藏起来
     *
     * @param ftn
     */
    private void hideFragments(FragmentTransaction ftn) {
        // TODO Auto-generated method stub
        if (homeFragment != null) {
            ftn.hide(homeFragment);
        }
        if (promotionDivisionFragment != null) {
            ftn.hide(promotionDivisionFragment);
        }
        if (cartFragment != null) {
            ftn.hide(cartFragment);
        }
        if (myFragment != null) {
            ftn.hide(myFragment);
        }
        if (shoppingMallFragment != null) {
            ftn.hide(shoppingMallFragment);
        }
    }

    private void refresh() {
        imgHome.setSelected(false);
        imagePro.setSelected(false);
        imgCart.setSelected(false);
        imgMine.setSelected(false);
        imageMall.setSelected(false);
    }

    /**
     * 去登陆
     */
    private void goLogin() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "phone", phone,
                "password", password
        );
        OkHttpUtils.post().url(Constants.LOGIN_WITH_PASSWORD)
                .addParams("phone", phone)
                .addParams("password", password)
                .addParams("sign", sign)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .build().execute(new StringCallback() {
            @Override
            public void onAfter(int id) {
                super.onAfter(id);
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(R.string.app_on_request_error_msg, Toast.LENGTH_SHORT);
                MyLog.e("login", "Main__goPasswordLogin____onError___" + e);
            }

            @Override
            public void onResponse(String s, int id) {
                MyLog.e("login", "Main__goPasswordLogin____onResponse___" + s);
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(s, LoginBean.class);
                if (loginBean != null && loginBean.getCode() == 1) {
                    List<LoginBean.ContentBean> appCommonBeanInfo = loginBean.getContent();
                    AppCommonBean appCommonBean = new AppCommonBean();
                    for (int i = 0; i < appCommonBeanInfo.size(); i++) {
                        if (i == 0) {
                            if (appCommonBeanInfo.get(i).getIs_newyetai() != null) {
                                appCommonBean.setIsNewYeTai(appCommonBeanInfo.get(i).getIs_newyetai());
                            }
                            if (appCommonBeanInfo.get(i).getHunter_id() != null) {
                                appCommonBean.setHunter_id(appCommonBeanInfo.get(i).getHunter_id());
                            }
                            if (appCommonBeanInfo.get(i).getAddress() != null) {
                                appCommonBean.setAddress(appCommonBeanInfo.get(i).getAddress());
                            }
                            if (appCommonBeanInfo.get(i).getIMpassword() != null) {
                                appCommonBean.setIm_password(appCommonBeanInfo.get(i).getIMpassword());
                            }
                            if (appCommonBeanInfo.get(i).getNickname() != null) {
                                appCommonBean.setNickname(appCommonBeanInfo.get(i).getNickname());
                            }
                            if (appCommonBeanInfo.get(i).getName() != null) {
                                appCommonBean.setName(appCommonBeanInfo.get(i).getName());
                            }
                            if (appCommonBeanInfo.get(i).getId() != null) {
                                appCommonBean.setId(appCommonBeanInfo.get(i).getId());
                            }
                            if (appCommonBeanInfo.get(i).getIMid() != null) {
                                appCommonBean.setIm_id(appCommonBeanInfo.get(i).getIMid());
                            }
                            if (appCommonBeanInfo.get(i).getUuid() != null) {
                                appCommonBean.setUuid(appCommonBeanInfo.get(i).getUuid());
                            }
                            if (appCommonBeanInfo.get(i).getImSign() != null) {
                                appCommonBean.setImSign(appCommonBeanInfo.get(i).getImSign());
                            }
                            if (appCommonBeanInfo.get(i).getIsCertification() != null) {
                                appCommonBean.setIsCertification(appCommonBeanInfo.get(i).getIsCertification());
                            }

                        }
                        if (i == 1) {
                            if (appCommonBeanInfo.get(i).getIdent() != null) {
                                appCommonBean.setIdent(appCommonBeanInfo.get(i).getIdent());
                            }
                        }
                    }
                    appCommonBean.setPhone(phone);
                    appCommonBean.setPassword(password);
                    TianyiApplication.mAppCommonManager.saveUserInfo(appCommonBean);
                    TianyiApplication.mAppCommonManager.setLoginStaus();
                    TianyiApplication.mAppCommonManager.setLoginType(AppCommonManager.PASSWORDLOGINTYPE);
                    TIMManager.getInstance().init(context);
                    //初始化TLS
                    TlsBusiness.init(getApplicationContext());
                    //设置刷新监听
                    RefreshEvent.getInstance();

                    String identifier = appCommonBean.getIm_id();
                    String userSig = appCommonBean.getImSign();
                    TIMUser user = new TIMUser();
                    user.setIdentifier(identifier);
                    if (identifier != null && userSig != null) {
                        TIMManager.getInstance().login(Constant.SDK_APPID, user, userSig, new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                MyLog.e("mainactivity", "onError onError === " + i);
                                MyLog.e("mainactivity", "onError onError === " + s);
                            }

                            @Override
                            public void onSuccess() {
                                MyLog.e("mainactivity", "onSuccess onSuccess === ");

                            }
                        });
                    }
                    TianyiApplication.isLogin = true;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionUtil.getInstance().setmPermissionGrantListener(new PermissionUtil.OnPermissionGrantListener() {
            @Override
            public void grantPermission(boolean isGrant, String permission, int requestCode) {
                if (isGrant == false) {
                    MyLog.e("aaa", "false");
                    return;
                }
                MyLog.e("aaa", "grantPermission");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyLog.e("aaa", "Runnable");
                        uploadApkFile();
                    }
                });
            }
        });

        if (isCart) {
            setTabSelection(2);
            isCart = false;
        } else {

        }
        isLogin = PreferenceUtil.getBoolean(AppCommonManager.SPISLOGIN, false);
        loginType = PreferenceUtil.getString(AppCommonManager.LOGINTYPE, "");
        MyLog.e("login", "initData___" + isLogin + loginType);
        if (isLogin && !TextUtils.isEmpty(loginType)) {
            if (loginType.equals(AppCommonManager.PASSWORDLOGINTYPE)) {
                phone = PreferenceUtil.getString(AppCommonManager.SPUSER_ACCOUNT, "");
                password = PreferenceUtil.getString(AppCommonManager.SPUSER_PASSWORD, "");
                goLogin();
            } else {
                AppCommonBean appCommonBean = new AppCommonBean();
                appCommonBean.setAddress(PreferenceUtil.getString(AppCommonManager.ADDRESS, ""));
                appCommonBean.setPhone(PreferenceUtil.getString(AppCommonManager.SPUSER_ACCOUNT, ""));
                appCommonBean.setUuid(PreferenceUtil.getString(AppCommonManager.SPUSER_UUID, ""));
                appCommonBean.setHunter_id(PreferenceUtil.getString(AppCommonManager.SPUSER_HUNTER_ID, ""));
                appCommonBean.setIm_id(PreferenceUtil.getString(AppCommonManager.SPUSER_IM_ID, ""));
                appCommonBean.setId(PreferenceUtil.getString(AppCommonManager.SPUSER_ID, ""));
                appCommonBean.setIdent(PreferenceUtil.getString(AppCommonManager.SPUSER_IDENT, ""));
                appCommonBean.setIm_password(PreferenceUtil.getString(AppCommonManager.SPUSER_IM_PASSWORD, ""));
                appCommonBean.setImSign(PreferenceUtil.getString(AppCommonManager.SPUSER_IMSIGN, ""));
                appCommonBean.setNickname(PreferenceUtil.getString(AppCommonManager.SPUSER_NICKNAME, ""));
                appCommonBean.setToken(PreferenceUtil.getString(AppCommonManager.SPUSER_TOKEN, ""));
                appCommonBean.setIsNewYeTai(PreferenceUtil.getString(AppCommonManager.SPUSER_NEWYETAI, ""));

                TianyiApplication.mAppCommonManager.saveUserInfo(appCommonBean);
                TianyiApplication.mAppCommonManager.setLoginStaus();
                TianyiApplication.mAppCommonManager.setLoginType(AppCommonManager.CODELOGINTYPE);
                TianyiApplication.isLogin = true;
                TIMManager.getInstance().init(context);
                //初始化TLS
                TlsBusiness.init(getApplicationContext());
                //设置刷新监听
                RefreshEvent.getInstance();

                String identifier = appCommonBean.getIm_id();
                TIMUser user = new TIMUser();
                user.setIdentifier(identifier);
                String userSig = appCommonBean.getImSign();
                if (identifier != null && userSig != null) {
                    TIMManager.getInstance().login(Constant.SDK_APPID, user, userSig, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            MyLog.e("TIM", "onError onError === " + i);
                        }

                        @Override
                        public void onSuccess() {
                            MyLog.e("TIM", "onSuccess onSuccess === ");
                        }
                    });
                }
            }

        } else {
            TianyiApplication.isLogin = false;
        }

    }

    /**
     * 打开侧滑菜单
     */
    public void openLayout(ArrayList<String> items, int curPos) {
        drawerLayout.openDrawer(Gravity.RIGHT);
        flowlayout.setVisibility(View.VISIBLE);
        flowlayout.addDatas(items);
        flowlayout.setCurView(curPos);
    }

    public void initDrawLayout() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(final View drawerView, float slideOffset) {
                if (slideOffset == 1) {
                    llayoutFragmentRoot.clearFocus();
                }
                drawerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                drawerView.requestFocus();
                drawerLayout.setFocusable(false);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @OnClick({R.id.ll_home, R.id.ll_mall, R.id.ll_tuiguangshi, R.id.ll_shopcar, R.id.ll_mine})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.ll_home:
                MyLog.e("sss", "ll_home");
                if (tagPosition == 0 && Const.ISRELOAD) {
                    EventBus.getDefault().post(new UpdateEvent("home"));
                }
                setTabSelection(0);
                tagPosition = 0;
                break;
            case R.id.ll_mall:
                if (tagPosition == Const.INTEGER_1) {
                    EventBus.getDefault().post(new UpdateEvent("mall"));
                }
                setTabSelection(1);
                tagPosition = 1;
                break;

            case R.id.ll_shopcar:
                if (TianyiApplication.isLogin) {
                    if (tagPosition == Const.INTEGER_2) {
                        return;
                    }
                    setTabSelection(2);
                    tagPosition = 2;
                } else {
                    JumpIntent.jump(this, LoginActivity.class);
                }
                break;
            case R.id.ll_mine:
                EventBus.getDefault().post(new EventBean("myfragment", "", "", "", ""));
                MyLog.e("Main", "onResume___" + TianyiApplication.isLogin);
                if (TianyiApplication.isLogin) {
                    if (tagPosition == Const.INTEGER_3) {
                        return;
                    }
                    setTabSelection(3);
                    tagPosition = 3;
                } else {
                    JumpIntent.jump(this, LoginActivity.class);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyLog.e("aaa", "requestCode:" + requestCode);
        if (requestCode == Const.INTEGER_200) {
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        } else {
            RxBus.get().send(RxCode.NEW_VERSION_APK);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        MyLog.e("aaa", "requestCode0:" + requestCode);
        getWritePermission();

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }


    public interface OnRightItemCheckListener {
        /**
         * 右侧加号点击事件
         *
         * @param pos
         */
        void onItemCheck(int pos);
    }

    public void setOnRightItemCheckListener(OnRightItemCheckListener buttonClickedListener) {
        this.rightItemCheckListener = buttonClickedListener;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case MyFragment.REQUEST_IMAGE:
                List<String> list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (list.size() > 0) {
                    EventBus.getDefault().post(Uri.fromFile(new File(list.get(0))));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > Const.INTEGER_2000) {
                ToastUtils.show(getString(R.string.main_activity_exit_msg));
                time = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 跳转安装
     */
    private void installNewApk() {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        /**由于没有在Activity环境下启动Activity,设置下面的标签*/
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        /**判读版本是否在7.0以上*/
        if (Build.VERSION.SDK_INT >= Const.INTEGER_24) {
            /**参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件*/
            Uri apkUri = FileProvider.getUriForFile(context, "com.yst.onecity.fileprovider", file);
            /**添加这一句表示对目标应用临时授权该Uri所代表的文件*/
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        startActivity(intent);
        mHandler.removeCallbacksAndMessages(null);
        MainActivity.this.finish();
    }

    /**
     * 版本检测
     */
    private void getNewVersionApk() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "versionNo", ConstUtils.getVerCode(MainActivity.this),
                "timestamp", timestamp);
        OkHttpUtils
                .post()
                .url(Constants.CHECK_VERSION)
                .addParams("versionNo", ConstUtils.getVerCode(MainActivity.this))
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null && jsonObject.has(Const.CONS_STR_CODE)) {
                                int code = jsonObject.getInt(Const.CONS_STR_CODE);
                                switch (code) {
                                    /**
                                     * 不需要更新
                                     */
                                    case Const.INTEGER_1:
                                        RxBus.get().send(RxCode.NEW_VERSION_APK);
                                        break;
                                    /**
                                     * 需要更新
                                     */
                                    case Const.INTEGER_2:
                                        JSONObject content = jsonObject.getJSONObject(Const.CONS_STR_CONTENT);
                                        if (content != null && content.has(Const.APK_FORCED_UPDATE) && content.has(Const.APK_LOAD_ADDRESS)) {
                                            int forcedUpdate = content.getInt(Const.APK_FORCED_UPDATE);
                                            Constants.lujing = content.getString(Const.APK_LOAD_ADDRESS);
                                            if (Const.INTEGER_1 == forcedUpdate) {
                                                file = FileUtil.updatDelteApkFile();
                                                remindVersionUpdateDialog(true);
                                            } else {
                                                remindVersionUpdateDialog(false);
                                            }
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    /**
     * 版本更新弹出框
     *
     * @param flag 是否弹出标志位
     */
    private void remindVersionUpdateDialog(boolean flag) {
        AbstractVersionCheckDialog dialog = new AbstractVersionCheckDialog(this, flag) {
            @Override
            public void onSureClick() {
                setDialog(Gravity.CENTER, (WindowUtils.getScreenWidth(MainActivity.this) / 6) * 5, R.layout.dialog_tips_mid, R.id.tvId, 0);
            }
        };
        dialog.setCancleStyle(0, R.color.black, "取消");
        dialog.setSureStyle(0, R.color.black, "确认");
        dialog.showDialog();
    }

    /**
     * 版本更新进度框
     *
     * @param grivate 样式
     * @param width   宽
     * @param layout  布局
     * @param txtId   id
     * @param anim    动画
     */
    private void setDialog(int grivate, int width, int layout, int txtId, int anim) {
        tipsDialog = TipsDialog.creatTipsDialog(this, width, layout, grivate, anim);
        progressBar = (ProgressBar) tipsDialog.findViewById(R.id.down_pb);
        progressText = (TextView) tipsDialog.findViewById(txtId);
        tipsDialog.setCancelable(false);
        Button btnCancle = (Button) tipsDialog.findViewById(R.id.btnCancle);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                // 由intent启动service，后台运行下载进程，在服务中调用notifycation状态栏显示进度条
                startService(intent);
                tipsDialog.dismiss();
            }
        });
        tipsDialog.show();
        tipsDialog.setCanceledOnTouchOutside(false);
        //获取读写权限
        getWritePermission();
    }

    private void getWritePermission() {
        if (EasyPermissions.hasPermissions(MainActivity.this, writePermission)) {
            //下载APK
            uploadApkFile();
        } else {
            EasyPermissions.requestPermissions(this, "请打开存储权限", 200, writePermission);
        }
    }

    /**
     * 下载APK文件
     */
    private void uploadApkFile() {
        OkHttpUtils.get()
                .url(Constants.lujing)
                .tag(MainActivity.this)
                .build()
                .execute(new FileCallBack(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath(), Constants.APP_NAME) {
                    @Override
                    public void inProgress(float v, long l, int id) {
                        loadingProgress = (int) (v * 100);
                        progressBar.setProgress(loadingProgress);
                        progressText.setText("已为您加载了:" + loadingProgress + "%");
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i("User", "e =" + e.getMessage());
                        ToastUtils.show("下载失败");
                        tipsDialog.dismiss();
                        MainActivity.this.finish();
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        tipsDialog.dismiss();
                        installNewApk();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
        EventBus.getDefault().unregister(this);
        broadcastManager.unregisterReceiver(mItemViewListClickReceiver);
    }

    @gorden.rxbus2.Subscribe(code = RxCode.CODE_PUBLISH_ADD_SHOPPING_CARD, threadMode = ThreadMode.MAIN)
    public void getRefresh() {
        MyLog.e("aaa","getRefresh");
        if (cartFragment != null) {
            cartFragment.getResume();
        }
    }

}