package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.order.MemberOffLineBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 会员先下订单列表
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractMemberOffLineCallBack extends Callback<MemberOffLineBean> {

    @Override
    public MemberOffLineBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("@@@@@@@@@\n", string);
        MemberOffLineBean bean = new Gson().fromJson(string, MemberOffLineBean.class);
        return bean;
    }
}
