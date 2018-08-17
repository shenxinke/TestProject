package com.yst.onecity.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMValueCallBack;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.bean.LiveMeasageBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.interfaces.LiveView;
import java.util.List;

/**
 * @author Shenxinke
 * @version 4.1.0
 * @data 2018/5/21
 * <p>
 * 直播工具类
 */

public class LiveHelp {
    private TIMConversation mGroupConversation;
    public Context mContext;
    private LiveView mLiveView;

    public LiveHelp(Context context, LiveView liveview) {
        mContext = context;
        mLiveView = liveview;
    }

    public void sendGroupText(TIMMessage nMsg) {
        if (mGroupConversation == null) {
            return;
        }
        mGroupConversation.sendMessage(nMsg, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {
                Log.e("sss", "---------onError  " + s);
                //消息体太长
                if (i == Const.INTEGER_200) {
                    if (null != mContext) {
                        Toast.makeText(mContext, "Text too long ", Toast.LENGTH_SHORT).show();
                    }
                    //群主不存在
                } else if (i == Const.INTEGER_6011) {
                    if (null != mContext) {
                        Toast.makeText(mContext, "Host don't exit ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                Log.e("sss", "---------onSuccess");
                //发送成回显示消息内容
                for (int j = 0; j < timMessage.getElementCount(); j++) {
                    TIMElem elem = (TIMElem) timMessage.getElement(0);
                    if (timMessage.isSelf()) {
                        MyLog.e("sss", "onSuccess--- " + TianyiApplication.appCommonBean.getNickname());
                        handleTextMessage(elem);
                    } else {
                        handleTextMessage(elem);
                    }
                }

            }
        });
    }

    /**
     * 群消息回调
     */
    private TIMMessageListener msgListener = new TIMMessageListener() {
        @Override
        public boolean onNewMessages(List<TIMMessage> list) {

            Log.e("sss", "--onNewMessages  " + list.size());
            //  解析TIM推送消息
            parseIMMessage(list);
            return false;
        }
    };

    /**
     * 解析消息回调
     *
     * @param list 消息列表
     */
    private void parseIMMessage(List<TIMMessage> list) {
        List<TIMMessage> tlist = list;
        if (tlist.size() > 0) {
            if (mGroupConversation != null) {
                mGroupConversation.setReadMessage(tlist.get(0));
            }
            MyLog.d("sss", "parseIMMessage readMessage " + tlist.get(0).timestamp());
        }

        for (int i = tlist.size() - 1; i >= 0; i--) {
            TIMMessage currMsg = tlist.get(i);
            for (int j = 0; j < currMsg.getElementCount(); j++) {
                if (currMsg.getElement(j) == null) {
                    continue;
                }
                TIMElem elem = currMsg.getElement(j);
                TIMElemType type = elem.getType();
                String sendId = currMsg.getSender();
                //系统消息
///                if (type == TIMElemType.GroupSystem) {
//                    if (TIMGroupSystemElemType.TIM_GROUP_SYSTEM_DELETE_GROUP_TYPE == ((TIMGroupSystemElem) elem).getSubtype()) {
//                        if (null != mContext) {
//                            mContext.sendBroadcast(new Intent(Constants.ACTION_HOST_LEAVE));
//                        }
//                    }
///                }

                /**
                 * 定制消息
                 */
                if (type == TIMElemType.Custom) {
                    String id, nickname;
                    if (currMsg.getSenderProfile() != null) {
                        id = currMsg.getSenderProfile().getIdentifier();
                        nickname = currMsg.getSenderProfile().getNickName();
                    } else {
                        id = sendId;
                        nickname = sendId;
                    }
                    handleTextMessage(elem);
///                    handleCustomMsg(elem, id, nickname);
                    continue;
                }

                //其他群消息过滤
///                if (currMsg.getConversation() != null && currMsg.getConversation().getPeer() != null)
//                    if (!CurLiveInfo.getChatRoomId().equals(currMsg.getConversation().getPeer())) {
//                        continue;
///                    }

                //最后处理文本消息
                if (type == TIMElemType.Text) {
                    if (currMsg.isSelf()) {
                        handleTextMessage(elem);
                    } else {
                        handleTextMessage(elem);
                    }
                }
            }
        }
    }

    /**
     * 处理文本消息解析
     *
     * @param elem
     */
    private void handleTextMessage(TIMElem elem) {
        if (elem == null) {
            return;
        }

        try {
            byte[] data = ((TIMCustomElem) elem).getData();
            String jsonStr = new String(data);
            MyLog.e("sss", "jsonStr____" + jsonStr);
            LiveMeasageBean liveMeasageBean = new Gson().fromJson(jsonStr, LiveMeasageBean.class);
            if (liveMeasageBean != null) {
                String name = liveMeasageBean.getName();
                String msg = liveMeasageBean.getMsg();
                int type = liveMeasageBean.getType();
                mLiveView.refreshText(msg, name, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置监听器
     */
    public void initTIMListener(String chatRoomId) {
        Log.e("sss", "initTIM ----current room id " + chatRoomId);
        mGroupConversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, chatRoomId);
        Log.e("sss", "initTIM --- mGroupConversation");
        TIMManager.getInstance().addMessageListener(msgListener);
    }

    public void onDestory() {
        TIMManager.getInstance().removeMessageListener(msgListener);
        mLiveView = null;
        mContext = null;
    }

}
