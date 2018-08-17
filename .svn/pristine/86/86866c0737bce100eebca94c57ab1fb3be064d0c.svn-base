package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 线上订单
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ServerOnLineBean implements Serializable {

    /**
     * code : 1
     * msg : 查询成功！
     * content : [{"created_time":"2017-09-22 17:38:41","orderNo":"20170922173838058667","total_price":5000,"phone":"17710268732","id":37,"totalScore":3.2,"mImId":"17710268732","status":0},{"created_time":"2017-09-22 17:40:30","orderNo":"20170922174029797608","total_price":5000,"phone":"17710268732","id":39,"mImId":"17710268732","status":0},{"created_time":"2017-09-22 17:40:30","orderNo":"20170922174030098880","total_price":5000,"phone":"17710268732","id":41,"mImId":"17710268732","status":0}]
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
         * created_time : 2017-09-22 17:38:41
         * orderNo : 20170922173838058667
         * total_price : 5000
         * phone : 17710268732
         * id : 37
         * totalScore : 3.2
         * mImId : 17710268732
         * status : 0
         */

        private String created_time;
        private String orderNo;
        private String phone;
        private String id;
        private String merchant_id;
        private double totalScore;
        private String mImId;
        private int status;

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public double getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(double totalScore) {
            this.totalScore = totalScore;
        }

        public String getMImId() {
            return mImId;
        }

        public void setMImId(String mImId) {
            this.mImId = mImId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
