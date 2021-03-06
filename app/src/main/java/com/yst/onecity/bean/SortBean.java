package com.yst.onecity.bean;

import java.util.List;

/**
 * 资讯分类
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class SortBean {

    /**
     * code : 0
     * msg : 查询成功!
     * content : [{"created_time":1505907573000,"update_time":null,"description_name":"咨询分类名称","id":1,"create_user":"","created_ip":null}]
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
         * created_time : 1505907573000
         * update_time : null
         * description_name : 资讯分类名称
         * id : 1
         * create_user :
         * created_ip : null
         */

        private long created_time;
        private Object update_time;
        private String description_name;
        private int id;
        private String create_user;
        private Object created_ip;

        public long getCreated_time() {
            return created_time;
        }

        public void setCreated_time(long created_time) {
            this.created_time = created_time;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }

        public String getDescription_name() {
            return description_name;
        }

        public void setDescription_name(String description_name) {
            this.description_name = description_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreate_user() {
            return create_user;
        }

        public void setCreate_user(String create_user) {
            this.create_user = create_user;
        }

        public Object getCreated_ip() {
            return created_ip;
        }

        public void setCreated_ip(Object created_ip) {
            this.created_ip = created_ip;
        }
    }
}
