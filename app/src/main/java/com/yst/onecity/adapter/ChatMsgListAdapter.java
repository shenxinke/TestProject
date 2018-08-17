package com.yst.onecity.adapter;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yst.onecity.R;
import com.yst.onecity.bean.liveroom.ChatEntityBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.MySelfInfo;
import com.yst.onecity.view.CustomTextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 消息列表适配器
 *
 * @author Shenxinke
 * @version 4.1.0
 * @data 2018/5/21
 */
public class ChatMsgListAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

    private static final int ITEMCOUNT = 7;
    private List<ChatEntityBean> listMessage = null;
    private Context context;
    private ListView mListView;
    private ArrayList<ChatEntityBean> myArray = new ArrayList<ChatEntityBean>();

    class AnimatorInfo {
        long createTime;

        public AnimatorInfo(long uTime) {
            createTime = uTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }

    private static final int MAXANIMATORCOUNT = 8;
    private static final int ANIMATORDURING = 8000;
    private static final int MAXITEMCOUNT = 50;
    private LinkedList<AnimatorSet> mAnimatorSetList;
    private LinkedList<AnimatorInfo> mAnimatorInfoList;
    private boolean mScrolling = false;
    private boolean mCreateAnimator = false;

    public ChatMsgListAdapter(Context context, ListView listview, List<ChatEntityBean> objects) {
        this.context = context;
        mListView = listview;
        this.listMessage = objects;

        mAnimatorSetList = new LinkedList<AnimatorSet>();
        mAnimatorInfoList = new LinkedList<AnimatorInfo>();

        mListView.setOnScrollListener(this);
    }


    @Override
    public int getCount() {
        return listMessage.size();
    }

    @Override
    public Object getItem(int position) {
        return listMessage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 通过名称计算颜色
     */
    private int calcNameColor(String strName) {
        if (strName == null) {
            return 0;
        }
        byte idx = 0;
        byte[] byteArr = strName.getBytes();
        for (int i = 0; i < byteArr.length; i++) {
            idx ^= byteArr[i];
        }

        switch (idx & 0x7) {
            case 1:
                return context.getResources().getColor(R.color.color_FFFFE891);
            case 2:
                return context.getResources().getColor(R.color.color_FFFFE891);
            default:
                return context.getResources().getColor(R.color.color_FFFFE891);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        SpannableString spanString;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_chatmsg, null);
            holder.textItem = (LinearLayout) convertView.findViewById(R.id.text_item);
            holder.sendContext = (CustomTextView) convertView.findViewById(R.id.sendcontext);
            convertView.setTag(R.id.tag_first, holder);
        } else {
            holder = (ViewHolder) convertView.getTag(R.id.tag_first);
        }

        ChatEntityBean item = listMessage.get(position);

        if (mCreateAnimator && MySelfInfo.getInstance().isbLiveAnimator()) {
            playViewAnimator(convertView, position, item);
        }

        if (item.getType() != Const.INTEGER_1) {
            spanString = new SpannableString(item.getSenderName() + "  " + item.getContext());
            StyleSpan boldStyle = new StyleSpan(Typeface.NORMAL);
            spanString.setSpan(boldStyle, 0, item.getSenderName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.sendContext.setTextColor(context.getResources().getColor(R.color.color_FFFFE891));
        } else {
            String name = item.getSenderName() + " :";
            spanString = new SpannableString(name + "  " + item.getContext());
            spanString.setSpan(new ForegroundColorSpan(calcNameColor(name)),
                    0, item.getSenderName().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            holder.sendContext.setTextColor(context.getResources().getColor(R.color.white));
        }
        holder.sendContext.setText(spanString);
        // 设置控件实际宽度以便计算列表项实际高度
        holder.sendContext.fixViewWidth(mListView.getWidth());
        return convertView;
    }


    static class ViewHolder {
        public LinearLayout textItem;
        public CustomTextView sendContext;

    }

    /**
     * 停止View属性动画
     *
     * @param itemView
     */
    private void stopViewAnimator(View itemView) {
        AnimatorSet aniSet = (AnimatorSet) itemView.getTag(R.id.tag_second);
        if (null != aniSet) {
            aniSet.cancel();
            mAnimatorSetList.remove(aniSet);
        }
    }

    /**
     * 播放View属性动画
     *
     * @param itemView   动画对应View
     * @param startAlpha 初始透明度
     * @param duringTime 动画时长
     */
    private void playViewAnimator(View itemView, float startAlpha, long duringTime) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(itemView, "alpha", startAlpha, 0f);
        AnimatorSet aniSet = new AnimatorSet();
        aniSet.setDuration(duringTime);
        aniSet.play(animator);
        aniSet.start();
        mAnimatorSetList.add(aniSet);
        itemView.setTag(R.id.tag_second, aniSet);
    }


    /**
     * 继续播放渐消动画
     *
     * @param itemView
     * @param position
     * @param item
     */
    private void continueAnimator(View itemView, int position, final ChatEntityBean item) {
        int animatorIdx = listMessage.size() - 1 - position;

        if (animatorIdx < MAXANIMATORCOUNT) {
            float startAlpha = 1f;
            long during = ANIMATORDURING;

            stopViewAnimator(itemView);

            // 播放动画
            if (position < mAnimatorInfoList.size()) {
                AnimatorInfo info = mAnimatorInfoList.get(position);
                //  获取列表项加载的初始时间
                long time = info.getCreateTime();
                // 计算动画剩余时长
                during = during - (System.currentTimeMillis() - time);
                // 计算动画初始透明度
                startAlpha = 1f * during / ANIMATORDURING;
                // 剩余时长小于0直接设置透明度为0并返回
                if (during < 0) {
                    itemView.setAlpha(0f);
                    return;
                }
            }

            // 创建属性动画并播放
            playViewAnimator(itemView, startAlpha, during);
        }
    }

    /**
     * 播放消失动画
     */
    private void playDisappearAnimator() {
        for (int i = 0; i < mListView.getChildCount(); i++) {
            View itemView = mListView.getChildAt(i);
            if (null == itemView) {
                break;
            }

            // 更新动画创建时间
            int position = mListView.getFirstVisiblePosition() + i;
            if (position < mAnimatorInfoList.size()) {
                mAnimatorInfoList.get(position).setCreateTime(System.currentTimeMillis());
            }

            playViewAnimator(itemView, 1f, ANIMATORDURING);
        }
    }

    /**
     * 播放列表项动画
     *
     * @param itemView 要播放动画的列表项
     * @param position 列表项的位置
     * @param item     列表数据
     */
    private void playViewAnimator(View itemView, int position, final ChatEntityBean item) {
        // 首次加载的列表项动画
        if (!myArray.contains(item)) {
            myArray.add(item);
            mAnimatorInfoList.add(new AnimatorInfo(System.currentTimeMillis()));
        }
        // 滚动时不播放动画，设置透明度为1
        if (mScrolling) {
            itemView.setAlpha(1f);
        } else {
            continueAnimator(itemView, position, item);
        }
    }

    /**
     * 删除超过上限(MAXITEMCOUNT)的列表项
     */
    private void clearFinishItem() {
        // 删除超过的列表项
        while (listMessage.size() > MAXITEMCOUNT) {
            listMessage.remove(0);
            if (mAnimatorInfoList.size() > 0) {
                mAnimatorInfoList.remove(0);
            }
        }

        // 缓存列表延迟删除
        while (myArray.size() > (MAXITEMCOUNT << 1)) {
            myArray.remove(0);
        }

        while (mAnimatorInfoList.size() >= listMessage.size()) {
            if (mAnimatorInfoList.size() > 0) {
                mAnimatorInfoList.remove(0);
            } else {
                break;
            }
        }
    }

    /**
     * 重新计算ITEMCOUNT条记录的高度，并动态调整ListView的高度
     */
    private void redrawListViewHeight() {
        int totalHeight = 0;
        int start = 0, lineCount = 0;

        if (listMessage.size() <= 0) {
            return;
        }

        /**
         * 计算底部ITEMCOUNT条记录的高度
         * 计算高度时不播放属性动画
         */
        mCreateAnimator = false;
        for (int i = listMessage.size() - 1; i >= start && lineCount < ITEMCOUNT; i--, lineCount++) {
            View listItem = getView(i, null, mListView);
            listItem.measure(0, 0);
            // add item height
            totalHeight = totalHeight + listItem.getMeasuredHeight();
        }
        mCreateAnimator = true;

        // 调整ListView高度
        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = totalHeight + (mListView.getDividerHeight() * (lineCount - 1));
        mListView.setLayoutParams(params);
    }

    /**
     * 停止当前所有属性动画并重置透明度
     */
    private void stopAnimator() {
        // 停止动画
        for (AnimatorSet anSet : mAnimatorSetList) {
            anSet.cancel();
        }
        mAnimatorSetList.clear();
    }

    /**
     * 重置透明度
     */
    private void resetAlpha() {
        for (int i = 0; i < mListView.getChildCount(); i++) {
            View view = mListView.getChildAt(i);
            view.setAlpha(1f);
        }
    }

    /**
     * 继续可视范围内所有动画
     */
    private void continueAllAnimator() {
        int startPos = mListView.getFirstVisiblePosition();

        for (int i = 0; i < mListView.getChildCount(); i++) {
            View view = mListView.getChildAt(i);
            if (null != view && startPos + i < listMessage.size()) {
                continueAnimator(view, startPos + i, listMessage.get(startPos + i));
            }
        }
    }

    /**
     * 重载notifyDataSetChanged方法实现渐消动画并动态调整ListView高度
     */
    @Override
    public void notifyDataSetChanged() {
        if (mScrolling) {
            // 滑动过程中不刷新
            super.notifyDataSetChanged();
            return;
        }

        // 删除多余项
        clearFinishItem();

        if (MySelfInfo.getInstance().isbLiveAnimator()) {
            // 停止之前动画
            stopAnimator();

            // 清除动画
            mAnimatorSetList.clear();
        }

        super.notifyDataSetChanged();

        // 重置ListView高度
//        redrawListViewHeight();

        if (MySelfInfo.getInstance().isbLiveAnimator() && listMessage.size() >= MAXITEMCOUNT) {
            continueAllAnimator();
        }

        // 自动滚动到底部
        mListView.post(new Runnable() {
            @Override
            public void run() {
                mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                mListView.setStackFromBottom(true);
//                mListView.setSelection(mListView.getCount() - 1);
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_FLING:
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                if (MySelfInfo.getInstance().isbLiveAnimator()) {
                    // 开始滚动时停止所有属性动画
                    stopAnimator();
                    resetAlpha();
                }
                mScrolling = true;
                break;
            case SCROLL_STATE_IDLE:
                mScrolling = false;
                if (MySelfInfo.getInstance().isbLiveAnimator()) {
                    // 停止滚动时播放渐消动画
                    playDisappearAnimator();
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
