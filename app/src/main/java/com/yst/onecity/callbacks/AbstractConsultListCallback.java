package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.consult.ConsultListBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 商品收藏的回调
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractConsultListCallback extends Callback<ConsultListBean>{
    @Override
    public ConsultListBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("consult_list ",string);
        return new Gson().fromJson(string, ConsultListBean.class);
    }
}
