package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.consult.ConsultSortBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 评论回复的回调
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractConsultSortBeanCallBack extends Callback<ConsultSortBean> {
    @Override
    public ConsultSortBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("@@ SortBean ",string);
        ConsultSortBean consultSortBean = new Gson().fromJson(string, ConsultSortBean.class);
        return consultSortBean;
    }
}