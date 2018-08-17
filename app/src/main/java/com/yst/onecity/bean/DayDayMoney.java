package com.yst.onecity.bean;

import java.io.Serializable;

/**
 * 天天奖
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class DayDayMoney implements Serializable {

    /**
     * code : 200
     * msg : 操作成功
     * content : 0.00
     */

    private int code;
    private String msg;
    private String content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
