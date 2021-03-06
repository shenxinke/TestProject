package com.yst.onecity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.viewholder.AbstractTypeViewHolder;
import com.yst.onecity.bean.ShoppingLocalityBean;
import com.yst.onecity.interfaces.BaseAdapterListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地直供适配器
 *
 * @author Shenxinke
 * @version 4.2.0
 * @data 2018/6/13
 */

public class ShoppingLocalityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<ShoppingLocalityBean.ContentBean> mData = new ArrayList<>();
    private int mtype;

    private  ShoppingLocalityListener shoppingLocalityListener;

    public void ShoppingLocalityAdapter( ShoppingLocalityListener shoppingLocalityListener) {
        this.shoppingLocalityListener = shoppingLocalityListener;
    }

    public ShoppingLocalityAdapter() {
        mLayoutInflater = LayoutInflater.from(TianyiApplication.getContext());
    }

    public void addList(List<ShoppingLocalityBean.ContentBean> data, int type) {
        if (data != null) {
            mData = data;
            mtype = type;
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ShoppingLocalityHolder(mLayoutInflater.inflate(R.layout.this_locality_item, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AbstractTypeViewHolder) holder).bindHolder(mData.get(position));
        setAdapterListener(position, holder);
    }

    public void setAdapterListener(final int position, final RecyclerView.ViewHolder holder) {
        if (holder instanceof ShoppingLocalityHolder) {
            //按钮点击事件
            if (shoppingLocalityListener != null) {
                ((ShoppingLocalityHolder) holder).imgShoppingCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String state = String.valueOf(((ShoppingLocalityHolder) holder).id);
                        shoppingLocalityListener.btShoppingCart(position, state);
                    }
                });

                //故事点击事件
                ((ShoppingLocalityHolder) holder).tvStory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String state = String.valueOf(((ShoppingLocalityHolder) holder).id);
                        shoppingLocalityListener.btShoppingStory(position,state);
                    }
                });
            }
            //条目点击事件
            ((ShoppingLocalityHolder) holder).clParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shoppingLocalityListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface ShoppingLocalityListener extends BaseAdapterListener {
        /**
         * 多种状态的按钮点击监听回调
         *
         * @param position
         * @param status
         */
        void btShoppingCart(int position, String status);

        /**
         * 产品故事监听
         * @param position
         * @param status
         */
        void btShoppingStory(int position, String status);
    }
}