package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.livevideo.VideoComment;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 首页视频评论列表页面
 *
 * @author WangJingWei
 * @version 4.1.0
 * @date 2018/5/14
 */
public abstract class AbstractVideoCommentListCallback extends Callback<VideoComment>{
    @Override
    public VideoComment parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        VideoComment videoComment = new Gson().fromJson(string, VideoComment.class);
        return videoComment;
    }
}
