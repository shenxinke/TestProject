package com.yst.onecity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 自定义ScrollView
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/12/18
 */
public class ScrollBottomScrollView extends ScrollView {

    private ScrollBottomListener scrollBottomListener;

    public ScrollBottomScrollView(Context context) {
        super(context);
    }

    public ScrollBottomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollBottomScrollView(Context context, AttributeSet attrs,int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt){
        if(t + getHeight() >=  computeVerticalScrollRange()){
            //ScrollView滑动到底部了
            scrollBottomListener.scrollBottom();
        }
    }

    public void setScrollBottomListener(ScrollBottomListener scrollBottomListener){
        this.scrollBottomListener = scrollBottomListener;
    }

    public interface ScrollBottomListener{
        /**
         * 滑动到底部
         */
        public void scrollBottom();
    }

}
