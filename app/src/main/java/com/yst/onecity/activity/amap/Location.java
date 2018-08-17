package com.yst.onecity.activity.amap;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.utils.MyLog;

/**
 * 高德地图定位工具类
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class Location {
    /**
     * 声明AMapLocationClient类对象
     */
    public AMapLocationClient mLocationClient = null;
    /**
     * 声明AMapLocationClientOption对象
     */
    public AMapLocationClientOption mLocationOption = null;
    private LocationCallback locationCallback;

    public Location(LocationCallback locationCallback) {
        this.locationCallback = locationCallback;
    }

    /**
     * 初始化定位配置信息
     */
    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(TianyiApplication.getInstance().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mAMapLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(2000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        initLocation();
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 声明定位回调监听器
     */
    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation == null) {
                return;
            }
            if (amapLocation.getErrorCode() == 0) {
                if (locationCallback != null) {
                    locationCallback.locSuccess(amapLocation);
                }
                MyLog.e("luxc", "高德地图定位结果" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                MyLog.e("高德地图定位结果", "错误码: " + amapLocation.getErrorCode() + ", 错误信息:" + amapLocation.getErrorInfo());
                if (locationCallback != null) {
                    locationCallback.locFailure(amapLocation.getErrorCode(), amapLocation.getErrorInfo());
                }
            }
        }
    };

    /**
     * 定位结果回调
     */
    public interface LocationCallback {
        /**
         * 成功
         * @param amapLocation
         */
        void locSuccess(AMapLocation amapLocation);

        /**
         * 失败
         * @param code
         * @param errorInfo
         */
        void locFailure(int code, String errorInfo);
    }

}
