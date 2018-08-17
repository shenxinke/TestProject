package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.AddressListEntity;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * 收货地址
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractReceiverAddressListCallBack extends Callback<AddressListEntity> {

    @Override
    public AddressListEntity parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        MyLog.e("RecieveAddress","管理收货地址列表______"+string);
        AddressListEntity entity = new Gson().fromJson(string, AddressListEntity.class);
        return entity;
    }

}
