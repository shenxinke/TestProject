package com.yst.onecity.fragment;

import android.Manifest;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.ChooseAddressActivity;
import com.yst.onecity.activity.MainActivity;
import com.yst.onecity.activity.SearchActivity;
import com.yst.onecity.activity.amap.Location;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.activity.member.MyMessageActivity;
import com.yst.onecity.adapter.CommomFragmentPagerAdapter;
import com.yst.onecity.base.BaseFragment;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.SortBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.eventbus.UpdateEvent;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.NetworkUtils;
import com.yst.onecity.utils.RxCode;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.ShoppingMallPopupWindow;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;
import gorden.rxbus2.RxBus;
import gorden.rxbus2.ThreadMode;
import okhttp3.Call;
import okhttp3.Request;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 首页
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.indicator)
    ViewPagerIndicator indicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.messageTagTV)
    TextView messageTagTV;
    @BindView(R.id.position)
    TextView position;
    @BindView(R.id.iv_home_arrow)
    ImageView ivArrow;
    public static double locationLon, locationLat;
    /**
     * 街道
     */
    public static String street;
    public static String district;
    private String[] locationPermission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE};
    private MainActivity mActivity;

    private CommomFragmentPagerAdapter adapter;
    private ArrayList<String> sortList = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private static final int LOCATION = 1;
    private ShoppingMallPopupWindow pop;
    private HomeShareFragment mHomeShareFragment;

    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
        RxBus.get().register(this);
        EventBus.getDefault().register(this);
        mActivity = (MainActivity) getActivity();
        mActivity.setOnRightItemCheckListener(new MainActivity.OnRightItemCheckListener() {
            @Override
            public void onItemCheck(int pos) {
                viewpager.setCurrentItem(pos, true);
            }
        });
        getUnReadMessageNum();
        initSort();
    }

    @OnPageChange(R.id.viewpager)
    public void onPageSelected(int position) {
        if (viewpager.getCurrentItem() == Const.INTEGER_1) {
            if (mHomeShareFragment != null) {
                mHomeShareFragment.setPublishVisibility();
            }
        }
    }

    @Subscribe
    public void onEventMainThread(UpdateEvent event) {
        if (Const.CONS_STR_HOME.equals(event.getMsg())) {
            MyLog.e("sss", "home");
            initSort();
        }
    }


    private void getUnReadMessageNum() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp,
                "type", "0");
        OkHttpUtils.post().url(Constants.GET_UNREADMESSAGE)
                .addParams("type", "0")
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt(Const.CONS_STR_CODE) == 1) {
                        if (jsonObject.getInt(Const.CONS_STR_CONTENT) > 0) {
                            messageTagTV.setVisibility(View.VISIBLE);
                            messageTagTV.setText(String.valueOf(jsonObject.getInt("content")));
                        } else {
                            messageTagTV.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 开启定位
     */
    @gorden.rxbus2.Subscribe(code = RxCode.NEW_VERSION_APK, threadMode = ThreadMode.MAIN)
    public void noNewVersionApk() {
        startLocation();
    }

    public void startLocation() {
        if (EasyPermissions.hasPermissions(getActivity(), locationPermission)) {
            //启动定位
            new Location(locationCallback).startLocation();
            position.setText("定位中…");
        } else {
            EasyPermissions.requestPermissions(this, "请打开定位权限", 100, locationPermission);
        }
    }

    Location.LocationCallback locationCallback = new Location.LocationCallback() {
        @Override
        public void locSuccess(AMapLocation amapLocation) {
            if (amapLocation.getAoiName() == null || "".equals(amapLocation.getAoiName())) {
                position.setText(amapLocation.getAoiName());
            } else {
                position.setText(amapLocation.getAoiName());
                locationLat = amapLocation.getLatitude();
                locationLon = amapLocation.getLongitude();
                street = amapLocation.getStreet();
                district = amapLocation.getDistrict();

                TianyiApplication.appCommonBean.setProvinceName(amapLocation.getProvince());
                TianyiApplication.appCommonBean.setCityName(amapLocation.getCity());
                TianyiApplication.appCommonBean.setCountyName(amapLocation.getDistrict());
                MyLog.e("sss","----" + amapLocation.getProvince() +"---" +amapLocation.getProvince()+"----" +amapLocation.getDistrict());
            }
        }

        @Override
        public void locFailure(int code, String errorInfo) {
            position.setText("定位失败");
            locationLat = 39.966036;
            locationLon = 116.471981;
        }
    };

    private void initSort() {
        fragments.clear();
        sortList.clear();

        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "status", "0",
                "timestamp", timestamp);
        OkHttpUtils.post().url(Constants.GET_HOME_SORT)
                .addParams("status", "0")
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                showInfoProgressDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                dismissInfoProgressDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                if (!NetworkUtils.isConnectNet(TianyiApplication.getInstance())) {
                    Const.ISRELOAD = true;
                }
            }

            @Override
            public void onResponse(String response, int id) {
                Const.ISRELOAD = false;
                try {
                    Gson gson = new Gson();
                    SortBean sortBean = gson.fromJson(response, SortBean.class);
                    List<SortBean.ContentBean> content = sortBean.getContent();
                    MyLog.e("tag", "response==========="+response);
                    addCustomTab();

                    for (int i = 0; i < content.size(); i++) {
                        sortList.add(content.get(i).getDescription_name());
                        fragments.add(InformationContentFragment.newInstance(String.valueOf(content.get(i).getId())));
                    }

                    adapter = new CommomFragmentPagerAdapter(getChildFragmentManager(), fragments, sortList);
                    viewpager.setAdapter(adapter);
                    indicator.bindViewPager(viewpager, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     *添加自定义Tab栏
     */
    private void addCustomTab() {
        sortList.add("直播");
        fragments.add(HomeLiveTabFragment.newInstance());
        sortList.add("分享");
        mHomeShareFragment = HomeShareFragment.newInstance();
        fragments.add(mHomeShareFragment);
    }

    @OnClick({R.id.searchBarLayout, R.id.messages, R.id.messagesRL, R.id.ll_choose_label, R.id.position})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.searchBarLayout:
                JumpIntent.jump(getActivity(), SearchActivity.class);
                break;
            case R.id.messages:
            case R.id.messagesRL:
                if (!TianyiApplication.isLogin) {
                    JumpIntent.jump(getActivity(), LoginActivity.class);
                    return;
                }
                JumpIntent.jump(getActivity(), MyMessageActivity.class);
                break;
            case R.id.ll_choose_label:
                //选择标签
                if (sortList == null || sortList.size() < 1) {
                    return;
                }

                if(pop == null){
                    pop = new ShoppingMallPopupWindow(getActivity());
                    pop.setOnRightItemCheckListener(new ShoppingMallPopupWindow.OnRightItemCheckListener() {
                        @Override
                        public void onItemCheck(int pos) {
                            viewpager.setCurrentItem(pos, true);
                            pop.showPop(indicator, ivArrow, sortList, viewpager.getCurrentItem());
                        }
                    });
                    pop.setOnDismissListener(() -> ivArrow.setImageResource(R.mipmap.xia));
                }
                pop.showPop(indicator, ivArrow, sortList, viewpager.getCurrentItem());

                break;
            case R.id.position:
                JumpIntent.jump(getActivity(), ChooseAddressActivity.class);
                break;
            default:
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(EventBean event) {
        String choose = "choose";
        String location = "location";
        String resultAddress = "resultAddress";
        if (choose.equals(event.getMsg())) {
            position.setText(event.getAdress());
            Const.HOME_ADDRES = event.getAdress();
            TianyiApplication.appCommonBean.setProvinceName(event.getPID());
            TianyiApplication.appCommonBean.setCityName(event.getCID());
            TianyiApplication.appCommonBean.setCountyName(event.getAID());
        } else if (location.equals(event.getMsg()) && Const.positionFlag == event.getPosition()) {
            position.setText(event.getAdress());
            Const.HOME_ADDRES = event.getAdress();
            TianyiApplication.appCommonBean.setProvinceName(event.getPID());
            TianyiApplication.appCommonBean.setCityName(event.getCID());
            TianyiApplication.appCommonBean.setCountyName(event.getAID());
        } else if (resultAddress.equals(event.getMsg()) && Const.positionFlag == event.getPosition()) {
            position.setText(event.getAID());
            Const.HOME_ADDRES = event.getAID();
            TianyiApplication.appCommonBean.setProvinceName(event.getPID());
            TianyiApplication.appCommonBean.setCityName(event.getCID());
            TianyiApplication.appCommonBean.setCountyName(event.getAID());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
        EventBus.getDefault().unregister(this);
    }
}
