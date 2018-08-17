package com.yst.onecity.bean.liveroom;

/**
 * @author Shenxinke
 * @version 4.1.0
 * @data 2018/5/21
 *
 * 消息实体类
 */

public class ChatEntityBean {
    private String grpSendName;
    private String context;
    private int  type;




    public ChatEntityBean() {
        // TODO Auto-generated constructor stub
    }



    public String getSenderName() {
        return grpSendName;
    }

    public void setSenderName(String grpSendName) {
        this.grpSendName = grpSendName;
    }



    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
