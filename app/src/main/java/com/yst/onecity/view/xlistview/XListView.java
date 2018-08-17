package com.yst.onecity.view.xlistview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.utils.WindowUtils;


/**
 * 可加载更多下拉刷新主控件
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/12/18
 */
public class XListView extends ListView implements OnScrollListener {

    /**
     * save event y
     */
    private float mLastY = -1;
    /**
     * used for scroll back
     */
    private Scroller mScroller;
    /**
     * user's scroll listener
     */
    private OnScrollListener mScrollListener;

    /**
     * the interface to trigger refresh and load more.
     */
    private IXListViewListener mListViewListener;

    /**
     * header view
     */
    private XListViewHeader mHeaderView;
    /**
     * header view content, use it to calculate the Header's height.
     * And hide it when disable pull refresh.
     */
    private RelativeLayout mHeaderViewContent;
    private TextView mHeaderTimeView;
    private TextView txtTime;
    /**
     * header view's height
     */
    private int mHeaderViewHeight;
    private boolean mEnablePullRefresh = true;
    /**
     * is refreashing.
     */
    private boolean mPullRefreshing = false;

    /**
     * footer view
     */
    private XListViewFooter mFooterView;
    private boolean mEnablePullLoad;
    private boolean mPullLoading;
    private boolean mIsFooterReady = false;

    /**
     * total list items, used to detect is at the bottom of listview.
     */
    private int mTotalItemCount;

    /**
     * for mScroller, scroll back from header or footer.
     */
    private int mScrollBack;
    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;

    /**
     * scroll back duration
     */
    private final static int SCROLL_DURATION = 400;
    /**
     * when pull up >= 50px
     */
    private final static int PULL_LOAD_MORE_DELTA = 50;
    /**
     * at bottom, trigger
     * load more.
     * support iOS like pull
     */
    private final static float OFFSET_RADIO = 1.8f;

    /**
     * @param context
     */
    public XListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        super.setOnScrollListener(this);

