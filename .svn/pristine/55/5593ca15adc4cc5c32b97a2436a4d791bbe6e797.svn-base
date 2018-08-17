package com.yst.onecity.adapter;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.yst.onecity.activity.ProductDetailActivity;
import com.yst.onecity.bean.consult.ConsultDetailBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.GlideCircleTransform;
import com.yst.onecity.view.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发布适配器
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/0/19
 */
public class PublishAdapter extends BaseAdapter {

    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_PIC = 1;
    private static final int TYPE_PRODUCT = 2;

    private List<ConsultDetailBean.ContentBean.ConsultationBean.InfoBean> datas;
    private Activity context;
    private String hunterId;
    private int mReleaseUserType;

    public PublishAdapter(List<ConsultDetailBean.ContentBean.ConsultationBean.InfoBean> datas, Activity context, String hunterId, int userType) {
        this.datas = datas;
        this.context = context;
        this.hunterId = hunterId;
        this.mReleaseUserType = userType;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        switch (datas.get(position).getType()) {
            case TYPE_CONTENT:
                return TYPE_CONTENT;
            case TYPE_PIC:
                return TYPE_PIC;
            case TYPE_PRODUCT:
                return TYPE_PRODUCT;
            default:
                break;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = null;
        switch (getItemViewType(position)) {
            case TYPE_CONTENT:
                ContentHolder contentHolder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_publish_content, parent, false);
                    contentHolder = new ContentHolder(convertView);
                    convertView.setTag(contentHolder);
                } else {
                    contentHolder = (ContentHolder) convertView.getTag();
                }

                contentHolder.content.setText(datas.get(position).getContent());
                break;
            case TYPE_PIC:
                PicHolder picHolder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_publish_image, parent, false);
                    picHolder = new PicHolder(convertView);
                    convertView.setTag(picHolder);
                } else {
                    picHolder = (PicHolder) convertView.getTag();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (!context.isDestroyed()) {
                        Glide.with(context).load(ConstUtils.matchingImageUrl(datas.get(position).getAddress())).placeholder(R.mipmap.img_default_bg).error(R.mipmap.img_default_bg).into(picHolder.imageView);
                    } else {
                        MyLog.e("Destroyed", "Picture loading failed,activity is Destroyed");
                    }
                }
                break;
            case TYPE_PRODUCT:
                ProductHolder productHolder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_product_choose, null);
                    productHolder = new ProductHolder(convertView);
                    convertView.setTag(productHolder);
                } else {
                    productHolder = (ProductHolder) convertView.getTag();
                }
                productHolder.mItemCb.setVisibility(View.GONE);
                Glide.with(context).load(ConstUtils.matchingImageUrl(datas.get(position).getPimage())).error(R.mipmap.img_default_bg).into(productHolder.mItemIvBg);
                productHolder.mItemTxtTitle.setText(datas.get(position).getPname());
                productHolder.mItemTxtPrice.setText(datas.get(position).getSalePrice());
                productHolder.mItemTxtBuy.setText(TextUtils.isEmpty(datas.get(position).getSaleNum()) ? "0人购买" : datas.get(position).getSaleNum()+"人购买");
                productHolder.mItemTxtComment.setText(String.valueOf(datas.get(position).getCommentNum()+"人评价"));
                productHolder.mItemTxtWatch.setText(String.valueOf(datas.get(position).getViews()+"人浏览"));
                productHolder.mItemTxtZan.setText(datas.get(position).getFabulousNum());
                productHolder.mItemTxtZhuanfa.setText(datas.get(position).getShareNum());
                if (Const.STR1.equals(datas.get(position).getProductType())) {
                    productHolder.mItemIvTips.setImageResource(R.mipmap.tips);
                    productHolder.mItemTxtShop.setVisibility(View.VISIBLE);
                    productHolder.mItemTxtShop.setText(String.valueOf("由"+datas.get(position).getShopName()+"提供"));
                } else {
                    productHolder.mItemIvTips.setImageResource(R.mipmap.zhuanyuan_label);
                    productHolder.mItemTxtShop.setVisibility(View.GONE);
                }
                List<ConsultDetailBean.ContentBean.ConsultationBean.ConsultationPublisherListBean> list = datas.get(position).getConsultationPublisherList();
                productHolder.mItemTxtGushi.setText(String.valueOf(datas.get(position).getProductStoryCount()+"个产品故事..."));
                switch (list.size()) {
                    case 0:
                        productHolder.mItemIvPic1.setVisibility(View.GONE);
                        productHolder.mItemIvPic2.setVisibility(View.GONE);
                        productHolder.mItemIvPic3.setVisibility(View.GONE);
                        break;
                    case 1:
                        productHolder.mItemIvPic1.setVisibility(View.GONE);
                        productHolder.mItemIvPic2.setVisibility(View.GONE);
                        productHolder.mItemIvPic3.setVisibility(View.VISIBLE);
                        if (list.get(0).getUserType() == 0) {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(list.get(0).getHeadImg()))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.mipmap.head_2)
                                    .into(productHolder.mItemIvPic3);
                        } else {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(String.valueOf(R.drawable.icon_logo)))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.drawable.icon_logo)
                                    .into(productHolder.mItemIvPic3);
                        }
                        break;
                    case 2:
                        productHolder.mItemIvPic1.setVisibility(View.GONE);
                        productHolder.mItemIvPic2.setVisibility(View.VISIBLE);
                        productHolder.mItemIvPic3.setVisibility(View.VISIBLE);
                        if (list.get(0).getUserType() == 0) {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(list.get(0).getHeadImg()))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.mipmap.head_2)
                                    .into(productHolder.mItemIvPic3);
                        } else {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(String.valueOf(R.mipmap.logo_member)))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.mipmap.logo_member)
                                    .into(productHolder.mItemIvPic3);
                        }
                        if (list.get(1).getUserType() == 0) {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(list.get(1).getHeadImg()))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.mipmap.head_2)
                                    .into(productHolder.mItemIvPic2);
                        } else {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(String.valueOf(R.drawable.icon_logo)))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.drawable.icon_logo)
                                    .into(productHolder.mItemIvPic2);
                        }
                        break;
                    case 3:
                        productHolder.mItemIvPic1.setVisibility(View.VISIBLE);
                        productHolder.mItemIvPic2.setVisibility(View.VISIBLE);
                        productHolder.mItemIvPic3.setVisibility(View.VISIBLE);
                        if (list.get(0).getUserType() == 0) {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(list.get(0).getHeadImg()))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.mipmap.head_2)
                                    .into(productHolder.mItemIvPic3);
                        } else {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(String.valueOf(R.drawable.icon_logo)))
                                    .asBitmap()
                                    .error(R.drawable.icon_logo)
                                    .into(productHolder.mItemIvPic3);
                        }
                        if (list.get(1).getUserType() == 0) {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(list.get(1).getHeadImg()))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.mipmap.head_2)
                                    .into(productHolder.mItemIvPic2);
                        } else {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(String.valueOf(R.drawable.icon_logo)))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.drawable.icon_logo)
                                    .into(productHolder.mItemIvPic2);
                        }
                        if (list.get(Const.INTEGER_2).getUserType() == 0) {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(list.get(2).getHeadImg()))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.mipmap.head_2)
                                    .into(productHolder.mItemIvPic1);
                        } else {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(String.valueOf(R.drawable.icon_logo)))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.drawable.icon_logo)
                                    .into(productHolder.mItemIvPic1);
                        }
                        break;
                    default:
                        productHolder.mItemIvPic1.setVisibility(View.VISIBLE);
                        productHolder.mItemIvPic2.setVisibility(View.VISIBLE);
                        productHolder.mItemIvPic3.setVisibility(View.VISIBLE);
                        if (list.get(0).getUserType() == 0) {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(list.get(0).getHeadImg()))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.mipmap.head_2)
                                    .into(productHolder.mItemIvPic3);
                        } else {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(String.valueOf(R.drawable.icon_logo)))
                                    .asBitmap()
                                    .error(R.drawable.icon_logo)
                                    .into(productHolder.mItemIvPic3);
                        }
                        if (list.get(1).getUserType() == 0) {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(list.get(1).getHeadImg()))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.mipmap.head_2)
                                    .into(productHolder.mItemIvPic2);
                        } else {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(String.valueOf(R.drawable.icon_logo)))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.drawable.icon_logo)
                                    .into(productHolder.mItemIvPic2);
                        }
                        if (list.get(Const.INTEGER_2).getUserType() == 0) {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(list.get(2).getHeadImg()))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.mipmap.head_2)
                                    .into(productHolder.mItemIvPic1);
                        } else {
                            Glide.with(TianyiApplication.getInstance())
                                    .load(ConstUtils.matchingImageUrl(String.valueOf(R.drawable.icon_logo)))
                                    .asBitmap()
                                    .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                                    .error(R.drawable.icon_logo)
                                    .into(productHolder.mItemIvPic1);
                        }
                        break;
                }
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Utils.isClickable()) {
                            return;
                        }
                        Bundle b = new Bundle();
                        b.putString("spid", datas.get(position).getPsId());
                        b.putString("merchantId", datas.get(position).getMerchantId());
                        b.putString("productId", String.valueOf(datas.get(position).getProductId()));
                        b.putString("hunterId", mReleaseUserType == 3 ? "-1" : hunterId);
                        b.putString(Const.CONS_STR_AUTHORID, mReleaseUserType == 3 ? "-1" : hunterId);
                        JumpIntent.jump(context, ProductDetailActivity.class, b);
                    }
                });

                productHolder.mItemIvAddCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.addCartClick(v, position);
                    }
                });
                break;
            default:
                break;
        }
        return convertView;
    }

    /**
     * 添加购物车
     */
    public interface AddCartListener {
        /**
         * 购物车按钮
         *
         * @param v view
         * @param position position
         */
        void addCartClick(View v, int position);
    }
    private AddCartListener mListener;
    public void setOnAddCartListener(AddCartListener listener) {
        mListener = listener;
    }

    static class ContentHolder {
        @BindView(R.id.content)
        TextView content;

        ContentHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class PicHolder {
        @BindView(R.id.imageView)
        ImageView imageView;

        PicHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ProductHolder {
        @BindView(R.id.item_iv_bg)
        ImageView mItemIvBg;
        @BindView(R.id.item_iv_tips)
        ImageView mItemIvTips;
        @BindView(R.id.item_cb)
        CheckBox mItemCb;
        @BindView(R.id.item_txt_title)
        TextView mItemTxtTitle;
        @BindView(R.id.item_txt_price)
        TextView mItemTxtPrice;
        @BindView(R.id.item_txt_buy)
        TextView mItemTxtBuy;
        @BindView(R.id.item_txt_comment)
        TextView mItemTxtComment;
        @BindView(R.id.item_txt_watch)
        TextView mItemTxtWatch;
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
        @BindView(R.id.item_iv_addCart)
        ImageView mItemIvAddCart;
        @BindView(R.id.item_txt_shop)
        TextView mItemTxtShop;

        ProductHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
