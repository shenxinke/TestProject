package com.yst.onecity.bean;

import java.util.List;

/**
 * 品秀滑动播放实体
 *
 * @author WangJingWei
 * @version 4.2.0
 * @date 2018/6/7.
 */
public class PxVideoBean {
    /**
     * code : 1
     * msg : 成功
     * content : [{"commentNum":0,"headImg":"upload/skysimple/20171220183931222306037.png","readNum":0,"phone":"18101249503","fabulousNum":0,"nickname":"小旭","id":9,"videoAddress":"http://yst-images.img-cn-hangzhou.aliyuncs.com/upload/consultationimg/20180531170036441691862.mp4","shareNum":0,"content":"斯蒂芬斯蒂芬","productName":"鸿雁麻辣鸭腿肉"},{"commentNum":8,"headImg":"upload/skysimple/20171220183931222306037.png","readNum":8,"phone":"18101249503","fabulousNum":0,"nickname":"小旭","id":7,"videoAddress":"http://yst-images.img-cn-hangzhou.aliyuncs.com/upload/consultationimg/20180531170036441691862.mp4","shareNum":0,"content":"斯蒂芬斯蒂芬","productName":"蜂巢蜜"},{"commentNum":0,"headImg":"upload/skysimple/20171220183931222306037.png","readNum":0,"phone":"18101249503","fabulousNum":0,"nickname":"小旭","id":6,"videoAddress":"http://yst-images.img-cn-hangzhou.aliyuncs.com/upload/consultationimg/20180531170036441691862.mp4","shareNum":0,"content":"斯蒂芬斯蒂芬","productName":"专属"}]
     */

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

    public static class ContentBean {
        /**
         * commentNum : 0
         * headImg : upload/skysimple/20171220183931222306037.png
         * readNum : 0
         * phone : 18101249503
         * fabulousNum : 0
         * nickname : 小旭
         * id : 9
         * videoAddress : http://yst-images.img-cn-hangzhou.aliyuncs.com/upload/consultationimg/20180531170036441691862.mp4
         * shareNum : 0
         * content : 斯蒂芬斯蒂芬
         * productName : 鸿雁麻辣鸭腿肉
         */

        private int commentNum;
        private String headImg;
        private int readNum;
        private String phone;
        private int fabulousNum;
        private int isFabulous;
        private String nickname;
        private int id;
        private String videoAddress;
        private int shareNum;
        private String content;
        private String productName;
        private String hunterId;
        private String productId;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public int getReadNum() {
            return readNum;
        }

        public void setReadNum(int readNum) {
            this.readNum = readNum;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getFabulousNum() {
            return fabulousNum;
        }

        public void setFabulousNum(int fabulousNum) {
            this.fabulousNum = fabulousNum;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVideoAddress() {
            return videoAddress;
        }

        public void setVideoAddress(String videoAddress) {
            this.videoAddress = videoAddress;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getIsFabulous() {
            return isFabulous;
        }

        public void setIsFabulous(int isFabulous) {
            this.isFabulous = isFabulous;
        }

        public String getHunterId() {
            return hunterId;
        }

        public void setHunterId(String hunterId) {
            this.hunterId = hunterId;
        }
    }

}
