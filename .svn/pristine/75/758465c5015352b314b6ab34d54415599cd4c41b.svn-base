package com.yst.onecity.view.swipyrefreshlayout;
/**
 * 刷新控件
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public enum SwipyRefreshLayoutDirection {
    /**
     * 顶部
     */
    TOP(0),
    /**
     * 底部
     */
    BOTTOM(1),
    /**
     * 同时成立
     */
    BOTH(2);

    private int mValue;

    SwipyRefreshLayoutDirection(int value) {
        this.mValue = value;
    }

    public static SwipyRefreshLayoutDirection getFromInt(int value) {
        for (SwipyRefreshLayoutDirection direction : SwipyRefreshLayoutDirection.values()) {
            if (direction.mValue == value) {
                return direction;
            }
        }
        return BOTH;
    }

}