        // init header view
        mHeaderView = new XListViewHeader(context);
        mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
        mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_time);
        txtTime = (TextView) mHeaderView.findViewById(R.id.txt_time);

        addHeaderView(mHeaderView);

        // init footer view
        mFooterView = new XListViewFooter(context);

        // init header height
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeaderViewHeight = mHeaderViewContent.getHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        // make sure XListViewFooter is the last footer view, and only add once.
        if (mIsFooterReady == false) {
            mIsFooterReady = true;
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);
    }

    /**
     * 闅愯棌鐣岄潰鐨勫姞杞芥洿澶氭柟娉�
     */
    public void removeFooterView() {
        if (mFooterView != null) {
            mFooterView.hide();
        }
    }

    /**
     * enable or disable pull down refresh feature.
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        // disable, hide the content
        if (!mEnablePullRefresh) {
            mHeaderViewContent.setVisibility(View.INVISIBLE);
        } else {
            mHeaderViewContent.setVisibility(View.VISIBLE);
        }
    }

    public void autoRefresh() {
        // reset
        mLastY = -1;
        // 判断是否在第一行，如果不是第一行，则不执行
        if (getFirstVisiblePosition() == 0) {
            // 判断是否可刷新和不处于刷新状态
            if (mEnablePullRefresh && mPullRefreshing != true) {
                mPullRefreshing = true;
                mScrollBack = SCROLLBACK_HEADER;
                if (mHeaderViewHeight == 0) {
                    int width = WindowUtils.getScreenWidth((Activity) getContext());
                    mHeaderViewContent.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                            MeasureSpec.makeMeasureSpec((1 << 30) - 1, MeasureSpec.AT_MOST));
                    mScroller.startScroll(0, 0, 0, mHeaderViewContent.getMeasuredHeight(), SCROLL_DURATION);
                    invalidate();
                } else {
                    mScroller.startScroll(0, 0, 0, mHeaderViewHeight, SCROLL_DURATION);
                    invalidate();
                }
                mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
                if (mListViewListener != null) {
                    mListViewListener.onRefresh();
                }
            }
            resetHeaderHeight();
        }
    }

    private boolean isTimeInVisiable;

    public void setTimeInVisiable(boolean isTimeNoVisiable) {
        this.isTimeInVisiable = isTimeNoVisiable;
    }

    /**
     * enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
        } else {
            mPullLoading = false;
            mFooterView.show();
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    /**
     * stop refresh, reset header view.
     */
    public void stopRefresh() {
        if (mPullRefreshing == true) {
            mPullRefreshing = false;
            resetHeaderHeight();
        }
    }

    /**
     * stop load more, reset footer view.
     */
    public void stopLoadMore() {
        if (mPullLoading == true) {
            mPullLoading = false;
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
        }
    }

    /**
     * set last refresh time
     *
     * @param time
     */
    public void setRefreshTime(String time) {
        if (null == time) {
            mHeaderTimeView.setText("");
            return;
        }
        mHeaderTimeView.setText(time);
    }

    public void setRefreshTime(String time, String t) {
        txtTime.setText(time);
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener l = (OnXScrollListener) mScrollListener;
            l.onXScrolling(this);
        }
    }

    private boolean isGoneProgress;

    public void setGoneProgress(boolean flag) {
        this.isGoneProgress = flag;
    }

    private void updateHeaderHeight(float delta) {
        if (isGoneProgress) {
            mHeaderView.setGoneProgress(true);
        }

        mHeaderView.setVisiableHeight((int) delta
                + mHeaderView.getVisiableHeight());
        // 鏈浜庡埛鏂扮姸鎬侊紝鏇存柊绠ご
        if (mEnablePullRefresh && !mPullRefreshing) {
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                mHeaderView.setState(XListViewHeader.STATE_READY);
            } else {
                mHeaderView.setState(XListViewHeader.STATE_NORMAL);
            }
        }
        // scroll to top each time
        setSelection(0);
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisiableHeight();
        // not visible.
        if (height == 0) {
            return;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return;
        }
        // default: scroll back to dismiss header.
        int finalHeight = 0;
        // is refreshing, just scroll back to show all the header.
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);
        // trigger computeScroll
        invalidate();
    }

    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;
        if (mEnablePullLoad && !mPullLoading) {
            // height enough to invoke load
            if (height > PULL_LOAD_MORE_DELTA) {
                // more.
                mFooterView.setState(XListViewFooter.STATE_READY);
            } else {
                mFooterView.setState(XListViewFooter.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);

        // setSelection(mTotalItemCount - 1); // scroll to bottom
    }

    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                    SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setState(XListViewFooter.STATE_LOADING);
        if (mListViewListener != null) {
            mListViewListener.onLoadMore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                boolean flag = getFirstVisiblePosition() == 0 && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0);
                boolean flag2 = getLastVisiblePosition() == mTotalItemCount - 1 && (mFooterView.getBottomMargin() > 0 || deltaY < 0);

                if (flag) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();
                } else if (flag2) {
                    // last item, already pulled up or want to pull up.
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;
            default:
                // reset
                mLastY = -1;
                if (getFirstVisiblePosition() == 0) {
                    // invoke refresh
                    if (mEnablePullRefresh
                            && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                        mPullRefreshing = true;
                        mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
                        if (mListViewListener != null) {
                            mListViewListener.onRefresh();
                        }
                    }
                    resetHeaderHeight();
                }
                if (getLastVisiblePosition() == mTotalItemCount - 1) {
                    // invoke load more.
                    if (mEnablePullLoad
                            && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight(mScroller.getCurrY());
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
    }

    public void setXListViewListener(IXListViewListener l) {
        mListViewListener = l;
    }


    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     */
    public interface OnXScrollListener extends OnScrollListener {
        /**
         * 滚动
         *
         * @param view
         */
        public void onXScrolling(View view);
    }

    /**
     * implements this interface to get refresh/load more event.
     */
    public interface IXListViewListener {
        /**
         * 刷新
         */
        public void onRefresh();

        /**
         * 加载更多
         */
        public void onLoadMore();
    }
}
