package com.yst.onecity.bean.consult;

import java.util.List;

/**
 * 排序实体
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/0/19
 */
public class ConsultSortBean {

    /**
     * code : 1
     * msg : 查询成功!
     * content : [{"created_time":1506042810000,"update_time":1506042810000,"user_type":0,"consultation_classify_id":1,"consultation_classify_name":"咨询分类11","user_id":1,"id":1,"create_user":"","created_ip":""}]
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
         * created_time : 1506042810000
         * update_time : 1506042810000
         * user_type : 0
         * consultation_classify_id : 1
         * consultation_classify_name : 咨询分类11
         * user_id : 1
         * id : 1
         * create_user :
         * created_ip :
         */

        private long created_time;
        private long update_time;
        private int user_type;
        private int consultation_classify_id;
        private String consultation_classify_name;
        private int user_id;
        private int id;
        private String create_user;
        private String created_ip;

        public long getCreated_time() {
            return created_time;
        }

        public void setCreated_time(long created_time) {
            this.created_time = created_time;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public int getUser_type() {
            return user_type;
        }

        public void setUser_type(int user_type) {
            this.user_type = user_type;
        }

        public int getConsultation_classify_id() {
            return consultation_classify_id;
        }

        public void setConsultation_classify_id(int consultation_classify_id) {
            this.consultation_classify_id = consultation_classify_id;
        }

        public String getConsultation_classify_name() {
            return consultation_classify_name;
        }

        public void setConsultation_classify_name(String consultation_classify_name) {
            this.consultation_classify_name = consultation_classify_name;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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

        public String getCreated_ip() {
            return created_ip;
        }

        public void setCreated_ip(String created_ip) {
            this.created_ip = created_ip;
        }
    }
}
