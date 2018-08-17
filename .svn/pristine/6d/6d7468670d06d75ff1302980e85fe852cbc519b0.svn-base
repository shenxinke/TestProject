package com.yst.onecity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.viewholder.HomeLiveViewHolder;
import com.yst.onecity.bean.livevideo.LiveVideoBean;
import com.yst.onecity.interfaces.BaseAdapterListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页直播列表适配器
 *
 * @author WangJingWei
 * @version 4.1.0
 * @date 2018/5/14.
 */
public class HomeLiveListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LiveVideoBean.ContentBean> mData = new ArrayList<>();
    private LayoutInflater mLayoutInflater;

    public HomeLiveListAdapter() {
        mLayoutInflater = LayoutInflater.from(TianyiApplication.getContext());
    }

    public void addList(List<LiveVideoBean.ContentBean> data) {
        if (data != null) {
            this.mData = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeLiveViewHolder(mLayoutInflater.inflate(R.layout.item_home_livevideo,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((HomeLiveViewHolder) holder).bindHolder(mData.get(position));
        setAdapterListener(position, holder);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setAdapterListener(final int position, final RecyclerView.ViewHolder holder) {
        if (holder instanceof HomeLiveViewHolder) {
            ((HomeLiveViewHolder) holder).viewRoot.setOnClickListener(v -> {
                if(baseAdapterListener != null){
                    baseAdapterListener.onItemClick(position);
                }
            });
        }
    }

    public BaseAdapterListener baseAdapterListener;
    public void setBaseAdapterListener(BaseAdapterListener baseAdapterListener){
        this.baseAdapterListener = baseAdapterListener;
    }
}
