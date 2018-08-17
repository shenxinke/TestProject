package com.yst.onecity.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.UserCollectionMessageBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用户资讯收藏适配器
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/0/19
 */
public class UserCollectionMessageAdapter extends BaseAdapter {

    private List<UserCollectionMessageBean.ContentBean> mdata;
    private Context mContext;
    /**
     * 没有图片
     */
    private final int TYPE_NOP = 0;
    /**
     * 一张图片
     */
    private final int TYPE_ONEP = 1;
    /**
     * 三张图片
     */
    private final int TYPE_THREEP = 2;
    private LayoutInflater inflater;
    private int type = 0;

    public UserCollectionMessageAdapter(Context context, List<UserCollectionMessageBean.ContentBean> data) {
        this.mContext = context;
        this.mdata = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mdata == null ? 0 : mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
                if (null == convertView) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_collection_message, null);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                if (mdata.get(position).isShow()) {
                    holder.imgCollectionMessageCheck.setVisibility(View.VISIBLE);
                    holder.imgCollectionMessageCheck.setImageResource(mdata.get(position).isCheck() ? R.drawable.collection_edit_select_s : R.drawable.collection_edit_select_n);
                } else {
                    holder.imgCollectionMessageCheck.setVisibility(View.GONE);
                }

                if(mdata.get(position).getUser_type() == Const.INTEGER_3){
                    Glide.with(mContext).load(R.drawable.icon_logo).error(R.mipmap.head_2).into(holder.imgCollectionHeader);
                    holder.txtCollectionName.setText("普济一城资讯");
                }else{
                    Glide.with(mContext).load(ConstUtils.matchingImageUrl(mdata.get(position).getAddress())).error(R.mipmap.head_2).into(holder.imgCollectionHeader);
                    holder.txtCollectionName.setText(mdata.get(position).getNickname());
                }
                holder.txtCollectionContent.setText(mdata.get(position).getTitle());
                holder.txtCollectionLoveCount.setText(String.valueOf(mdata.get(position).getFabulous_num()));
                holder.txtCollectionMessageCount.setText(String.valueOf(mdata.get(position).getComment_num()));
                holder.imgCollectionMessageCheck.setOnClickListener(new MyListener(position));
                holder.collectionmessagelayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onJumpPage(String.valueOf(mdata.get(position).getId()));
                    }
                });
                if(mdata.get(position).getModel_type() == Const.INTEGER_0){
                    holder.imgCollectionContent.setVisibility(View.GONE);
                    holder.imgCollectionContent2.setVisibility(View.GONE);
                    holder.threeimg.setVisibility(View.GONE);
                    holder.cover.setVisibility(View.GONE);
                    holder.cover1.setVisibility(View.GONE);
                    holder.cover2.setVisibility(View.GONE);
                } else if (mdata.get(position).getModel_type() == Const.INTEGER_1){
                    holder.imgCollectionContent2.setVisibility(View.VISIBLE);
                    for (int i = 0; i < mdata.get(position).getPhotoAddress().size(); i++) {
                        Glide.with(mContext).load(ConstUtils.matchingImageUrl(mdata.get(position).getPhotoAddress().get(0).getAddress())).error(R.mipmap.img_default_p).into(holder.imgCollectionContent2);
                    }
                    holder.imgCollectionContent.setVisibility(View.GONE);
                    holder.threeimg.setVisibility(View.GONE);
                    holder.cover.setVisibility(View.GONE);
                    holder.cover1.setVisibility(View.GONE);
                    holder.cover2.setVisibility(View.GONE);
                } else if (mdata.get(position).getModel_type() == Const.INTEGER_2){
                    for (int i = 0; i < mdata.get(position).getPhotoAddress().size(); i++) {
                        holder.threeimg.setVisibility(View.VISIBLE);
                        holder.cover.setVisibility(View.VISIBLE);
                        holder.cover1.setVisibility(View.VISIBLE);
                        holder.cover2.setVisibility(View.VISIBLE);
                        holder.imgCollectionContent.setVisibility(View.GONE);
                        holder.imgCollectionContent2.setVisibility(View.GONE);
                        switch (i) {
                            case 0:
                                Glide.with(mContext).load(ConstUtils.matchingImageUrl(mdata.get(position).getPhotoAddress().get(0).getAddress())).error(R.mipmap.img_default_p).into(holder.cover);
                                break;
                            case 1:
                                Glide.with(mContext).load(ConstUtils.matchingImageUrl(mdata.get(position).getPhotoAddress().get(1).getAddress())).error(R.mipmap.img_default_p).into(holder.cover1);
                                break;
                            case 2:
                                Glide.with(mContext).load(ConstUtils.matchingImageUrl(mdata.get(position).getPhotoAddress().get(2).getAddress())).error(R.mipmap.img_default_p).into(holder.cover2);
                                break;
                            default:
                                break;
                        }
                    }
                }
        return convertView;
    }

    public interface OnClickListener {
        /**
         * 收藏全部
         */
        void checkCollectionAll();

        /**
         * 页数跳转
         * @param id
         */
        void onJumpPage(String id);
    }

    private OnClickListener onClickListener;

    public void checkAll(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    class MyListener implements View.OnClickListener {

        private int position;

        public MyListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            mdata.get(position).setCheck(!mdata.get(position).isCheck());
            onClickListener.checkCollectionAll();
            notifyDataSetChanged();
        }
    }


    static class ViewHolder {
        @BindView(R.id.collection_message_layout)
        LinearLayout collectionmessagelayout;
        @BindView(R.id.img_collection_header)
        RoundedImageView imgCollectionHeader;
        @BindView(R.id.txt_collection_name)
        TextView txtCollectionName;
        @BindView(R.id.img_collection_message_check)
        ImageView imgCollectionMessageCheck;
        @BindView(R.id.txt_collection_content)
        TextView txtCollectionContent;
        @BindView(R.id.img_collection_content)
        RoundedImageView imgCollectionContent;
        @BindView(R.id.img_collection_content2)
        RoundedImageView imgCollectionContent2;
        @BindView(R.id.img_collection_content_pic1)
        RoundedImageView imgCollectionContentPic1;
        @BindView(R.id.img_collection_content_pic2)
        RoundedImageView imgCollectionContentPic2;
        @BindView(R.id.img_collection_content_pic3)
        RoundedImageView imgCollectionContentPic3;
        @BindView(R.id.llayout_collection_message_multi_image)
        LinearLayout llayoutCollectionMessageMultiImage;
        @BindView(R.id.txt_collection_love_count)
        TextView txtCollectionLoveCount;
        @BindView(R.id.txt_collection_message_count)
        TextView txtCollectionMessageCount;
        @BindView(R.id.iv_cover)
        ImageView cover;
        @BindView(R.id.iv_cover1)
        ImageView cover1;
        @BindView(R.id.iv_cover2)
        ImageView cover2;
        @BindView(R.id.three_img)
        LinearLayout threeimg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
