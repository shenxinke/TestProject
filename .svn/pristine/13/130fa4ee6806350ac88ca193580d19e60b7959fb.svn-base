package com.yst.onecity.bean.chatmodel;

import android.content.Context;

import com.tencent.TIMConversationType;

/**
 * 会话数据
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public abstract class AbstractConversation implements Comparable {

    /**
     * 会话对象id
     */
    protected String identify;

    /**
     * 会话类型
     */
    protected TIMConversationType type;

    /**
     * 会话对象名称
     */
    protected String name;

    /**
     * 会话对象昵称
     */
    protected String nickName;

    /**
     * 头像地址
     */
    protected String faceUrl;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    /**
     * 获取最后一条消息的时间
     * @return
     */
    abstract public long getLastMessageTime();

    /**
     * 获取未读消息数量
     * @return
     */
    abstract public long getUnreadNum();


    /**
     * 将所有消息标记为已读
     */
    abstract public void readAllMessage();


    /**
     * 获取头像
     * @return
     */
    abstract public int getAvatar();


    /**
     * 跳转到聊天界面或会话详情
     *
     * @param context 跳转上下文
     */
    abstract public void navToDetail(Context context);

    /**
     * 获取最后一条消息摘要
     * @return
     */
    abstract public String getLastMessageSummary();

    /**
     * 获取名称
     * @return
     */
    abstract public String getName();


    public String getIdentify(){
        return identify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractConversation that = (AbstractConversation) o;
        if (!identify.equals(that.identify)) {
            return false;
        }
        return type == that.type;

    }

    @Override
    public int hashCode() {
        int result = identify.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }


    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(Object another) {
        if (another instanceof AbstractConversation){
            AbstractConversation anotherConversation = (AbstractConversation) another;
            long timeGap = anotherConversation.getLastMessageTime() - getLastMessageTime();
            if (timeGap > 0) {
                return 1;
            } else if (timeGap < 0) {
                return -1;
            }
            return 0;
        }else{
            throw new ClassCastException();
        }
    }



}
