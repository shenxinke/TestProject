package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.wechat.WeChatInfoEntry;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 微信支付
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractWechatPayCallBack extends Callback<WeChatInfoEntry> {

    @Override
    public WeChatInfoEntry parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("WeChat\n",string);
        WeChatInfoEntry bean = new Gson().fromJson(string, WeChatInfoEntry.class);
        return bean;
    }
}
