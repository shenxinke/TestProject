package com.yst.onecity.bean;

import java.util.List;

/**
 * 用户的关注列表实体类
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class UserFocusBean {

    private int code;
    private String msg;
    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {

        private String hunter_id;
        private String member_id;
        private String address;
        private String nickname;
        private String id;
        private String type;

        public String getHunter_id() {
            return hunter_id;
        }

        public void setHunter_id(String hunter_id) {
            this.hunter_id = hunter_id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
