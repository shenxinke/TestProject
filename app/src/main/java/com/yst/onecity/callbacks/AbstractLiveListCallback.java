package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.livevideo.LiveVideoBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 首页直播列表页面
 *
 * @author WangJingWei
 * @version 4.1.0
 * @date 2018/5/14
 */
public abstract class AbstractLiveListCallback extends Callback<LiveVideoBean>{
    @Override
    public LiveVideoBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        LiveVideoBean liveVideoBean = new Gson().fromJson(string, LiveVideoBean.class);
        return liveVideoBean;
    }
}
