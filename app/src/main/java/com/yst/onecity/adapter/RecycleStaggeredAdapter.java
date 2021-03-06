package com.yst.onecity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.servermember.ProductListBean;
import com.yst.onecity.config.Const;

import java.util.List;

/**
 * 设置Grid适配器   瀑布流的效果
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/0/19
 */
public class RecycleStaggeredAdapter extends RecyclerView.Adapter<RecycleStaggeredAdapter.ItemViewHolder> {

    private List<ProductListBean.ContentBean> recycleViewBean;
    private int column;
    private Context mContext;

    public interface OnItemClickLitener {
        /**
         * 条目监听
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);

    }

    public OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public RecycleStaggeredAdapter(List<ProductListBean.ContentBean> bean, Context context) {
        this.recycleViewBean = bean;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return recycleViewBean.size();
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder staggeredHolder, int position) {

        setUpItemClick(staggeredHolder);

        if (position == 1) {
            staggeredHolder.image.setVisibility(View.VISIBLE);
        } else {
            staggeredHolder.image.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(recycleViewBean.get(position).getImageurl()).error(R.mipmap.img_default_p).into(staggeredHolder.imageView);
        staggeredHolder.name.setText(recycleViewBean.get(position).getName());
        staggeredHolder.price.setText(String.format(mContext.getString(R.string.string_money),String.format("%.2f",(double)recycleViewBean.get(position).getSalePrice())));
        staggeredHolder.saleCount.setText(String.format(mContext.getString(R.string.sale_count),recycleViewBean.get(position).getSaleNum()));
        if (recycleViewBean.get(position).getSaleNum() >= Const.INTEGER_10000){
            staggeredHolder.saleCount.setText(String.format("销量%1$f万", String.format("%.2f",recycleViewBean.get(position).getSaleNum()/10000)));
        }else{
            staggeredHolder.saleCount.setText(String.format("销量%1$d", recycleViewBean.get(position).getSaleNum()));
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewgroup, int arg1) {
        View view = LayoutInflater.from(viewgroup.getContext()).inflate(
                R.layout.item_product_list, viewgroup, false);
        ItemViewHolder mGridItemViewHolder = new ItemViewHolder(view);
        return mGridItemViewHolder;
    }

    @SuppressWarnings("rawtypes")
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageView image;
        private TextView name;
        private TextView price;
        private TextView saleCount;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.goods_iv);
            image = (ImageView) itemView.findViewById(R.id.imageview);
            name = (TextView) itemView.findViewById(R.id.goodsIntroduce_tv);
            price = (TextView) itemView.findViewById(R.id.goodsPrice_tv);
            saleCount = (TextView) itemView.findViewById(R.id.salesVolume_tv);

        }

    }

    protected void setUpItemClick(final ItemViewHolder staggeredHolder) {
        if (mOnItemClickLitener != null) {
            staggeredHolder.itemView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    int layoutPosition = staggeredHolder.getPosition();
                    mOnItemClickLitener.onItemClick(staggeredHolder.itemView, layoutPosition);
                }
            });
        }
    }
}
