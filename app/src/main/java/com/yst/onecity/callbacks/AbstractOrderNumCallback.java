package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.order.OrderStatusNumBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 线上订单状态数量
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractOrderNumCallback extends Callback<OrderStatusNumBean>{
    @Override
    public OrderStatusNumBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("@@@@@@@\n",string);
        OrderStatusNumBean bean = new Gson().fromJson(string, OrderStatusNumBean.class);
        return bean;
    }
}
