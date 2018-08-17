package com.yst.onecity.adapter.commissoner;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.yst.onecity.R;
import com.yst.onecity.adapter.GridAdapter;
import com.yst.onecity.bean.commissioner.PublishDataBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.databinding.ItemNoPicBinding;
import com.yst.onecity.databinding.ItemPublishBinding;
import com.yst.onecity.databinding.ItemPublishGvBinding;
import com.yst.onecity.databinding.LayoutPublishVideoBinding;
import com.yst.onecity.interfaces.BaseAdapterListener;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的分享的适配器
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/5
 */

public class PubLishAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<PublishDataBean> publishList = new ArrayList<>();
    private int type;
    private BaseAdapterListener baseAdapterListener;
    private OnItemClick onItemClick;
    private final static int NO_PIC = Const.INTEGER_0;
    private final static int ONE_PIC = Const.INTEGER_1;
    private final static int THREE_PIC = Const.INTEGER_2;
    private final static int VIDEO_PIC = Const.INTEGER_200;
    private OnPlayClick onPlayClick;

    public PubLishAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    /**
     * 为集合添加数据
     *
     * @param publishList 集合
     */
    public void addData(List<PublishDataBean> publishList) {
        if (null != publishList) {
            this.publishList = publishList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!publishList.isEmpty()) {
            int s = publishList.get(position).modelType.get();
            String address = publishList.get(position).address.get();
            MyLog.e("sss", "--modelType: " + s);
            switch (s) {
                case Const.INTEGER_0:
                    if (TextUtils.isEmpty(address)) {
                    }
                    return NO_PIC;
                case Const.INTEGER_1:
                    if (TextUtils.isEmpty(address)) {
                        return NO_PIC;
                    } else {
                        return ONE_PIC;
                    }
                case Const.INTEGER_2:
                    if (TextUtils.isEmpty(address)) {
                        return NO_PIC;
                    } else {
                        return THREE_PIC;
                    }
                case Const.INTEGER_9:
                    if (TextUtils.isEmpty(address)) {
                        return NO_PIC;
                    } else {
                        return VIDEO_PIC;
                    }
                default:
                    return Const.INTEGER_100;
            }
        }
        return Const.INTEGER_10000;
    }

    /**
     * 设置监听
     *
     * @param baseAdapterListener
     * @param onItemClick
     */
    public void setOnclick(BaseAdapterListener baseAdapterListener, OnItemClick onItemClick) {
        this.baseAdapterListener = baseAdapterListener;
        this.onItemClick = onItemClick;
    }

    public void setOnPlayClick(OnPlayClick onPlayClick) {
        this.onPlayClick = onPlayClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        MyLog.e("sss", "---type;" + viewType);
        if (viewType == ONE_PIC) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_publish, parent, false);
            return new PublishHolder(itemView);
        } else if (viewType == THREE_PIC) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_publish_gv, parent, false);
            return new GvHolder(itemView);
        } else if (viewType == NO_PIC) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_no_pic, parent, false);
            return new noPicHolder(itemView);
        } else if (viewType == VIDEO_PIC) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.layout_publish_video, parent, false);
            return new videoHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PublishHolder) {
            ((PublishHolder) holder).binding.setPublish(publishList.get(position));
            if (type == Const.INTEGER_0) {
                ((PublishHolder) holder).binding.tvLookNum.setVisibility(View.GONE);
            }
            ((PublishHolder) holder).binding.getRoot().findViewById(R.id.iv_share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyLog.e("sss", "--adapter2: " + position);
                    if (!Utils.isClickable()) {
                        return;
                    }
                    baseAdapterListener.onItemClick(position);
                    onItemClick.onClick();
                }
            });
        } else if (holder instanceof GvHolder) {
            ((GvHolder) holder).binding.setPublish(publishList.get(position));
            if (type == Const.INTEGER_0) {
                ((GvHolder) holder).binding.tvLookNum.setVisibility(View.GONE);
            }
            String s = publishList.get(position).address.get();
            MyLog.e("sss", "--sss-" + s);
            if (!TextUtils.isEmpty(s)) {
                if (s.contains(Const.CONS_STR_DOU)) {
                    String[] split = s.split(Const.CONS_STR_DOU);
                    String[] strings = new String[split.length];
                    for (int i = 0; i < split.length; i++) {
                        String s1 = ConstUtils.matchingImageUrl(split[i]);
                        strings[i] = s1;
                    }
                    int po = position;
                    ((GvHolder) holder).binding.gridView.setAdapter(new GridAdapter(strings, context));
                    ((GvHolder) holder).binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (!Utils.isClickable()) {
                                return;
                            }
                            baseAdapterListener.onItemClick(po);
                            onItemClick.onClick();
                        }
                    });
                }
            }
        } else if (holder instanceof noPicHolder) {
            ((noPicHolder) holder).binding.setPublish(publishList.get(position));
            if (type == Const.INTEGER_0) {
                ((noPicHolder) holder).binding.tvLookNum.setVisibility(View.GONE);
            }
            ((noPicHolder) holder).binding.getRoot().findViewById(R.id.tv_content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Utils.isClickable()) {
                        return;
                    }
                    baseAdapterListener.onItemClick(position);
                    onItemClick.onClick();
                }
            });
        } else if (holder instanceof videoHolder) {
            ((videoHolder) holder).binding.setPublish(publishList.get(position));
            if (type == Const.INTEGER_0) {
                ((videoHolder) holder).binding.tvLookNum.setVisibility(View.GONE);
            }
            ((videoHolder) holder).binding.getRoot().findViewById(R.id.iv_play).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Utils.isClickable()) {
                        return;
                    }
                    if (TextUtils.isEmpty(publishList.get(position).videoAddress.get())) {
                        ToastUtils.show("视频地址为空");
                        return;
                    }
                    baseAdapterListener.onItemClick(position);
                    onPlayClick.onPlay();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        MyLog.e("sss", "---list: " + publishList.size());
        return publishList.size();
    }

    public static class PublishHolder extends RecyclerView.ViewHolder {

        private final ItemPublishBinding binding;

        public PublishHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(@NonNull PublishDataBean user) {
            binding.setPublish(user);
        }
    }

    public static class GvHolder extends RecyclerView.ViewHolder {

        private final ItemPublishGvBinding binding;

        public GvHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(@NonNull PublishDataBean user) {
            binding.setPublish(user);
        }
    }

    public static class noPicHolder extends RecyclerView.ViewHolder {

        private final ItemNoPicBinding binding;

        public noPicHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(@NonNull PublishDataBean user) {
            binding.setPublish(user);
        }
    }

    public static class videoHolder extends RecyclerView.ViewHolder {

        private final LayoutPublishVideoBinding binding;

        public videoHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(@NonNull PublishDataBean user) {
            binding.setPublish(user);
        }
    }

    public interface OnItemClick {
        /**
         * 条目监听事件
         */
        void onClick();
    }

    public interface OnPlayClick {
        /**
         * 播放事件
         */
        void onPlay();
    }

    public class MyHandler {

        /**
         * 播放的监听
         *
         * @param view
         */
        public void onViewPlay(View view) {
            onItemClick.onClick();
        }

        /**
         * 草稿箱图片的监听
         *
         * @param view
         */
        public void onViewDraft(View view) {
            onItemClick.onClick();
        }
    }
}
