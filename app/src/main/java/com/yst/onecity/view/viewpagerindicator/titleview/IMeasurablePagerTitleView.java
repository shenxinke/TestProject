package com.yst.onecity.view.viewpagerindicator.titleview;


/**
 * 可测量内容区域的指示器标题
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public interface IMeasurablePagerTitleView extends IPagerTitleView {
    /**
     * 左
     * @return
     */
    int getContentLeft();

    /**
     * 顶部
     * @return
     */
    int getContentTop();

    /**
     *
     * 右
     * @return
     */
    int getContentRight();

    /**
     * 底部
     * @return
     */
    int getContentBottom();
}
