package com.yst.onecity.view.viewpagerindicator.iinterface;

/**
 * 抽象的ViewPager导航器
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public interface IPagerNavigator {

    /**
     * 滚动
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
     * 滚动状态
     * @param state
     */
    void onPageScrollStateChanged(int state);

    /**
     * 当IPagerNavigator被添加到MagicIndicator时调用
     */
    void onAttachToMagicIndicator();

    /**
     * 当IPagerNavigator从MagicIndicator上移除时调用
     */
    void onDetachFromMagicIndicator();

    /**
     * ViewPager内容改变时需要先调用此方法，自定义的IPagerNavigator应当遵守此约定
     */
    void notifyDataSetChanged();
}