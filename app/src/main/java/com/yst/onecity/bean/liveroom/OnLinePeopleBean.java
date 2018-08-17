package com.yst.onecity.bean.liveroom;

/**
 * @author Shenxinke
 * @version 4.1.0
 * @data 2018/5/21
 *
 * 直播在线人数
 */

public class OnLinePeopleBean {

    /**
     * code : 1
     * msg : 获取直播在线人数成功!
     * content : 1
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
