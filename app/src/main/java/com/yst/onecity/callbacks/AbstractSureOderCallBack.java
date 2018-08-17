package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.addorder.OrderBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 确认订单回调
 *
 * @author jiaofan
 * @version 4.0.0
 * @date 2018/3/21
 */
public abstract class AbstractSureOderCallBack extends Callback<OrderBean> {
    @Override
    public OrderBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("response\n",string);
        return new Gson().fromJson(string, OrderBean.class);
    }
}
