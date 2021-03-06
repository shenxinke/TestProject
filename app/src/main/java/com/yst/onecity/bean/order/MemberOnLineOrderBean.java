package com.yst.onecity.bean.order;

import com.yst.onecity.bean.CodeMsgBean;

import java.util.List;

/**
 * 会员线下订单详情
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class MemberOnLineOrderBean extends CodeMsgBean{

    private List<OnLineBean> content;

    public List<OnLineBean> getContent() {
        return content;
    }

    public void setContent(List<OnLineBean> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MemberOnLineOrderBean{" +
                "content=" + content +
                '}';
    }

    public class OnLineBean {

        /**
         * mechantImg : 推广师头像
         * orderNo : 20170926173633654
         * total_price : 6
         * totalNum : 2
         * merchant_name : 我是商户1
         * id : 63
         * totalScore : 6
         * mImId : 18101249503
         */

        private String mechantImg;
        private String orderNo;
        private double total_price;
        private int totalNum;
        private String merchant_name;
        private String id;
        private String merchant_id;
        private double totalScore;
        private String payType;
        private String mImId;
        private String weight;
        private List<SonBean> son;
        private int flag;
        private int scoreStatus;
        private int commentStatus;
        private double freight_fee;
        private String fenRunType;
        private int isXYT;

        public int getIsXYT() {
            return isXYT;
        }

        public void setIsXYT(int isXYT) {
            this.isXYT = isXYT;
        }

        public int getScoreStatus() {
            return scoreStatus;
        }

        public void setScoreStatus(int scoreStatus) {
            this.scoreStatus = scoreStatus;
        }

        public int getCommentStatus() {
            return commentStatus;
        }

        public void setCommentStatus(int commentStatus) {
            this.commentStatus = commentStatus;
        }

        public double getFreight_fee() {
            return freight_fee;
        }

        public void setFreight_fee(double freight_fee) {
            this.freight_fee = freight_fee;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getMechantImg() {
            return mechantImg;
        }

        public void setMechantImg(String mechantImg) {
            this.mechantImg = mechantImg;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(double total_price) {
            this.total_price = total_price;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public String getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(String merchant_name) {
            this.merchant_name = merchant_name;
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

        public List<SonBean> getSon() {
            return son;
        }

        public void setSon(List<SonBean> son) {
            this.son = son;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getFenRunType() {
            return fenRunType;
        }

        public void setFenRunType(String fenRunType) {
            this.fenRunType = fenRunType;
        }

    }
}
