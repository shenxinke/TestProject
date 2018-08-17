package com.yst.onecity.bean.servermember;

import java.util.List;

/**
 * 商品分类
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/0/19
 */
public class ProductSortBean{

    /**
     * code : 1
     * msg : 查询成功
     * content : [{"id":1,"classifyName":"男女服饰"},{"id":3,"classifyName":"家居用品"}]
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
         * id : 1
         * classifyName : 男女服饰
         */

        private int id;
        private String classifyName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }
    }
}
