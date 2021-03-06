package com.yst.onecity.bean;

import java.util.List;

/**
 * @author Shenxinke
 * @version 3.0.1
 * @data 2018/4/8
 */

public class OrderEvaluteAccomplishBean {

    /**
     * code : 1
     * msg : 查询成功！
     * content : {"pList":[{"product_id":338,"pImg":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180404/20180404094129270369226.jpg","name":"眼霜","start":5,"content":"第二次评价"}],"hList":[]}
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
        private List<PlistBean> pList;
        private List<?> hList;

        public List<PlistBean> getPList() {
            return pList;
        }

        public void setPList(List<PlistBean> pList) {
            this.pList = pList;
        }

        public List<?> getHList() {
            return hList;
        }

        public void setHList(List<?> hList) {
            this.hList = hList;
        }

        public static class PlistBean {
            /**
             * product_id : 338
             * pImg : https://ph-images.oss-cn-shenzhen.aliyuncs.com/product/20180404/20180404094129270369226.jpg
             * name : 眼霜
             * start : 5
             * content : 第二次评价
             */

            private int product_id;
            private String pImg;
            private String name;
            private int start;
            private String content;

            public int getProduct_id() {
                return product_id;
            }

            public void setProduct_id(int product_id) {
                this.product_id = product_id;
            }

            public String getPImg() {
                return pImg;
            }

            public void setPImg(String pImg) {
                this.pImg = pImg;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getStart() {
                return start;
            }

            public void setStart(int start) {
                this.start = start;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
