package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.UserCollectionGoodBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 商品收藏的回调
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractCollectionGoodCallback extends Callback<UserCollectionGoodBean>{
    @Override
    public UserCollectionGoodBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        UserCollectionGoodBean codeMsgBean = new Gson().fromJson(string, UserCollectionGoodBean.class);
        return codeMsgBean;
    }
}
