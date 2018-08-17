package com.yst.onecity.activity.servermember;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.yst.onecity.R;
import com.yst.onecity.utils.MyLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 服务专员基本信息地图页面
 *
 * @author Chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class EssentialInfoMapActivity extends AppCompatActivity {

    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.activity_essential_info_map)
    LinearLayout activityEssentialInfoMap;
    private AMap aMap;
    private Marker mCenterMarker;
    private MarkerOptions mMarkerOptions;
    public LatLng latLng;
    private String lng, lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essential_info_map);
        ButterKnife.bind(this);
        // 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        mapView.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            lng = getIntent().getExtras().getString("lng", "");
            lat = getIntent().getExtras().getString("lat", "");
        }
        latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        MyLog.e("essmap", lng + "-----" + lat + "___" + latLng);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
            //设置中心点和缩放比例
            MyLocationStyle myLocationStyle = new MyLocationStyle();
            myLocationStyle.showMyLocation(true);
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
            myLocationStyle.strokeColor(0x88D4E2EF);
            myLocationStyle.radiusFillColor(0x88D4E2EF);
            //设置定位蓝点的Style
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.setMyLocationEnabled(false);
            // 设置默认定位按钮是否显示
            aMap.getUiSettings().setMyLocationButtonEnabled(false);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17.5f));
            aMap.getUiSettings().setZoomControlsEnabled(false);

            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        }
        //------------------------------------------添加中心标记
        mMarkerOptions = new MarkerOptions();
        //可拖放性
        mMarkerOptions.draggable(false);
        mMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_choose_map_address));
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
}
