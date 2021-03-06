package com.yst.onecity.bean.servermember;

import java.util.List;

/**
 * 商品列表
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/0/19
 */
public class ProductListBean {


    /**
     * code : 1
     * msg : 成功
     * content : [{"salePrice":2,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171122/20171122155928785062537.jpg","name":"蛋糕","merchatId":19,"id":121,"saleNum":0,"spid":147,"firstid":27},{"salePrice":0.09,"merchantId":19,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171122/20171122144227740779229.jpg","name":"口香糖","id":116,"saleNum":0,"spid":137,"firstid":27}]
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
         * salePrice : 2.0
         * imageurl : https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171122/20171122155928785062537.jpg
         * name : 蛋糕
         * merchatId : 19
         * id : 121
         * saleNum : 0
         * spid : 147
         * firstid : 27
         * merchantId : 19
         */

        private double salePrice;
        private String imageurl;
        private String name;
        private int id;
        private int saleNum;
        private int spid;
        private int firstid;
        private int merchantId;

        public double getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(double salePrice) {
            this.salePrice = salePrice;
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

        public int getSpid() {
            return spid;
        }

        public void setSpid(int spid) {
            this.spid = spid;
        }

        public int getFirstid() {
            return firstid;
        }

        public void setFirstid(int firstid) {
            this.firstid = firstid;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }
    }
}
