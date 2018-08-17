package com.yst.onecity.activity.video;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class LandLayoutVideo extends StandardGSYVideoPlayer {


    private boolean isDimissVideoBottomView;

    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    public LandLayoutVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public LandLayoutVideo(Context context) {
        super(context);
    }

    public LandLayoutVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public int getLayoutId() {
        if (mIfCurrentIsFullscreen) {
            return R.layout.sample_video_land;
        } else {
            return R.layout.sample_video_normal;
        }
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
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_land_again);
            if (mStartButton instanceof ImageView) {
                ImageView imageView = (ImageView) mStartButton;
                if (mCurrentState == CURRENT_STATE_PLAYING) {
                    linearLayout.setVisibility(GONE);
                    imageView.setImageResource(R.drawable.video_click_pause_selector_c);
                } else if (mCurrentState == CURRENT_STATE_AUTO_COMPLETE) {
                    linearLayout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickStartIcon();
                            linearLayout.setVisibility(GONE);
                        }
                    });
                    imageView.setImageResource(R.drawable.video_click_play_selector_c);
                    linearLayout.setVisibility(VISIBLE);
                } else if (mCurrentState == CURRENT_STATE_ERROR) {
                    linearLayout.setVisibility(GONE);
                    imageView.setImageResource(R.drawable.video_click_play_selector_c);
                } else {
                    linearLayout.setVisibility(GONE);
                    imageView.setImageResource(R.drawable.video_click_play_selector_c);
                }
            }
        } else {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_again);
            if (mStartButton instanceof ENPlayView) {
                ENPlayView enPlayView = (ENPlayView) mStartButton;
                enPlayView.setDuration(500);
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
                    linearLayout.setVisibility(GONE);
                    imageView.setImageResource(R.drawable.video_click_pause_selector_c);
                } else if (mCurrentState == CURRENT_STATE_ERROR) {
                    linearLayout.setVisibility(GONE);
                    imageView.setImageResource(R.drawable.video_click_error_selector);
                } else if (mCurrentState == CURRENT_STATE_AUTO_COMPLETE) {
                    linearLayout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickStartIcon();
                            linearLayout.setVisibility(GONE);
                        }
                    });
                    imageView.setImageResource(R.drawable.video_click_play_selector_c);
                    linearLayout.setVisibility(VISIBLE);
                } else {
                    linearLayout.setVisibility(GONE);
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
