package com.yst.onecity.callbacks;

import android.util.Log;

import com.google.gson.Gson;
import com.yst.onecity.bean.order.ServerOnLineDetailBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 会员线上订单详情
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractMemberOnLineDetailsCallBack extends Callback<ServerOnLineDetailBean> {

    @Override
    public ServerOnLineDetailBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Log.e("response\n", string);
        ServerOnLineDetailBean bean = new Gson().fromJson(string, ServerOnLineDetailBean.class);
        return bean;
    }
}
