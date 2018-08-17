package com.yst.onecity.bean.information;

import java.util.List;

/**
 * 发布资讯
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class PublishNewsJsonBean {

    /**
     * userId : 4
     * userType : 0
     * action : 0
     * type : 0
     * modelType : 0
     * title : 这是第一个咨询标题
     * imgNum : 0
     * consultationClassifyId : 1
     * descriptionName : 咨询分类名称
     * content : [{"sortNum":"1","type":"0","detail":"咨询正文第一个文本"}]
     */
    /**
     * 资讯id
     */
    private String id;
    private String userId;
    /**
     * 0普通会员 1推广师 2 门店
     */
    private String userType;
    /**
     * 0保存草稿 1申请发布
     */
    private String action;
    /**
     * 暂时固定传0
     */
    private String type;
    /**
     * 0无图 1单图 2三图
     */
    private int modelType;
    /**
     * 咨询标题
     */
    private String title;
    /**
     * 图片数量
     */
    private int imgNum;
    /**
     * 分类id
     */
    private String consultationClassifyId;
    /**
     * 分类名称
     */
    private String descriptionName;
    /**
     * 正文内容及封面图片
     */
    private List<AppNewsDetailBean> content;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getImgNum() {
        return imgNum;
    }

    public void setImgNum(int imgNum) {
        this.imgNum = imgNum;
    }

    public String getConsultationClassifyId() {
        return consultationClassifyId;
    }

    public void setConsultationClassifyId(String consultationClassifyId) {
        this.consultationClassifyId = consultationClassifyId;
    }

    public String getDescriptionName() {
        return descriptionName;
    }

    public void setDescriptionName(String descriptionName) {
        this.descriptionName = descriptionName;
    }

    public List<AppNewsDetailBean> getContent() {
        return content;
    }

    public void setContent(List<AppNewsDetailBean> content) {
        this.content = content;
    }

    public PublishNewsJsonBean(String userId, String userType, String action, String type, int modelType,
                               String title, int imgNum, String consultationClassifyId, String descriptionName,
                               List<AppNewsDetailBean> content) {
        this.userId = userId;
        this.userType = userType;
        this.action = action;
        this.type = type;
        this.modelType = modelType;
        this.title = title;
        this.imgNum = imgNum;
        this.consultationClassifyId = consultationClassifyId;
        this.descriptionName = descriptionName;
        this.content = content;
    }
    public PublishNewsJsonBean(String id,String userId, String userType, String action, String type, int modelType,
                               String title, int imgNum, String consultationClassifyId, String descriptionName,
                               List<AppNewsDetailBean> content) {
        this.id = id;
        this.userId = userId;
        this.userType = userType;
        this.action = action;
        this.type = type;
        this.modelType = modelType;
        this.title = title;
        this.imgNum = imgNum;
        this.consultationClassifyId = consultationClassifyId;
        this.descriptionName = descriptionName;
        this.content = content;
    }
}
