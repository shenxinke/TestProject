package com.yst.onecity.bean.commissioner;

import java.util.List;

/**
 * 商品bean
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/1
 */

public class GoodsBean {

    /**
     * code : 1
     * msg : 获取成功
     * content : [{"specificationId":1297,"common_comment_num":0,"productId":519,"sale_num":9,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180521/20180521205125546429007.jpg","name":"ces","type":0,"sale_price":1,"views":1},{"specificationId":1295,"common_comment_num":0,"productId":518,"sale_num":23,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180521/20180521204035877968329.jpg","name":"电脑","type":0,"sale_price":12,"views":1},{"specificationId":984,"common_comment_num":0,"productId":369,"sale_num":3000,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180420/20180420112446813379233.jpg","name":"白色休闲外套","type":0,"sale_price":0.01,"views":1},{"specificationId":1165,"common_comment_num":0,"productId":436,"sale_num":0,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180515/20180515150410276239220.jpg","name":"の","type":0,"sale_price":5,"views":0},{"specificationId":972,"common_comment_num":3,"productId":384,"sale_num":423,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180427/20180427185747498056476.jpg","name":"蓝色妖姬","type":0,"sale_price":2,"views":6},{"specificationId":531,"common_comment_num":0,"productId":277,"sale_num":720,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180408/20180408181456775927009.jpg","name":"白百合","type":0,"sale_price":2,"views":1},{"specificationId":248,"common_comment_num":0,"productId":110,"sale_num":0,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171129/20171129183102694831718.jpg","name":"圆珠笔","type":1,"sale_price":5,"views":10},{"specificationId":382,"common_comment_num":0,"productId":116,"sale_num":0,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171129/20171129215914700459083.png","name":"topri蘑俪水润洗护套装（单洗单护）","type":1,"sale_price":158,"views":0},{"specificationId":382,"common_comment_num":0,"productId":116,"sale_num":0,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171129/20171129215914700459083.png","name":"topri蘑俪水润洗护套装（单洗单护）","type":1,"sale_price":158,"views":0},{"specificationId":206,"common_comment_num":0,"productId":21,"sale_num":0,"imageurl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171021/20171021094938160958251.jpg","name":"《科技文摘》----十九大特刊(10份)","type":1,"sale_price":50,"views":1}]
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
         * specificationId : 1297
         * common_comment_num : 0
         * productId : 519
         * sale_num : 9
         * imageurl : https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180521/20180521205125546429007.jpg
         * name : ces
         * type : 0
         * sale_price : 1
         * views : 1
         */

        private int specificationId;
        private int common_comment_num;
        private int productId;
        private int sale_num;
        private String imageurl;
        private String name;
        private int type;
        private float sale_price;
        private int views;

        public int getSpecificationId() {
            return specificationId;
        }

        public void setSpecificationId(int specificationId) {
            this.specificationId = specificationId;
        }

        public int getCommon_comment_num() {
            return common_comment_num;
        }

        public void setCommon_comment_num(int common_comment_num) {
            this.common_comment_num = common_comment_num;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getSale_num() {
            return sale_num;
        }

        public void setSale_num(int sale_num) {
            this.sale_num = sale_num;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public float getSale_price() {
            return sale_price;
        }

        public void setSale_price(float sale_price) {
            this.sale_price = sale_price;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }
    }
}
