package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.tickets.TableTicketsBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 只需要得到code的回调
 *
 * @author wangjingwei
 * @version 4.0.0
 * @date 2018/3/29.
 */
public abstract class AbstractTableTicketsBeanCallback extends Callback<TableTicketsBean>{
    @Override
    public TableTicketsBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("response",string);
        TableTicketsBean mTableTicketsBean = new Gson().fromJson(string, TableTicketsBean.class);
        return mTableTicketsBean;
    }
}
