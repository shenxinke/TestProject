package com.yst.onecity.view.viewpagerindicator.iinterface;

/**
 * 自定义滚动状态，消除对ViewPager的依赖
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */

public interface ScrollState {
    int SCROLL_STATE_IDLE = 0;
    int SCROLL_STATE_DRAGGING = 1;
    int SCROLL_STATE_SETTLING = 2;
}
