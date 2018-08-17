package com.yst.onecity.view.viewpagerindicator.titleview;


/**
 * 抽象的指示器标题，适用于CommonNavigator
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public interface IPagerTitleView {
    /**
     * 被选中
     * @param index
     * @param totalCount
     */
    void onSelected(int index, int totalCount);

    /**
     * 未被选中
     * @param index
     * @param totalCount
     */
    void onDeselected(int index, int totalCount);

    /**
     * 离开
     *
     * @param index 索引
     * @param totalCount 总数量
     * @param leavePercent 离开的百分比, 0.0f - 1.0f
     * @param leftToRight 从左至右离开
     */
    void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight);

    /**
     * 进入
     *
     * @param index 索引
     * @param totalCount 总数量
     * @param enterPercent 进入的百分比, 0.0f - 1.0f
     * @param leftToRight 从左至右离开
     */
    void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight);
}
