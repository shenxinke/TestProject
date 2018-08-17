package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.StoreDetailBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 会员列表的回调
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractStoreDetailCallBack extends Callback<StoreDetailBean> {
    @Override
    public StoreDetailBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("@@ storeDetailBean ",string);
        StoreDetailBean storeDetailBean = new Gson().fromJson(string, StoreDetailBean.class);
        return storeDetailBean;
    }
}
