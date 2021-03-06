package com.yst.onecity.bean;

/**
 * 专员推荐
 *
 * @author Shenxinke
 * @version 4.2.0
 * @data 2018/6/8
 */

public class CommissionerProductBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"hId":25,"product":{"commentNum":0,"pName":"韩都衣舍2018夏装新款韩版女装印花破洞学生宽松短袖T恤TK0568婏 ","imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180418/20180418153628639500799.jpg","collectNum":5,"pId":351,"saleNum":0},"flag":1,"serviceArea":null,"nickName":"巧了我也是","hImg":"upload/skysimple/20171128220724322193499.png"}
     */

    private int code;
    private String msg;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * hId : 25
         * product : {"commentNum":0,"pName":"韩都衣舍2018夏装新款韩版女装印花破洞学生宽松短袖T恤TK0568婏 ","imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180418/20180418153628639500799.jpg","collectNum":5,"pId":351,"saleNum":0}
         * flag : 1
         * serviceArea : null
         * nickName : 巧了我也是
         * hImg : upload/skysimple/20171128220724322193499.png
         */

        private int hId;
        private ProductBean product;
        private int flag;
        private String serviceArea;
        private String nickName;
        private String hImg;

        public int getHId() {
            return hId;
        }

        public void setHId(int hId) {
            this.hId = hId;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getServiceArea() {
            return serviceArea;
        }

        public void setServiceArea(String serviceArea) {
            this.serviceArea = serviceArea;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHImg() {
            return hImg;
        }

        public void setHImg(String hImg) {
            this.hImg = hImg;
        }

        public static class ProductBean {
            /**
             * commentNum : 0
             * pName : 韩都衣舍2018夏装新款韩版女装印花破洞学生宽松短袖T恤TK0568婏
             * imageurl : https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180418/20180418153628639500799.jpg
             * collectNum : 5
             * pId : 351
             * saleNum : 0
             */

            private int commentNum;
            private String pName;
            private String imageurl;
            private int collectNum;
            private int pId;
            private int saleNum;

            public int getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(int commentNum) {
                this.commentNum = commentNum;
            }

            public String getPName() {
                return pName;
            }

            public void setPName(String pName) {
                this.pName = pName;
            }

            public String getImageurl() {
                return imageurl;
            }

            public void setImageurl(String imageurl) {
                this.imageurl = imageurl;
            }

            public int getCollectNum() {
                return collectNum;
            }

            public void setCollectNum(int collectNum) {
                this.collectNum = collectNum;
            }

            public int getPId() {
                return pId;
            }

            public void setPId(int pId) {
                this.pId = pId;
            }

            public int getSaleNum() {
                return saleNum;
            }

            public void setSaleNum(int saleNum) {
                this.saleNum = saleNum;
            }
        }
    }
}
