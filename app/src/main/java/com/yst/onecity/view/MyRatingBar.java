package com.yst.onecity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RatingBar;

/**
 * 自定义RatingBar
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/12/18
 */
public class MyRatingBar extends RatingBar {

    public MyRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    // 构造函数略...

    public MyRatingBar(Context context) {
        super(context);
    }

    public MyRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,
                                          int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        /**这里需要根据实际图片宽高调整*/
        h = w / 5;
        setMeasuredDimension(w, h);
    }

}
