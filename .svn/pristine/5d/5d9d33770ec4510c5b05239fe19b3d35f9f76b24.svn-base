package com.yst.onecity.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.activity.NewDetailActivity;
import com.yst.onecity.activity.ProductDetailActivity;
import com.yst.onecity.bean.consult.ConsultListBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.JumpIntent;
import com.yst.onecity.view.GlideCircleTransform;
import com.yst.onecity.view.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分享fragment列表适配器
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/30
 */

public class HomeShareAdapter extends BaseAdapter {
    private Context mContext;
    private List<ConsultListBean.ContentBean.ContentListBean> mList;
    private LayoutInflater inflater;

    private static final int TYPE_NO = 0;
    private static final int TYPE_ONE = 1;
    private static final int TYPE_THREE = 2;
    private static final int TYPE_VIDEO = 3;

    public HomeShareAdapter(Context context, List<ConsultListBean.ContentBean.ContentListBean> list) {
        mContext = context;
        mList = list;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<ConsultListBean.ContentBean.ContentListBean> list) {
        if (list != null) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        switch (mList.get(position).getModelType()) {
            case TYPE_NO:
                return TYPE_NO;
            case TYPE_ONE:
                return TYPE_ONE;
            case TYPE_THREE:
                return TYPE_THREE;
            case TYPE_VIDEO:
                return TYPE_VIDEO;
            default:
                break;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case TYPE_NO:
                NoViewHolder no;
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.item_share_no_pic, null);
                    no = new NoViewHolder(convertView);
                    convertView.setTag(no);
                } else {
                    no = (NoViewHolder) convertView.getTag();
                }

