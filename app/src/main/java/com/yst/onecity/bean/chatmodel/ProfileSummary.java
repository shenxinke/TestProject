package com.yst.onecity.bean.chatmodel;

import android.content.Context;

/**
 * {@link ProfileSummary }Adapter的数据元素,可提取获取资料的摘要
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public interface ProfileSummary {


    /**
     * 获取头像资源
     * @return
     */
    int getAvatarRes();


    /**
     * 获取头像地址
     * @return
     */
    String getAvatarUrl();


    /**
     * 获取名字
     * @return
     */
    String getName();


    /**
     * 获取描述信息
     * @return
     */
    String getDescription();


    /**
     * 获取id
     * @return
     */
    String getIdentify();


    /**
     * 显示详情等点击事件
     *
     * @param context 上下文
     */
    void onClick(Context context);


}
