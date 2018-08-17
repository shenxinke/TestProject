package com.yst.onecity.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.yst.onecity.R;

/**
 * 固定比例imageview
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/12/18
 */
public class ScaleImageiew extends AppCompatImageView {
    private double[] scaleArr = {1.0 * 9 / 16, 1};
    /**
     * 按照宽度分比例---0,16:9   1正方形
     */
    private int scaleModeWidth;


    public void setScaleMode(int scaleMode) {
        this.scaleModeWidth = scaleMode;
    }

    public ScaleImageiew(Context context) {
        this(context, null);
    }

    public ScaleImageiew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageiew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScaleImageiew);
        scaleModeWidth = typedArray.getInteger(R.styleable.ScaleImageiew_scale_mode, 0);
        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(w, (int) (w * (scaleArr[scaleModeWidth])));
    }
}
