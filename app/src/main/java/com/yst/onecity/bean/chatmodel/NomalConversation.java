package com.yst.onecity.bean.chatmodel;

import android.content.Context;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.R;
import com.yst.onecity.activity.chat.ChatActivity;

/**
 * 好友或群聊的会话
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class NomalConversation extends AbstractConversation {


    private TIMConversation conversation;


    /**
     * 最后一条消息
     */
    private AbstractMessage lastMessage;


    public NomalConversation(TIMConversation conversation){
        this.conversation = conversation;
        type = conversation.getType();
        identify = conversation.getPeer();
    }


    public void setLastMessage(AbstractMessage lastMessage) {
        this.lastMessage = lastMessage;
    }


    @Override
    public int getAvatar() {
        switch (type){
            case C2C:
                return R.drawable.head_other;
            case Group:
                return R.drawable.head_group;
            default:
                break;
        }
        return 0;
    }

    /**
     * 跳转到聊天界面或会话详情
     *
     * @param context 跳转上下文
     */
    @Override
    public void navToDetail(Context context) {
        ChatActivity.navToChat(context,identify,"" ,type);
    }

    /**
     * 获取最后一条消息摘要
     */
    @Override
    public String getLastMessageSummary(){
        if (conversation.hasDraft()){
            TextMessage textMessage = new TextMessage(conversation.getDraft());
            if (lastMessage == null || lastMessage.getMessage().timestamp() < conversation.getDraft().getTimestamp()){
                return TianyiApplication.getContext().getString(R.string.conversation_draft) + textMessage.getSummary();
            }else{
                return lastMessage.getSummary();
            }
        }else{
            if (lastMessage == null) {
                return "";
            }
            return lastMessage.getSummary();
        }
    }

    /**
     * 获取名称
     */
    @Override
    public String getName() {
        if (type == TIMConversationType.Group){
            name=GroupInfo.getInstance().getGroupName(identify);
            if ("".equals(name)) {
                name = identify;
            }
        }else{
            FriendProfile profile = FriendshipInfo.getInstance().getProfile(identify);
            name=profile == null?identify:profile.getName();
        }
        return name;
    }


    /**
     * 获取未读消息数量
     */
    @Override
    public long getUnreadNum(){
        if (conversation == null) {
            return 0;
        }
        return conversation.getUnreadMessageNum();
    }

    /**
     * 将所有消息标记为已读
     */
    @Override
    public void readAllMessage(){
        if (conversation != null){
            conversation.setReadMessage();
        }
    }


    /**
     * 获取最后一条消息的时间
     */
    @Override
    public long getLastMessageTime() {
        if (conversation.hasDraft()){
            if (lastMessage == null || lastMessage.getMessage().timestamp() < conversation.getDraft().getTimestamp()){
                return conversation.getDraft().getTimestamp();
            }else{
                return lastMessage.getMessage().timestamp();
            }
        }
        if (lastMessage == null) {
            return 0;
        }
        return lastMessage.getMessage().timestamp();
    }

    /**
     * 获取会话类型
     */
    public TIMConversationType getType(){
        return conversation.getType();
    }
}
