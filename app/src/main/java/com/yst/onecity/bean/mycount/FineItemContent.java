package com.yst.onecity.bean.mycount;

import java.util.List;

/**
 * 首页精选类实体类
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class FineItemContent {

    /**
     * code : 1
     * msg : 请求成功
     * content : [{"address":"upload/productTrace/20170420143954772810433.png,","num":222,"authorInfo":"万花筒","collectNum":224,"createdTime":"2017/06/13","id":3,"modelType":1,"title":"访谈标题1","type":1,"detailSummary":"摘要信息"}]
     */

    private int code;
    private String msg;
    private List<FineItemBean> content;

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

    public List<FineItemBean> getContent() {
        return content;
    }

    public void setContent(List<FineItemBean> content) {
        this.content = content;
    }

}
