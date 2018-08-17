package com.yst.onecity.bean.product;

import java.util.List;

/**
 * 商品详情
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/0/19
 */
public class ProductDetailBean {

    /**
     * code : 1
     * msg : 成功
     * content : {"phone":"15210268812","LogoAdress":"upload/memberheadimg/20170717100550711414593.png","countComment":"125","pId":1,"type":"1","ImageUrl":[{"address":"upload/memberheadimg/20170717100859263561425.png"},{"address":"upload/toutiao/20170905114803790977163.jpg"}],"commonNum":"2","content":"haha","nickName":"我是会员","specificid":1,"niceNum":"123","text":[{"sale_num":12,"price":500,"imageurl":"upload/memberheadimg/20170717100550711414593.png","name":"好东西"}],"createTime":1505982131000,"bonus":"500","badNum":"0"}
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
         * phone : 15210268812
         * LogoAdress : upload/memberheadimg/20170717100550711414593.png
         * countComment : 125
         * pId : 1
         * type : 1
         * ImageUrl : [{"address":"upload/memberheadimg/20170717100859263561425.png"},{"address":"upload/toutiao/20170905114803790977163.jpg"}]
         * commonNum : 2
         * content : haha
         * nickName : 我是会员
         * specificid : 1
         * niceNum : 123
         * text : [{"sale_num":12,"price":500,"imageurl":"upload/memberheadimg/20170717100550711414593.png","name":"好东西"}]
         * createTime : 1505982131000
         * bonus : 500
         * badNum : 0
         */

        private String phone;
        private String LogoAdress;
        private String countComment;
        private int pId;
        private int type;
        private String commonNum;
        private String content;
        private String nickName;
        private int specificid;
        private String niceNum;
        private long createTime;
        private String bonus;
        private String badNum;
        private List<ImageUrlBean> ImageUrl;
        private List<TextBean> text;
        private int isScope;
        private Brand brand;
        private String merchantAddress;
        private String typeScoure;
        private List<String> productScope;

        public int getIsScope() {
            return isScope;
        }

        public void setIsScope(int isScope) {
            this.isScope = isScope;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLogoAdress() {
            return LogoAdress;
        }

        public void setLogoAdress(String LogoAdress) {
            this.LogoAdress = LogoAdress;
        }

        public String getCountComment() {
            return countComment;
        }

        public void setCountComment(String countComment) {
            this.countComment = countComment;
        }

        public int getPId() {
            return pId;
        }

        public void setPId(int pId) {
            this.pId = pId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCommonNum() {
            return commonNum;
        }

        public void setCommonNum(String commonNum) {
            this.commonNum = commonNum;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getSpecificid() {
            return specificid;
        }

        public void setSpecificid(int specificid) {
            this.specificid = specificid;
        }

        public String getNiceNum() {
            return niceNum;
        }

        public void setNiceNum(String niceNum) {
            this.niceNum = niceNum;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getBonus() {
            return bonus;
        }

        public void setBonus(String bonus) {
            this.bonus = bonus;
        }

        public String getBadNum() {
            return badNum;
        }

        public void setBadNum(String badNum) {
            this.badNum = badNum;
        }

        public List<ImageUrlBean> getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(List<ImageUrlBean> ImageUrl) {
            this.ImageUrl = ImageUrl;
        }

        public List<TextBean> getText() {
            return text;
        }

        public void setText(List<TextBean> text) {
            this.text = text;
        }

        public Brand getBrand() {
            return brand;
        }

        public void setBrand(Brand brand) {
            this.brand = brand;
        }

        public String getMerchantAddress() {
            return merchantAddress;
        }

        public void setMerchantAddress(String merchantAddress) {
            this.merchantAddress = merchantAddress;
        }

        public String getTypeScoure() {
            return typeScoure;
        }

        public void setTypeScoure(String typeScoure) {
            this.typeScoure = typeScoure;
        }

        public List<String> getProductScope() {
            return productScope;
        }

        public void setProductScope(List<String> productScope) {
            this.productScope = productScope;
        }

        public static class ImageUrlBean {
            /**
             * address : upload/memberheadimg/20170717100859263561425.png
             */

            private String address;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }

        public static class TextBean {
            /**
             * sale_num : 12
             * price : 500.0
             * imageurl : upload/memberheadimg/20170717100550711414593.png
             * name : 好东西
             */

            private int sale_num;
            private double minPrice;
            private double maxPrice;
            private String imageurl;
            private String name;

            public int getSale_num() {
                return sale_num;
            }

            public void setSale_num(int sale_num) {
                this.sale_num = sale_num;
            }

            public double getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(double minPrice) {
                this.minPrice = minPrice;
            }

            public double getMaxPrice() {
                return maxPrice;
            }

            public void setMaxPrice(double maxPrice) {
                this.maxPrice = maxPrice;
            }

            public String getImageurl() {
                return imageurl;
            }

            public void setImageurl(String imageurl) {
                this.imageurl = imageurl;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class Brand{
            private String brandImg;
            private String barndName;
            private String brandid;
            private String branddescription;
            private String productCount;
            private String address;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getBrandImg() {
                return brandImg;
            }

            public void setBrandImg(String brandImg) {
                this.brandImg = brandImg;
            }

            public String getBarndName() {
                return barndName;
            }

            public void setBarndName(String barndName) {
                this.barndName = barndName;
            }

            public String getBrandid() {
                return brandid;
            }

            public void setBrandid(String brandid) {
                this.brandid = brandid;
            }

            public String getBranddescription() {
                return branddescription;
            }

            public void setBranddescription(String branddescription) {
                this.branddescription = branddescription;
            }

            public String getProductCount() {
                return productCount;
            }

            public void setProductCount(String productCount) {
                this.productCount = productCount;
            }
        }

    }
}
