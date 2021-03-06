package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.information.NewsTypeBean;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.ToastUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 资讯分类回调
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractNewsTypeCallback extends Callback<NewsTypeBean>{
    @Override
    public NewsTypeBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("@@@@@@@@@@@@@",string);
        NewsTypeBean bean = new Gson().fromJson(string, NewsTypeBean.class);
        return bean;
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        MyLog.e("error",e.getMessage());
        ToastUtils.show("网络访问失败，请重试!");
    }
}
