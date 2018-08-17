package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.servermember.ProductSortBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 商品详情
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractProductSortCallBack extends Callback<ProductSortBean> {
    @Override
    public ProductSortBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("@@ productSortBean ",string);
        ProductSortBean productSortBean = new Gson().fromJson(string, ProductSortBean.class);
        return productSortBean;
    }
}