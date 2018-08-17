package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.servermember.ProductListBean;
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
public abstract class AbstractProductListCallBack extends Callback<ProductListBean> {
    @Override
    public ProductListBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("@@ productList ",string);
        ProductListBean productListBean = new Gson().fromJson(string, ProductListBean.class);
        return productListBean;
    }
}