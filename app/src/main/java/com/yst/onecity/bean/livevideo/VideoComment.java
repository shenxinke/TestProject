package com.yst.onecity.bean.livevideo;

import java.util.List;

/**
 * 视频评论实体
 *
 * @author WangJingWei
 * @version 4.1.0
 * @date 2018/5/15.
 */
public class VideoComment {

    private int code;
    private String msg;
    private List<Comment> content;

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

    public List<Comment> getContent() {
        return content;
    }

    public void setContent(List<Comment> content) {
        this.content = content;
    }

    public static class Comment {

        private String id;
        private String headImg;
        private String nickname;
        private String createdTime;
        private String isFabulous;
        private String fabulousNum;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHeadUrl() {
            return headImg;
        }

        public void setHeadUrl(String headUrl) {
            this.headImg = headUrl;
        }

        public String getName() {
            return nickname;
        }

        public void setName(String name) {
            this.nickname = name;
        }

        public String getDate() {
            return createdTime;
        }

        public void setDate(String date) {
            this.createdTime = date;
        }

        public String getDzNum() {
            return fabulousNum;
        }

        public void setDzNum(String dzNum) {
            this.fabulousNum = dzNum;
        }

        public String getCommentDesc() {
            return content;
        }

        public void setCommentDesc(String commentDesc) {
            this.content = commentDesc;
        }

        public String getIsFabulous() {
            return isFabulous;
        }

        public void setIsFabulous(String isFabulous) {
            this.isFabulous = isFabulous;
        }
    }

}
