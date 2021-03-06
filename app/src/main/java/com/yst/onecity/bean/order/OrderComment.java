package com.yst.onecity.bean.order;

import java.util.List;

/**
 * 订单评价实体
 *
 * @author WangJingWei
 * @version 4.2.0
 * @date 2018/5/30.
 */
public class OrderComment {

    private int code;
    private String msg;
    private List<CommentBean> content;

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

    public List<CommentBean> getContent() {
        return content;
    }

    public void setContent(List<CommentBean> content) {
        this.content = content;
    }

    public static class CommentBean{
        private String id;
        private String imgStore;
        private String storeName;
        private String storeDesc;
        private String storeRating;
        private String imgUser;
        private String userName;
        private String userDesc;
        private String userRating;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgStore() {
            return imgStore;
        }

        public void setImgStore(String imgStore) {
            this.imgStore = imgStore;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreDesc() {
            return storeDesc;
        }

        public void setStoreDesc(String storeDesc) {
            this.storeDesc = storeDesc;
        }

        public String getStoreRating() {
            return storeRating;
        }

        public void setStoreRating(String storeRating) {
            this.storeRating = storeRating;
        }

        public String getImgUser() {
            return imgUser;
        }

        public void setImgUser(String imgUser) {
            this.imgUser = imgUser;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserDesc() {
            return userDesc;
        }

        public void setUserDesc(String userDesc) {
            this.userDesc = userDesc;
        }

        public String getUserRating() {
            return userRating;
        }

        public void setUserRating(String userRating) {
            this.userRating = userRating;
        }

        @Override
        public String toString() {
            return "CommentBean{" +
                    "id='" + id + '\'' +
                    ", imgStore='" + imgStore + '\'' +
                    ", storeName='" + storeName + '\'' +
                    ", storeDesc='" + storeDesc + '\'' +
                    ", storeRating='" + storeRating + '\'' +
                    ", imgUser='" + imgUser + '\'' +
                    ", userName='" + userName + '\'' +
                    ", userDesc='" + userDesc + '\'' +
                    ", userRating='" + userRating + '\'' +
                    '}';
        }
    }
}
