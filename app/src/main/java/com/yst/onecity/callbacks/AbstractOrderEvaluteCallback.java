package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.order.EvaluteListBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 线上订单详情
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractOrderEvaluteCallback extends Callback<EvaluteListBean>{
    @Override
    public EvaluteListBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("@@@@@@@@@\n",string);
        EvaluteListBean bean = new Gson().fromJson(string, EvaluteListBean.class);
        return bean;
    }
}
