package com.yst.onecity.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 多种类型的ViewHolder基类
 *
 * @author WangJingWei
 * @version 4.0.2
 * @date 2018/4/24.
 */
public abstract class AbstractTypeViewHolder<T> extends RecyclerView.ViewHolder {
    public AbstractTypeViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * 子类viewHolder重新该方法
     *
     * @param item
     */
    public abstract void bindHolder(T item);
}
