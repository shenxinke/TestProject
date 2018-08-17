package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.order.OrderRewardBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 订单奖励
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractOrderRewardCallBack extends Callback<OrderRewardBean> {

    @Override
    public OrderRewardBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("@@@@@@@@@\n", string);
        OrderRewardBean bean = new Gson().fromJson(string, OrderRewardBean.class);
        return bean;
    }
}
