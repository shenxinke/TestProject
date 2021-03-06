package com.yst.onecity.adapter;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.adapter.viewholder.AbstractTypeViewHolder;
import com.yst.onecity.bean.ShareDetailsBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.Utils;
import com.yst.onecity.view.GlideCircleTransform;
import com.yst.onecity.view.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商城类型列表的ViewHolder
 *
 * @author Shenxinke
 * @version 4.2.0
 * @data 2018/6/4
 */

public class ShareDetailsHolder extends AbstractTypeViewHolder<ShareDetailsBean.ContentBean.ConsultationBean.InfoBean> {

    @BindView(R.id.cl_root)
    public ConstraintLayout clParent;
    @BindView(R.id.mall_item_gouwuche)
    public ImageView imgShoppingCard;
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
    @BindView(R.id.zhuanyuan_fenxiang)
    TextView tvShareNum;
    @BindView(R.id.zhuanyuan_zan)
    TextView tvPraiseNum;
    @BindView(R.id.mall_item_image)
    ImageView imgItemImage;
    @BindView(R.id.mall_item_head)
    RoundedImageView imgHeadPic;
    @BindView(R.id.mall_item_head2)
    RoundedImageView imgHeadPic2;
    @BindView(R.id.mall_item_head3)
    RoundedImageView imgHeadPic3;
    @BindView(R.id.tv_story)
    TextView tvStory;
    public String id;

    public ShareDetailsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void bindHolder(ShareDetailsBean.ContentBean.ConsultationBean.InfoBean item) {
        id = (String) item.getPsId();
        tvTitle.setText(Utils.signString(item.getPname()));
        Glide.with(TianyiApplication.getInstance())
                .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty(item.getPimage())))
                .asBitmap()
                .error(R.mipmap.iv_default_class)
                .into(imgItemImage);
        tvPraiseNum.setText(Utils.signString(String.valueOf(item.getNiceNum())));
        tvShareNum.setText(Utils.signString(String.valueOf(item.getCoverSortNum())));
        tvPrice.setText(Utils.signString(Const.CONS_STR_MONEY + item.getSalePrice()));
        tvBuyPeopleNum.setText(Utils.signString(Const.CONS_STR_BUY_PEOPLE + String.valueOf(item.getSaleNum())));
        tvCommentPeopleNum.setText(Utils.signString(Const.CONS_STR_COMMENT_PEOPLE + String.valueOf(item.getCommentNum())));
        tvBrowsePeopleNum.setText(Utils.signString(Const.CONS_STR_BROWSE_PEOPLE + String.valueOf(item.getViews())));
        List<ShareDetailsBean.ContentBean.ConsultationBean.InfoBean.ConsultationPublisherListBean> memberImg = item.getConsultationPublisherList();
        tvStory.setText(String.valueOf(item.getProductStoryCount()));
        if (null == memberImg) {
            return;
        }
        if (memberImg.size() == Const.INTEGER_0) {
            imgHeadPic.setVisibility(View.GONE);
            imgHeadPic2.setVisibility(View.GONE);
            imgHeadPic3.setVisibility(View.GONE);
        } else if (memberImg.size() == Const.INTEGER_1) {
            imgHeadPic.setVisibility(View.GONE);
            imgHeadPic2.setVisibility(View.GONE);
            imgHeadPic3.setVisibility(View.VISIBLE);
        } else if (memberImg.size() == Const.INTEGER_2) {
            imgHeadPic.setVisibility(View.GONE);
            imgHeadPic2.setVisibility(View.VISIBLE);
            imgHeadPic3.setVisibility(View.VISIBLE);
        } else if (memberImg.size() == Const.INTEGER_3) {
            imgHeadPic.setVisibility(View.VISIBLE);
            imgHeadPic2.setVisibility(View.VISIBLE);
            imgHeadPic3.setVisibility(View.VISIBLE);
        }
        switch (memberImg.size()) {
            case Const.INTEGER_1:
                Glide.with(TianyiApplication.getInstance())
                        .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty((String) memberImg.get(0).getHeadImg())))
                        .asBitmap()
                        .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                        .error(R.mipmap.head_2)
                        .into(imgHeadPic3);
                break;
            case Const.INTEGER_2:
                Glide.with(TianyiApplication.getInstance())
                        .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty((String) memberImg.get(0).getHeadImg())))
                        .asBitmap()
                        .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                        .error(R.mipmap.head_2)
                        .into(imgHeadPic3);
                Glide.with(TianyiApplication.getInstance())
                        .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty((String) memberImg.get(1).getHeadImg())))
                        .asBitmap()
                        .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                        .error(R.mipmap.head_2)
                        .into(imgHeadPic2);
                break;
            case Const.INTEGER_3:
                Glide.with(TianyiApplication.getInstance())
                        .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty((String) memberImg.get(0).getHeadImg())))
                        .asBitmap()
                        .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                        .error(R.mipmap.head_2)
                        .into(imgHeadPic3);
                Glide.with(TianyiApplication.getInstance())
                        .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty((String) memberImg.get(1).getHeadImg())))
                        .asBitmap()
                        .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                        .error(R.mipmap.head_2)
                        .into(imgHeadPic2);
                Glide.with(TianyiApplication.getInstance())
                        .load(ConstUtils.matchingImageUrl(ConstUtils.getStringNoEmpty((String) memberImg.get(2).getHeadImg())))
                        .asBitmap()
                        .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                        .error(R.mipmap.head_2)
                        .into(imgHeadPic);
                break;
            default:
                break;
        }
    }
}
