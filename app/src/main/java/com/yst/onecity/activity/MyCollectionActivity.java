package com.yst.onecity.activity;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.config.Const;
import com.yst.onecity.eventbus.EditChangetEvent;
import com.yst.onecity.fragment.CollectionGoodsFragment;
import com.yst.onecity.fragment.CollectionMessageFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的收藏页面
 *
 * @author chejianqi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class MyCollectionActivity extends BaseActivity {

    @BindView(R.id.txt_collection_left_goods_content)
    TextView txtCollectionLeftGoodsContent;
    @BindView(R.id.txt_collection_left_goods_bottom)
    TextView txtCollectionLeftGoodsBottom;
    @BindView(R.id.llayout_collection_goods)
    LinearLayout llayoutCollectionGoods;
    @BindView(R.id.txt_collection_right_message_content)
    TextView txtCollectionRightMessageContent;
    @BindView(R.id.txt_collection_right_message_bottom)
    TextView txtCollectionRightMessageBottom;
    @BindView(R.id.llayout_collection_message)
    LinearLayout llayoutCollectionMessage;
    @BindView(R.id.llayout_collection_top)
    LinearLayout llayoutCollectionTop;
    @BindView(R.id.view_collection_line)
    View viewCollectionLine;
    @BindView(R.id.frame_collection_root)
    FrameLayout frameCollectionRoot;
    @BindView(R.id.activity_my_collection)
    LinearLayout activityMyCollection;
    @BindView(R.id.activity_back_iv)
    ImageView activityBackIv;
    @BindView(R.id.activity_title_tv)
    TextView activityTitleTv;
    @BindView(R.id.activity_title_right_img)
    ImageView activityTitleRightImg;
    @BindView(R.id.activity_title_right_chat_say_img)
    ImageView activityTitleRightChatSayImg;
    @BindView(R.id.activity_complete_tv)
    TextView activityCompleteTv;
    /**
     * 页面跳转控制器
     */
    private FragmentManager fMgr;
    private CollectionGoodsFragment fragmentCollectionGoods;
    private CollectionMessageFragment fragmentCollectMessage;
    private int index = 0;
    private boolean isGoodEdit = false, isMessageEdit = false;
    public static MyCollectionActivity instance = null;

    @Override
    public int bindLayout() {
        return R.layout.activity_my_collection;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        instance = this;
        activityTitleTv.setText(getString(R.string.my_collection));
        activityCompleteTv.setVisibility(View.VISIBLE);
        activityCompleteTv.setText("编辑");
        fMgr = getSupportFragmentManager();
        setTabSelection(index);
    }

    @OnClick({R.id.activity_back_iv})
    public void finishActivity() {
        //TODO change main view bottom button status
        finish();
    }

    @OnClick({R.id.activity_complete_tv})
    public void editCollection() {
        if (Const.CONS_STR_WANCHENG.equals(activityCompleteTv.getText())) {
            activityCompleteTv.setText("编辑");
        } else {
            activityCompleteTv.setText("完成");
        }
        //TODO change main view bottom button status
        switch (index) {
            case 0:
                isGoodEdit = !isGoodEdit;
                fragmentCollectionGoods.isEdit(isGoodEdit);
                break;
            case 1:
                isMessageEdit = !isMessageEdit;
                fragmentCollectMessage.isEdit(isMessageEdit);
                break;
            default:
        }
    }

    @OnClick({R.id.llayout_collection_goods})
    public void showCollectGoods() {
        activityCompleteTv.setText("编辑");
        //TODO change main view bottom button status
        index = 0;
        viewCollectionLine.setVisibility(View.VISIBLE);
        txtCollectionLeftGoodsContent.setTextColor(Color.parseColor("#FFBE0F"));
        txtCollectionLeftGoodsBottom.setVisibility(View.VISIBLE);
        txtCollectionRightMessageContent.setTextColor(Color.parseColor("#000000"));
        txtCollectionRightMessageBottom.setVisibility(View.GONE);
        setTabSelection(index);
    }

    @OnClick({R.id.llayout_collection_message})
    public void showCollectMessage() {
        activityCompleteTv.setText("编辑");
        index = 1;
        viewCollectionLine.setVisibility(View.GONE);
        //TODO change main view bottom button status
        txtCollectionRightMessageContent.setTextColor(Color.parseColor("#FFBE0F"));
        txtCollectionRightMessageBottom.setVisibility(View.VISIBLE);
        txtCollectionLeftGoodsContent.setTextColor(Color.parseColor("#000000"));
        txtCollectionLeftGoodsBottom.setVisibility(View.GONE);
        setTabSelection(index);
    }

    @Subscribe
    public void onEventMainThread(EditChangetEvent editChangetEvent) {
        activityCompleteTv.setText("编辑");
        isGoodEdit = false;
        isMessageEdit = false;
    }

    /**
     * 设置当前选中的标签状态和对应的内容展示
     */
    private void setTabSelection(int position) {
        // TODO Auto-generated method stub
        // 更改底部导航栏按钮状态
        FragmentTransaction ftn = fMgr.beginTransaction();
        // 想要显示一个fragment,先隐藏所有fragment，防止重叠
        hideFragments(ftn);
        switch (position) {
            case 0:
                if (fragmentCollectionGoods == null) {
                    fragmentCollectionGoods = new CollectionGoodsFragment();
                    ftn.add(R.id.frame_collection_root, fragmentCollectionGoods, "fragmentCollectGoods");
                } else {
                    ftn.show(fragmentCollectionGoods);
                }
                break;
            case 1:
                if (fragmentCollectMessage == null) {
                    fragmentCollectMessage = new CollectionMessageFragment();
                    ftn.add(R.id.frame_collection_root, fragmentCollectMessage, "fragmentCollectHead");
                } else {
                    ftn.show(fragmentCollectMessage);
                }
                break;
            default:
        }
        ftn.commitAllowingStateLoss();
    }

    /**
     * 当fragment已被实例化，就隐藏起来
     */
    private void hideFragments(FragmentTransaction ftn) {
        // TODO Auto-generated method stub
        if (fragmentCollectionGoods != null) {
            ftn.hide(fragmentCollectionGoods);
        }
        if (fragmentCollectMessage != null) {
            ftn.hide(fragmentCollectMessage);
        }
    }

    public void setEdit(int index) {
        switch (index) {
            case 0:
                isGoodEdit = false;
                break;
            case 1:
                isMessageEdit = false;
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
