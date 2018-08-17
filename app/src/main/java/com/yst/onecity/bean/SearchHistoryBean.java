package com.yst.onecity.bean;

import java.util.List;

/**
 * 搜索历史列表实体类
 *
 * @author jiaofan
 * @date 2018/6/1
 * @version 4.2.0
 */
public class SearchHistoryBean {

    /**
     * 搜索历史列表
     */
    private List<SearchHistory> history;

    /**
     * 获取搜索历史列表
     * @return 搜索过的缓存
     */
    public List<SearchHistory> getHistory() {
        return history;
    }

    /**
     * 设置搜索历史
     * @param history 要设置的数据列表
     */
    public void setHistory(List<SearchHistory> history) {
        this.history = history;
    }
}