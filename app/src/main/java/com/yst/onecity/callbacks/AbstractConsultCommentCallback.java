package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.consult.ConsultCommentBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 咨询评价的回调
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractConsultCommentCallback extends Callback<ConsultCommentBean>{
    @Override
    public ConsultCommentBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("zixun commment",string);
        ConsultCommentBean consultCommentBean = new Gson().fromJson(string, ConsultCommentBean.class);
        return consultCommentBean;
    }
}
