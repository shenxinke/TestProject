package com.yst.onecity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.bean.MoreServerMemberBean;
import com.yst.onecity.bean.product.ProductCommentListBean;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.RatingBar;
import com.yst.onecity.view.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品详情评价 适配器
 *
 * @author Shenxinke
 * @version 1.2.0
 * @data 2018/7/20
 */

public class ShoppingEvaluateAdapter extends BaseAdapter {
    private Context context;
    private List<ProductCommentListBean.ContentBean.ContentListBean> comments = new ArrayList<>();
    private LayoutInflater inflater;

    public ShoppingEvaluateAdapter(Context context, List<ProductCommentListBean.ContentBean.ContentListBean> comments) {
        this.context = context;
        this.comments = comments;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<ProductCommentListBean.ContentBean.ContentListBean> mDatas) {
        if (mDatas != null) {
            this.comments = mDatas;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HolderView holderView = null;
        if (view == null) {
            view = inflater.inflate(R.layout.item_product_comment, null);
            holderView = new HolderView(view);
            view.setTag(holderView);
        } else {
            holderView = (HolderView) view.getTag();
        }
        holderView.tvName.setText(ConstUtils.getStringNoEmpty(ConstUtils.showIdNumber(comments.get(i).getPnickname())));
        holderView.tvTime.setText(ConstUtils.getStringNoEmpty(comments.get(i).getPcreatedtime()));
        holderView.tvGuige.setText("规格: " + ConstUtils.getStringNoEmpty(comments.get(i).getProduct_specification_name()));
        holderView.tvDesc.setText(ConstUtils.getStringNoEmpty(comments.get(i).getPcontent()));
        holderView.mRatingBar.setStar(comments.get(i).getPstart());
        Glide.with(TianyiApplication.getInstance()).load(ConstUtils.matchingImageUrl(comments.get(i).getPaddress())).error(R.mipmap.img_default_p).into(holderView.imageHead);
        return view;
    }

    class HolderView {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_guige)
        TextView tvGuige;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.comment_ratingbar)
        RatingBar mRatingBar;
        @BindView(R.id.iv_comment_head)
        RoundedImageView imageHead;

        HolderView(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
