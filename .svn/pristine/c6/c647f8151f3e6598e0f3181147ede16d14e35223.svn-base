package com.yst.onecity.interfaces;

import com.google.gson.Gson;
import com.yst.onecity.bean.livevideo.VideoDetailInfo;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 视频详情数据回调
 * @author wuxiaofang
 * @version 1.0.1
 * @date 2018/05/16
 */

public abstract class AbstractVideoDetailsCallBack extends Callback<VideoDetailInfo>{

    @Override
    public VideoDetailInfo parseNetworkResponse(Response response, int id) throws Exception {
        String string =response.body().string();
        MyLog.e("string",string);
        VideoDetailInfo videoDetailsInfo = new Gson().fromJson(string, VideoDetailInfo.class);
        return videoDetailsInfo;
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        MyLog.e("ok-onError",e.getMessage());
    }
}
