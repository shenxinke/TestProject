package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.bean.ShoppingMallBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.GlideCircleTransform;
import com.yst.onecity.view.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品搜索适配器
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/6/14
 */

public class ProductSearchAdapter extends BaseAdapter {
    private Context mContext;
    private List<ShoppingMallBean.ContentBean> list;

    public ProductSearchAdapter(Context context, List<ShoppingMallBean.ContentBean> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_product_choose, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mItemCb.setVisibility(View.GONE);
        holder.mItemTxtBuy.setText(String.valueOf(list.get(position).getSaleNum()+"人购买"));
        holder.mItemTxtComment.setText(String.valueOf(list.get(position).getCommentNum()+"人评价"));
        holder.mItemTxtWatch.setText(String.valueOf(list.get(position).getViews()+"人浏览"));
        holder.mItemTxtPrice.setText(list.get(position).getSalePrice());
        holder.mItemTxtTitle.setText(list.get(position).getName());
        holder.mItemTxtZan.setText(String.valueOf(list.get(position).getFabulousNum()));
        holder.mItemTxtZhuanfa.setText(String.valueOf(list.get(position).getShareNum()));
        Glide.with(mContext).load(list.get(position).getImageurl()).placeholder(R.mipmap.img_default_bg).into(holder.mItemIvBg);
        if (Const.STR1.equals(list.get(position).getType())) {
            holder.mItemIvTips.setImageResource(R.mipmap.tips);
        } else {
            holder.mItemIvTips.setImageResource(R.mipmap.zhuanyuan_label);
        }

        List<ShoppingMallBean.ContentBean.MemberImgBean> memberImg = list.get(position).getMemberImg();
        holder.mItemTxtGushi.setText(String.valueOf(memberImg.size()+"个产品故事..."));
        if (memberImg.size() == Const.INTEGER_0) {
            holder.mItemIvPic1.setVisibility(View.GONE);
            holder.mItemIvPic2.setVisibility(View.GONE);
            holder.mItemIvPic3.setVisibility(View.GONE);
        } else if (memberImg.size() == Const.INTEGER_1) {
            holder.mItemIvPic1.setVisibility(View.GONE);
            holder.mItemIvPic2.setVisibility(View.GONE);
            holder.mItemIvPic3.setVisibility(View.VISIBLE);
        } else if (memberImg.size() == Const.INTEGER_2) {
            holder.mItemIvPic1.setVisibility(View.GONE);
            holder.mItemIvPic2.setVisibility(View.VISIBLE);
            holder.mItemIvPic3.setVisibility(View.VISIBLE);
        } else if (memberImg.size() == Const.INTEGER_3) {
            holder.mItemIvPic1.setVisibility(View.VISIBLE);
            holder.mItemIvPic2.setVisibility(View.VISIBLE);
            holder.mItemIvPic3.setVisibility(View.VISIBLE);
        }

        switch (memberImg.size()) {
            case 1:
                if (memberImg.get(0).getUserType() == 0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(memberImg.get(0).getHeadImg())))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .placeholder(R.mipmap.head_2)
                            .into(holder.mItemIvPic3);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.drawable.icon_logo)))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.drawable.icon_logo)
                            .into(holder.mItemIvPic3);
                }
                break;
            case 2:
                if (memberImg.get(0).getUserType() == 0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(memberImg.get(0).getHeadImg())))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.head_2)
                            .into(holder.mItemIvPic3);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.drawable.icon_logo)))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.drawable.icon_logo)
                            .into(holder.mItemIvPic3);
                }
                if (memberImg.get(1).getUserType() == 0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(memberImg.get(1).getHeadImg())))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.head_2)
                            .into(holder.mItemIvPic2);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.drawable.icon_logo)))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.drawable.icon_logo)
                            .into(holder.mItemIvPic2);
                }
                break;
            case 3:
                if (memberImg.get(0).getUserType() == 0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(memberImg.get(0).getHeadImg())))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.head_2)
                            .into(holder.mItemIvPic3);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.drawable.icon_logo)))
                            .asBitmap()
                            .error(R.drawable.icon_logo)
                            .into(holder.mItemIvPic3);
                }
                if (memberImg.get(1).getUserType() == 0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(memberImg.get(1).getHeadImg())))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.head_2)
                            .into(holder.mItemIvPic2);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.drawable.icon_logo)))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.drawable.icon_logo)
                            .into(holder.mItemIvPic2);
                }
                if (memberImg.get(Const.INTEGER_2).getUserType() == 0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(memberImg.get(2).getHeadImg())))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.head_2)
                            .into(holder.mItemIvPic1);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.drawable.icon_logo)))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.drawable.icon_logo)
                            .into(holder.mItemIvPic1);
                }
                break;
            default:
                break;
        }
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.item_iv_bg)
        ImageView mItemIvBg;
        @BindView(R.id.item_iv_tips)
        ImageView mItemIvTips;
        @BindView(R.id.item_cb)
        CheckBox mItemCb;
        @BindView(R.id.item_txt_title)
        TextView mItemTxtTitle;
        @BindView(R.id.item_txt_rmb)
        TextView mItemTxtRmb;
        @BindView(R.id.item_txt_price)
        TextView mItemTxtPrice;
        @BindView(R.id.item_txt_buy)
        TextView mItemTxtBuy;
        @BindView(R.id.item_txt_comment)
        TextView mItemTxtComment;
        @BindView(R.id.item_txt_watch)
        TextView mItemTxtWatch;
        @BindView(R.id.item_txt_shop)
        TextView mItemTxtShop;
        @BindView(R.id.item_line)
        View mItemLine;
        @BindView(R.id.item_iv_pic1)
        RoundedImageView mItemIvPic1;
        @BindView(R.id.item_iv_pic2)
        RoundedImageView mItemIvPic2;
        @BindView(R.id.item_iv_pic3)
        RoundedImageView mItemIvPic3;
        @BindView(R.id.item_txt_gushi)
        TextView mItemTxtGushi;
        @BindView(R.id.item_txt_zan)
        TextView mItemTxtZan;
        @BindView(R.id.item_txt_zhuanfa)
        TextView mItemTxtZhuanfa;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
