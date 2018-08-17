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
import com.yst.onecity.bean.commissioner.CommentBean;
import com.yst.onecity.utils.ConstUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 专员页面的评论列表适配器
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/1
 */

public class CommentAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<CommentBean.ContentBean> commentList = new ArrayList<>();

    public CommentAdapter(Context context) {
        this.context = context;
    }

    /**
     * 为集合添加数据
     *
     * @param mList 有数据的集合
     */
    public void addData(List<CommentBean.ContentBean> mList) {
        if (null != mList) {
            commentList = mList;
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coment, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.tvName.setText(commentList.get(position).getName());
        myHolder.tvContent.setText(commentList.get(position).getContent());
        myHolder.tvDate.setText(commentList.get(position).getCreated_time());
        Glide.with(context).load(ConstUtils.matchingImageUrl(String.valueOf(commentList.get(position).getAddress()))).error(R.mipmap.head_2).into(myHolder.ivHead);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    private static class MyHolder extends RecyclerView.ViewHolder {

        private final ImageView ivHead;
        private final TextView tvName;
        private final TextView tvDate;
        private final TextView tvContent;

        private MyHolder(View itemView) {
            super(itemView);
            ivHead = (ImageView) itemView.findViewById(R.id.iv_head);
            tvName = (TextView) itemView.findViewById(R.id.tv_nick_name);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvContent = (TextView) itemView.findViewById(R.id.tv_comment_content);

        }
    }
}
