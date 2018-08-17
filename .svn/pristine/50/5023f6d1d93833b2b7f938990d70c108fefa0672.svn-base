package com.yst.onecity.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.bean.product.ProductDetailBean;

/**
 * 商品详情轮播图适配器
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/12/18
 */
public class NetworkImageHolderView implements Holder<ProductDetailBean.ContentBean.ImageUrlBean> {
    private ScaleImageiew imageView;
    private Context mContext;

    @Override
    public View createView(Context context) {
        mContext = context;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView = new ScaleImageiew(context);
        imageView.setScaleMode(1);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ScaleImageiew.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, ProductDetailBean.ContentBean.ImageUrlBean data) {
        Glide.with(mContext).load(data.getAddress()).placeholder(R.mipmap.img_default_p).error(R.mipmap.img_default_p).into(imageView);
    }
}