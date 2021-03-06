package com.yst.onecity.utils;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 设置 recyclerivew的item间距
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/1
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int leftRight;
    private int topBottom;

    /**
     * @param leftRight leftRight为横向间的距离
     * @param topBottom topBottom为纵向间距离
     */

    public SpaceItemDecoration(int leftRight, int topBottom) {
        this.leftRight = leftRight;
        this.topBottom = topBottom;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        /*
         * 竖直方向的
         */
        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            /*
             * 最后一项需要 bottom
             */
            if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                outRect.bottom = topBottom;
            }
            outRect.top = topBottom;
            outRect.left = leftRight;
            outRect.right = leftRight;
        } else {
            /*
             * 最后一项需要right
             */
            if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                outRect.right = leftRight;
            }
            outRect.top = topBottom;
            outRect.left = leftRight;
            outRect.bottom = topBottom;
        }
    }


}