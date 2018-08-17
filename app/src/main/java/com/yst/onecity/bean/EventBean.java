package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 消息通知
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class EventBean implements Serializable {
    private String msg;
    private String adress;
    private String PID;
    private String CID;
    private String AID;
    private int position;
    private boolean flag;
    private String content;
    private String province;
    private String city;
    private String district;
    private List<BigCartBean.ContentBean> mLists;

    public EventBean(String msg) {
        this.msg = msg;
    }

    public EventBean(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public EventBean(String msg, List<BigCartBean.ContentBean> lists) {
        this.msg = msg;
        mLists = lists;
    }

    public EventBean(String msg, String content) {
        this.msg = msg;
        this.content = content;
    }

    public EventBean(String msg, String province, String city, String district) {
        this.msg = msg;
        this.province = province;
        this.city = city;
        this.district = district;
    }

    public EventBean(String msg, String content, int position) {
        this.msg = msg;
        this.content = content;
        this.position = position;
    }

    public EventBean(String CID, int position) {
        this.CID = CID;
        this.position = position;
    }

    public EventBean(String msg, String adress, String PID, String CID, String AID) {
        this.msg = msg;
        this.adress = adress;
        this.PID = PID;
        this.CID = CID;
        this.AID = AID;
    }

    public EventBean(String msg, String adress, String PID, String CID, String AID, int position) {
        this.msg = msg;
        this.adress = adress;
        this.PID = PID;
        this.CID = CID;
        this.AID = AID;
        this.position = position;
    }

    public List<BigCartBean.ContentBean> getLists() {
        return mLists;
    }

    public void setLists(List<BigCartBean.ContentBean> lists) {
        mLists = lists;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getAID() {
        return AID;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }


    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
