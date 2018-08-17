package com.yst.onecity.bean.tickets;

import java.io.Serializable;

/**
 * 未开奖订单列表实体类
 *
 * @author WangJingWei
 * @version 4.0.0
 * @date 2018/3/20.
 */
public class TicketsContent implements Serializable{

    private String id;
    /**
     * 订单总金额
     */
    private String total_price;
    /**
     * 红包数量
     */
    private String red_packet_num;
    /**
     * 订单号
     */
    private String order_num;
    /**
     * 开奖时间
     */
    private String create_time;
    /**
     * 几等奖
     */
    private String name;
    /**
     * 劵号
     */
    private String num;
    /**
     * 奖励金额
     */
    private String lottery_price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTkMoney() {
        return total_price;
    }

    public void setTkMoney(String tkMoney) {
        this.total_price = tkMoney;
    }

    public String getTkNum() {
        return red_packet_num;
    }

    public void setTkNum(String tkNum) {
        this.red_packet_num = tkNum;
    }

    public String getOrderNum() {
        return order_num;
    }

    public void setOrderNum(String orderNum) {
        this.order_num = orderNum;
    }

    public String getDate() {
        return create_time;
    }

    public void setDate(String date) {
        this.create_time = date;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTkAward() {
        return name;
    }

    public void setTkAward(String tkAward) {
        this.name = tkAward;
    }

    public String getLottery_price() {
        return lottery_price;
    }

    public void setLottery_price(String lottery_price) {
        this.lottery_price = lottery_price;
    }
}
