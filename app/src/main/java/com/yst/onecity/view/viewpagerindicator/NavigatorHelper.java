package com.yst.onecity.view.viewpagerindicator;

/**
 * Created by lenovo on 2017/7/13.
 */


import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.yst.onecity.config.Const;
import com.yst.onecity.view.viewpagerindicator.iinterface.ScrollState;


/**
 * 方便扩展IPagerNavigator的帮助类，将ViewPager的3个回调方法转换成
 * onSelected、onDeselected、onEnter等回调，方便扩展
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public class NavigatorHelper {
    private SparseBooleanArray mDeselectedItems = new SparseBooleanArray();
    private SparseArray<Float> mLeavedPercents = new SparseArray<Float>();

    private int mTotalCount;
    private int mCurrentIndex;
    private int mLastIndex;
    private float mLastPositionOffsetSum;
    private int mScrollState;

    private boolean mSkimOver;
    private NavigatorHelper.OnNavigatorScrollListener mNavigatorScrollListener;

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        float currentPositionOffsetSum = position + positionOffset;
        boolean leftToRight = false;
        if (mLastPositionOffsetSum <= currentPositionOffsetSum) {
            leftToRight = true;
        }
        if (mScrollState != ScrollState.SCROLL_STATE_IDLE) {
            if (currentPositionOffsetSum == mLastPositionOffsetSum) {
                return;
            }
            int nextPosition = position + 1;
            boolean normalDispatch = true;
            if (positionOffset == Const.FLOAT_0_0F) {
                if (leftToRight) {
                    nextPosition = position - 1;
                    normalDispatch = false;
                }
            }
            for (int i = 0; i < mTotalCount; i++) {
                if (i == position || i == nextPosition) {
                    continue;
                }
                Float leavedPercent = mLeavedPercents.get(i, 0.0f);
                if (leavedPercent != 1.0f) {
                    dispatchOnLeave(i, 1.0f, leftToRight, true);
                }
            }
            if (normalDispatch) {
                if (leftToRight) {
                    dispatchOnLeave(position, positionOffset, true, false);
                    dispatchOnEnter(nextPosition, positionOffset, true, false);
                } else {
                    dispatchOnLeave(nextPosition, 1.0f - positionOffset, false, false);
                    dispatchOnEnter(position, 1.0f - positionOffset, false, false);
                }
            } else {
                dispatchOnLeave(nextPosition, 1.0f - positionOffset, true, false);
                dispatchOnEnter(position, 1.0f - positionOffset, true, false);
            }
        } else {
            for (int i = 0; i < mTotalCount; i++) {
                if (i == mCurrentIndex) {
                    continue;
                }
                boolean deselected = mDeselectedItems.get(i);
                if (!deselected) {
                    dispatchOnDeselected(i);
                }
                Float leavedPercent = mLeavedPercents.get(i, 0.0f);
                if (leavedPercent != 1.0f) {
                    dispatchOnLeave(i, 1.0f, false, true);
                }
            }
            dispatchOnEnter(mCurrentIndex, 1.0f, false, true);
            dispatchOnSelected(mCurrentIndex);
        }
        mLastPositionOffsetSum = currentPositionOffsetSum;
    }

    private void dispatchOnEnter(int index, float enterPercent, boolean leftToRight, boolean force) {
        if (mSkimOver || index == mCurrentIndex || mScrollState == ScrollState.SCROLL_STATE_DRAGGING || force) {
            if (mNavigatorScrollListener != null) {
                mNavigatorScrollListener.onEnter(index, mTotalCount, enterPercent, leftToRight);
            }
            mLeavedPercents.put(index, 1.0f - enterPercent);
        }
    }
    private void dispatchOnLeave(int index, float leavePercent, boolean leftToRight, boolean force) {
        boolean flag = mSkimOver || index == mLastIndex || mScrollState == ScrollState.SCROLL_STATE_DRAGGING || ((index == mCurrentIndex - 1 || index == mCurrentIndex + 1) && mLeavedPercents.get(index, Const.FLOAT_0_0F) != Const.FLOAT_1_0F) || force;
        if (flag) {
            if (mNavigatorScrollListener != null) {
                mNavigatorScrollListener.onLeave(index, mTotalCount, leavePercent, leftToRight);
            }
            mLeavedPercents.put(index, leavePercent);
        }
    }

    private void dispatchOnSelected(int index) {
        if (mNavigatorScrollListener != null) {
            mNavigatorScrollListener.onSelected(index, mTotalCount);
        }
        mDeselectedItems.put(index, false);
    }

    private void dispatchOnDeselected(int index) {
        if (mNavigatorScrollListener != null) {
            mNavigatorScrollListener.onDeselected(index, mTotalCount);
        }
        mDeselectedItems.put(index, true);
    }

    public void onPageSelected(int position) {
        mLastIndex = mCurrentIndex;
        mCurrentIndex = position;
        dispatchOnSelected(mCurrentIndex);
        for (int i = 0; i < mTotalCount; i++) {
            if (i == mCurrentIndex) {
                continue;
            }
            boolean deselected = mDeselectedItems.get(i);
            if (!deselected) {
                dispatchOnDeselected(i);
            }
        }
    }

    public void onPageScrollStateChanged(int state) {
        mScrollState = state;
    }

    public void setNavigatorScrollListener(NavigatorHelper.OnNavigatorScrollListener navigatorScrollListener) {
        mNavigatorScrollListener = navigatorScrollListener;
    }

    public void setSkimOver(boolean skimOver) {
        mSkimOver = skimOver;
    }

    public int getTotalCount() {
        return mTotalCount;
    }

    public void setTotalCount(int totalCount) {
        mTotalCount = totalCount;
        mDeselectedItems.clear();
        mLeavedPercents.clear();
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    public int getScrollState() {
        return mScrollState;
    }

    public interface OnNavigatorScrollListener {
        /**
         * 进入
         * @param index
         * @param totalCount
         * @param enterPercent
         * @param leftToRight
         */
        void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight);

        /**
         * 移动
         * @param index
         * @param totalCount
         * @param leavePercent
         * @param leftToRight
         */
        void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight);

        /**
         * 选中
         * @param index
         * @param totalCount
         */
        void onSelected(int index, int totalCount);

        /**
         * 选中
         * @param index
         * @param totalCount
         */
        void onDeselected(int index, int totalCount);
    }
}