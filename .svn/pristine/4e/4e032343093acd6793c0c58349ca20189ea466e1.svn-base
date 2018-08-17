package com.yst.onecity.activity.chat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMFriendFutureItem;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGroupCacheInfo;
import com.tencent.TIMGroupPendencyItem;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.presenter.ConversationPresenter;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;
import com.tencent.qcloud.presentation.presenter.GroupManagerPresenter;
import com.tencent.qcloud.presentation.viewfeatures.ConversationView;
import com.tencent.qcloud.presentation.viewfeatures.FriendshipMessageView;
import com.tencent.qcloud.presentation.viewfeatures.GroupManageMessageView;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.chatadapter.ConversationAdapter;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.bean.chatmodel.AbstractConversation;
import com.yst.onecity.bean.chatmodel.CustomMessage;
import com.yst.onecity.bean.chatmodel.FriendshipConversation;
import com.yst.onecity.bean.chatmodel.GroupManageConversation;
import com.yst.onecity.bean.chatmodel.MessageFactory;
import com.yst.onecity.bean.chatmodel.NomalConversation;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.slidingswipemenu.SwipeMenu;
import com.yst.onecity.view.slidingswipemenu.SwipeMenuCreator;
import com.yst.onecity.view.slidingswipemenu.SwipeMenuItem;
import com.yst.onecity.view.slidingswipemenu.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 会话列表页面
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/07/06
 */
public class ConvaActivity extends BaseActivity implements ConversationView,FriendshipMessageView,GroupManageMessageView {

    private final String TAG = "ConversationFragment";
    private List<AbstractConversation> conversationList = new LinkedList<AbstractConversation>();
    private ConversationAdapter adapter;
    private SwipeMenuListView listView;
    private ConversationPresenter presenter;
    private FriendshipManagerPresenter friendshipManagerPresenter;
    private GroupManagerPresenter groupManagerPresenter;
    private List<String> groupList;
    private FriendshipConversation friendshipConversation;
    private GroupManageConversation groupManageConversation;
    private ImageView mBackIv;
    private TextView mTitleTv;

    @Override
    public int bindLayout() {
        return R.layout.activity_conva;
    }

