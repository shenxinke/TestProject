package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.UserFocusBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 商品收藏的回调
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractFocusCallback extends Callback<UserFocusBean>{
    @Override
    public UserFocusBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        UserFocusBean codeMsgBean = new Gson().fromJson(string, UserFocusBean.class);
        return codeMsgBean;
    }
}