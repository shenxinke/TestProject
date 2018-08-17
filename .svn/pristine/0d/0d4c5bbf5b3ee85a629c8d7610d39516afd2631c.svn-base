package com.yst.onecity.addressmanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.yst.onecity.R;

/**
 * 编辑收货地址的悬浮框
 *
 * @author :wuxiaofang
 * @version :4.0.0
 * @date :2018/03/27
 */
public class SideLetterBar extends View {
    private static final String[] STRARR = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int choose = -1;
    private Paint paint = new Paint();
    private boolean showBg = false;
    private OnLetterChangedListener onLetterChangedListener;
    private TextView overlay;

    public SideLetterBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideLetterBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideLetterBar(Context context) {
        super(context);
    }

    /**
     * 设置悬浮的textview
     *
     * @param overlay
     */
    public void setOverlay(TextView overlay) {
        this.overlay = overlay;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("deprecation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBg) {
            canvas.drawColor(Color.TRANSPARENT);
        }

        int cellHeight =getMeasuredHeight()/STRARR.length;
        int cellWidth = getMeasuredWidth();

        for (int i = 0; i < STRARR.length; i++) {
            paint.setTextSize(getResources().getDimension(R.dimen.text_size_12));
            paint.setColor(ContextCompat.getColor(getContext(), R.color.black));
            paint.setAntiAlias(true);

            Rect mBounds = new Rect();
            paint.getTextBounds(STRARR[i],0,STRARR[i].length(),mBounds);

            if (i == choose) {
                paint.setColor(ContextCompat.getColor(getContext(), R.color.color_909090));
//                paint.setFakeBoldText(true);  //加粗
            }

            float xPos = cellWidth / 2 - mBounds.width()/2;
            float yPos =  cellHeight/2 + mBounds.height()/2 + i*cellHeight;
            canvas.drawText(STRARR[i], xPos, yPos, paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnLetterChangedListener listener = onLetterChangedListener;
        final int c = (int) (y / getHeight() * STRARR.length);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBg = true;
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < STRARR.length) {
                        listener.onLetterChanged(STRARR[c]);
                        choose = c;
                        invalidate();
                        if (overlay != null) {
                            overlay.setVisibility(VISIBLE);
                            overlay.setText(STRARR[c]);
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < STRARR.length) {
                        listener.onLetterChanged(STRARR[c]);
                        choose = c;
                        invalidate();
                        if (overlay != null) {
                            overlay.setVisibility(VISIBLE);
                            overlay.setText(STRARR[c]);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBg = false;
                choose = -1;
                invalidate();
                if (overlay != null) {
                    overlay.setVisibility(GONE);
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnLetterChangedListener(OnLetterChangedListener onLetterChangedListener) {
        this.onLetterChangedListener = onLetterChangedListener;
    }

    public interface OnLetterChangedListener {
        /**
         * 字符串变化
         * @param letter
         */
        void onLetterChanged(String letter);
    }

}
