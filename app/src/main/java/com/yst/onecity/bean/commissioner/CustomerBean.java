package com.yst.onecity.bean.commissioner;

import java.util.List;

/**
 * 客户bean
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/8
 */

public class CustomerBean {

    /**
     * code : 1
     * msg : 获取成功
     * content : [{"logoAttachmentId":"","headImg":"","phone":"13120238966","name":"吴晓芳"}]
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
         * logoAttachmentId :
         * headImg :
         * phone : 13120238966
         * name : 吴晓芳
         */

        private String logoAttachmentId;
        private String headImg;
        private String phone;
        private String name;

        public String getLogoAttachmentId() {
            return logoAttachmentId;
        }

        public void setLogoAttachmentId(String logoAttachmentId) {
            this.logoAttachmentId = logoAttachmentId;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
