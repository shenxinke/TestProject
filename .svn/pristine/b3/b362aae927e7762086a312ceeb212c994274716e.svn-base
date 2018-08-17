package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.UserCollectionMessageBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 资讯收藏的回调
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractCollectionMessageCallback extends Callback<UserCollectionMessageBean>{
    @Override
    public UserCollectionMessageBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        UserCollectionMessageBean codeMsgBean = new Gson().fromJson(string, UserCollectionMessageBean.class);
        return codeMsgBean;
    }
}
