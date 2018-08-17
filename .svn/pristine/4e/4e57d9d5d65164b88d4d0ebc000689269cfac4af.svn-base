package com.yst.onecity.callbacks;

import com.google.gson.Gson;
import com.yst.onecity.bean.CodeMsgBean;
import com.yst.onecity.utils.MyLog;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * 只需要得到code的回调
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractCodeMsgCallback extends Callback<CodeMsgBean>{
    @Override
    public CodeMsgBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        MyLog.e("response\n",string);
        CodeMsgBean codeMsgBean = new Gson().fromJson(string, CodeMsgBean.class);
        return codeMsgBean;
    }
}
