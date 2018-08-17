package com.yst.onecity.bean.tickets;

import java.util.List;

/**
 * 开奖消息实体类
 *
 * @author WangJingWei
 * @version 4.0.0
 * @date 2018/3/22.
 */
public class TicketsBroadcastBean {
    private int code;
    private String msg;
    private List<BroadcastInfo> content;

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

    public List<BroadcastInfo> getContent() {
        return content;
    }

    public void setContent(List<BroadcastInfo> content) {
        this.content = content;
    }

    public static class BroadcastInfo {
        private String id;
        private String nickname;
        private String winAPrizeName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getWinAPrizeName() {
            return winAPrizeName;
        }

        public void setWinAPrizeName(String winAPrizeName) {
            this.winAPrizeName = winAPrizeName;
        }
    }

}
