package com.yst.onecity.bean.commissioner;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.view.GlideCircleTransform;

/**
 * databinding加载图片
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/9
 */

public class ImageUtils {

    /**
     * url地址
     *
     * @param iv
     * @param url
     */
    @BindingAdapter({"imgUrl"})
    public static void setImage(ImageView iv, String url) {
        String s = ConstUtils.matchingImageUrl(url);
        Glide.with(TianyiApplication.getInstance())
                .load(s)
                .transform(new GlideCircleTransform(TianyiApplication.getContext()))
                .placeholder(R.mipmap.brand_default)
                .centerCrop()
                .error(R.mipmap.brand_default)
                .into(iv);
    }

}
