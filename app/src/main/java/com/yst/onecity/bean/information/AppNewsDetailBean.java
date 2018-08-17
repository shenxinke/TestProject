package com.yst.onecity.bean.information;

import java.io.Serializable;

/**
 * 咨询实体
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class AppNewsDetailBean implements Serializable{

    /**
     * sortNum : 1
     * type : 0
     * detail : 咨询正文第一个文本
     */
    /**
     * 排序顺序
     */
    private int sortNum;
    /**
     *  0 文本 1 图片 2商品
     */
    private int type;
    /**
     * type为0必传
     */
    private String detail;
    /**
     * 图片id type为1必传
     */
    private String attachmentId;
    /**
     * 0 不是封面 1 是封面 type为1必传
     */
    private String cover;
    /**
     * 封面排序
     */
    private int coverSortNum;
    /**
     * 商品id type为2时必传
     */
    private String productId;
    /**
     * 正文的图片地址
     */
    private String url;
    /**
     * 正文的图片地址
     */
    private String psId;
    /**
     * 正文的图片地址
     */
    private String merchantId;

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getCoverSortNum() {
        return coverSortNum;
    }

    public void setCoverSortNum(int coverSortNum) {
        this.coverSortNum = coverSortNum;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPsId() {
        return psId;
    }

    public void setPsId(String psId) {
        this.psId = psId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public AppNewsDetailBean(int sortNum, int type, String detail, String attachmentId,
                             String cover, int coverSortNum, String productId, String url,
                             String psId, String merchantId) {
        this.sortNum = sortNum;
        this.type = type;
        this.detail = detail;
        this.attachmentId = attachmentId;
        this.cover = cover;
        this.coverSortNum = coverSortNum;
        this.productId = productId;
        this.url = url;
        this.psId = psId;
        this.merchantId = merchantId;
    }
}
