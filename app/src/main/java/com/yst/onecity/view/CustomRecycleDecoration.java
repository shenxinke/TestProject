package com.yst.onecity.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 通用decoration
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/12/18
 */
public class CustomRecycleDecoration extends RecyclerView.ItemDecoration {

    private int space;

    /**方向*/
    public static int orientation = -1;
    public int horizontal = 6;
    public int vertical = 8;

    /**
     * 布局列数
     */
    public static int Column = 2;

    public static int Type = 0;

    public CustomRecycleDecoration(int space, int type) {
        this.space = space;
        Type = type;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {


        switch (Type) {
            case 0:
                // TYPE_LinearLayoutManager
                if (orientation == vertical) {
                    outRect.top = space;
                    outRect.left = space / 2;
                    outRect.right = space / 2;
                    outRect.bottom = 0;
                } else if (orientation == horizontal) {
                    outRect.top = 0;
                    outRect.left = space;
                    outRect.bottom = 0;
                    outRect.right = 0;
                } else {
                }
                break;
            case 1:
                // TYPE_GridLayoutManager
                if (parent.getChildPosition(view) % Column == 0) {
                    outRect.left = space;
                } else {
                    outRect.left = space / 3;
                }
                outRect.right = space;
                outRect.top = space;
                break;
            case 2:
                // TYPE_StaggeredGridLayoutManager
                if (parent.getChildPosition(view) % Column == 0) {
                    outRect.left = space / 3;
                    outRect.right = space / 3;
                } else {
                    outRect.left = space;
                }
                outRect.top = space;
                break;
            default:
                break;
        }

    }
}
