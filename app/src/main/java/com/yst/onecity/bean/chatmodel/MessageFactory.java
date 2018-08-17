package com.yst.onecity.bean.chatmodel;


import com.tencent.TIMMessage;

/**
 * 消息工厂
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/0/19
 */
public class MessageFactory {

    private MessageFactory() {}

    /**
     * 消息工厂方法
     */
    public static AbstractMessage getMessage(TIMMessage message){
        switch (message.getElement(0).getType()){
            case Text:
            case Face:
                return new TextMessage(message);
            case Image:
                return new ImageMessage(message);
            case Sound:
                return new VoiceMessage(message);
            case Video:
                return new VideoMessage(message);
            case GroupTips:
                return new GroupTipMessage(message);
            case File:
                return new FileMessage(message);
            case Custom:
                return new CustomMessage(message);
            default:
                return null;
        }
    }



}
