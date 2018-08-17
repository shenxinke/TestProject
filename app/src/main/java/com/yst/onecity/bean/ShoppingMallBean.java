package com.yst.onecity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 商城分类列表适配器
 *
 * @author Shenxinke
 * @version 4.2.0
 * @data 2018/5/31
 */

public class ShoppingMallBean implements Serializable {


    /**
     * code : 1
     * msg : 查询成功
     * content : [{"commentNum":0,"salePrice":258,"shopCartNum":0,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171214/20171214100657212675522.jpg","fabulousNum":10,"name":"百年堂阿胶速溶粉","memberImg":[{"headImg":"upload/skysimple/20171220183931222306037.png","userType":0}],"id":150,"saleNum":4,"shareNum":0,"views":0},{"commentNum":0,"salePrice":618,"shopCartNum":0,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171214/20171214100813872756454.jpg","fabulousNum":0,"name":"百年堂阿胶","memberImg":[],"id":151,"saleNum":4,"shareNum":0,"views":0},{"commentNum":1,"salePrice":360,"shopCartNum":0,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171214/20171214103111799037470.jpg","fabulousNum":0,"name":"怡美乐高钙富硒配方羊奶粉","memberImg":[],"id":159,"saleNum":6,"shareNum":0,"views":0},{"commentNum":0,"salePrice":680,"shopCartNum":0,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171214/20171214104547475342651.jpg","fabulousNum":0,"name":"纳豆红曲植萃素","memberImg":[],"id":163,"saleNum":20,"shareNum":0,"views":0},{"commentNum":0,"salePrice":1280,"shopCartNum":0,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180118/20180118100959846287230.jpg","fabulousNum":0,"name":"阿胶速溶粉","memberImg":[],"id":258,"saleNum":20,"shareNum":0,"views":0},{"commentNum":0,"salePrice":596,"shopCartNum":0,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180427/20180427143926959221105.jpg","fabulousNum":0,"name":"阿森纳球衣","memberImg":[],"id":382,"saleNum":750,"shareNum":0,"views":0}]
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

    public static class ContentBean implements Serializable {
        /**
         * commentNum : 0
         * salePrice : 258
         * shopCartNum : 0
         * imageurl : https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171214/20171214100657212675522.jpg
         * fabulousNum : 10
         * name : 百年堂阿胶速溶粉
         * memberImg : [{"headImg":"upload/skysimple/20171220183931222306037.png","userType":0}]
         * id : 150
         * saleNum : 4
         * shareNum : 0
         * views : 0
         */
        private int isScope;
        private int commentNum;
        private String salePrice;
        private int shopCartNum;
        private String imageurl;
        private int fabulousNum;
        private String name;
        private int id;
        private int saleNum;
        private int shareNum;
        private int views;
        private String type;
        private String consultaionNum;
        private List<MemberImgBean> memberImg;
        private boolean isClick;

        public String getConsultaionNum() {
            return consultaionNum;
        }

        public void setConsultaionNum(String consultaionNum) {
            this.consultaionNum = consultaionNum;
        }

        public int getShopCartNum() {
            return shopCartNum;
        }

        public void setShopCartNum(int shopCartNum) {
            this.shopCartNum = shopCartNum;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getIsScope() {
            return isScope;
        }

        public void setIsScope(int isScope) {
            this.isScope = isScope;
        }

        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public String getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(String salePrice) {
            this.salePrice = salePrice;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public int getFabulousNum() {
            return fabulousNum;
        }

        public void setFabulousNum(int fabulousNum) {
            this.fabulousNum = fabulousNum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSaleNum() {
            return saleNum;
        }

        public void setSaleNum(int saleNum) {
            this.saleNum = saleNum;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public List<MemberImgBean> getMemberImg() {
            return memberImg;
        }

        public void setMemberImg(List<MemberImgBean> memberImg) {
            this.memberImg = memberImg;
        }

        public static class MemberImgBean implements Serializable {
            /**
             * headImg : upload/skysimple/20171220183931222306037.png
             * userType : 0
             */

            private String headImg;
            private int userType;

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }
        }
    }
}
