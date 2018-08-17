package com.tencent.qcloud.presentation.viewfeatures;

import com.tencent.TIMConversation;
import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMMessage;

import java.util.List;

/**
 * 会话列表界面的接口
 *
 * @author wangjingwei
 * @version 1.0.0
 * @date 18/04/11
 */
public interface ConversationView extends MvpView {

    /**
     * 初始化界面或刷新界面
     * @param conversationList
     */
    void initView(List<TIMConversation> conversationList);

    /**
     * 更新最新消息显示
     *
     * @param message 最后一条消息
     */
    void updateMessage(TIMMessage message);

    /**
     * 更新好友关系链消息
     */
    void updateFriendshipMessage();


    /**
     * 删除会话
     * @param identify
     */
    void removeConversation(String identify);

    /**
     * 更新群信息
     * @param info
     */
    void updateGroupInfo(TIMGroupCacheInfo info);

    /**
     * 刷新
     */
    void refresh();
}