    @Override
    public void initData() {
        TianyiApplication.instance.addActivity(ConvaActivity.this);
        ((ImageView) findViewById(R.id.activity_back_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConvaActivity.this.finish();
            }
        });
        ((TextView) findViewById(R.id.activity_title_tv)).setText(getString(R.string.home_conversation_tab));
///        ((Button) findViewById(R.id.my_btn_new)).setOnClickListener(new View.OnClickListener() {
///            @Override
///            public void onClick(View v) {
///                Intent intent = new Intent(ConvaActivity.this, SearchFriendActivity.class);
///                ConvaActivity.this.startActivity(intent);
///                ChatActivity.navToChat(ConvaActivity.this, "ssss1234", TIMConversationType.C2C);
///            }
///        });
        listView = (SwipeMenuListView) findViewById(R.id.list);
        adapter = new ConversationAdapter(this, R.layout.item_conversation, conversationList);
        listView.setAdapter(adapter);
        initSwipeList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                conversationList.get(position).navToDetail(ConvaActivity.this);
                if (conversationList.get(position) instanceof GroupManageConversation) {
                    groupManagerPresenter.getGroupManageLastMessage();
                }

            }
        });
        friendshipManagerPresenter = new FriendshipManagerPresenter(this);
        groupManagerPresenter = new GroupManagerPresenter(this);
        presenter = new ConversationPresenter(this);
        presenter.getConversation();
        registerForContextMenu(listView);
    }

    @Override
    public void initView(List<TIMConversation> conversationList) {
        this.conversationList.clear();
        groupList = new ArrayList<String>();
        for (TIMConversation item:conversationList){
            switch (item.getType()){
                case C2C:
                case Group:
                    this.conversationList.add(new NomalConversation(item));
                    groupList.add(item.getPeer());
                    break;
                default:
                    break;
            }
        }
        friendshipManagerPresenter.getFriendshipLastMessage();
        groupManagerPresenter.getGroupManageLastMessage();
    }

    @Override
    public void updateMessage(TIMMessage message) {
        if (message == null){
            adapter.notifyDataSetChanged();
            return;
        }
        if (message.getConversation().getType() == TIMConversationType.System){
            groupManagerPresenter.getGroupManageLastMessage();
            return;
        }
        if (MessageFactory.getMessage(message) instanceof CustomMessage) {
            return;
        }
        NomalConversation conversation = new NomalConversation(message.getConversation());
        Iterator<AbstractConversation> iterator =conversationList.iterator();
        while (iterator.hasNext()){
            AbstractConversation c = iterator.next();
            if (conversation.equals(c)){
                conversation = (NomalConversation) c;
                iterator.remove();
                break;
            }
        }
        conversation.setLastMessage(MessageFactory.getMessage(message));
        conversationList.add(conversation);
        Collections.sort(conversationList);
        refresh();
    }

    @Override
    public void updateFriendshipMessage() {
        friendshipManagerPresenter.getFriendshipLastMessage();
    }

    @Override
    public void removeConversation(String identify) {
        Iterator<AbstractConversation> iterator = conversationList.iterator();
        while(iterator.hasNext()){
            AbstractConversation conversation = iterator.next();
            if (conversation.getIdentify()!=null&&conversation.getIdentify().equals(identify)){
                iterator.remove();
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void updateGroupInfo(TIMGroupCacheInfo info) {
        for (AbstractConversation conversation : conversationList){
            if (conversation.getIdentify()!=null && conversation.getIdentify().equals(info.getGroupInfo().getGroupId())){
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void refresh() {
        Collections.sort(conversationList);
        adapter.notifyDataSetChanged();
//        setMsgUnread(getTotalUnreadNum() == 0);
        MyLog.e("sss", "count === " + getTotalUnreadNum());

        //获取用户资料
        TIMFriendshipManager.getInstance().getUsersProfile(getUserList(), new TIMValueCallBack<List<TIMUserProfile>>(){
            @Override
            public void onError(int code, String s) {
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                MyLog.e("sss", "getUsersProfile failed: " + code + " desc:" + s);
            }

            @Override
            public void onSuccess(List<TIMUserProfile> result) {
                MyLog.e("sss", "getUsersProfile succ");
                for(TIMUserProfile res : result){
                    MyLog.e("sss", "identifier: " + res.getIdentifier()
                                 + " nickName: " + res.getNickName()
                                 + " remark: " + res.getRemark()
                                 + " image:" + res.getFaceUrl());
                    for(AbstractConversation conversation : conversationList) {
                        if(res.getIdentifier().equals(conversation.getIdentify())) {
                            conversation.setFaceUrl(res.getFaceUrl());
                            conversation.setNickName(res.getNickName());
                        }
                    }
                }
                Collections.sort(conversationList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onGetFriendshipLastMessage(TIMFriendFutureItem message, long unreadCount) {
        if (friendshipConversation == null){
            friendshipConversation = new FriendshipConversation(message);
            conversationList.add(friendshipConversation);
        }else{
            friendshipConversation.setLastMessage(message);
        }
        friendshipConversation.setUnreadCount(unreadCount);
        Collections.sort(conversationList);
        refresh();
    }

    @Override
    public void onGetFriendshipMessage(List<TIMFriendFutureItem> message) {
        friendshipManagerPresenter.getFriendshipLastMessage();
    }

    @Override
    public void onGetGroupManageLastMessage(TIMGroupPendencyItem message, long unreadCount) {
        if (groupManageConversation == null){
            groupManageConversation = new GroupManageConversation(message);
            conversationList.add(groupManageConversation);
        }else{
            groupManageConversation.setLastMessage(message);
        }
        groupManageConversation.setUnreadCount(unreadCount);
        Collections.sort(conversationList);
        refresh();
    }

    @Override
    public void onGetGroupManageMessage(List<TIMGroupPendencyItem> message) {

    }

    private long getTotalUnreadNum(){
        long num = 0;
        for (AbstractConversation conversation : conversationList){
            num += conversation.getUnreadNum();
        }
        return num;
    }

    /**
     * 待获取用户资料的用户列表
     * @return 好友
     */
    private List<String> getUserList() {
        List<String> users = new ArrayList<String>();
        users.clear();
        for (AbstractConversation conversation : conversationList){
            users.add(conversation.getIdentify());
        }
        return users;
    }

    /**
     * 创建侧滑按钮以及事件监听
     */
    private void initSwipeList() {
        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(Utils.dpToPx(getResources(), 72));
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(swipeMenuCreator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        if(TIMManager.getInstance().deleteConversation(TIMConversationType.C2C, conversationList.get(position).getIdentify())) {
                            conversationList.remove(position);
                            refresh();
                            ToastUtils.show("会话删除成功!");
                        } else {
                            ToastUtils.show("会话删除失败,请重试!");
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
