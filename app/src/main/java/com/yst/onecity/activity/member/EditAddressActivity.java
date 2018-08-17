package com.yst.onecity.activity.member;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
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
import com.yst.onecity.R;
import com.yst.onecity.activity.amap.Location;
import com.yst.onecity.adapter.AbstractCommonAdapter;
import com.yst.onecity.adapter.CommonViewHolder;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 地图定位
 *
 * @author wuxiaofang
 * @version 4.0.1
 * @date 2018/03/22
 */
public class EditAddressActivity extends Activity implements AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener {

    ListView listView;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_locate)
    TextView tvLocate;
    @BindView(R.id.searchBarLayout)
    LinearLayout searchBarLayout;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.activity_shop_address)
    LinearLayout activityShopAddress;
    private AbstractCommonAdapter<PoiItem> adapter;
    /**地图部分*/
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
    private int index = 0;
    private String shopName;
    private int flag;
    private String[] exreRnalPermission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int LOCATION_CODE = 100;

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

    public static void navAmaPMarketActivity(Activity context, LatLng myLatLng, String street, int requestCode) {
        Intent intent = new Intent(context, EditAddressActivity.class);
        intent.putExtra("myLatLng", myLatLng);
        intent.putExtra("street", street);
        intent.putExtra("flag", 1);
        context.startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_address);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvMainTitle.setText("编辑收货地址");
        initData();
    }

    @OnClick(R.id.searchBarLayout)
    public void search(){
//        KeyBoardUtils.openKeybord();
    }
    private void initData() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.showMyLocation(true);
        ////定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.strokeColor(0x88D4E2EF);
        myLocationStyle.radiusFillColor(0x88D4E2EF);
        // 自定义系统定位小蓝点
//            myLocationStyle.myLocationIcon(BitmapDescriptorFactory
//                    .fromResource(R.mipmap.hospitaladdress3));// 设置小蓝点的图标
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
///       aMap.setMyLocationEnabled(true);
        //手动移动地图监听
        aMap.setOnCameraChangeListener(this);
        // 设置默认定位按钮是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        aMap.getUiSettings().setZoomControlsEnabled(false);
        //设置显示地图的默认比例尺
        aMap.getUiSettings().setScaleControlsEnabled(true);

        if (latlng != null && latlng.longitude != 0 && latlng.latitude != 0 && !TextUtils.isEmpty(streetName)) {
///            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18));
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));
//            poiSearch(streetName);
        } else {
            Log.e("sss", "重新定位");
            initLocation();
        }
        //------------------------------------------添加中心标记
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
        mGeocoderSearch = new GeocodeSearch(this);
        mGeocoderSearch.setOnGeocodeSearchListener(this);

    }

    private void initLocation() {
        if (EasyPermissions.hasPermissions(EditAddressActivity.this, exreRnalPermission)) {
            startLocation();
        } else {
            Log.e("TAG", ">>>onClick---" + "请求权限");
            EasyPermissions.requestPermissions(this, "请打开定位权限", LOCATION_CODE, exreRnalPermission);
        }
    }

    private void startLocation() {
        //启动定位
        new Location(locationCallback).startLocation();
    }

    Location.LocationCallback locationCallback = new Location.LocationCallback() {
        @Override
        public void locSuccess(AMapLocation amapLocation) {
            mGeocoderSearch = new GeocodeSearch(EditAddressActivity.this);
            mGeocoderSearch.setOnGeocodeSearchListener(EditAddressActivity.this);
            longitude = amapLocation.getLongitude();
            latitude = amapLocation.getLatitude();

            Log.e("onLocationChanged", "********" + longitude + "---" + latitude);
            String address = amapLocation.getPoiName();
            // 设置初始地图的中心位置
            if (latlng == null || latlng.latitude == 0 || latlng.longitude == 0) {
                latlng = new LatLng(latitude, longitude);
            }
            if (aMap == null) {
                aMap = mapView.getMap();
                //设置中心点和缩放比例
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18));
//                poiSearch(streetName);
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));

            }
            aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        }

        @Override
        public void locFailure(int code, String errorInfo) {
            ToastUtils.show(errorInfo);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
//        locationAddress();
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

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        latlng = cameraPosition.target;
        mCurrentPoint = new LatLonPoint(cameraPosition.target.
                latitude, cameraPosition.target.longitude);
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(mCurrentPoint, 1000, GeocodeSearch.AMAP);
        // 设置同步逆地理编码请求
        mGeocoderSearch.getFromLocationAsyn(query);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == Const.INTEGER_1000) {
            if (result != null && result.getRegeocodeAddress() != null &&
                    result.getRegeocodeAddress().getFormatAddress() != null) {
                /**
                 * 汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|
                 * 体育休闲服务|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体
                 * |科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
                 */
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

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == Const.INTEGER_1000) {
            // 搜索poi的结果
            if (result != null && result.getQuery() != null) {
                // 取得第一页的poiitem数据，页数从数字0开始
                poiItems = result.getPois();
                shopName = poiItems.get(0).getTitle();
                initCtcl();
                if (isSearch) {
                    isSearch = false;
                    aMap.clear();
                    longitude = poiItems.get(0).getLatLonPoint().getLongitude();
                    latitude = poiItems.get(0).getLatLonPoint().getLatitude();
                    LatLng latLng = new LatLng(latitude, longitude);
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                    aMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
                }

            } else {
                ToastUtils.show("对不起，没有搜索到相关数据！");
            }
        }
    }

    private void initCtcl() {
        adapter = new AbstractCommonAdapter<PoiItem>(getApplicationContext(), poiItems, R.layout.item_map) {
            @Override
            public void convert(CommonViewHolder holder, int position, PoiItem item) {
                holder.setText(R.id.tv_title, item.getTitle());
                holder.setText(R.id.tv_address, item.getSnippet());

///                tvRight.setEnabled(true);
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                shopName = poiItems.get(i).getTitle();
                adapter.notifyDataSetChanged();

                longitude = poiItems.get(index).getLatLonPoint().getLongitude();
                latitude = poiItems.get(index).getLatLonPoint().getLatitude();

                Intent intent = new Intent();
                intent.putExtra("result", shopName);
                intent.putExtra("longitude", String.valueOf(longitude));
                intent.putExtra("latitude", String.valueOf(latitude));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            // 如果不能匹配,则该字符是Emoji表情
            if (!isEmojiCharacter(codePoint)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

}
