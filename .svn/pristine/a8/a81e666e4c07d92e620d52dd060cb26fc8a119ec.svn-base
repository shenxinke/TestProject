package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.IsPayPwdBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 只需要得到code的回调
 *
 * @author jiaofan
 * @version 4.0.0
 * @date 2018/3/23
 */
public abstract class AbstractIsPayPwdCallback extends Callback<IsPayPwdBean> {
    @Override
    public IsPayPwdBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("response\n", string);
        return new Gson().fromJson(string, IsPayPwdBean.class);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        MyLog.e("zzz", e.toString());
    }
}
