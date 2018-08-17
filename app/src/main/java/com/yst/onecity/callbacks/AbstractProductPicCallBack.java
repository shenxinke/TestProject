package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.product.ProductPicListBean;
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
public abstract class AbstractProductPicCallBack extends Callback<ProductPicListBean>{
    @Override
    public ProductPicListBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("respone\n",string);
        ProductPicListBean codeMsgBean = new Gson().fromJson(string, ProductPicListBean.class);
        return codeMsgBean;
    }
}