package com.yst.onecity.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.activity.ProductDetailActivity;
import com.yst.onecity.activity.servermember.StoreDetailActivity;
import com.yst.onecity.bean.order.OrderProduct;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.view.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单详情适配器
 *
 * @author chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class OrderDetailAdapter extends BaseExpandableListAdapter {
    private List<OrderProduct> groupList;
    private Activity mContext;
    private String merchantId;
    private LayoutInflater inflater;
    private int status;
    private String orderNo;
    private int scoreStatus;
    private String payType;

    public OrderDetailAdapter(List<OrderProduct> groupList, int status, String orderNo, Activity context, String merchantId, int scoreStatus, String payType, int i) {
        this.groupList = groupList;
        this.mContext = context;
        this.status = status;
        this.merchantId = merchantId;
        this.orderNo = orderNo;
        this.scoreStatus = scoreStatus;
        this.payType = payType;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupList.get(groupPosition).getpList().size();
    }

    @Override
    public OrderProduct getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public OrderProduct.ProductDetailBean getChild(int groupPosition, int childPosition) {
        return groupList.get(groupPosition).getpList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.order_detail_head, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        final OrderProduct groupBean = groupList.get(groupPosition);
        groupViewHolder.txtName.setText(TextUtils.isEmpty(groupBean.getMerchantName()) ? "暂无昵称" : groupBean.getMerchantName());
        Glide.with(mContext).load(groupBean.getMerchantImg()).error(R.mipmap.head_2).into(groupViewHolder.ivImage);
        groupViewHolder.llShopMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merchantId", String.valueOf(groupBean.getMechantId()));
                JumpIntent.jump(mContext, StoreDetailActivity.class, bundle);
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.order_info_item, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        final OrderProduct.ProductDetailBean detailBean = groupList.get(groupPosition).getpList().get(childPosition);
        final OrderProduct groupBean = groupList.get(groupPosition);
        childViewHolder.txtGoodsName.setText(ConstUtils.getStringNoEmpty(detailBean.getPame()));
        childViewHolder.txtGoodsMoney.setText("¥" + detailBean.getSalePrice());
        childViewHolder.txtGoodsNumber.setText("x" + detailBean.getNum());
        childViewHolder.txtGoodsColor.setText(ConstUtils.getStringNoEmpty(detailBean.getpContent()));
        childViewHolder.txtIntegral.setText(detailBean.getScorePrice());
        childViewHolder.tvWidthl.setText(detailBean.getWeight() + "kg");
        Glide.with(mContext).load(detailBean.getpImg())
                .placeholder(R.mipmap.img_default_p)
                .error(R.mipmap.img_default_p)
                .into(childViewHolder.imgGoods);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("spid", String.valueOf(detailBean.getPsId()));
                bundle.putString("merchantId", merchantId);
                bundle.putString("productId", detailBean.getpId());
                bundle.putString("hunterId", groupBean.gethId());
                JumpIntent.jump(mContext, ProductDetailActivity.class, bundle);
            }
        });
        //判断是否是代付款
        if (status == 0) {
            /**
             * 判断是否是新业态用户  判断是否是积分支付  判断后台积分按钮是否打开
             * 0 是新业态  1不是新业态
             * 0是开启  1是不开启
             */
            if (TianyiApplication.appCommonBean.getIsNewYeTai().equals(Const.STR1)) {
                childViewHolder.txtIntegral.setVisibility(View.GONE);
                childViewHolder.imJiFen.setVisibility(View.GONE);
            } else {
                if (scoreStatus == Const.INTEGER_0) {
                    childViewHolder.txtIntegral.setVisibility(View.VISIBLE);
                    childViewHolder.imJiFen.setVisibility(View.VISIBLE);
                } else {
                    childViewHolder.txtIntegral.setVisibility(View.GONE);
                    childViewHolder.imJiFen.setVisibility(View.GONE);
                }
            }
        } else {
            if (TianyiApplication.appCommonBean.getIsNewYeTai().equals(Const.STR1)) {
                childViewHolder.txtIntegral.setVisibility(View.GONE);
                childViewHolder.imJiFen.setVisibility(View.GONE);
            } else {
                if (Const.CONS_STR_JIFENZHIFU.equals(payType)) {
                    childViewHolder.txtIntegral.setVisibility(View.VISIBLE);
                    childViewHolder.imJiFen.setVisibility(View.VISIBLE);
                } else {
                    childViewHolder.txtIntegral.setVisibility(View.GONE);
                    childViewHolder.imJiFen.setVisibility(View.GONE);
                }
            }
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        @BindView(R.id.tv_name)
        TextView txtName;
        @BindView(R.id.iv_image)
        RoundedImageView ivImage;
        @BindView(R.id.ll_shop_merchant)
        LinearLayout llShopMerchant;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    class ChildViewHolder {

        @BindView(R.id.tv_goods_name)
        TextView txtGoodsName;
        @BindView(R.id.tv_goods_money)
        TextView txtGoodsMoney;
        @BindView(R.id.tv_goods_number)
        TextView txtGoodsNumber;
        @BindView(R.id.iv_goods)
        ImageView imgGoods;
        @BindView(R.id.tv_goods_color)
        TextView txtGoodsColor;
        @BindView(R.id.tv_integral)
        TextView txtIntegral;
        @BindView(R.id.tv_width)
        TextView tvWidthl;
        @BindView(R.id.im_jifen)
        ImageView imJiFen;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
