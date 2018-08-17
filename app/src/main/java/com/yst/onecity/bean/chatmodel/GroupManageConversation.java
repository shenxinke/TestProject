package com.yst.onecity.bean.chatmodel;

import android.content.Context;
import com.yst.onecity.utils.MyLog;

import com.tencent.TIMCallBack;
import com.tencent.TIMGroupPendencyItem;
import com.tencent.qcloud.presentation.presenter.GroupManagerPresenter;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.R;

import java.util.Calendar;

/**
 * 群管理会话
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class GroupManageConversation extends AbstractConversation {

    private final String TAG = "GroupManageConversation";

    private TIMGroupPendencyItem lastMessage;

    private long unreadCount;


    public GroupManageConversation(TIMGroupPendencyItem message){
        lastMessage = message;
    }


    /**
     * 获取最后一条消息的时间
     */
    @Override
    public long getLastMessageTime() {
        return lastMessage.getAddTime();
    }

    /**
     * 获取未读消息数量
     */
    @Override
    public long getUnreadNum() {
        return unreadCount;
    }

    /**
     * 将所有消息标记为已读
     */
    @Override
    public void readAllMessage() {
        //不能传入最后一条消息时间，由于消息时间戳的单位是秒
        GroupManagerPresenter.readGroupManageMessage(Calendar.getInstance().getTimeInMillis(), new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                MyLog.i(TAG, "read all message error,code " + i);
            }

            @Override
            public void onSuccess() {
                MyLog.i(TAG, "read all message succeed");
            }
        });
    }

    /**
     * 获取头像
     */
    @Override
    public int getAvatar() {
        return R.drawable.ic_news;
    }

    /**
     * 跳转到聊天界面或会话详情
     *
     * @param context 跳转上下文
     */
    @Override
    public void navToDetail(Context context) {
        readAllMessage();
    }

    /**
     * 获取最后一条消息摘要
     */
    @Override
    public String getLastMessageSummary() {
        if (lastMessage == null) {
            return "";
        }
        String from = lastMessage.getFromUser();
        String to = lastMessage.getToUser();

        boolean isSelf = from.equals(UserInfo.getInstance().getId());
        switch (lastMessage.getPendencyType()){
            case INVITED_BY_OTHER:
                if (isSelf){
                    return TianyiApplication.getContext().getResources().getString(R.string.summary_me)+
                            TianyiApplication.getContext().getResources().getString(R.string.summary_group_invite)+
                            to+
                            TianyiApplication.getContext().getResources().getString(R.string.summary_group_add);
                }else{
                    if (to.equals(UserInfo.getInstance().getId())){
                        return from+
                                TianyiApplication.getContext().getResources().getString(R.string.summary_group_invite)+
                                TianyiApplication.getContext().getResources().getString(R.string.summary_me)+
                                TianyiApplication.getContext().getResources().getString(R.string.summary_group_add);
                    }else{
                        return from+
                                TianyiApplication.getContext().getResources().getString(R.string.summary_group_invite)+
                                to+
                                TianyiApplication.getContext().getResources().getString(R.string.summary_group_add);
                    }

                }
            case APPLY_BY_SELF:
                if (isSelf){
                    return TianyiApplication.getContext().getResources().getString(R.string.summary_me)+
                            TianyiApplication.getContext().getResources().getString(R.string.summary_group_apply)+
                            GroupInfo.getInstance().getGroupName(lastMessage.getGroupId());
                }else{
                    return from+TianyiApplication.getContext().getResources().getString(R.string.summary_group_apply)+GroupInfo.getInstance().getGroupName(lastMessage.getGroupId());
                }

            default:
                return "";
        }
    }

    /**
     * 获取名称
     */
    @Override
    public String getName() {
        return TianyiApplication.getContext().getString(R.string.conversation_system_group);
    }


    /**
     * 设置最后一条消息
     */
    public void setLastMessage(TIMGroupPendencyItem message){
        lastMessage = message;
    }


    /**
     * 设置未读数量
     *
     * @param count 未读数量
     */
    public void setUnreadCount(long count){
        unreadCount = count;
    }


}
