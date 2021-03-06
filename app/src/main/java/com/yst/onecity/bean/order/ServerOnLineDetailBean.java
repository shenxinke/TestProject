package com.yst.onecity.bean.order;

import com.yst.onecity.bean.CodeMsgBean;

/**
 * 线上订单状态数量
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class ServerOnLineDetailBean extends CodeMsgBean {

    /**
     * actualMoney : 0.00
     * receivePhone : 1
     * orderNo : 20170922173838058667
     * receiveAddress : 0省0市1
     * totalScore : 3.2
     * content : null
     * productName : 好东西
     * receiveName : 1
     * totalNum : 2
     * createTime : 20170922 17:38
     * price : 50.00
     * serviceMoney : 0.00
     * status : 0
     */

    private double actualMoney;
    private String receivePhone;
    private String orderNo;
    private String receiveAddress;
    private double totalScore;
    private String content;
    private String productName;
    private String receiveName;
    private int totalNum;
    private String createTime;
    private double price;
    private String serviceMoney;
    private int status;

    public double getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(double actualMoney) {
        this.actualMoney = actualMoney;
    }

    public String getReceivePhone() {
        return receivePhone;
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

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getServiceMoney() {
        return serviceMoney;
    }

    public void setServiceMoney(String serviceMoney) {
        this.serviceMoney = serviceMoney;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
