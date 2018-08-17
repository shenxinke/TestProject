package com.yst.onecity.bean;

/**
 * 可用余额实体类
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/3/23
 */
public class BalanceNumBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"score":0,"xyt":0,"integral":0}
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
         * 总余额
         * score : 0
         *
         * 冻结金额
         * xyt : 0
         *
         * 积分
         * integral : 0
         */

        private String score;
        private String isXyt;
        private String integral;

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getIsXyt() {
            return isXyt;
        }

        public void setIsXyt(String isXyt) {
            this.isXyt = isXyt;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }
    }
}
