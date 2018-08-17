package com.yst.onecity.activity.chat;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tencent.TIMManager;
import com.tencent.TIMUserStatusListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;

import java.io.IOException;

/**
 * 聊天的小视频显示页面
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/07/06
 */
public class VideoActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    private SurfaceView videoSurface;
    MediaPlayer player;
    /**登出时弹出取消对话框*/
    private PopupWindow mCancelPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);
        TianyiApplication.instance.addActivity(VideoActivity.this);
        videoSurface = (SurfaceView) findViewById(R.id.video);
        SurfaceHolder videoHolder = videoSurface.getHolder();
        videoHolder.addCallback(this);
        player = new MediaPlayer();
        player.setOnPreparedListener(this);
        player.setLooping(true);
        try {
            player.setDataSource(getIntent().getStringExtra("path"));
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
        TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                //被踢下线
                showCancelDialog();
            }

            @Override
            public void onUserSigExpired() {
                //票据过期，需要换票后重新登录
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        if (player!=null){
            player.release();
        }
    }

    /**
     * This is called immediately after the surface is first created.
     * Implementations of this should start up whatever rendering code
     * they desire.  Note that only one thread can ever draw into
     * a {@link Surface}, so you should not draw into the Surface here
     * if your normal rendering will be in another thread.
     *
     * @param holder The SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player.setDisplay(holder);
        player.prepareAsync();
    }

    /**
     * This is called immediately after any structural changes (format or
     * size) have been made to the surface.  You should at this point update
     * the imagery in the surface.  This method is always called at least
     * once, after {@link #surfaceCreated}.
     *
     * @param holder The SurfaceHolder whose surface has changed.
     * @param format The new PixelFormat of the surface.
     * @param width  The new width of the surface.
     * @param height The new height of the surface.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * This is called immediately before a surface is being destroyed. After
     * returning from this call, you should no longer try to access this
     * surface.  If you have a rendering thread that directly accesses
     * the surface, you must ensure that thread is no longer touching the
     * Surface before returning from this function.
     *
     * @param holder The SurfaceHolder whose surface is being destroyed.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /**
     * Called when the media file is ready for playback.
     *
     * @param mp the MediaPlayer that is ready for playback
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        player.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return false;
    }

    /**
     * 退出提示框
     */
    private void showCancelDialog() {
        final View mCancelView = getLayoutInflater().inflate(R.layout.activity_logout_alert_view, null);
        mCancelPopWindow = new PopupWindow(mCancelView, RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT, true);
        TextView mCancelTv = (TextView) mCancelView.findViewById(R.id.activity_logout_cancel_tv);
        TextView mLogoutTv = (TextView) mCancelView.findViewById(R.id.activity_logout_tv);
        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleMyPopWindow();
            }
        });

        // 退出确定按钮
        mLogoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleMyPopWindow();
            }
        });
        mCancelPopWindow.setAnimationStyle(R.style.AnimTop2);
        mCancelPopWindow.update();
        // 设置可点击
        mCancelPopWindow.setTouchable(true);
        // 设置popupwindow外部可点击
        mCancelPopWindow.setOutsideTouchable(true);
        // 如果有edittext，阻挡输入法遮挡
        mCancelPopWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mCancelPopWindow.showAtLocation(mCancelView, Gravity.CENTER, 0, 0);
        mCancelPopWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mCancelPopWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 关闭弹框提示，并跳转到登录页面
     */
    private void cancleMyPopWindow() {
        if (mCancelPopWindow != null && mCancelPopWindow.isShowing()) {
            mCancelPopWindow.dismiss();
        }
//        App.destroyActivity();
//        App.mAppCommonManager.quitLogin();
//        TIMManager.getInstance().logout();
//        Intent intent = new Intent(ConstantListActivity.ChangePage);
//        LocalBroadcastManager.getInstance(VideoActivity.this).sendBroadcast(intent);
//        startActivity(new Intent(VideoActivity.this, SubPageActivity.class).putExtra(SubPageActivity.CLASSFRAMENT, LoginPasswordFragment.class));
    }
}