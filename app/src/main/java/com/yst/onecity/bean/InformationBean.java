package com.yst.onecity.bean;

import java.util.List;

/**
 * @author Shenxinke
 * @version 4.0.0
 * @data 2018/3/30
 */

public class InformationBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"telPhone":"95311","logiName":"中通快递","stateValue":"已签收","orderNum":"535962308717","logisticMessage":[{"context":"[重庆市] [重庆黔江]的派件已签收 感谢使用中通快递,期待再次为您服务!","time":"2017-08-20 17:22:37"},{"context":"[重庆市] [重庆黔江]的阳光花园物业配送中心正在第1次派件 电话:暂无 请保持电话畅通、耐心等待","time":"2017-08-20 16:39:34"},{"context":"[重庆市] 快件到达 [重庆黔江]","time":"2017-08-20 15:51:49"},{"context":"[重庆市] 快件离开 [重庆]已发往[重庆黔江]","time":"2017-08-20 03:47:27"},{"context":"[漯河市] 快件到达 [漯河中转]","time":"2017-08-19 09:17:19"},{"context":"[郑州市] 快件离开 [郑州中转]已发往[重庆中转]","time":"2017-08-17 23:39:27"},{"context":"[郑州市] 快件到达 [郑州]","time":"2017-08-17 23:37:09"},{"context":"[郑州市] 快件离开 [郑州宇通]已发往[重庆]","time":"2017-08-17 20:09:21"},{"context":"[郑州市] [郑州宇通]的陈永军18703650762已收件 电话:18703650762","time":"2017-08-17 16:31:34"}]}
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
         * telPhone : 95311
         * logiName : 中通快递
         * stateValue : 已签收
         * orderNum : 535962308717
         * logisticMessage : [{"context":"[重庆市] [重庆黔江]的派件已签收 感谢使用中通快递,期待再次为您服务!","time":"2017-08-20 17:22:37"},{"context":"[重庆市] [重庆黔江]的阳光花园物业配送中心正在第1次派件 电话:暂无 请保持电话畅通、耐心等待","time":"2017-08-20 16:39:34"},{"context":"[重庆市] 快件到达 [重庆黔江]","time":"2017-08-20 15:51:49"},{"context":"[重庆市] 快件离开 [重庆]已发往[重庆黔江]","time":"2017-08-20 03:47:27"},{"context":"[漯河市] 快件到达 [漯河中转]","time":"2017-08-19 09:17:19"},{"context":"[郑州市] 快件离开 [郑州中转]已发往[重庆中转]","time":"2017-08-17 23:39:27"},{"context":"[郑州市] 快件到达 [郑州]","time":"2017-08-17 23:37:09"},{"context":"[郑州市] 快件离开 [郑州宇通]已发往[重庆]","time":"2017-08-17 20:09:21"},{"context":"[郑州市] [郑州宇通]的陈永军18703650762已收件 电话:18703650762","time":"2017-08-17 16:31:34"}]
         */

        private String telPhone;
        private String logiName;
        private String stateValue;
        private String orderNum;
        private List<LogisticMessageBean> logisticMessage;

        public String getTelPhone() {
            return telPhone;
        }

        public void setTelPhone(String telPhone) {
            this.telPhone = telPhone;
        }

        public String getLogiName() {
            return logiName;
        }

        public void setLogiName(String logiName) {
            this.logiName = logiName;
        }

        public String getStateValue() {
            return stateValue;
        }

        public void setStateValue(String stateValue) {
            this.stateValue = stateValue;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public List<LogisticMessageBean> getLogisticMessage() {
            return logisticMessage;
        }

        public void setLogisticMessage(List<LogisticMessageBean> logisticMessage) {
            this.logisticMessage = logisticMessage;
        }

        public static class LogisticMessageBean {
            /**
             * context : [重庆市] [重庆黔江]的派件已签收 感谢使用中通快递,期待再次为您服务!
             * time : 2017-08-20 17:22:37
             */

            private String context;
            private String time;

            public String getContext() {
                return context;
            }

            public void setContext(String context) {
                this.context = context;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
