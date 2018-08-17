package com.yst.onecity.view.viewpagerindicator.indicatorview;

import com.yst.onecity.view.viewpagerindicator.PositionData;

import java.util.List;

/**
 * 抽象的viewpager指示器，适用于CommonNavigator
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public interface IPagerIndicator {
    /**
     * 滑动
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    /**
     * 选中
     * @param position
     */
    void onPageSelected(int position);

    /**
     * 滑动状态
     * @param state
     */
    void onPageScrollStateChanged(int state);

    /**
     * 下标数据
     * @param dataList
     */
    void onPositionDataProvide(List<PositionData> dataList);
}
