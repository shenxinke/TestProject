package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.PxVideoCommentBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 品秀视频评论回调
 *
 * @author WangJingWei
 * @version 4.2.0
 * @date 2018/6/8
 */
public abstract class AbstractPxVideoCommentCallback extends Callback<PxVideoCommentBean>{
    @Override
    public PxVideoCommentBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        PxVideoCommentBean liveVideoBean = new Gson().fromJson(string, PxVideoCommentBean.class);
        return liveVideoBean;
    }
}