package com.yst.onecity.callbacks;

import android.widget.Toast;

import com.google.gson.Gson;
import com.yst.onecity.R;
import com.yst.onecity.bean.BalanceNumBean;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.ToastUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 只需要得到code的回调
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractBalanceNumCallback extends Callback<BalanceNumBean>{
    @Override
    public BalanceNumBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("response\n",string);
        return new Gson().fromJson(string, BalanceNumBean.class);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        ToastUtils.show(R.string.app_on_request_error_msg, Toast.LENGTH_SHORT);
        MyLog.e("zzz", e.toString());
    }
}
