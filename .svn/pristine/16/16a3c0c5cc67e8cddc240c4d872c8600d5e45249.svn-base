package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.product.ProductDetailBean;
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
public abstract class AbstractProductDetailCallback extends Callback<ProductDetailBean>{
    @Override
    public ProductDetailBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("ProductDetailBean == ",string);
        ProductDetailBean codeMsgBean = new Gson().fromJson(string, ProductDetailBean.class);
        return codeMsgBean;
    }
}
