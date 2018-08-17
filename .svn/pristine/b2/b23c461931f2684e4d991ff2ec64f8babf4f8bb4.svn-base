package com.yst.onecity.activity.servermember;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.StoreDetailBean;
import com.yst.onecity.callbacks.AbstractStoreDetailCallBack;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.SignUtils;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.MapContainer;
import com.yst.onecity.view.roundedimageview.RoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 店铺详情
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public class StoreDetailActivity extends BaseActivity {
    @BindView(R.id.background)
    ImageView background;
    @BindView(R.id.merchantImg)
    RoundedImageView merchantImg;
    @BindView(R.id.store_name)
    TextView storeName;
    @BindView(R.id.merchantIntroduce)
    TextView merchantIntroduce;
    @BindView(R.id.store_phone)
    TextView storePhone;
    @BindView(R.id.store_address)
    TextView storeAddress;
    @BindView(R.id.iv_address)
    MapView mapView;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.map_container)
    MapContainer mapContainer;

    private String merchantId;
    private AMap aMap;
    private Marker mCenterMarker;
    private MarkerOptions mMarkerOptions;
    public LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        mapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        // 设置默认定位按钮是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        aMap.getUiSettings().setZoomControlsEnabled(true);
        aMap.getUiSettings().setAllGesturesEnabled(false);
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

    @Override
    public int bindLayout() {
        return R.layout.activity_store_detail;
    }

    @Override
    public void initData() {
        initTitleBar("店铺详情");
        if (getIntent() != null) {
            merchantId = getIntent().getStringExtra("merchantId");
        }
        mapContainer.setScrollView(scrollView);
        if (!TextUtils.isEmpty(merchantId)) {
            getMerchantDetail();
        }
    }

    /**
     * 获取店铺详情信息
     */
    private void getMerchantDetail() {
        String timestamp = SignUtils.getTimeStamp();
        String sign = Utils.getSign("timestamp", timestamp, "merchantid", merchantId);
        OkHttpUtils.post().url(Constants.GET_MERCHANT_DETAIL)
                .addParams("timestamp", timestamp)
                .addParams("client_type", "A")
                .addParams("merchantid", merchantId)
                .addParams("sign", sign)
                .build().execute(new AbstractStoreDetailCallBack() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getString(R.string.str_net_error_message));
            }

            @Override
            public void onResponse(StoreDetailBean response, int id) {
                if (response.getCode() == 1) {
                    String url = (response.getContent().getAttachmentImg() == null ? "" : response.getContent().getAttachmentImg());
                    Glide.with(StoreDetailActivity.this).load(url).error(R.mipmap.img_default_p).into(background);
                    storeName.setText(response.getContent().getMerchantName());
                    storePhone.setText(response.getContent().getTelPhone());
                    String introduce = (TextUtils.isEmpty(response.getContent().getDescription()) ? "店铺未设置简介" : response.getContent().getDescription());
                    merchantIntroduce.setText(introduce);
                    storeAddress.setText(response.getContent().getAddress());

                    latLng = new LatLng(Double.parseDouble(response.getContent().getLatitude()), Double.parseDouble(response.getContent().getLongitude()));
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                }
            }
        });
    }

    @OnClick({R.id.ll_back, R.id.store_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.store_phone:
                if (TextUtils.isEmpty(storePhone.getText().toString().trim())) {
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + storePhone.getText().toString().trim()));
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
