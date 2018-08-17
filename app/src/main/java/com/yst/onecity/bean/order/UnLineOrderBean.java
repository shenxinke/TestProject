package com.yst.onecity.bean.order;

/**
 * 线下订单详情
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 * 订单列表实体类
 */

public class UnLineOrderBean {

    /**
     * score : 3
     * phType : 0
     * orderNo : 20170922173838058667
     * total_price : 3
     * price : 3
     * pimg : upload/memberheadimg/20170717100550711414593.png
     * num : 1
     * name : 好东西
     * id : 37
     * orderSubNo : 20170922173838058667_1
     * content : null
     * status : 0
     */
    private String orderNo;
    private String address;
    private double total_price;
    private int num;
    private String weight;
    private String content;
    private String mImId;
    private double scorePrice;
    private int phType;
    private double price;
    private String nickname;
    private String name;
    private String id;
    private String hImId;
    private String orderSubNo;
    private int status;
    private String pContent;

    public String getpContent() {
        return pContent;
    }

    public void setpContent(String pContent) {
        this.pContent = pContent;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public double getScorePrice() {
        return scorePrice;
    }

    public void setScorePrice(double scorePrice) {
        this.scorePrice = scorePrice;
    }

    public int getPhType() {
        return phType;
    }

    public void setPhType(int phType) {
        this.phType = phType;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderSubNo() {
        return orderSubNo;
    }

    public void setOrderSubNo(String orderSubNo) {
        this.orderSubNo = orderSubNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getmImId() {
        return mImId;
    }

    public void setmImId(String mImId) {
        this.mImId = mImId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String gethImId() {
        return hImId;
    }

    public void sethImId(String hImId) {
        this.hImId = hImId;
    }


}
