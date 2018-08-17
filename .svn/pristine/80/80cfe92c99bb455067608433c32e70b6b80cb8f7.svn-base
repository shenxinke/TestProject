package com.yst.onecity.adapter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 公用RecyclerView适配器
 *
 * @author WangJingWei
 * @version 4.0.0
 * @date 2017/12/13.
 */
public abstract class AbstractCommonRcyAdapter<T> extends RecyclerView.Adapter<CommonRcyHolder>{
    protected LayoutInflater mInflater;
    protected int mItemLayoutId;
    protected List<T> mList;
    protected Context context;
    protected  int mCurrentIndex;

    public AbstractCommonRcyAdapter(Context context, List<T> list, int itemLayoutId) {
        this.mItemLayoutId = itemLayoutId;
        this.context = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
    }

    public void onRefresh(List<T> mList) {
        if (mList != null) {
            this.mList.clear();
            this.mList.addAll(mList);
            notifyDataSetChanged();
        }
    }

    public void onLoadMore(List<T> mList) {
        if (mList != null && mList.size() != 0) {
            this.mList.addAll(mList);
            notifyDataSetChanged();
        }
    }

    public void setData(List<T> mList) {
        if (mList != null && mList.size() != 0) {
            this.mList = mList;
            notifyDataSetChanged();
        }
    }

    public void setRrefresh(int index) {
        this.mCurrentIndex = index;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public CommonRcyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mConverView = LayoutInflater.from(context).inflate(mItemLayoutId, parent, false);
        return CommonRcyHolder.getRecyclerHolder(context, mConverView);
    }

    @Override
    public void onBindViewHolder(CommonRcyHolder holder, int position) {
        this.convert(holder, position, mList.get(position));
    }

    /**
     * 设置数据
     * @param holder holder对象
     * @param position 下标
     * @param item 实体对象
     */
    public abstract void convert(CommonRcyHolder holder, int position, T item);
}
