package com.yst.onecity.view.viewpagerindicator.titleview;

import android.content.Context;

/**
 * 带颜色渐变和缩放的指示器标题
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public class ScaleTransitionPagerTitleView extends ColorTransitionPagerTitleView {
    /**
     * 字体大小缩放比例
     */
    private float mMinScale = 1.0f;

    public ScaleTransitionPagerTitleView(Context context) {
        super(context);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        // 实现颜色渐变
        super.onEnter(index, totalCount, enterPercent, leftToRight);
        setScaleX(mMinScale + (1.0f - mMinScale) * enterPercent);
        setScaleY(mMinScale + (1.0f - mMinScale) * enterPercent);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        // 实现颜色渐变
        super.onLeave(index, totalCount, leavePercent, leftToRight);
        setScaleX(1.0f + (mMinScale - 1.0f) * leavePercent);
        setScaleY(1.0f + (mMinScale - 1.0f) * leavePercent);
    }

    public float getMinScale() {
        return mMinScale;
    }

    public void setMinScale(float minScale) {
        mMinScale = minScale;
    }
}
