package com.yst.onecity.interfaces;


/**
 *直播界面回调
 *
 * @author Shenxinke
 * @version 4.1.0
 * @data 2018/5/21
 */
public interface LiveView{
    /**
     *处理文本消息解析
     *
     * @param text
     * @param name
     * @param type
     */
    void refreshText(String text, String name,int type);
}
