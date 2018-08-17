package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.order.OrderComment;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 订单评价
 *
 * @author WangJingWei
 * @version 4.2.0
 * @date 2018/5/31
 */
public abstract class AbstractOrderCommentCallback extends Callback<OrderComment>{
    @Override
    public OrderComment parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        OrderComment mOrderComment = new Gson().fromJson(string, OrderComment.class);
        return mOrderComment;
    }
}
