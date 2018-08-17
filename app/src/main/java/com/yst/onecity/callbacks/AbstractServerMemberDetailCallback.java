package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.servermember.ServerMemberDetailBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 搜索服务专员的回调
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractServerMemberDetailCallback extends Callback<ServerMemberDetailBean> {
    @Override
    public ServerMemberDetailBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("@@ response",string);
        ServerMemberDetailBean serverMemberDetailBean = new Gson().fromJson(string, ServerMemberDetailBean.class);
        return serverMemberDetailBean;
    }
}
