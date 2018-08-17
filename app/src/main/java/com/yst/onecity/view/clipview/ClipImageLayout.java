package com.yst.onecity.view.clipview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.yst.onecity.utils.WindowUtils;

/**
 * 裁剪图片view
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/12/18
 */
public class ClipImageLayout extends RelativeLayout {

    private ClipZoomImageView mZoomImageView;
    private ClipImageBorderView mClipImageView;
    Context context;
    /**
     * 这里测试，直接写死了大小，真正使用过程中，可以提取为自定义属性
     */
    private int mHorizontalPadding = 0;

    public ClipImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mZoomImageView = new ClipZoomImageView(context);
        mClipImageView = new ClipImageBorderView(context);

    }

    /**
     * 设置图片
     */
    public void setClipImage(Bitmap bitmap) {

        android.view.ViewGroup.LayoutParams lp = new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);

        mZoomImageView.setImageBitmap(bitmap);
        this.addView(mZoomImageView, lp);
        this.addView(mClipImageView, lp);


//        // 计算padding的px
//        mHorizontalPadding = (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, getResources()
//                        .getDisplayMetrics());

    }

    /**
     * 对外公布设置边距的方法,单位为dp
     *
     * @param mHorizontalPadding
     */
    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }

    /**
     * 裁剪框宽 高 ,单位为px
     *
     * @param width 裁剪框宽
     */
    public void setClipSize(int width, int height) {
        mHorizontalPadding = (WindowUtils.getScreenWidth((Activity) context) - width) / 2;
///        mZoomImageView.setHorizontalPadding(mHorizontalPadding);
        mZoomImageView.setHorizontalPadding(width, height);
        mClipImageView.setHorizontalPadding(width, height);
    }

    /**
     * 裁切图片
     *
     * @return
     */
    public Bitmap clip() {
        return mZoomImageView.clip();
    }

}
