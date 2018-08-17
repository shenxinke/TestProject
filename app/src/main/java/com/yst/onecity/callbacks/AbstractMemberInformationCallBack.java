package com.yst.onecity.callbacks;

import android.util.Log;

import com.google.gson.Gson;
import com.yst.onecity.bean.information.InformationBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 资讯
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractMemberInformationCallBack extends Callback<InformationBean> {

    @Override
    public InformationBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Log.e("response\n", string);
        InformationBean bean = new Gson().fromJson(string, InformationBean.class);
        return bean;
    }
}
