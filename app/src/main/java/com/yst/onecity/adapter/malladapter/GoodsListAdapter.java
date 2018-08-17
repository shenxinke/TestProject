package com.yst.onecity.adapter.malladapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.bean.mall.GoodsListBean;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.utils.WindowUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商城列表gridview的适配器
 *
 * @author wuxiaofang
 * @version 3.1.0
 * @date 2018/3/20
 */

public class GoodsListAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsListBean.ContentBean.ProductBean> goodsList = new ArrayList<>();
    private ShowDialogInterface showDialogInterface;
    private final int screenWidth;

    public GoodsListAdapter(Context context) {
        this.context = context;
        screenWidth = WindowUtils.getScreenWidth((Activity) context);
    }

    /**
     * 为集合添加数据
     */
    public void addData(List<GoodsListBean.ContentBean.ProductBean> mList) {
        if (mList != null) {
            goodsList.clear();
            goodsList.addAll(mList);
            notifyDataSetChanged();
        }
    }

    public void initInterface(ShowDialogInterface showDialogInterface) {
        this.showDialogInterface = showDialogInterface;
    }

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_goodslist_gridview, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MyLog.e("sss", "imgWidth: " + (screenWidth / 2 - 5));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth / 2 - 5, screenWidth / 2 - 5);
        viewHolder.ivGoodslist.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(screenWidth / 2 - 5, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewHolder.tvGoodslistName.setLayoutParams(layoutParams2);
        viewHolder.rlPrice.setLayoutParams(layoutParams2);
        try {
            Glide.with(TianyiApplication.getContext()).load(goodsList.get(position).getImg())
                    .thumbnail(0.1f)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(viewHolder.ivGoodslist);
            viewHolder.tvGoodslistCount.setText("销量" + goodsList.get(position).getSaleNum());
            viewHolder.tvGoodslistName.setText(goodsList.get(position).getName());
            viewHolder.tvGoodslistPrice.setText(String.format(context.getResources().getString(R.string.string_money), String.format("%.2f", goodsList.get(position).getSalePrice())));
            viewHolder.ivAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ConstUtils.isClickable()) {
                        showDialogInterface.showDialog(position);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public interface ShowDialogInterface {
        /**
         * 显示弹框
         *
         * @param position
         */
        void showDialog(int position);
    }


    class ViewHolder {
        @BindView(R.id.iv_goodslist)
        ImageView ivGoodslist;
        @BindView(R.id.tv_goodslist_name)
        TextView tvGoodslistName;
        @BindView(R.id.tv_goodslist_price)
        TextView tvGoodslistPrice;
        @BindView(R.id.tv_goodslist_count)
        TextView tvGoodslistCount;
        @BindView(R.id.iv_add_cart)
        ImageView ivAddCart;
        @BindView(R.id.rl_price)
        LinearLayout rlPrice;
        @BindView(R.id.ll_item)
        LinearLayout llItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
