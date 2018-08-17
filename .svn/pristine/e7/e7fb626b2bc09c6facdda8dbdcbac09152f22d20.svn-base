package com.yst.onecity.bean.chatmodel;

import android.content.Context;

import com.yst.onecity.utils.MyLog;

import com.tencent.TIMCustomElem;
import com.tencent.TIMMessage;
import com.yst.onecity.adapter.chatadapter.ChatAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 自定义消息
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class CustomMessage extends AbstractMessage {

    private final int TYPE_TYPING = 14;
    private String eimamsginputstatusend = "EIMAMSG_InputStatus_End";
    private Type type;
    private String desc;
    private String data;

    public CustomMessage(TIMMessage message) {
        this.message = message;
        TIMCustomElem elem = (TIMCustomElem) message.getElement(0);
        parse(elem.getData());

    }

    public CustomMessage(Type type) {
        message = new TIMMessage();
        String data = "";
        JSONObject dataJson = new JSONObject();
        try {
            switch (type) {
                case TYPING:
                    dataJson.put("userAction", TYPE_TYPING);
                    dataJson.put("actionParam", "EIMAMSG_InputStatus_Ing");
                    data = dataJson.toString();
                default:
                    break;
            }
        } catch (JSONException e) {
            MyLog.e(TAG, "generate json error");
        }
        TIMCustomElem elem = new TIMCustomElem();
        elem.setData(data.getBytes());
        message.addElement(elem);
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    private void parse(byte[] data) {
        type = Type.INVALID;
        try {
            String str = new String(data, "UTF-8");
            JSONObject jsonObj = new JSONObject(str);
            int action = jsonObj.getInt("userAction");
            switch (action) {
                case TYPE_TYPING:
                    type = Type.TYPING;
                    this.data = jsonObj.getString("actionParam");
                    if (eimamsginputstatusend.equals(this.data)) {
                        type = Type.INVALID;
                    }
                    break;
                default:
                    break;
            }

        } catch (IOException | JSONException e) {
            MyLog.e(TAG, "parse json error");
        }
    }

    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     * @param context    显示消息的上下文
     */
    @Override
    public void showMessage(ChatAdapter.ViewHolder viewHolder, Context context) {

    }

    /**
     * 获取消息摘要
     */
    @Override
    public String getSummary() {
        return null;
    }

    /**
     * 保存消息或消息文件
     */
    @Override
    public void save() {

    }

    public enum Type {
        /**
         * 正在输入
         */
        TYPING,
        /**
         * 无效的
         */
        INVALID,
    }
}
