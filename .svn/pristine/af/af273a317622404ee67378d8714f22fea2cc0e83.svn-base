package com.yst.onecity.adapter.commissoner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.commissioner.CustomerBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 专员页面的客户列表适配器
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/1
 */

public class CustomerAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<CustomerBean.ContentBean> customerList = new ArrayList<>();

    public CustomerAdapter(Context context) {
        this.context = context;
    }

    /**
     * 为集合添加数据
     *
     * @param mList 有数据的集合
     */
    public void addData(List<CustomerBean.ContentBean> mList) {
        if (null != mList) {
            customerList = mList;
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        Glide.with(context).load(ConstUtils.matchingImageUrl(customerList.get(position).getHeadImg())).error(R.mipmap.head_2).into(myHolder.ivHead);
        myHolder.tvContent.setText(customerList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    private static class MyHolder extends RecyclerView.ViewHolder {

        private final ImageView ivHead;
        private final TextView tvContent;

        private MyHolder(View itemView) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);

        }
    }
}
