package com.yst.onecity.adapter;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.viewholder.AbstractTypeViewHolder;
import com.yst.onecity.bean.ShoppingLocalityBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.GlideCircleTransform;
import com.yst.onecity.view.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 本地直供的ViewHolder
 *
 * @author Shenxinke
 * @version 4.2.0
 * @data 2018/6/13
 */

public class ShoppingLocalityHolder extends AbstractTypeViewHolder<ShoppingLocalityBean.ContentBean> {

    public int id;
    @BindView(R.id.cl_root)
    public ConstraintLayout clParent;
    @BindView(R.id.mall_item_gouwuche)
    public ImageView imgShoppingCard;
    @BindView(R.id.tv_story)
    public TextView tvStory;
    @BindView(R.id.zhuanyuan_fenxiang)
    TextView tvShareNum;
    @BindView(R.id.zhuanyuan_zan)
    TextView tvPraiseNum;
    @BindView(R.id.mall_item_title)
    TextView tvTitle;
    @BindView(R.id.mall_item_price)
    TextView tvPrice;
    @BindView(R.id.mall_item_goumai)
    TextView tvBuyPeopleNum;
    @BindView(R.id.mall_item_pingjia)
    TextView tvCommentPeopleNum;
    @BindView(R.id.mall_item_liulan)
    TextView tvBrowsePeopleNum;
    @BindView(R.id.mall_item_image)
    ImageView imgItemImage;
    @BindView(R.id.mall_item_head)
    RoundedImageView imgHeadPic;
    @BindView(R.id.mall_item_head2)
    RoundedImageView imgHeadPic2;
    @BindView(R.id.mall_item_head3)
    RoundedImageView imgHeadPic3;
    @BindView(R.id.tv_scope)
    TextView tvScope;
    @BindView(R.id.tv_locality)
    TextView tvLocality;

