package com.yst.onecity.bean.mycount;

/**
 * 首页精选类实体类
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class FineItemBean {
    /**
     * address : upload/productTrace/20170331135925629816042.png,
     * num : 252
     * authorInfo : 万花筒
     * collectNum : 254
     * createdTime : 2017/06/13
     * id : 6
     * modelType : 1
     * title : 访谈标题4
     * type : 1
     * detailSummary : 摘要信息
     */

    private String num;
    private String authorInfo;
    private String collectNum;
    private String createdTime;
    private int id;
    private int modelType;
    private String title;
    private int type;
    private String detailSummary;
    private int status;

    public String getFavourNum() {
        return favourNum;
    }

    public void setFavourNum(String favourNum) {
        this.favourNum = favourNum;
    }

    /**
     * 点赞数量
     */
    private String favourNum;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAuthorInfo() {
        return authorInfo;
    }

    public void setAuthorInfo(String authorInfo) {
        this.authorInfo = authorInfo;
    }

    public String getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(String collectNum) {
        this.collectNum = collectNum;
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

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDetailSummary() {
        return detailSummary;
    }

    public void setDetailSummary(String detailSummary) {
        this.detailSummary = detailSummary;
    }
}
