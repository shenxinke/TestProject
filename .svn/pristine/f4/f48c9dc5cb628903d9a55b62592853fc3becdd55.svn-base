package com.yst.onecity.interfaces;

import com.google.gson.Gson;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.ToastUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * code和message的回调
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/1/5
 */
public abstract class AbstractCodeMsgCallBack extends Callback<CodeMsgBean> {

    @Override
    public CodeMsgBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("response", string);
        CodeMsgBean bean = new Gson().fromJson(string, CodeMsgBean.class);
        return bean;
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        ToastUtils.show("请求失败");
    }
}
