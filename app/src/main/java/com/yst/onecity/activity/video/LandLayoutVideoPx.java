package com.yst.onecity.activity.video;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.yst.onecity.R;
import com.yst.onecity.utils.MyLog;

import moe.codeest.enviews.ENPlayView;

/**
 * CustomGSYVideoPlayer是试验中，建议使用的时候使用StandardGSYVideoPlayer
 *
 * @author Shenxinke
 * @version 4.1.0
 * @data 2018/5/21
 */

public class LandLayoutVideoPx extends StandardGSYVideoPlayer {

    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    public LandLayoutVideoPx(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public LandLayoutVideoPx(Context context) {
        super(context);
    }

    public LandLayoutVideoPx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public int getLayoutId() {
        MyLog.e("sss", "getLayoutId----");
        if (mIfCurrentIsFullscreen) {
            return R.layout.sample_video_land;
        }
        return R.layout.sample_video_normal_px;
    }

    @Override
    public int getShrinkImageRes() {
        return R.mipmap.xiao;
    }

    @Override
    public int getEnlargeImageRes() {
        return R.mipmap.quanping;
    }

    @Override
    protected void updateStartImage() {
        MyLog.e("sss", "updateStartImage----");
        if (mIfCurrentIsFullscreen) {
            if (mStartButton instanceof ImageView) {
                ImageView imageView = (ImageView) mStartButton;

                if (mCurrentState == CURRENT_STATE_PLAYING) {
                    imageView.setImageResource(R.drawable.video_click_pause_selector_c);
                } else if (mCurrentState == CURRENT_STATE_ERROR) {
                    imageView.setImageResource(R.drawable.video_click_play_selector_c);
                } else {
                    imageView.setImageResource(R.drawable.video_click_play_selector_c);
                }
            }
        } else {

                ImageView bottomStart = (ImageView) findViewById(R.id.tab);
                mCurrentTimeTextView.setVisibility(INVISIBLE);
                mTotalTimeTextView.setVisibility(INVISIBLE);
                mProgressBar.setVisibility(INVISIBLE);
                bottomStart.setVisibility(GONE);

            if (mStartButton instanceof ENPlayView) {
                ENPlayView enPlayView = (ENPlayView) mStartButton;
                enPlayView.setDuration(300);
                if (mCurrentState == CURRENT_STATE_PLAYING) {
                    enPlayView.play();
                } else if (mCurrentState == CURRENT_STATE_ERROR) {
                    enPlayView.pause();
                } else {
                    enPlayView.pause();
                }
            } else if (mStartButton instanceof ImageView) {
                ImageView imageView = (ImageView) mStartButton;
                if (mCurrentState == CURRENT_STATE_PLAYING) {
                    imageView.setImageResource(R.drawable.video_click_pause_selector_c);
                } else if (mCurrentState == CURRENT_STATE_ERROR) {
                    imageView.setImageResource(R.drawable.video_click_error_selector);
                } else {
                    imageView.setImageResource(R.drawable.video_click_play_selector_c);
                }
            }
        }
    }

    /**
     * 处理锁屏屏幕触摸逻辑
     */
    @Override
    protected void lockTouchLogic() {
        if (mLockCurScreen) {
            mLockScreen.setImageResource(R.mipmap.weisuo);
            mLockCurScreen = false;
        } else {
            mLockScreen.setImageResource(R.mipmap.suo);
            mLockCurScreen = true;
            hideAllWidget();
        }
    }
}
