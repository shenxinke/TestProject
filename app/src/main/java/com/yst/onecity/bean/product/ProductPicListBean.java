package com.yst.onecity.bean.product;

import java.util.List;

/**
 * 商品图片详情
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/0/19
 */
public class ProductPicListBean {


    /**
     * code : 1
     * msg : 成功
     * content : [{"id":6,"type":0,"content":"介绍2"},{"address":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/editor/20171023/20171023145858811497807.jpg","id":7,"type":1},{"id":8,"type":0,"content":"介绍3"},{"address":"https://ph-images.oss-cn-shenzhen.aliyuncs.com/editor/20171023/20171023145917316955291.jpg","id":9,"type":1}]
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
         * id : 6
         * type : 0
         * content : 介绍2
         * address : https://ph-images.oss-cn-shenzhen.aliyuncs.com/editor/20171023/20171023145858811497807.jpg
         */

        private int id;
        private int type;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
