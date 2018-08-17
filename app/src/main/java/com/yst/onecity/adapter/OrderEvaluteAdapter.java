package com.yst.onecity.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.bean.OrderEvaluteBean;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单评价适配器
 *
 * @author chenxiaowei
 * @version 3.2.1
 * @date 2017/0/19
 */
public class OrderEvaluteAdapter extends BaseAdapter {

    private Context context;
    private List<OrderEvaluteBean> data;
    private EvalueLitener listener;
    private int flag;

    public OrderEvaluteAdapter(Context context, List<OrderEvaluteBean> data, int flag, EvalueLitener listener) {
        this.context = context;
        this.data = data;
        this.flag = flag;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public OrderEvaluteBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_evelute, null);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        final OrderEvaluteBean bean = data.get(position);
        if (flag == 1) {
            myViewHolder.etComment.setEnabled(false);
            myViewHolder.etComment.setText(ConstUtils.getStringNoEmpty(bean.getContent()));
            myViewHolder.etComment.setTextColor(0xff333333);
            myViewHolder.ratingBar.setEnabled(false);
            myViewHolder.ratingBar.setRating(bean.getStart());

            if (!TextUtils.isEmpty(bean.getNickname())) {
                myViewHolder.tvName.setText(ConstUtils.getStringNoEmpty(bean.getNickname()));
                Glide.with(context).load(bean.gethImg()).into(myViewHolder.ivHead);
            } else {
                myViewHolder.tvName.setText(ConstUtils.getStringNoEmpty(bean.getName()));
                Glide.with(context).load(bean.getpImg()).into(myViewHolder.ivHead);
            }

        } else {
            /**
             * 服务专员
             */
            if (!TextUtils.isEmpty(bean.getNickname())) {
                bean.setHid(bean.gethId());
                bean.setCommentType(2);
                bean.setOid(bean.getOid());
                myViewHolder.etComment.setHint("评价一下我的服务");
                myViewHolder.tvName.setText(ConstUtils.getStringNoEmpty(bean.getNickname()));
                Glide.with(context).load(bean.gethImg()).into(myViewHolder.ivHead);
            } else {
                /**
                 * 商品
                 */
                bean.setOid(bean.getOid());
                bean.setCommentType(1);
                myViewHolder.etComment.setHint("宝贝满足您的期待吗?说说您的体验");
                myViewHolder.tvName.setText(ConstUtils.getStringNoEmpty(bean.getName()));
                Glide.with(context).load(bean.getpImg()).into(myViewHolder.ivHead);
            }
            bean.setMid(TianyiApplication.appCommonBean.getId());
            //把Bean与输入框进行绑定
            myViewHolder.etComment.setTag(bean);
            myViewHolder.ratingBar.setTag(bean);
            myViewHolder.etComment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //获得Edittext所在position里面的Bean，并设置数据
                    OrderEvaluteBean bean = (OrderEvaluteBean) myViewHolder.etComment.getTag();
                    bean.setComment(s.toString() + "");
                    if (listener != null) {
                        listener.getEvaluteData(data);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            if (!TextUtils.isEmpty(bean.getComment())) {
                    myViewHolder.etComment.setText(bean.getComment());
            } else {
                myViewHolder.etComment.setText("");
            }

            myViewHolder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    OrderEvaluteBean bean = (OrderEvaluteBean) myViewHolder.etComment.getTag();
                    bean.setStart((int) v);
                    if (listener != null) {
                        listener.getEvaluteData(data);
                    }
                }
            });
            myViewHolder.ratingBar.setRating(bean.getStart());
        }
        return convertView;
    }

    class MyViewHolder {
        @BindView(R.id.et_comment)
        EditText etComment;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.iv_head)
        RoundedImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;

        MyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface EvalueLitener {
        /**
         * 获取评价数据
         * @param list
         */
        void getEvaluteData(List<OrderEvaluteBean> list);
    }
}
