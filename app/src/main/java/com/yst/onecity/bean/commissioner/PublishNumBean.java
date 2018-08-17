package com.yst.onecity.bean.commissioner;

/**
 * 获取分享的列表条数的bean
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/9
 */

public class PublishNumBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"publishedNum":5,"underReviewNum":5,"notPassNum":5,"draftBoxNum":3}
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
         * publishedNum : 5
         * underReviewNum : 5
         * notPassNum : 5
         * draftBoxNum : 3
         */

        private int publishedNum;
        private int underReviewNum;
        private int notPassNum;
        private int draftBoxNum;

        public int getPublishedNum() {
            return publishedNum;
        }

        public void setPublishedNum(int publishedNum) {
            this.publishedNum = publishedNum;
        }

        public int getUnderReviewNum() {
            return underReviewNum;
        }

        public void setUnderReviewNum(int underReviewNum) {
            this.underReviewNum = underReviewNum;
        }

        public int getNotPassNum() {
            return notPassNum;
        }

        public void setNotPassNum(int notPassNum) {
            this.notPassNum = notPassNum;
        }

        public int getDraftBoxNum() {
            return draftBoxNum;
        }

        public void setDraftBoxNum(int draftBoxNum) {
            this.draftBoxNum = draftBoxNum;
        }
    }
}