    public ShoppingLocalityHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindHolder(ShoppingLocalityBean.ContentBean item) {
        id = item.getId();
        tvTitle.setText(Utils.signString(item.getName()));
        Glide.with(TianyiApplication.getInstance())
                .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(item.getImageurl())))
                .asBitmap()
                .error(R.mipmap.iv_default_class)
                .into(imgItemImage);
        tvPraiseNum.setText(Utils.signString(String.valueOf(item.getFabulousNum())));
        tvShareNum.setText(Utils.signString(String.valueOf(item.getShareNum())));
        tvPrice.setText(Utils.signString(Const.CONS_STR_MONEY + item.getSalePrice()));
        tvBuyPeopleNum.setText(Utils.signString(String.valueOf(item.getSaleNum())+ Const.CONS_STR_BUY_PEOPLE ));
        tvCommentPeopleNum.setText(Utils.signString( String.valueOf(item.getCommentNum())+Const.CONS_STR_COMMENT_PEOPLE));
        tvBrowsePeopleNum.setText(Utils.signString(String.valueOf(item.getViews()+Const.CONS_STR_BROWSE_PEOPLE )));
        List<ShoppingLocalityBean.ContentBean.MemberImgBean> memberImg = item.getMemberImg();
        tvStory.setText(item.getConsultaionNum() + Const.CONS_STR_STORY);
        tvLocality.setText(String.valueOf("由"+item.getMerchantName()+"提供"));

        /**
         * 1超出 0未超出
         */
        if (item.getIsScope() == Const.INTEGER_1) {
            imgShoppingCard.setVisibility(View.GONE);
            tvScope.setVisibility(View.VISIBLE);
        } else {
            imgShoppingCard.setVisibility(View.VISIBLE);
            tvScope.setVisibility(View.GONE);
        }
        MyLog.e("aaa", "memberImg.size()————————" + memberImg.size());

        switch (memberImg.size()) {
            case Const.INTEGER_0:
                imgHeadPic.setVisibility(View.GONE);
                imgHeadPic2.setVisibility(View.GONE);
                imgHeadPic3.setVisibility(View.GONE);
                break;
            case Const.INTEGER_1:
                imgHeadPic.setVisibility(View.GONE);
                imgHeadPic2.setVisibility(View.GONE);
                imgHeadPic3.setVisibility(View.VISIBLE);
                if (memberImg.get(Const.INTEGER_0).getUserType() == Const.INTEGER_0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(memberImg.get(0).getHeadImg()))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.head_2)
                            .into(imgHeadPic3);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.mipmap.logo_member)))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.logo_member)
                            .into(imgHeadPic3);
                }
                break;
            case Const.INTEGER_2:
                imgHeadPic.setVisibility(View.GONE);
                imgHeadPic2.setVisibility(View.VISIBLE);
                imgHeadPic3.setVisibility(View.VISIBLE);
                if (memberImg.get(Const.INTEGER_0).getUserType() == Const.INTEGER_0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(memberImg.get(0).getHeadImg()))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.head_2)
                            .into(imgHeadPic3);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.mipmap.logo_member)))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.logo_member)
                            .into(imgHeadPic3);
                }
                if (memberImg.get(Const.INTEGER_1).getUserType() == Const.INTEGER_0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(memberImg.get(1).getHeadImg()))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.head_2)
                            .into(imgHeadPic2);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.mipmap.logo_member)))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.logo_member)
                            .into(imgHeadPic2);
                }
                break;
            case Const.INTEGER_3:
                imgHeadPic.setVisibility(View.VISIBLE);
                imgHeadPic2.setVisibility(View.VISIBLE);
                imgHeadPic3.setVisibility(View.VISIBLE);
                if (memberImg.get(Const.INTEGER_0).getUserType() == Const.INTEGER_0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(memberImg.get(0).getHeadImg()))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.head_2)
                            .into(imgHeadPic3);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.mipmap.logo_member)))
                            .asBitmap()
                            .error(R.mipmap.logo_member)
                            .into(imgHeadPic3);
                }
                if (memberImg.get(Const.INTEGER_1).getUserType() == Const.INTEGER_0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(memberImg.get(1).getHeadImg()))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.head_2)
                            .into(imgHeadPic2);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.mipmap.logo_member)))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.logo_member)
                            .into(imgHeadPic2);
                }
                if (memberImg.get(Const.INTEGER_2).getUserType() == Const.INTEGER_0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(memberImg.get(2).getHeadImg()))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.head_2)
                            .into(imgHeadPic);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.mipmap.logo_member)))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.logo_member)
                            .into(imgHeadPic);
                }
                break;
            default:
                imgHeadPic.setVisibility(View.VISIBLE);
                imgHeadPic2.setVisibility(View.VISIBLE);
                imgHeadPic3.setVisibility(View.VISIBLE);
                if (memberImg.get(Const.INTEGER_0).getUserType() == Const.INTEGER_0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(memberImg.get(0).getHeadImg()))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.head_2)
                            .into(imgHeadPic3);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.mipmap.logo_member)))
                            .asBitmap()
                            .error(R.mipmap.logo_member)
                            .into(imgHeadPic3);
                }
                if (memberImg.get(Const.INTEGER_1).getUserType() == Const.INTEGER_0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(memberImg.get(1).getHeadImg()))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.head_2)
                            .into(imgHeadPic2);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.mipmap.logo_member)))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.logo_member)
                            .into(imgHeadPic2);
                }
                if (memberImg.get(Const.INTEGER_2).getUserType() == Const.INTEGER_0) {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(memberImg.get(2).getHeadImg()))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.head_2)
                            .into(imgHeadPic);
                } else {
                    Glide.with(TianyiApplication.getInstance())
                            .load(ConstUtils.matchingImageUrl(String.valueOf(R.mipmap.logo_member)))
                            .asBitmap()
                            .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                            .error(R.mipmap.logo_member)
                            .into(imgHeadPic);
                }
                break;
        }
    }
}
