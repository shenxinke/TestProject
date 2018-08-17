package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.tickets.TicketsBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 我的奖券
 *
 * @author WangJingWei
 * @version 4.0.0
 * @date 2018/3/20
 */
public abstract class AbstractTicketsListCallback extends Callback<TicketsBean>{
    @Override
    public TicketsBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("respone\n",string);
        TicketsBean mTicketsBean = new Gson().fromJson(string, TicketsBean.class);
        return mTicketsBean;
    }
}
