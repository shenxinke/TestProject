package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.livevideo.LiveOnLineNum;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 直播在线人数
 *
 * @author WangJingWei
 * @version 4.1.0
 * @date 2018/5/18.
 */
public abstract class AbstractLiveOnLineNumCallback extends Callback<LiveOnLineNum>{

    @Override
    public LiveOnLineNum parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        LiveOnLineNum liveOnLineNum = new Gson().fromJson(string, LiveOnLineNum.class);
        return liveOnLineNum;
    }
}
