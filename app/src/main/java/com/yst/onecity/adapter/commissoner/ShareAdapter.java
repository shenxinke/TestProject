package com.yst.onecity.adapter.commissoner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.activity.commissioner.CommissionerHomePageActivity;
import com.yst.onecity.activity.commissioner.ShareDetailsActivity;
import com.yst.onecity.adapter.GridAdapter;
import com.yst.onecity.bean.commissioner.ShareBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.interfaces.BaseAdapterListener;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 分享的适配器
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/1
 */

public class ShareAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ShareBean.ContentBean> shareList = new ArrayList<>();
    private final static int NO_PIC = Const.INTEGER_0;
    private final static int ONE_PIC = Const.INTEGER_1;
    private final static int THREE_PIC = Const.INTEGER_2;
    private final static int VIDEO = Const.INTEGER_3;
    private BaseAdapterListener baseAdapterListener;
    private onClikListener onClikListener;
    private String memberId;

    public ShareAdapter(Context context) {
        this.context = context;
    }

    /**
     * 为集合添加数据
     *
     * @param mListList 集合
     */
    public void addData(List<ShareBean.ContentBean> mListList) {
        if (null != mListList) {
            shareList = mListList;
            notifyDataSetChanged();
        }
    }

    /**
     * 设置memberId
     *
     * @param memberId
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * 设置接口回调
     *
     * @param baseAdapterListener
     * @param onClikListener
     */
    public void setOnclick(BaseAdapterListener baseAdapterListener, onClikListener onClikListener) {
        this.baseAdapterListener = baseAdapterListener;
        this.onClikListener = onClikListener;
    }

    @Override
    public int getItemViewType(int position) {
        int type = shareList.get(position).getType();
        /*
         * 0咨询 1品秀  资讯类型 0无图 1单图 2三图
         */
        if (type == Const.INTEGER_0) {
            int modelType = shareList.get(position).getModelType();
            if (modelType == Const.INTEGER_0) {
                return NO_PIC;
            } else if (modelType == Const.INTEGER_1) {
                return ONE_PIC;
            } else if (modelType == Const.INTEGER_2) {
                return THREE_PIC;
            }
        } else if (type == Const.INTEGER_1) {
            return VIDEO;
        }
        return Const.INTEGER_100;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NO_PIC) {
            return new noPicHolder(LayoutInflater.from(context).inflate(R.layout.layout_commisoner_nopic, parent, false));
        } else if (viewType == THREE_PIC) {
            return new GvHolder(View.inflate(context, R.layout.item_share_grd, null));
        } else if (viewType == ONE_PIC) {
            return new MyHolder(View.inflate(context, R.layout.item_share, null));
        } else if (viewType == VIDEO) {
            return new MyHolder(View.inflate(context, R.layout.item_share, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
            myHolder.tvCommentNum.setText(String.valueOf(shareList.get(position).getComment_num()));
            myHolder.tvContent.setText(String.valueOf(shareList.get(position).getTitle()));
            myHolder.tvLikeNum.setText(String.valueOf(shareList.get(position).getFabulous_num()));
            myHolder.tvShareNum.setText(String.valueOf(shareList.get(position).getShare_num()));
            if (getItemViewType(position) == ONE_PIC) {
                myHolder.ivPlay.setVisibility(View.GONE);
                myHolder.ivShare.setVisibility(View.VISIBLE);
                Glide.with(context).load(ConstUtils.matchingImageUrl(shareList.get(position).getAddress())).error(R.mipmap.brand_default).into(myHolder.ivShare);
                myHolder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Utils.isClickable()) {
                            return;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("id", String.valueOf(shareList.get(position).getId()));
                        bundle.putString(Const.CONS_STR_HUNTERID, memberId);
                        bundle.putString(Const.CONS_STR_AUTHORID, memberId);
                        bundle.putString("type", "share");
                        Const.TAG = Const.INTEGER_6;
                        CommissionerHomePageActivity.isFirst = false;
                        JumpIntent.jump((Activity) context, ShareDetailsActivity.class, bundle);
                    }
                });
            } else if (getItemViewType(position) == VIDEO) {
                myHolder.ivPlay.setVisibility(View.VISIBLE);
                myHolder.ivShare.setVisibility(View.VISIBLE);
                Glide.with(context).load(ConstUtils.matchingImageUrl(shareList.get(position).getAddress())).error(R.mipmap.brand_default).into(myHolder.ivShare);
                myHolder.ivPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Utils.isClickable()) {
                            return;
                        }
                        if (TextUtils.isEmpty(shareList.get(position).getAddress())) {
                            ToastUtils.show("视频地址为空");
                            return;
                        }
                        baseAdapterListener.onItemClick(position);
                        onClikListener.onClick();
                    }
                });
            }
        } else if (holder instanceof GvHolder) {
            GvHolder myHolder = (GvHolder) holder;
            myHolder.tvCommentNum.setText(String.valueOf(shareList.get(position).getComment_num()));
            myHolder.tvContent.setText(String.valueOf(shareList.get(position).getTitle()));
            myHolder.tvLikeNum.setText(String.valueOf(shareList.get(position).getFabulous_num()));
            myHolder.tvShareNum.setText(String.valueOf(shareList.get(position).getShare_num()));
            String address = shareList.get(position).getAddress();
            if (!TextUtils.isEmpty(address) && address.contains(Const.CONS_STR_DOU)) {
                String[] split = address.split(Const.CONS_STR_DOU);
                String[] strings = new String[split.length];
                for (int i = 0; i < split.length; i++) {
                    strings[i] = ConstUtils.matchingImageUrl(split[i]);
                }
                GridAdapter gvAdapter = new GridAdapter(strings, context);
                myHolder.gridView.setAdapter(gvAdapter);
                String shareId = String.valueOf(shareList.get(position).getId());
                myHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (!Utils.isClickable()) {
                            return;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("id", shareId);
                        bundle.putString("memberId", memberId);
                        bundle.putString("authorId", memberId);
                        Const.TAG = Const.INTEGER_6;
                        CommissionerHomePageActivity.isFirst = false;
                        JumpIntent.jump((Activity) context, ShareDetailsActivity.class, bundle);
                    }
                });
            }
        } else if (holder instanceof noPicHolder) {
            noPicHolder myHolder = (noPicHolder) holder;
            myHolder.tvCommentNum.setText(String.valueOf(shareList.get(position).getComment_num()));
            myHolder.tvContent.setText(String.valueOf(shareList.get(position).getTitle()));
            myHolder.tvLikeNum.setText(String.valueOf(shareList.get(position).getFabulous_num()));
            myHolder.tvShareNum.setText(String.valueOf(shareList.get(position).getShare_num()));
            myHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Utils.isClickable()) {
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(shareList.get(position).getId()));
                    bundle.putString("memberId", memberId);
                    bundle.putString("authorId", memberId);
                    Const.TAG = Const.INTEGER_6;
                    CommissionerHomePageActivity.isFirst = false;
                    JumpIntent.jump((Activity) context, ShareDetailsActivity.class, bundle);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return shareList.size();
    }

    private static class MyHolder extends RecyclerView.ViewHolder {

        private final TextView tvContent;
        private final RoundedImageView ivShare;
        private final TextView tvShareNum;
        private final TextView tvCommentNum;
        private final TextView tvLikeNum;
        private final ImageView ivPlay;
        private final ConstraintLayout parent;

        private MyHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            ivShare = (RoundedImageView) itemView.findViewById(R.id.iv_share);
            tvShareNum = (TextView) itemView.findViewById(R.id.tv_share_num);
            tvCommentNum = (TextView) itemView.findViewById(R.id.tv_comment_num);
            tvLikeNum = (TextView) itemView.findViewById(R.id.tv_like_num);
            ivPlay = (ImageView) itemView.findViewById(R.id.iv_play);
            parent = (ConstraintLayout) itemView.findViewById(R.id.parent);
        }
    }

    private static class noPicHolder extends RecyclerView.ViewHolder {

        private final TextView tvContent;
        private final RoundedImageView ivShare;
        private final TextView tvShareNum;
        private final TextView tvCommentNum;
        private final TextView tvLikeNum;
        private final ImageView ivPlay;
        private final RelativeLayout parent;

        private noPicHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            ivShare = (RoundedImageView) itemView.findViewById(R.id.iv_share);
            tvShareNum = (TextView) itemView.findViewById(R.id.tv_share_num);
            tvCommentNum = (TextView) itemView.findViewById(R.id.tv_comment_num);
            tvLikeNum = (TextView) itemView.findViewById(R.id.tv_like_num);
            ivPlay = (ImageView) itemView.findViewById(R.id.iv_play);
            parent = (RelativeLayout) itemView.findViewById(R.id.parent);
        }
    }

    private static class GvHolder extends RecyclerView.ViewHolder {

        private final TextView tvContent;
        private final GridView gridView;
        private final TextView tvShareNum;
        private final TextView tvCommentNum;
        private final TextView tvLikeNum;
        private final ConstraintLayout parent;

        private GvHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            gridView = (GridView) itemView.findViewById(R.id.gridView);
            tvShareNum = (TextView) itemView.findViewById(R.id.tv_share_num);
            tvCommentNum = (TextView) itemView.findViewById(R.id.tv_comment_num);
            tvLikeNum = (TextView) itemView.findViewById(R.id.tv_like_num);
            parent = (ConstraintLayout) itemView.findViewById(R.id.parent);

        }
    }

    public interface onClikListener {
        /**
         * 条目监听
         */
        void onClick();
    }
}
