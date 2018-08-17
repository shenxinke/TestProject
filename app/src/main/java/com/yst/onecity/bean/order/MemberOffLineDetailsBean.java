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
public class MemberOffLineDetailsBean extends CodeMsgBean{

    private MemberOffLineDetailBean content;

    public MemberOffLineDetailBean getContent() {
        return content;
    }

    public void setContent(MemberOffLineDetailBean content) {
        this.content = content;
    }

    public class MemberOffLineDetailBean {

        /**
         * created_time : 2017.09.26 17:40
         * orderNo : 20170926173633654
         * total_price : 100000
         * mimg : 3234
         * merchant_name : 我是商户2
         * pay_type : null
         * totalScore : 3
         */

        private String created_time;
        private String order_no;
        private String orderNo;
        private double total_price;
        private double totalMoney;
        private String mimg;
        private String merchant_name;
        private String payType;
        private String address;
        private double totalScore;
        private String phone;
        private int flag;
        private String fenRunType;
        private int isXYT;
        private List<UnLineOrderBean> son;

        public int getIsXYT() {
            return isXYT;
        }

        public void setIsXYT(int isXYT) {
            this.isXYT = isXYT;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
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

        public double getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(double totalMoney) {
            this.totalMoney = totalMoney;
        }

        public String getMimg() {
            return mimg;
        }

        public void setMimg(String mimg) {
            this.mimg = mimg;
        }

        public String getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(String merchant_name) {
            this.merchant_name = merchant_name;
        }

        public String getPay_type() {
            return payType;
        }

        public void setPay_type(String pay_type) {
            this.payType = pay_type;
        }


        public double getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(int totalScore) {
            this.totalScore = totalScore;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public List<UnLineOrderBean> getSon() {
            return son;
        }

        public void setSon(List<UnLineOrderBean> son) {
            this.son = son;
        }
    }
}
