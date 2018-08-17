package com.yst.onecity.callbacks;

import android.util.Log;

import com.google.gson.Gson;
import com.yst.onecity.bean.order.OnLineOrderDetailBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 线上订单详情
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractOnLineDetailsCallBack extends Callback<OnLineOrderDetailBean> {

    @Override
    public OnLineOrderDetailBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Log.e("response\n", string);
        OnLineOrderDetailBean bean = new Gson().fromJson(string, OnLineOrderDetailBean.class);
        return bean;
    }
}
