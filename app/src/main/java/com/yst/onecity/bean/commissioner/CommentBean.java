package com.yst.onecity.bean.commissioner;

import java.util.List;

/**
 * 评价的bean
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/7
 */

public class CommentBean {


    /**
     * code : 1
     * msg : 获取成功
     * content : [{"created_time":"17-05-11","address":null,"name":"何丽霞","id":77,"content":"很无语，发货员不识字吗？150的电话填158，裕华区填桥西区，位同新村填委同心村"}]
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
         * created_time : 17-05-11
         * address : null
         * name : 何丽霞
         * id : 77
         * content : 很无语，发货员不识字吗？150的电话填158，裕华区填桥西区，位同新村填委同心村
         */

        private String created_time;
        private Object address;
        private String name;
        private int id;
        private String content;

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
