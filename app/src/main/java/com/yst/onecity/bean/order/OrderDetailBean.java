package com.yst.onecity.bean.order;

import java.util.List;

/**
 * 会员线上订单详情
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class OrderDetailBean {

    /**
     * receivePhone : 1
     * orderNo : 20170922173838058667
     * receiveAddress : 0省0市1
     * payTime : null
     * totalPrice : 7000
     * sumScore : 0.7
     * sendTime : null
     * receiveTime : null
     * receiveName : 1
     * createTime : 2017年09月22日 17:38
     * cancelTime : 2017年09月22日 17:38
     * totalNum : 2
     */

    private String receivePhone;
    private String orderNo;
    private String receiveAddress;
    private String payTime;
    private double totalPrice;
    private double sumScore;
    private String sendTime;
    private String receiveTime;
    private String receiveName;
    private String createTime;
    private String cancelTime;
    private String finishTime;
    private int totalNum;
    private String iMid;
    private String fenRunType;
    private int flag;
    private String invoiceMark;
    private String invoiceType;
    private String invoiceName;
    private double totalWeight;
    private List<OrderProduct> merchantList;
    private int freightFee;
    private String totalOrderPrice;
    private String totalScorePrice;
    private int scoreStatus;
    private String payType;
    private int isXYT;
    private String refundMsg;
    private String message;

    public String getRefundMsg() {
        return refundMsg;
    }

    public void setRefundMsg(String refundMsg) {
        this.refundMsg = refundMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIsXYT() {
        return isXYT;
    }

    public void setIsXYT(int isXYT) {
        this.isXYT = isXYT;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getScoreStatus() {
        return scoreStatus;
    }

    public void setScoreStatus(int scoreStatus) {
        this.scoreStatus = scoreStatus;
    }

    public String getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public void setTotalOrderPrice(String totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public String getTotalScorePrice() {
        return totalScorePrice;
    }

    public void setTotalScorePrice(String totalScorePrice) {
        this.totalScorePrice = totalScorePrice;
    }

    public int getFreightFee() {
        return freightFee;
    }

    public void setFreightFee(int freightFee) {
        this.freightFee = freightFee;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public String getInvoiceMark() {
        return invoiceMark;
    }

    public void setInvoiceMark(String invoiceMark) {
        this.invoiceMark = invoiceMark;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getSumScore() {
        return sumScore;
    }

    public void setSumScore(double sumScore) {
        this.sumScore = sumScore;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public String getiMid() {
        return iMid;
    }

    public void setiMid(String iMid) {
        this.iMid = iMid;
    }

    public String getFenRunType() {
        return fenRunType;
    }

    public void setFenRunType(String fenRunType) {
        this.fenRunType = fenRunType;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<OrderProduct> getMerchantList() {
        return merchantList;
    }

    public void setMerchantList(List<OrderProduct> merchantList) {
        this.merchantList = merchantList;
    }
}
