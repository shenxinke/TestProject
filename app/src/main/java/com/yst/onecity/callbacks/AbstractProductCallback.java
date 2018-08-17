package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.information.ProductContentBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 添加商品
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractProductCallback extends Callback<ProductContentBean>{
    @Override
    public ProductContentBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("respone\n",string);
        ProductContentBean codeMsgBean = new Gson().fromJson(string, ProductContentBean.class);
        return codeMsgBean;
    }
}
