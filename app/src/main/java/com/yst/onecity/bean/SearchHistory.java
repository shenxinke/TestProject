package com.yst.onecity.bean;

/**
 * 搜索历史
 *
 * @author jiaofan
 * @date 2018/6/1
 * @version 4.2.0
 */
public class SearchHistory {
    /**
     * 记录的id
     */
    private String id;
    /**
     * 保存的数据
     */
    private String keyword;

    public SearchHistory(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
