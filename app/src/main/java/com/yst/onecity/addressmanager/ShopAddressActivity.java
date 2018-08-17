package com.yst.onecity.addressmanager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.yst.onecity.R;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.CommonViewHolder;
import com.yst.onecity.adapter.MapCityAdater;
import com.yst.onecity.bean.addressmanager.CityBean;
import com.yst.onecity.bean.addressmanager.CitylistBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.KeyBoardUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.utils.security.PreferenceUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 门店地址
 *
 * @author wuxiaofang
 * @version 4.0.0
 * @date 2017/7/25
 */
public class ShopAddressActivity extends CheckPermissionsActivity implements LocationSource, AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener, CheckPermissionsActivity.CheckPermissionsListener
        , ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener, AMapLocationListener, AMap.OnMapTouchListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.tv_locate)
    TextView tvLocate;
    @BindView(R.id.et_input_content)
    EditText etInputContent;
    @BindView(R.id.searchBarLayout)
    LinearLayout searchBarLayout;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.activity_shop_address)
    LinearLayout activityShopAddress;
    @BindView(R.id.tv_location_current)
    TextView tvLocationCurrent;
    @BindView(R.id.ll_location_current)
    LinearLayout llLocationCurrent;
    @BindView(R.id.lv_city)
    ExpandableListView lvCity;
    @BindView(R.id.tv_first_name)
    TextView tvFirstName;
    @BindView(R.id.rl_lv)
    RelativeLayout rlLv;
    @BindView(R.id.side_letter_bar)
    SideLetterBar sideLetterBar;
    @BindView(R.id.ll_locate)
    LinearLayout llLocate;
    @BindView(R.id.ll_map)
    LinearLayout llMap;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    private AbstractCommonAdapter<PoiItem> adapter;
    /**
     * 地图部分
     */
    private AMap aMap;
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient = null;
    private double longitude;
    private double latitude;
    private LatLng latlng = null;
    private MarkerOptions mMarkerOptions;
    private Marker mCenterMarker;
    private GeocodeSearch mGeocoderSearch;
    private LatLonPoint mCurrentPoint;
    private PoiSearch.Query mPoiQuery;
    private List<PoiItem> poiItems;
    private String title;
    private boolean isFirstLocation;
    private String[] exreRnalPermission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int LOCATION_CODE = 100;
    private List<CitylistBean> cityList = new ArrayList<>();
    private List<String> cityList2 = new ArrayList<>();
    /**
     * 输入表情前的光标位置
     */
    private int cursorPos;
    /**
     * 是否重置了EditText的内容
     */
    private boolean resetText;
    private String inputAfterText;
    String streetName;
    private boolean isSearch;
    private MapCityAdater cityAdater;
    private GoogleApiClient client;
    private boolean isClick;
    private String mTag = "mTag";
    private String district;
    private String cityName;

    public static void locationHomeAddress(Activity context, LatLng myLatLng, String street, String district, int requestCode) {
        Intent intent = new Intent(context, ShopAddressActivity.class);
        intent.putExtra("myLatLng", myLatLng);
        intent.putExtra("street", street);
        intent.putExtra("district", district);
        intent.putExtra("flag", 1);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Const.INTEGER_23) {
            ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.white).statusBarAlpha(0.2f).init();
        } else if (Build.VERSION.SDK_INT >= Const.INTEGER_23) {
            ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();
        }
        setContentView(R.layout.activity_shop_address);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initConfig();
        initData();
        initViewListener();
    }

    /**
     * 初始化各类配置信息
     */
    private void initConfig() {
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        initLocationConfig();
        initMapConfig();
    }

    /**
     * 初始化页面数据信息
     */
    private void initData() {
        tvMainTitle.setText(getString(R.string.edit_address));
        streetName = getIntent().getStringExtra("street");
        MyLog.e("mTag", "-streetName:" + streetName);
        if (null != streetName && !TextUtils.isEmpty(streetName) && streetName.contains(Const.CONS_STR_CHAR)) {
            String[] split = streetName.split(Const.CONS_STR_CHAR);
            if (null != split && split.length > 0) {
                tvLocate.setText(ConstUtils.getStringNoEmpty(split[0]));
            }
        }
        latlng = getIntent().getParcelableExtra("myLatLng");
        district = getIntent().getStringExtra("district");
        if (!TextUtils.isEmpty(streetName) && !TextUtils.isEmpty(district) && latlng != null) {
            MyLog.e("mTag", "******" + streetName);
            isSearch = true;
            poiSearch(streetName);
        } else {
            doLocation();
            MyLog.e("mTag", "重新定位");
        }
        initCityView();
    }

    /**
     * 初始化覆盖层城市列表数据
     */
    private void initCityView() {
        rlLv.setVisibility(View.GONE);
        cityAdater = new MapCityAdater(this);
        lvCity.setAdapter(cityAdater);
        lvCity.setOnGroupClickListener(this);
        lvCity.setOnChildClickListener(this);
        requestCityData();
    }

    /**
     * 初始化定位地图显示相关监听事件
     */
    private void initMapLinstener() {
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        //手动移动地图监听
        aMap.setOnCameraChangeListener(this);
        aMap.setOnMapTouchListener(this);
        mGeocoderSearch = new GeocodeSearch(ShopAddressActivity.this);
        mGeocoderSearch.setOnGeocodeSearchListener(ShopAddressActivity.this);
    }

    /**
     * 初始化Activity组件监听
     */
    private void initViewListener() {

        etInputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!resetText) {
                    cursorPos = etInputContent.getSelectionEnd();
                    inputAfterText = charSequence.toString();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (!resetText) {
                    if (count >= Const.INTEGER_2) {
                        try {
                            if (null != charSequence && charSequence.length() >= cursorPos + count) {
                                CharSequence input = charSequence.subSequence(cursorPos, cursorPos + count);
                                if (Utils.containsEmoji(input.toString())) {
                                    resetText = true;
                                    etInputContent.setText(inputAfterText);
                                    CharSequence text = etInputContent.getText();
                                    if (text instanceof Spannable) {
                                        Spannable spanText = (Spannable) text;
                                        Selection.setSelection(spanText, text.length());
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    resetText = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                final String ss = etInputContent.getText() + "";
                streetName = ss.trim();
                streetName = etInputContent.getText().toString().trim();
                isSearch = true;
                poiSearch(streetName);
                MyLog.e("sss", "afterTextChanged == " + ss);
            }
        });

        sideLetterBar.setOverlay(tvFirstName);
        sideLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                MyLog.e(mTag, "onLetterChanged ==  " + letter);
                int position = cityList2.indexOf(letter);
                lvCity.setSelectedGroup(position);
                MyLog.e(mTag, "onLetterChanged == " + position);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MyLog.e("sss", "" + poiItems.size());
                if (poiItems != null && poiItems.size() > position) {
                    try {
                        PreferenceUtil.put("index", position);
                        title = poiItems.get(position).getTitle();
                        longitude = poiItems.get(position).getLatLonPoint().getLongitude();
                        latitude = poiItems.get(position).getLatLonPoint().getLatitude();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent();
                    intent.putExtra("title", title);
                    intent.putExtra("district", district);
                    intent.putExtra("cityName", cityName);
                    intent.putExtra("longitude", String.valueOf(longitude));
                    intent.putExtra("latitude", String.valueOf(latitude));
                    intent.putExtra("street", streetName);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @OnClick({R.id.ll_back, R.id.searchBarLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.ll_locate)
    public void locate() {
        KeyBoardUtils.closeKeybord(etInputContent, ShopAddressActivity.this);
        if (isClick) {
            isSearch = true;
            llMap.setVisibility(View.VISIBLE);
            rlLv.setVisibility(View.GONE);
            isClick = false;
        } else {
            llMap.setVisibility(View.GONE);
            rlLv.setVisibility(View.VISIBLE);
            llLocationCurrent.setVisibility(View.VISIBLE);
            tvLocationCurrent.setText(tvLocate.getText().toString());
            isClick = true;
        }
    }

    /**
     * 去定位 定位之前判断权限
     */
    private void doLocation() {
        if (EasyPermissions.hasPermissions(ShopAddressActivity.this, exreRnalPermission)) {
            mlocationClient.startLocation();
        } else {
            MyLog.e("TAG", ">>>onClick---" + "请求权限");
            EasyPermissions.requestPermissions(this, "请打开定位权限", LOCATION_CODE, exreRnalPermission);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onPause();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
    }

    /**
     * AMAP地图上移动监听回调
     *
     * @param cameraPosition
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        MyLog.e("mTag", "onCameraChangeFinish");
        mCurrentPoint = new LatLonPoint(cameraPosition.target.
                latitude, cameraPosition.target.longitude);
        RegeocodeQuery query = new RegeocodeQuery(mCurrentPoint, 1000, GeocodeSearch.AMAP);
        // 设置同步逆地理编码请求
        mGeocoderSearch.getFromLocationAsyn(query);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        MyLog.e("mTag", "onRegeocodeSearched");
        if (rCode == Const.INTEGER_1000) {
            if (result != null && result.getRegeocodeAddress() != null &&
                    result.getRegeocodeAddress().getFormatAddress() != null) {
                mPoiQuery = new PoiSearch.Query("", "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务" +
                        "|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|公司企业|" +
                        "道路附属设施|地名地址信息|公共设施",
                        result.getRegeocodeAddress().getCityCode());
                // 设置每页最多返回多少条poiitem
                mPoiQuery.setPageSize(10);
                //设置查第一页
                mPoiQuery.setPageNum(0);
                PoiSearch poiSearch = new PoiSearch(this, mPoiQuery);
                //设置数据返回的监听器
                poiSearch.setOnPoiSearchListener(this);
                //设置周边搜索的中心点以及区域
                poiSearch.setBound(new PoiSearch.SearchBound(mCurrentPoint, 1000, true));
                //开始搜索
                poiSearch.searchPOIAsyn();
            } else {
                ToastUtils.show("未搜索到结果");
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
    }

    private void poiSearch(String string) {
        MyLog.e("mTag", "poiSearch");
        if (TextUtils.isEmpty(string)) {
            return;
        }
        PoiSearch.Query query = new PoiSearch.Query(string, "", "");
        PoiSearch poiSearch = new PoiSearch(this, query);
        query.setPageSize(10);
        query.setPageNum(0);

        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == Const.INTEGER_1000) {
            MyLog.e("mTag", "onPoiSearched");
            // 搜索poi的结果
            if (result != null && result.getQuery() != null) {
                MyLog.e("mTag", "result.getQuery() != null");
                poiItems = result.getPois();
                if(null != poiItems && poiItems.size() > 0){
                    PoiItem poiItem = poiItems.get(0);
                    district = poiItem.getAdName();
                    cityName = poiItem.getCityName();
                    title = poiItem.getTitle();
                    tvLocate.setText(cityName);
                    listView.setVisibility(View.VISIBLE);
                    ivEmpty.setVisibility(View.GONE);
                    initCtcl(poiItems);
                    if (isSearch) {
                        isSearch = false;
                        aMap.clear();
                        longitude = poiItem.getLatLonPoint().getLongitude();
                        latitude = poiItem.getLatLonPoint().getLatitude();
                        LatLng latLng = new LatLng(latitude, longitude);
                        MyLog.e("mTag", "onPoiSearched____" + latitude + "--" + longitude);
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                        aMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
                    }
                }else{
                    initCtcl(poiItems);
                    listView.setVisibility(View.GONE);
                    ivEmpty.setVisibility(View.VISIBLE);
                }
            } else {
            }
        }
    }

    private void initCtcl(List<PoiItem> poiItems) {
        adapter = new AbstractCommonAdapter<PoiItem>(getApplicationContext(), poiItems, R.layout.item_map) {
            @Override
            public void convert(CommonViewHolder holder, int position, PoiItem item) {
                holder.setText(R.id.tv_title, item.getTitle());
                holder.setText(R.id.tv_address, item.getSnippet());
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onGranted() {
        mlocationClient.startLocation();
    }

    @Override
    public void onDenied(List<String> permissions) {
        Toast.makeText(this, "权限被禁用，请到设置里打开", Toast.LENGTH_SHORT).show();
    }


    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ShopAddress Page")
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return true;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        String s = cityList.get(groupPosition).getCityList().get(childPosition);
        poiSearch(s);
        tvLocate.setText(s);
        rlLv.setVisibility(View.GONE);
        mapView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        llMap.setVisibility(View.VISIBLE);
        llLocationCurrent.setVisibility(View.GONE);
        return false;
    }

    /**
     * setMyLocationButtonEnabled 定位到当前位置按钮回调
     *
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        etInputContent.getText().clear();
        isSearch = true;
        isFirstLocation = true;
        latlng = null;
        doLocation();
    }


    @Override
    public void deactivate() {
    }

    /**
     * 定位回调
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode() == 0) {
            MyLog.e(mTag, "onLocationChanged");
            // 设置初始地图的中心位置
            if (isFirstLocation) {
                MyLog.e(mTag, "isFirstLocation == true");
                isFirstLocation = false;
                if (latlng == null) {
                    longitude = aMapLocation.getLongitude();
                    latitude = aMapLocation.getLatitude();
                    streetName = aMapLocation.getStreet();
                    district = aMapLocation.getDistrict();
                    MyLog.e(mTag, "onLocationChanged ***" + "区： " + district + "----" + longitude + "---" + latitude + "-- " + streetName);
                    latlng = new LatLng(latitude, longitude);
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18));
                    poiSearch(streetName);
                } else {
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18));
                    poiSearch(streetName);
                }
            } else {
                MyLog.e(mTag, "isFirstLocation == false");
            }

        } else {

        }
    }

    /**
     * 这是Activity的触摸回调
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                KeyBoardUtils.closeKeybord(etInputContent, ShopAddressActivity.this);
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 这是AMAP的MapTouchListener回调
     *
     * @param motionEvent
     */
    @Override
    public void onTouch(MotionEvent motionEvent) {
        KeyBoardUtils.closeKeybord(etInputContent, ShopAddressActivity.this);
    }

    /**
     * 初始化定位配置信息
     */
    private void initLocationConfig() {
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getApplicationContext());
        }
        //初始化定位参数
        if (mLocationOption == null) {
            mLocationOption = new AMapLocationClientOption();
        }
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        if (mLocationOption.isOnceLocationLatest()) {
            mLocationOption.setOnceLocationLatest(true);
            //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
            //如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会。
        }
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
    }

    /**
     * 初始化地图信息配置
     */
    private void initMapConfig() {
        if (aMap == null) {
            aMap = mapView.getMap();
            initMapLinstener();
        }
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.showMyLocation(true);
        ////定位一次，且将视角移动到地图中心点。
        myLocationStyle.strokeColor(0x88D4E2EF);
        myLocationStyle.radiusFillColor(0x88D4E2EF);
        // 自定义系统定位小蓝点
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        // 设置默认定位按钮是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        //设置显示地图的默认比例尺
        aMap.getUiSettings().setScaleControlsEnabled(true);

        mMarkerOptions = new MarkerOptions();
        //可拖放性
        mMarkerOptions.draggable(true);
        mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.location));
        mCenterMarker = aMap.addMarker(mMarkerOptions);
        ViewTreeObserver vto = mapView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mCenterMarker.setPositionByPixels(mapView.getWidth() >> 1, mapView.getHeight() >> 1);
                mCenterMarker.showInfoWindow();
            }
        });
    }

    /**
     * 获取城市列表的数据
     */
    private void requestCityData() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign(
                "timestamp", timestamp);
        OkHttpUtils
                .post()
                .url(Constants.GET_CITY_LIST)
                .addParams("client_type", "A")
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.tv_net_error));
            }


            @Override
            public void onResponse(String response, int id) {
                MyLog.e("sss", "-城市列表：" + response);
                if (response != null) {
                    CityBean cityBean = new Gson().fromJson(response, CityBean.class);
                    if (cityBean.getCode() == 1) {
                        CityBean.ContentBean content = cityBean.getContent();
                        List<String> a = content.getA();
                        List<String> b = content.getB();
                        List<String> c = content.getC();
                        List<String> d = content.getD();
                        List<String> e = content.getE();
                        List<String> f = content.getF();
                        List<String> g = content.getG();
                        List<String> h = content.getH();
                        List<String> j = content.getJ();
                        List<String> k = content.getK();
                        List<String> l = content.getL();
                        List<String> m = content.getM();
                        List<String> n = content.getN();
                        List<String> o = content.getO();
                        List<String> p = content.getP();
                        List<String> q = content.getQ();
                        List<String> r = content.getR();
                        List<String> s = content.getS();
                        List<String> t = content.getT();
                        List<String> w = content.getW();
                        List<String> x = content.getX();
                        List<String> y = content.getY();
                        List<String> z = content.getZ();
                        cityList.add(new CitylistBean("A", a));
                        cityList.add(new CitylistBean("B", b));
                        cityList.add(new CitylistBean("C", c));
                        cityList.add(new CitylistBean("D", d));
                        cityList.add(new CitylistBean("E", e));
                        cityList.add(new CitylistBean("F", f));
                        cityList.add(new CitylistBean("G", g));
                        cityList.add(new CitylistBean("H", h));
                        cityList.add(new CitylistBean("G", j));
                        cityList.add(new CitylistBean("K", k));
                        cityList.add(new CitylistBean("L", l));
                        cityList.add(new CitylistBean("M", m));
                        cityList.add(new CitylistBean("N", n));
                        cityList.add(new CitylistBean("O", o));
                        cityList.add(new CitylistBean("P", p));
                        cityList.add(new CitylistBean("Q", q));
                        cityList.add(new CitylistBean("R", r));
                        cityList.add(new CitylistBean("S", s));
                        cityList.add(new CitylistBean("T", t));
                        cityList.add(new CitylistBean("W", w));
                        cityList.add(new CitylistBean("X", x));
                        cityList.add(new CitylistBean("Y", y));
                        cityList.add(new CitylistBean("Z", z));
                        cityList2.add("A");
                        cityList2.add("B");
                        cityList2.add("C");
                        cityList2.add("D");
                        cityList2.add("E");
                        cityList2.add("F");
                        cityList2.add("G");
                        cityList2.add("H");
                        cityList2.add("I");
                        cityList2.add("J");
                        cityList2.add("K");
                        cityList2.add("L");
                        cityList2.add("M");
                        cityList2.add("N");
                        cityList2.add("O");
                        cityList2.add("P");
                        cityList2.add("Q");
                        cityList2.add("R");
                        cityList2.add("S");
                        cityList2.add("T");
                        cityList2.add("U");
                        cityList2.add("V");
                        cityList2.add("W");
                        cityList2.add("X");
                        cityList2.add("Y");
                        cityList2.add("Z");
                        cityAdater.addData(cityList);
                        for (int i = 0; i < cityAdater.getGroupCount(); i++) {
                            lvCity.expandGroup(i);
                        }
                    } else {
                        ToastUtils.show(cityBean.getMsg());
                    }
                } else {
                    ToastUtils.show(getString(R.string.tv_check_service));
                }
            }
        });
    }
}