                if (Const.STR3.equals(mList.get(position).getUserType())) {
                    Glide.with(mContext).load(R.drawable.icon_logo).placeholder(R.mipmap.head_2).error(R.mipmap.head_2).into(no.mItemIvShareHead);
                    no.mItemTxtShareTitle.setText("普济一城资讯");
                } else {
                    Glide.with(mContext).load(ConstUtils.matchingImageUrl(mList.get(position).getHeadImg())).transform(new GlideCircleTransform(mContext)).placeholder(R.mipmap.head_2).error(R.mipmap.head_2).into(no.mItemIvShareHead);
                    no.mItemTxtShareTitle.setText(mList.get(position).getName());
                }
                no.mItemTxtShareContent.setText(mList.get(position).getTitle());
                no.mItemZhuanfa.setText(mList.get(position).getShareNum());
                no.mItemDianzan.setText(String.valueOf(mList.get(position).getFabulousNum()));
                no.mItemPinglun.setText(String.valueOf(mList.get(position).getCommentNum()));
                break;
            case TYPE_ONE:
                OneViewHolder one;
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.item_share_one_pic, null);
                    one = new OneViewHolder(convertView);
                    convertView.setTag(one);
                } else {
                    one = (OneViewHolder) convertView.getTag();
                }
                if (Const.STR3.equals(mList.get(position).getUserType())) {
                    Glide.with(mContext).load(R.drawable.icon_logo).placeholder(R.mipmap.head_2).error(R.mipmap.head_2).into(one.mItemIvShareHead);
                    one.mItemTxtShareTitle.setText("普济一城资讯");
                } else {
                    one.mItemTxtShareTitle.setText(mList.get(position).getName());
                    Glide.with(mContext).load(ConstUtils.matchingImageUrl(mList.get(position).getHeadImg())).transform(new GlideCircleTransform(mContext)).placeholder(R.mipmap.head_2).error(R.mipmap.head_2).into(one.mItemIvShareHead);
                }
                one.mItemTxtShareContent.setText(mList.get(position).getTitle());
                one.mItemZhuanfa.setText(mList.get(position).getShareNum());
                one.mItemPinglun.setText(String.valueOf(mList.get(position).getCommentNum()));
                one.mItemDianzan.setText(String.valueOf(mList.get(position).getFabulousNum()));
                if (mList.get(position).getInfo() != null && mList.get(position).getInfo().size() > 0) {
                    for (int i = 0; i < mList.get(position).getInfo().size(); i++) {
                        if (mList.get(position).getInfo().get(i).getCover() == 1) {
                            Glide.with(mContext).load(ConstUtils.matchingImageUrl(mList.get(position).getInfo().get(i).getAddress())).placeholder(R.mipmap.img_default_bg).error(R.mipmap.img_default_bg).into(one.mItemIvShareCover);
                        }
                    }
                }
                break;
            case TYPE_THREE:
                ThreeViewHolder three;
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.item_share_three_pic, null);
                    three = new ThreeViewHolder(convertView);
                    convertView.setTag(three);
                } else {
                    three = (ThreeViewHolder) convertView.getTag();
                }
                if (Const.STR3.equals(mList.get(position).getUserType())) {
                    Glide.with(mContext).load(R.drawable.icon_logo).placeholder(R.mipmap.head_2).error(R.mipmap.head_2).into(three.mItemIvThreeHead);
                    three.mItemTxtThreeTitle.setText("普济一城资讯");
                } else {
                    three.mItemTxtThreeTitle.setText(mList.get(position).getName());
                    Glide.with(mContext).load(ConstUtils.matchingImageUrl(mList.get(position).getHeadImg())).transform(new GlideCircleTransform(mContext)).placeholder(R.mipmap.head_2).error(R.mipmap.head_2).into(three.mItemIvThreeHead);
                }
                three.mItemTxtThreeContent.setText(mList.get(position).getTitle());
                three.mItemZhuanfa.setText(mList.get(position).getShareNum());
                three.mItemPinglun.setText(String.valueOf(mList.get(position).getCommentNum()));
                three.mItemDianzan.setText(String.valueOf(mList.get(position).getFabulousNum()));
                if (mList.get(position).getInfo() != null) {
                    List<String> coverPath = new ArrayList<>();
                    for (int i = 0; i < mList.get(position).getInfo().size(); i++) {
                        if (mList.get(position).getInfo().get(i).getCover() == 1) {
                            coverPath.add(ConstUtils.matchingImageUrl(mList.get(position).getInfo().get(i).getAddress()));
                        }
                    }
                    Glide.with(mContext).load(coverPath.get(0)).placeholder(R.mipmap.img_default_p).error(R.mipmap.img_default_p).into(three.mItemIvThreePic1);
                    Glide.with(mContext).load(coverPath.get(1)).placeholder(R.mipmap.img_default_p).error(R.mipmap.img_default_p).into(three.mItemIvThreePic2);
                    Glide.with(mContext).load(coverPath.get(2)).placeholder(R.mipmap.img_default_p).error(R.mipmap.img_default_p).into(three.mItemIvThreePic3);
                }
                break;
            case TYPE_VIDEO:
                VideoViewHolder video;
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.item_share_video, null);
                    video = new VideoViewHolder(convertView);
                    convertView.setTag(video);
                } else {
                    video = (VideoViewHolder) convertView.getTag();
                }
                if (Const.STR3.equals(mList.get(position).getUserType())) {
                    Glide.with(mContext).load(R.drawable.icon_logo).placeholder(R.mipmap.head_2).error(R.mipmap.head_2).into(video.mItemIvVideoHead);
                    video.mItemTxtVideoTitle.setText("普济一城资讯");
                } else {
                    video.mItemTxtVideoTitle.setText(mList.get(position).getName());
                    Glide.with(mContext).load(ConstUtils.matchingImageUrl(mList.get(position).getHeadImg())).transform(new GlideCircleTransform(mContext)).placeholder(R.mipmap.head_2).error(R.mipmap.head_2).into(video.mItemIvVideoHead);
                }
                Glide.with(mContext).load(ConstUtils.matchingImageUrl(mList.get(position).getCover())).placeholder(R.mipmap.img_default_bg).error(R.mipmap.top_backgroud).into(video.mItemIvVideoCover);
                video.mItemTxtVideoContent.setText(mList.get(position).getTitle());
                video.mItemZhuanfa.setText(mList.get(position).getShareNum());
                video.mItemPinglun.setText(String.valueOf(mList.get(position).getCommentNum()));
                video.mItemDianzan.setText(String.valueOf(mList.get(position).getFabulousNum()));
                break;
            default:
                break;
        }

        convertView.setOnClickListener(v -> {
            if (mList.get(position).getModelType() == Const.INTEGER_3) {
                Bundle bundle = new Bundle();
                bundle.putString("productId", mList.get(position).getProductId());
                bundle.putString("hunterId", String.valueOf(mList.get(position).getUserId()));
                bundle.putString("authorid", String.valueOf(mList.get(position).getUserId()));
                JumpIntent.jump((Activity) mContext, ProductDetailActivity.class, bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(mList.get(position).getId()));
                JumpIntent.jump((Activity) mContext, NewDetailActivity.class, bundle);
            }
        });
        return convertView;
    }

    class OneViewHolder {
        @BindView(R.id.item_iv_share_head)
        RoundedImageView mItemIvShareHead;
        @BindView(R.id.item_txt_share_title)
        TextView mItemTxtShareTitle;
        @BindView(R.id.item_txt_share_content)
        TextView mItemTxtShareContent;
        @BindView(R.id.item_iv_share_cover)
        RoundedImageView mItemIvShareCover;
        @BindView(R.id.item_zhuanfa)
        TextView mItemZhuanfa;
        @BindView(R.id.item_pinglun)
        TextView mItemPinglun;
        @BindView(R.id.item_dianzan)
        TextView mItemDianzan;

        OneViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ThreeViewHolder {
        @BindView(R.id.item_iv_three_head)
        RoundedImageView mItemIvThreeHead;
        @BindView(R.id.item_txt_three_title)
        TextView mItemTxtThreeTitle;
        @BindView(R.id.item_txt_three_content)
        TextView mItemTxtThreeContent;
        @BindView(R.id.item_iv_three_pic1)
        RoundedImageView mItemIvThreePic1;
        @BindView(R.id.item_iv_three_pic2)
        RoundedImageView mItemIvThreePic2;
        @BindView(R.id.item_iv_three_pic3)
        RoundedImageView mItemIvThreePic3;
        @BindView(R.id.item_zhuanfa)
        TextView mItemZhuanfa;
        @BindView(R.id.item_pinglun)
        TextView mItemPinglun;
        @BindView(R.id.item_dianzan)
        TextView mItemDianzan;

        ThreeViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class NoViewHolder {
        @BindView(R.id.item_iv_share_head)
        RoundedImageView mItemIvShareHead;
        @BindView(R.id.item_txt_share_title)
        TextView mItemTxtShareTitle;
        @BindView(R.id.item_txt_share_content)
        TextView mItemTxtShareContent;
        @BindView(R.id.item_zhuanfa)
        TextView mItemZhuanfa;
        @BindView(R.id.item_pinglun)
        TextView mItemPinglun;
        @BindView(R.id.item_dianzan)
        TextView mItemDianzan;

        NoViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class VideoViewHolder {
        @BindView(R.id.item_iv_video_head)
        RoundedImageView mItemIvVideoHead;
        @BindView(R.id.item_txt_video_title)
        TextView mItemTxtVideoTitle;
        @BindView(R.id.item_txt_video_content)
        TextView mItemTxtVideoContent;
        @BindView(R.id.item_iv_video_cover)
        RoundedImageView mItemIvVideoCover;
        @BindView(R.id.item_iv_play)
        ImageView mItemIvPlay;
        @BindView(R.id.item_zhuanfa)
        TextView mItemZhuanfa;
        @BindView(R.id.item_pinglun)
        TextView mItemPinglun;
        @BindView(R.id.item_dianzan)
        TextView mItemDianzan;

        VideoViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
