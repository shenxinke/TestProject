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
 * 草稿箱适配器adapter
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class DraftBoxAdapter extends BaseAdapter {

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
    private OnEditListener editListener;
    private OnUploadListener uploadListener;

    public DraftBoxAdapter(Context context, List<InformationContentBean> mDatas,
                           OnDeleteListener listener, OnEditListener editListener, OnUploadListener uploadListener) {
        this.mContext = context;
        this.mDatas = mDatas;
        this.listener = listener;
        this.editListener = editListener;
        this.uploadListener = uploadListener;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<InformationContentBean> data) {
        this.mDatas = data;
        this.notifyDataSetChanged();
    }

    public void onRefresh(List<InformationContentBean> mList) {
        if (mList != null) {
            this.mDatas.clear();
            this.mDatas.addAll(mList);
            notifyDataSetChanged();
        }
    }

    public void addData(List<InformationContentBean> mList) {
        if (mList != null && mList.size() != 0) {
            this.mDatas.addAll(mList);
            notifyDataSetChanged();
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
                    convertView = inflater.inflate(R.layout.item_draft_box_no_image, null);
                    noPicHolderView = new NoPicHolderView(convertView);
                    convertView.setTag(noPicHolderView);
                } else {
                    noPicHolderView = (NoPicHolderView) convertView.getTag();
                }

                final InformationContentBean noImageBean = mDatas.get(position);
                noPicHolderView.tvTitle.setText(ConstUtils.getStringNoEmpty(noImageBean.getTitle()));
                noPicHolderView.tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editListener != null) {
                            editListener.btnEditClick(noImageBean);
                        }
                    }
                });

                noPicHolderView.tvUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (uploadListener != null) {
                            uploadListener.uploadClick(noImageBean);
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
                    convertView = inflater.inflate(R.layout.item_draft_box_image1, null);
                    onePicHolderView = new OnePicHolderView(convertView);
                    convertView.setTag(onePicHolderView);
                } else {
                    onePicHolderView = (OnePicHolderView) convertView.getTag();
                }

                final InformationContentBean oneImageBean = mDatas.get(position);
                onePicHolderView.tvTitle.setText(ConstUtils.getStringNoEmpty(oneImageBean.getTitle()));
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
                            .error(R.mipmap.img_default_bg)
                            .placeholder(R.mipmap.img_default_bg)
                            .into(onePicHolderView.ivImage);
                }
                onePicHolderView.tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editListener != null) {
                            editListener.btnEditClick(oneImageBean);
                        }
                    }
                });

                onePicHolderView.tvUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (uploadListener != null) {
                            uploadListener.uploadClick(oneImageBean);
                        }
                    }
                });
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
                    convertView = inflater.inflate(R.layout.item_draft_box_image3, null);
                    threePicHolderView = new ThreePicHolderView(convertView);
                    convertView.setTag(threePicHolderView);
                } else {
                    threePicHolderView = (ThreePicHolderView) convertView.getTag();
                }

                final InformationContentBean threeImageBean = mDatas.get(position);
                threePicHolderView.tvTitle.setText(ConstUtils.getStringNoEmpty(threeImageBean.getTitle()));
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
                if (info3 != null && info3.size() == Const.INTEGER_3) {
                    if (info3.size() == Const.INTEGER_3) {
                        final List<String> threeImage = new ArrayList<>();
                        for (int i = 0; i < info3.size(); i++) {
                            if ("1".equals(info3.get(i).getCover())) {
                                threeImage.add(info3.get(i).getAddress());
                            }
                        }
                        Glide.with(mContext).load(Constants.URL_IMAGE_HEAD + ConstUtils.getStringNoEmpty(threeImage.get(0)))
                                .placeholder(R.mipmap.img_default_p)
                                .error(R.mipmap.img_default_p).into(threePicHolderView.ivImage1);
                        Glide.with(mContext).load(Constants.URL_IMAGE_HEAD + ConstUtils.getStringNoEmpty(threeImage.get(1)))
                                .placeholder(R.mipmap.img_default_p)
                                .error(R.mipmap.img_default_p).into(threePicHolderView.ivImage2);
                        Glide.with(mContext).load(Constants.URL_IMAGE_HEAD + ConstUtils.getStringNoEmpty(threeImage.get(2)))
                                .placeholder(R.mipmap.img_default_p)
                                .error(R.mipmap.img_default_p).into(threePicHolderView.ivImage3);
                    }
                }
                threePicHolderView.tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (editListener != null) {
                            editListener.btnEditClick(threeImageBean);
                        }
                    }
                });

                threePicHolderView.tvUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (uploadListener != null) {
                            uploadListener.uploadClick(threeImageBean);
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
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_upload)
        TextView tvUpload;
        @BindView(R.id.tv_delete)
        TextView tvDelete;

        NoPicHolderView(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class OnePicHolderView {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_upload)
        TextView tvUpload;
        @BindView(R.id.tv_delete)
        TextView tvDelete;

        OnePicHolderView(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ThreePicHolderView {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_image1)
        ImageView ivImage1;
        @BindView(R.id.iv_image2)
        ImageView ivImage2;
        @BindView(R.id.iv_image3)
        ImageView ivImage3;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_upload)
        TextView tvUpload;
        @BindView(R.id.tv_delete)
        TextView tvDelete;

        ThreePicHolderView(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 删除
     */
    public interface OnDeleteListener {
        /**
         * 删除
         * @param id
         */
        void deleteClick(String id);
    }

    /**
     * 编辑
     */
    public interface OnEditListener {
        /**
         * 编辑
         * @param bean
         */
        void btnEditClick(InformationContentBean bean);
    }

    /**
     * 修改
     */
    public interface OnUploadListener {
        /**
         * 修改
         * @param bean
         */
        void uploadClick(InformationContentBean bean);
    }
}
