package com.yst.onecity.bean.mycount;

import java.util.List;

/**
 * 首页精选类实体类
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class FineBean {

    private int code;
    private String msg;
    private List<ContentBean> content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }
    /**
     * 父级列表
     */
    public static class ContentBean {
        /**
         * address : upload/productTrace/20170329160517290913788.png,
         * num : 262
         * authorInfo : 万花筒
         * collectNum : 264
         * createdTime : 2017/06/14
         * isMore : false
         * id : 7
         * modelType : 1
         * title : 求助
         * type : 4
         * detailSummary : 摘要信息
         * consultationChidList : null
         */

        private String num;
        private String commentNum;
        private String authorInfo;
        private String collectNum;
        private String createdTime;
        private boolean isMore;
        private String id;
        private int modelType;
        private String title;
        private int type;
        private String detailSummary;
        private String detailContent;
        private boolean isLoaded;
        private int page;
        private int status;
        private int count;
        private String favourNum;

        public String getFavourNum() {
            return favourNum;
        }

        public void setFavourNum(String favourNum) {
            this.favourNum = favourNum;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(String commentNum) {
            this.commentNum = commentNum;
        }

        public String getDetailContent() {
            return detailContent;
        }

        public void setDetailContent(String detailContent) {
            this.detailContent = detailContent;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }


        /**
         * 子级列表
         */
        private List<FineItemBean> consultationChidList;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public boolean isLoaded() {
            return isLoaded;
        }

        public void setLoaded(boolean loaded) {
            isLoaded = loaded;
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

        public boolean isIsMore() {
            return isMore;
        }

        public void setIsMore(boolean isMore) {
            this.isMore = isMore;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public List<FineItemBean> getConsultationChidList() {
            return consultationChidList;
        }

        public void setConsultationChidList(List<FineItemBean> consultationChidList) {
            this.consultationChidList = consultationChidList;
        }

    }


}
