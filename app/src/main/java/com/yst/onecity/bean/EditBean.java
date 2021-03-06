package com.yst.onecity.bean;

import java.util.List;

/**
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/6/11
 */

public class EditBean {

    /**
     * modelType : 2
     * title : 1234
     * imgNum : 3
     * content : [{"sortNum":"0","attachmentId":3293,"coverSortNum":"0","type":"1","cover":"1","detail":"1234567790","productId":150}]
     * action : 1
     */

    private String id;
    private String modelType;
    private String title;
    private String imgNum;
    private String action;
    private List<ContentBean> content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgNum() {
        return imgNum;
    }

    public void setImgNum(String imgNum) {
        this.imgNum = imgNum;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * sortNum : 0
         * attachmentId : 3293
         * coverSortNum : 0
         * type : 1
         * cover : 1
         * detail : 1234567790
         * productId : 150
         */

        private String sortNum;
        private String attachmentId;
        private String coverSortNum;
        private String type;
        private String cover;
        private String detail;
        private String productId;

        public String getSortNum() {
            return sortNum;
        }

        public void setSortNum(String sortNum) {
            this.sortNum = sortNum;
        }

        public String getAttachmentId() {
            return attachmentId;
        }

        public void setAttachmentId(String attachmentId) {
            this.attachmentId = attachmentId;
        }

        public String getCoverSortNum() {
            return coverSortNum;
        }

        public void setCoverSortNum(String coverSortNum) {
            this.coverSortNum = coverSortNum;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }
    }
}
