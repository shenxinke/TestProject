package com.yst.onecity.bean;

import java.util.List;

/**
 * 商城分享页面
 *
 * @author Shenxinke
 * @version 4.2.0
 * @data 2018/6/5
 */

public class TrademarkShareBean {


    /**
     * code : 1
     * msg : 请求成功
     * content : {"branddetail":{"address":null,"sale_num":0,"name":"百年堂","description":null,"id":51,"brand_img":null,"story":null},"consultationInfoList":[{"headImg":"upload/skysimple/20180421100918428407530.png","modelType":1,"title":"123456","type":0,"shareNum":0,"userId":1452,"commentNum":0,"readNum":0,"fabulousNum":0,"name":"啦啦啦啦","createdTime":1528524232000,"id":578,"userType":0,"info":[{"cover":1,"address":"http://yst-images.img-cn-hangzhou.aliyuncs.com/upload/consultationimg/20180609140326355265031.jpeg","productId":null,"imgId":3323,"merchantId":null,"coverSortNum":0,"sortNum":0,"psId":null,"type":1,"content":null}]},{"headImg":"upload/skysimple/20180421100918428407530.png","modelType":1,"title":"66666","type":0,"shareNum":0,"userId":1452,"commentNum":0,"readNum":0,"fabulousNum":0,"name":"啦啦啦啦","createdTime":1528526950000,"id":587,"userType":0,"info":[{"cover":1,"address":"http://yst-images.img-cn-hangzhou.aliyuncs.com/upload/consultationimg/20180609144445359666404.jpeg","productId":null,"imgId":3334,"merchantId":null,"coverSortNum":0,"sortNum":0,"psId":null,"type":1,"content":null}]}],"count":4}
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
         * branddetail : {"address":null,"sale_num":0,"name":"百年堂","description":null,"id":51,"brand_img":null,"story":null}
         * consultationInfoList : [{"headImg":"upload/skysimple/20180421100918428407530.png","modelType":1,"title":"123456","type":0,"shareNum":0,"userId":1452,"commentNum":0,"readNum":0,"fabulousNum":0,"name":"啦啦啦啦","createdTime":1528524232000,"id":578,"userType":0,"info":[{"cover":1,"address":"http://yst-images.img-cn-hangzhou.aliyuncs.com/upload/consultationimg/20180609140326355265031.jpeg","productId":null,"imgId":3323,"merchantId":null,"coverSortNum":0,"sortNum":0,"psId":null,"type":1,"content":null}]},{"headImg":"upload/skysimple/20180421100918428407530.png","modelType":1,"title":"66666","type":0,"shareNum":0,"userId":1452,"commentNum":0,"readNum":0,"fabulousNum":0,"name":"啦啦啦啦","createdTime":1528526950000,"id":587,"userType":0,"info":[{"cover":1,"address":"http://yst-images.img-cn-hangzhou.aliyuncs.com/upload/consultationimg/20180609144445359666404.jpeg","productId":null,"imgId":3334,"merchantId":null,"coverSortNum":0,"sortNum":0,"psId":null,"type":1,"content":null}]}]
         * count : 4
         */

        private BranddetailBean branddetail;
        private String count;
        private List<ConsultationInfoListBean> consultationInfoList;

        public BranddetailBean getBranddetail() {
            return branddetail;
        }

        public void setBranddetail(BranddetailBean branddetail) {
            this.branddetail = branddetail;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<ConsultationInfoListBean> getConsultationInfoList() {
            return consultationInfoList;
        }

        public void setConsultationInfoList(List<ConsultationInfoListBean> consultationInfoList) {
            this.consultationInfoList = consultationInfoList;
        }

        public static class BranddetailBean {
            /**
             * address : null
             * sale_num : 0
             * name : 百年堂
             * description : null
             * id : 51
             * brand_img : null
             * story : null
             */

            private String address;
            private String sale_num;
            private String name;
            private String description;
            private int id;
            private String brand_img;
            private String story;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getSale_num() {
                return sale_num;
            }

            public void setSale_num(String sale_num) {
                this.sale_num = sale_num;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getBrand_img() {
                return brand_img;
            }

            public void setBrand_img(String brand_img) {
                this.brand_img = brand_img;
            }

            public String getStory() {
                return story;
            }

            public void setStory(String story) {
                this.story = story;
            }
        }

        public static class ConsultationInfoListBean {
            /**
             * headImg : upload/skysimple/20180421100918428407530.png
             * modelType : 1
             * title : 123456
             * type : 0
             * shareNum : 0
             * userId : 1452
             * commentNum : 0
             * readNum : 0
             * fabulousNum : 0
             * name : 啦啦啦啦
             * createdTime : 1528524232000
             * id : 578
             * userType : 0
             * info : [{"cover":1,"address":"http://yst-images.img-cn-hangzhou.aliyuncs.com/upload/consultationimg/20180609140326355265031.jpeg","productId":null,"imgId":3323,"merchantId":null,"coverSortNum":0,"sortNum":0,"psId":null,"type":1,"content":null}]
             */

            private String headImg;
            private int modelType;
            private String title;
            private int type;
            private String shareNum;
            private int userId;
            private int commentNum;
            private int readNum;
            private int fabulousNum;
            private String name;
            private long createdTime;
            private int id;
            private int userType;
            private List<InfoBean> info;

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
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

            public String getShareNum() {
                return shareNum;
            }

            public void setShareNum(String shareNum) {
                this.shareNum = shareNum;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(int commentNum) {
                this.commentNum = commentNum;
            }

            public int getReadNum() {
                return readNum;
            }

            public void setReadNum(int readNum) {
                this.readNum = readNum;
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

            public long getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(long createdTime) {
                this.createdTime = createdTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public List<InfoBean> getInfo() {
                return info;
            }

            public void setInfo(List<InfoBean> info) {
                this.info = info;
            }

            public static class InfoBean {
                /**
                 * cover : 1
                 * address : http://yst-images.img-cn-hangzhou.aliyuncs.com/upload/consultationimg/20180609140326355265031.jpeg
                 * productId : null
                 * imgId : 3323
                 * merchantId : null
                 * coverSortNum : 0
                 * sortNum : 0
                 * psId : null
                 * type : 1
                 * content : null
                 */

                private int cover;
                private String address;
                private Object productId;
                private int imgId;
                private Object merchantId;
                private int coverSortNum;
                private int sortNum;
                private Object psId;
                private int type;
                private Object content;

                public int getCover() {
                    return cover;
                }

                public void setCover(int cover) {
                    this.cover = cover;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public Object getProductId() {
                    return productId;
                }

                public void setProductId(Object productId) {
                    this.productId = productId;
                }

                public int getImgId() {
                    return imgId;
                }

                public void setImgId(int imgId) {
                    this.imgId = imgId;
                }

                public Object getMerchantId() {
                    return merchantId;
                }

                public void setMerchantId(Object merchantId) {
                    this.merchantId = merchantId;
                }

                public int getCoverSortNum() {
                    return coverSortNum;
                }

                public void setCoverSortNum(int coverSortNum) {
                    this.coverSortNum = coverSortNum;
                }

                public int getSortNum() {
                    return sortNum;
                }

                public void setSortNum(int sortNum) {
                    this.sortNum = sortNum;
                }

                public Object getPsId() {
                    return psId;
                }

                public void setPsId(Object psId) {
                    this.psId = psId;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public Object getContent() {
                    return content;
                }

                public void setContent(Object content) {
                    this.content = content;
                }
            }
        }
    }
}
