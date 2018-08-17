package com.tencent.qcloud.presentation.viewfeatures;

/**
 * 消息回调接口
 *
 * @author wangjingwei
 * @version 1.0.0
 * @date 18/04/11
 */
public interface MessageView {
    /**
     * 消息状态
     * @param newStatus
     */
    void onStatusChange(Status newStatus);

    enum Status{
        /**
         * 发送完成
         */
        NORMAL,
        /**
         * 发送中
         */
        SENDING,
        /**
         * 发送失败
         */
        ERROR,
    }
}
