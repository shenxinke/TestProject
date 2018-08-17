package com.yst.onecity.bean;

/**
 * 获取专员的列表条数
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/8
 */

public class ListCountBean {

    /**
     * code : 1
     * msg : 成功
     * content : {"memberCount":1,"productNum":8,"hunterCommentNum":1,"consultationNum":0}
     */

    private int code;
    private String msg;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * memberCount : 1
         * productNum : 8
         * hunterCommentNum : 1
         * consultationNum : 0
         */

        private int memberCount;
        private int productNum;
        private int hunterCommentNum;
        private int consultationNum;

        public int getMemberCount() {
            return memberCount;
        }

        public void setMemberCount(int memberCount) {
            this.memberCount = memberCount;
        }

        public int getProductNum() {
            return productNum;
        }

        public void setProductNum(int productNum) {
            this.productNum = productNum;
        }

        public int getHunterCommentNum() {
            return hunterCommentNum;
        }

        public void setHunterCommentNum(int hunterCommentNum) {
            this.hunterCommentNum = hunterCommentNum;
        }

        public int getConsultationNum() {
            return consultationNum;
        }

        public void setConsultationNum(int consultationNum) {
            this.consultationNum = consultationNum;
        }
    }
}
