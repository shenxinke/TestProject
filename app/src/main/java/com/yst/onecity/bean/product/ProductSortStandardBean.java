package com.yst.onecity.bean.product;

import java.util.List;

/**
 * 商品规格详情
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/0/19
 */
public class ProductSortStandardBean {


    /**
     * code : 1
     * msg : 成功
     * content : {"standard":[{"standard":"深色-大","salePrice":168,"standardId":228,"KuNum":0,"specificationsid":"106,108"},{"standard":"浅色-大","salePrice":168,"standardId":229,"KuNum":5,"specificationsid":"107,108"},{"standard":"深色-中","salePrice":140,"standardId":230,"KuNum":4,"specificationsid":"106,109"},{"standard":"浅色-中","salePrice":140,"standardId":231,"KuNum":3,"specificationsid":"107,109"},{"standard":"深色-小","salePrice":112,"standardId":232,"KuNum":6,"specificationsid":"106,110"},{"standard":"浅色-小","salePrice":120,"standardId":233,"KuNum":6,"specificationsid":"107,110"}],"classify":[{"classId":31,"classifyone":"生活"}],"bonus":"168","SaleSum":8,"pId":157,"spec":[{"subspec":[{"name":"深色","id":106},{"name":"浅色","id":107}],"name":"颜色","id":58},{"subspec":[{"name":"大","id":108},{"name":"中","id":109},{"name":"小","id":110}],"name":"大小","id":59}],"defaultNum":0,"imageUrl":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171124/20171124101350860527253.jpeg","minPrice":112,"name":"马克杯","defaultSalePrice":168,"maxPrice":168,"goodsnum":null}
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
         * standard : [{"standard":"深色-大","salePrice":168,"standardId":228,"KuNum":0,"specificationsid":"106,108"},{"standard":"浅色-大","salePrice":168,"standardId":229,"KuNum":5,"specificationsid":"107,108"},{"standard":"深色-中","salePrice":140,"standardId":230,"KuNum":4,"specificationsid":"106,109"},{"standard":"浅色-中","salePrice":140,"standardId":231,"KuNum":3,"specificationsid":"107,109"},{"standard":"深色-小","salePrice":112,"standardId":232,"KuNum":6,"specificationsid":"106,110"},{"standard":"浅色-小","salePrice":120,"standardId":233,"KuNum":6,"specificationsid":"107,110"}]
         * classify : [{"classId":31,"classifyone":"生活"}]
         * bonus : 168
         * SaleSum : 8
         * pId : 157
         * spec : [{"subspec":[{"name":"深色","id":106},{"name":"浅色","id":107}],"name":"颜色","id":58},{"subspec":[{"name":"大","id":108},{"name":"中","id":109},{"name":"小","id":110}],"name":"大小","id":59}]
         * defaultNum : 0
         * imageUrl : https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20171124/20171124101350860527253.jpeg
         * minPrice : 112
         * name : 马克杯
         * defaultSalePrice : 168
         * maxPrice : 168
         * goodsnum : null
         */

        private String bonus;
        private int SaleSum;
        private int pId;
        private int defaultNum;
        private String imageUrl;
        private double minPrice;
        private String name;
        private double defaultSalePrice;
        private double maxPrice;
        private Object goodsnum;
        private List<StandardBean> standard;
        private List<ClassifyBean> classify;
        private List<SpecBean> spec;

        public String getBonus() {
            return bonus;
        }

        public void setBonus(String bonus) {
            this.bonus = bonus;
        }

        public int getSaleSum() {
            return SaleSum;
        }

        public void setSaleSum(int SaleSum) {
            this.SaleSum = SaleSum;
        }

        public int getPId() {
            return pId;
        }

        public void setPId(int pId) {
            this.pId = pId;
        }

        public int getDefaultNum() {
            return defaultNum;
        }

        public void setDefaultNum(int defaultNum) {
            this.defaultNum = defaultNum;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public double getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(double minPrice) {
            this.minPrice = minPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getDefaultSalePrice() {
            return defaultSalePrice;
        }

        public void setDefaultSalePrice(double defaultSalePrice) {
            this.defaultSalePrice = defaultSalePrice;
        }

        public double getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(double maxPrice) {
            this.maxPrice = maxPrice;
        }

        public Object getGoodsnum() {
            return goodsnum;
        }

        public void setGoodsnum(Object goodsnum) {
            this.goodsnum = goodsnum;
        }

        public List<StandardBean> getStandard() {
            return standard;
        }

        public void setStandard(List<StandardBean> standard) {
            this.standard = standard;
        }

        public List<ClassifyBean> getClassify() {
            return classify;
        }

        public void setClassify(List<ClassifyBean> classify) {
            this.classify = classify;
        }

        public List<SpecBean> getSpec() {
            return spec;
        }

        public void setSpec(List<SpecBean> spec) {
            this.spec = spec;
        }

        public static class StandardBean {
            /**
             * standard : 深色-大
             * salePrice : 168
             * standardId : 228
             * KuNum : 0
             * specificationsid : 106,108
             */

            private String standard;
            private double salePrice;
            private int standardId;
            private int KuNum;
            private String specificationsid;

            public String getStandard() {
                return standard;
            }

            public void setStandard(String standard) {
                this.standard = standard;
            }

            public double getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(double salePrice) {
                this.salePrice = salePrice;
            }

            public int getStandardId() {
                return standardId;
            }

            public void setStandardId(int standardId) {
                this.standardId = standardId;
            }

            public int getKuNum() {
                return KuNum;
            }

            public void setKuNum(int KuNum) {
                this.KuNum = KuNum;
            }

            public String getSpecificationsid() {
                return specificationsid;
            }

            public void setSpecificationsid(String specificationsid) {
                this.specificationsid = specificationsid;
            }
        }

        public static class ClassifyBean {
            /**
             * classId : 31
             * classifyone : 生活
             */

            private int classId;
            private String classifyone;

            public int getClassId() {
                return classId;
            }

            public void setClassId(int classId) {
                this.classId = classId;
            }

            public String getClassifyone() {
                return classifyone;
            }

            public void setClassifyone(String classifyone) {
                this.classifyone = classifyone;
            }
        }

        public static class SpecBean {
            /**
             * subspec : [{"name":"深色","id":106},{"name":"浅色","id":107}]
             * name : 颜色
             * id : 58
             */

            private String name;
            private int id;
            private List<SubspecBean> subspec;

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

            public List<SubspecBean> getSubspec() {
                return subspec;
            }

            public void setSubspec(List<SubspecBean> subspec) {
                this.subspec = subspec;
            }

            public static class SubspecBean {
                /**
                 * name : 深色
                 * id : 106
                 */

                private String name;
                private int id;

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
            }
        }
    }
}
