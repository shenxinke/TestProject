package com.yst.onecity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.information.InformationContentBean;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.utils.ConstUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 资讯列表适配器
 *
 * @author chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class NewsAdapter extends BaseAdapter {

    private Context mContext;
    private List<InformationContentBean> mDatas;
    private LayoutInflater inflater;
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
    private OnDeleteListener listener;
    private ShowStateListener showStateListener;

    public NewsAdapter(Context context, List<InformationContentBean> mDatas, OnDeleteListener listener, ShowStateListener showStateListener) {
        this.mContext = context;
        this.mDatas = mDatas;
        this.listener = listener;
        this.showStateListener = showStateListener;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<InformationContentBean> data) {
        if (data != null) {
            this.mDatas = data;
            this.notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        switch (mDatas.get(position).getModelType()) {
            case TYPE_NOP:
                return TYPE_NOP;
            case TYPE_ONEP:
                return TYPE_ONEP;
            case TYPE_THREEP:
                return TYPE_THREEP;
            default:
                break;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = null;
        switch (getItemViewType(position)) {
            case TYPE_NOP:
                NoPicHolderView noPicHolderView = null;
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.item_publish_no_image, null);
                    noPicHolderView = new NoPicHolderView(convertView);
                    convertView.setTag(noPicHolderView);
                } else {
                    noPicHolderView = (NoPicHolderView) convertView.getTag();
                }
                final InformationContentBean noImageBean = mDatas.get(position);
                noPicHolderView.tvTitle.setText(ConstUtils.getStringNoEmpty(noImageBean.getTitle()));
                final int noImageStatus = noImageBean.getStatus();
                //0未发布，1审核中，2已发布，3驳回，4不显示
                if (noImageStatus == 1) {
                    noPicHolderView.tvState.setText("审核中");
                    noPicHolderView.tvShowState.setText("查看");
                } else if (noImageStatus == Const.INTEGER_2) {
                    noPicHolderView.tvState.setText("审核通过");
                    noPicHolderView.tvState.setBackgroundColor(0xffFCA31A);
                    noPicHolderView.tvShowState.setText("不显示");
                } else if (noImageStatus == Const.INTEGER_3) {
                    noPicHolderView.tvState.setText("审核失败");
                    noPicHolderView.tvState.setBackgroundColor(0xffdedede);
                    noPicHolderView.tvShowState.setText("编辑");
                } else {
                    noPicHolderView.tvState.setText("审核通过");
                    noPicHolderView.tvState.setBackgroundColor(0xffFCA31A);
                    noPicHolderView.tvShowState.setText("显示");
                }
                noPicHolderView.tvShowState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (showStateListener != null) {
                            showStateListener.btnStateClick(noImageStatus, noImageBean);
                        }
                    }
                });

                noPicHolderView.tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.deleteClick(noImageBean.getId());
                        }
                    }
                });
                break;
            case TYPE_ONEP:
                OnePicHolderView onePicHolderView = null;
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.item_publish_image1, null);
                    onePicHolderView = new OnePicHolderView(convertView);
                    convertView.setTag(onePicHolderView);
                } else {
                    onePicHolderView = (OnePicHolderView) convertView.getTag();
                }

                final InformationContentBean oneImageBean = mDatas.get(position);
                onePicHolderView.tvTitle.setText(ConstUtils.getStringNoEmpty(oneImageBean.getTitle()));
                final int oneImageStatus = oneImageBean.getStatus();
                //0未发布，1审核中，2已发布，3驳回，4不显示
                if (oneImageStatus == 1) {
                    onePicHolderView.tvState.setText("审核中");
                    onePicHolderView.tvShowState.setText("查看");
                } else if (oneImageStatus == Const.INTEGER_2) {
                    onePicHolderView.tvState.setText("审核通过");
                    onePicHolderView.tvState.setBackgroundColor(0xffFCA31A);
                    onePicHolderView.tvShowState.setText("不显示");
                } else if (oneImageStatus == Const.INTEGER_3) {
                    onePicHolderView.tvState.setText("审核失败");
                    onePicHolderView.tvState.setBackgroundColor(0xffdedede);
                    onePicHolderView.tvShowState.setText("编辑");
                } else {
                    onePicHolderView.tvState.setText("审核通过");
                    onePicHolderView.tvState.setBackgroundColor(0xffFCA31A);
                    onePicHolderView.tvShowState.setText("显示");
                }
                onePicHolderView.tvShowState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (showStateListener != null) {
                            showStateListener.btnStateClick(oneImageStatus, oneImageBean);
                        }
                    }
                });
                List<InformationContentBean.NewsInfoBean> info1 = new ArrayList<>();
                if (oneImageBean.getInfo() != null) {
                    for (int i = 0; i < oneImageBean.getInfo().size(); i++) {
                        if (oneImageBean.getInfo().get(i).getCover() != null) {
                            if ("1".equals(oneImageBean.getInfo().get(i).getCover())) {
                                info1.add(oneImageBean.getInfo().get(i));
                            }
                        }
                    }
                }
                if (info1.size() == 1) {
                    List<String> oneImage = new ArrayList<>();
                    for (int i = 0; i < info1.size(); i++) {
                        if (!TextUtils.isEmpty(info1.get(i).getCover())) {
                            oneImage.add(info1.get(i).getAddress());
                        }
                    }
                    Glide.with(mContext).load(Constants.URL_IMAGE_HEAD + oneImage.get(0))
                            .placeholder(R.mipmap.img_default_bg)
                            .error(R.mipmap.img_default_bg)
                            .into(onePicHolderView.ivPicture);
                }
                onePicHolderView.tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.deleteClick(oneImageBean.getId());
                        }
                    }
                });
                break;
            case TYPE_THREEP:
                ThreePicHolderView threePicHolderView = null;
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.item_publish_image3, null);
                    threePicHolderView = new ThreePicHolderView(convertView);
                    convertView.setTag(threePicHolderView);
                } else {
                    threePicHolderView = (ThreePicHolderView) convertView.getTag();
                }

                final InformationContentBean threeImageBean = mDatas.get(position);
                threePicHolderView.tvTitle.setText(ConstUtils.getStringNoEmpty(threeImageBean.getTitle()));
                final int threeImageStatus = threeImageBean.getStatus();
                //0未发布，1审核中，2已发布，3驳回，4不显示
                if (threeImageStatus == 1) {
                    threePicHolderView.tvState.setText("审核中");
                    threePicHolderView.tvShowState.setText("查看");
                } else if (threeImageStatus == Const.INTEGER_2) {
                    threePicHolderView.tvState.setText("审核通过");
                    threePicHolderView.tvState.setBackgroundColor(0xffFCA31A);
                    threePicHolderView.tvShowState.setText("不显示");
                } else if (threeImageStatus == Const.INTEGER_3) {
                    threePicHolderView.tvState.setText("审核失败");
                    threePicHolderView.tvState.setBackgroundColor(0xffdedede);
                    threePicHolderView.tvShowState.setText("编辑");
                } else {
                    threePicHolderView.tvState.setText("审核通过");
                    threePicHolderView.tvState.setBackgroundColor(0xffFCA31A);
                    threePicHolderView.tvShowState.setText("显示");
                }
                List<InformationContentBean.NewsInfoBean> info3 = new ArrayList<>();
                if (threeImageBean.getInfo() != null) {
                    for (int i = 0; i < threeImageBean.getInfo().size(); i++) {
                        if (threeImageBean.getInfo().get(i).getCover() != null) {
                            if ("1".equals(threeImageBean.getInfo().get(i).getCover())) {
                                info3.add(threeImageBean.getInfo().get(i));
                            }
                        }
                    }
                }
                if (info3.size() == Const.INTEGER_3) {
                    final List<String> threeImage = new ArrayList<>();
                    for (int i = 0; i < info3.size(); i++) {
                        if ("1".equals(info3.get(i).getCover())) {
                            threeImage.add(info3.get(i).getAddress());
                        }
                    }
                    Glide.with(mContext).load(Constants.URL_IMAGE_HEAD + ConstUtils.getStringNoEmpty(threeImage.get(0)))
                            .placeholder(R.mipmap.img_default_p)
                            .error(R.mipmap.img_default_p)
                            .into(threePicHolderView.ivImage1);
                    Glide.with(mContext).load(Constants.URL_IMAGE_HEAD + ConstUtils.getStringNoEmpty(threeImage.get(1)))
                            .placeholder(R.mipmap.img_default_p)
                            .error(R.mipmap.img_default_p)
                            .into(threePicHolderView.ivImage2);
                    Glide.with(mContext).load(Constants.URL_IMAGE_HEAD + ConstUtils.getStringNoEmpty(threeImage.get(2)))
                            .placeholder(R.mipmap.img_default_p)
                            .error(R.mipmap.img_default_p)
                            .into(threePicHolderView.ivImage3);
                }
                threePicHolderView.tvShowState.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (showStateListener != null) {
                            showStateListener.btnStateClick(threeImageStatus, threeImageBean);
                        }
                    }
                });

                threePicHolderView.tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.deleteClick(threeImageBean.getId());
                        }
                    }
                });
                break;
            default:
                break;
        }
        return convertView;
    }


    class NoPicHolderView {
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_show_state)
        TextView tvShowState;
        @BindView(R.id.tv_delete)
        TextView tvDelete;

        NoPicHolderView(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class OnePicHolderView {
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_show_state)
        TextView tvShowState;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.iv_picture)
        ImageView ivPicture;

        OnePicHolderView(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ThreePicHolderView {
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_show_state)
        TextView tvShowState;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.iv_image1)
        ImageView ivImage1;
        @BindView(R.id.iv_image2)
        ImageView ivImage2;
        @BindView(R.id.iv_image3)
        ImageView ivImage3;

        ThreePicHolderView(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnDeleteListener {
        /**
         * 删除
         * @param id
         */
        void deleteClick(String id);
    }

    public interface ShowStateListener {
        /**
         * 状态
         * @param state
         * @param bean
         */
        void btnStateClick(int state, InformationContentBean bean);
    }
}
