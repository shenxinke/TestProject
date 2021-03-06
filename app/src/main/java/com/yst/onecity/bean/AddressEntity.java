package com.yst.onecity.bean;

import java.io.Serializable;

/**
 * 收货地址
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class AddressEntity implements Serializable {
    /**
     * id : 668
     * memberId : 852
     * pid : 354
     * cid : 395
     * did : 398
     * detailAddress : 科技楼
     * code : null
     * mobile : 13120238966
     * userName : 12225
     * isDefault : 1
     * pname : 内蒙古
     * cname : 阿拉善
     * dname : 额济纳旗
     * longitude : 116.397
     * latitude : 39.92325
     * position : 北京旅游集散中心
     */

    private String id;
    private String memberId;
    private String pid;
    private String cid;
    private String did;
    private String detailAddress;
    private Object code;
    private String mobile;
    private String userName;
    private int isDefault;
    private String pname;
    private String cname;
    private String dname;
    private String longitude;
    private String latitude;
    private String position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
