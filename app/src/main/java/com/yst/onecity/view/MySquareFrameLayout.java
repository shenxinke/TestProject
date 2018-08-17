package com.yst.onecity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.taobao.av.ui.view.SizeChangedNotifier;

/**
 * @author WangJingWei
 * @version 4.2.0
 * @date 2018/6/2.
 */
public class MySquareFrameLayout extends FrameLayout  implements SizeChangedNotifier {
    private Listener myOnSizeChangedListener;

    public MySquareFrameLayout(Context context) {
        super(context);
    }

    public MySquareFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MySquareFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOnSizeChangedListener(Listener listener) {
        this.myOnSizeChangedListener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(this.myOnSizeChangedListener != null) {
            this.myOnSizeChangedListener.onSizeChanged(this, w, h, oldw, oldh);
        }
    }
}
