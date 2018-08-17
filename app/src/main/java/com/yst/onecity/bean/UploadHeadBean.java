package com.yst.onecity.bean;

/**
 * 上传头像成功bean
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/8
 */

public class UploadHeadBean {


    /**
     * address : https://ph-images.oss-cn-shenzhen.aliyuncs.com/upload/member/20180608/20180608165208747480858.png
     * code : 1
     * createUser :
     * createdIp :
     * createdTime : 2018-06-08 16:52:14
     * id : 3282
     * memory : 0.04
     * msg : 上传成功
     * name : 1528447927348.png
     * updateTime : 2018-06-08 16:52:14
     */

    private String address;
    private int code;
    private String createUser;
    private String createdIp;
    private String createdTime;
    private int id;
    private double memory;
    private String msg;
    private String name;
    private String updateTime;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMemory() {
        return memory;
    }

    public void setMemory(double memory) {
        this.memory = memory;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
