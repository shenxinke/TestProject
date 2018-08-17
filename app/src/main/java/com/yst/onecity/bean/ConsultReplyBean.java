package com.yst.onecity.bean;

import java.util.List;

/**
 * 回复评论
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ConsultReplyBean {

    /**
     * code : 1
     * msg : 成功
     * content : [{"created_time":"2017-09-29 17:34:19","consultation_id":9,"user_type":null,"address":"upload/skysimple/20170926184206341465695.png","user_id":null,"parent_id":null,"nickname":"hyh","id":19,"type":9,"content":"啊啊啊啊"},{"created_time":"2017-09-29 16:15:25","consultation_id":9,"user_type":null,"address":"upload/skysimple/20170926184206341465695.png","user_id":null,"parent_id":null,"nickname":"hyh","id":18,"type":9,"content":"jjj"},{"created_time":"2017-09-29 16:04:40","consultation_id":9,"user_type":null,"address":"upload/skysimple/20170926184206341465695.png","user_id":null,"parent_id":null,"nickname":"hyh","id":17,"type":9,"content":"iii"},{"created_time":"2017-09-29 15:53:20","consultation_id":9,"user_type":null,"address":"upload/skysimple/20170926184206341465695.png","user_id":null,"parent_id":null,"nickname":"hyh","id":16,"type":9,"content":"uuuuuu"},{"created_time":"2017-09-29 14:50:46","consultation_id":9,"user_type":null,"address":"upload/skysimple/20170926184206341465695.png","user_id":null,"parent_id":null,"nickname":"hyh","id":15,"type":9,"content":"66666666666666"},{"created_time":"2017-09-26 16:49:21","consultation_id":9,"user_type":null,"address":"upload/skysimple/20170926184206341465695.png","user_id":null,"parent_id":null,"nickname":"hyh","id":12,"type":0,"content":"11111111评论评论评论评论评论评论评论评论"},{"created_time":"2017-09-26 16:27:20","consultation_id":9,"user_type":null,"address":"upload/skysimple/20170926184206341465695.png","user_id":null,"parent_id":null,"nickname":"hyh","id":11,"type":9,"content":"11111111评论评论评论评论评论评论评论评论"},{"created_time":"2017-09-26 16:22:55","consultation_id":9,"user_type":null,"address":"upload/skysimple/20170926184206341465695.png","user_id":null,"parent_id":null,"nickname":"hyh","id":10,"type":9,"content":"评论评论评论评论评论评论评论评论"}]
     */

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
        /**
         * created_time : 2017-09-29 17:34:19
         * consultation_id : 9
         * user_type : null
         * address : upload/skysimple/20170926184206341465695.png
         * user_id : null
         * parent_id : null
         * nickname : hyh
         * id : 19
         * type : 9
         * content : 啊啊啊啊
         */

        private String created_time;
        private int consultation_id;
        private Object user_type;
        private String address;
        private Object user_id;
        private Object parent_id;
        private String nickname;
        private int id;
        private int type;
        private String content;

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public int getConsultation_id() {
            return consultation_id;
        }

        public void setConsultation_id(int consultation_id) {
            this.consultation_id = consultation_id;
        }

        public Object getUser_type() {
            return user_type;
        }

        public void setUser_type(Object user_type) {
            this.user_type = user_type;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getUser_id() {
            return user_id;
        }

        public void setUser_id(Object user_id) {
            this.user_id = user_id;
        }

        public Object getParent_id() {
            return parent_id;
        }

        public void setParent_id(Object parent_id) {
            this.parent_id = parent_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
