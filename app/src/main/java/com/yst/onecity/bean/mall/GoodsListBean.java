package com.yst.onecity.bean.mall;

import java.util.List;

/**
 * 商城列表的bean
 *
 * @author wuxiaofang
 * @version 4.0.0
 * @date 2018/3/21
 */

public class GoodsListBean {

    /**
     * code : 1
     * msg : 查询成功
     * content : {"product":[{"img":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171129/20171129200355873310812.jpg","salePrice":40,"name":"可口可乐","id":112,"saleNum":0},{"img":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180410/20180410101340197193413.jpg","salePrice":17.2,"name":"阿迪达斯EQT黑武士","id":302,"saleNum":0}],"count":2}
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
         * product : [{"img":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171129/20171129200355873310812.jpg","salePrice":40,"name":"可口可乐","id":112,"saleNum":0},{"img":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180410/20180410101340197193413.jpg","salePrice":17.2,"name":"阿迪达斯EQT黑武士","id":302,"saleNum":0}]
         * count : 2
         */

        private int count;
        private List<ProductBean> product;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public static class ProductBean {
            /**
             * img : https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171129/20171129200355873310812.jpg
             * salePrice : 40.0
             * name : 可口可乐
             * id : 112
             * saleNum : 0
             */

            private String img;
            private double salePrice;
            private String name;
            private int id;
            private int saleNum;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public double getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(double salePrice) {
                this.salePrice = salePrice;
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
        }
    }
}
