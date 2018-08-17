package com.yst.onecity.activity;

import android.Manifest;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.amap.Location;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.SearchHistory;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.view.AddressPopupWindow;
import com.yst.onecity.view.ContainsEmojiEditText;
import com.yst.onecity.view.SearchResultPopWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

import static com.yst.onecity.fragment.HomeFragment.district;
import static com.yst.onecity.fragment.HomeFragment.locationLat;
import static com.yst.onecity.fragment.HomeFragment.locationLon;
import static com.yst.onecity.fragment.HomeFragment.street;

/**
 * 选择地址
 *
 * @author jiaofan
 * @version 4.2.0
 * @date 2018/5/31
 */
public class ChooseAddressActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener, AdapterView.OnItemClickListener {
    @BindView(R.id.txt_city)
    TextView mTxtCity;
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.ll_city)
    LinearLayout mLlCity;
    @BindView(R.id.et_address)
    ContainsEmojiEditText mEtAddress;
    @BindView(R.id.txt_search)
    TextView mTxtSearch;
    @BindView(R.id.txt_position)
    TextView mTxtPosition;
    @BindView(R.id.txt_again)
    TextView mTxtAgain;
    @BindView(R.id.rel_position)
    RelativeLayout mRelPosition;
    @BindView(R.id.ll_history)
    LinearLayout mLlHistory;
    @BindView(R.id.ll_history_list)
    LinearLayout mLlHistoryList;

    private String[] locationPermission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private List<SearchHistory> historyList;
    private String searchName = "";
    private ArrayList<PoiItem> poiItems = new ArrayList<>();
    private int location = 1;
    private String mCity;
    private String mResultAddress;
    private String mProvince;
    private String mDistrict;

    @Override
    public int bindLayout() {
        return R.layout.activity_choose_address;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        setTitleBarTitle("选择地址");
        getSearchHistory();
        startLocation();
    }

    /**
     * 初始化搜索功能
     *
     * @param keyWord 关键字
     */
    private void initPoiSearchConfig(String keyWord, String district) {
        if (TextUtils.isEmpty(keyWord)) {
            return;
        }
        MyLog.e("zzz", "keyWord===" + keyWord + ", district===" + district);
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", TextUtils.isEmpty(district) ? "" : district);
        PoiSearch poiSearch = new PoiSearch(this, query);
        query.setPageSize(20);
        query.setPageNum(1);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult result, int code) {
        MyLog.e("zzz", "code===" + code);
        if (code == Const.INTEGER_1000) {
            if (result != null && result.getQuery() != null) {
                poiItems = result.getPois();
                if (null != poiItems && poiItems.size() > 0) {
                    PoiItem poiItem = poiItems.get(0);
                    mTxtCity.setText(poiItem.getCityName());
                    showResultPop(poiItems);
                } else {
                    ToastUtils.show("暂无搜索内容");
                }
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 显示搜索结果弹框
     *
     * @param poiItems 搜索结果list
     */
    private void showResultPop(ArrayList<PoiItem> poiItems) {
        SearchResultPopWindow pop = new SearchResultPopWindow(this, poiItems, this);
        pop.showPopupWindow(mEtAddress);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (poiItems != null && poiItems.size() > position) {
            EventBus.getDefault().post(new EventBean("choose", poiItems.get(position).getTitle(), poiItems.get(position).getProvinceName(), poiItems.get(position).getCityName(), poiItems.get(position).getAdName()));
            finish();
        }
    }

    @Subscribe
    public void onEventMainThread(EventBean bean) {
        String address = "address";
        MyLog.e("地址22222", bean.getMsg() + ", " + bean.getProvince() + bean.getCity() + bean.getDistrict());
        if (address.equals(bean.getMsg())) {
            mTxtCity.setText(bean.getProvince() + bean.getCity() + bean.getDistrict());
            mResultAddress = bean.getProvince() + bean.getCity() + bean.getDistrict();
            mProvince = bean.getProvince();
            mCity = bean.getCity();
            mDistrict = bean.getDistrict();
        }
    }

    @OnClick({R.id.ll_city, R.id.ll_back, R.id.txt_search, R.id.rel_position})
    public void onViewClicked(View view) {
        if (!ConstUtils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.ll_city:
                showAddressPop();
                break;
            case R.id.txt_search:
                ConstUtils.setFilter(mTxtSearch, Const.INTEGER_2000);
                searchName = mEtAddress.getText().toString().trim();
                if (TextUtils.isEmpty(searchName)) {
                    ToastUtils.show("请输入搜索内容~");
                    return;
                }
                initPoiSearchConfig(searchName, mCity);
                TianyiApplication.getInstance().saveSearchHistory(searchName);
                break;
            case R.id.rel_position:
                location = 2;
                startLocation();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(mResultAddress)) {
            EventBus.getDefault().post(new EventBean("resultAddress", mResultAddress, mProvince, mCity, mDistrict, 1));
        }
        finish();
    }

    /**
     * 显示省市区选择器弹窗
     */
    private void showAddressPop() {
        AddressPopupWindow pop = new AddressPopupWindow(this);
        pop.showPop(mLlCity, mIvArrow);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mIvArrow.setImageResource(R.mipmap.xia);
            }
        });
    }

    /**
     * 定位
     */
    public void startLocation() {
        if (EasyPermissions.hasPermissions(this, locationPermission)) {
            new Location(locationCallback).startLocation();
            mTxtPosition.setText("定位中…");
        } else {
            EasyPermissions.requestPermissions(this, "请打开定位权限", 100, locationPermission);
        }
    }

    /**
     * 定位回调
     */
    Location.LocationCallback locationCallback = new Location.LocationCallback() {
        @Override
        public void locSuccess(AMapLocation amapLocation) {
            if (amapLocation.getAoiName() == null || "".equals(amapLocation.getAoiName())) {
                MyLog.e("tag", "getAddress===" + amapLocation.getAddress());
                mTxtPosition.setText(amapLocation.getAddress());
            } else {
                MyLog.e("tag1", "getAoiName===" + amapLocation.getAoiName());
                mTxtPosition.setText(amapLocation.getAoiName());
                locationLat = amapLocation.getLatitude();
                locationLon = amapLocation.getLongitude();
                street = amapLocation.getStreet();
                district = amapLocation.getDistrict();
                if (location == Const.INTEGER_2) {
                    Const.positionFlag = 2;
                    EventBus.getDefault().post(new EventBean("location", amapLocation.getAoiName(), amapLocation.getProvince(), amapLocation.getCity(), amapLocation.getDistrict(), 2));
                    EventBus.getDefault().post(new EventBean("choose", amapLocation.getAoiName(), amapLocation.getProvince(), amapLocation.getCity(), amapLocation.getDistrict()));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 200);
                }
            }
        }

        @Override
        public void locFailure(int code, String errorInfo) {
            mTxtPosition.setText("定位失败");
            locationLat = 39.966036;
            locationLon = 116.471981;
        }
    };

    /**
     * 获取历史搜索记录
     */
    private void getSearchHistory() {
        historyList = TianyiApplication.getInstance().getSearchHistory();
        if (historyList.size() == Const.INTEGER_0) {
            mLlHistory.setVisibility(View.GONE);
        } else {
            mLlHistory.setVisibility(View.VISIBLE);
            mLlHistoryList.removeAllViews();
            for (int i = 0; i < historyList.size(); i++) {
                final String searchName = historyList.get(i).getKeyword();
                LinearLayout ll = (LinearLayout) View.inflate(this, R.layout.item_search_history, null);
                TextView tv = (TextView) ll.findViewById(R.id.txt_history);
                tv.setText(searchName);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEtAddress.setText(searchName);
                        mEtAddress.setSelection(searchName.length());
                    }
                });
                mLlHistoryList.addView(ll);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
