package com.yst.onecity.fragment;

import android.app.Fragment;

/**
 * Fragment懒加载
 *
 * @author Shenxinke
 * @version 4.2.0
 * @data 2018/6/15
 */

public abstract class AbstractLazyFragment extends Fragment {
    protected boolean isVisible;

    /**
     * 在这里实现Fragment数据的缓加载. * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 加载数据
     */
    protected abstract void lazyLoad();

    protected void onInvisible() {
    }
}



