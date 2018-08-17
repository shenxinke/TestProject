package com.yst.onecity.utils;

import android.media.MediaPlayer;
import android.net.Uri;

import com.yst.onecity.TianyiApplication;

import java.io.FileInputStream;
import java.io.IOException;

/**
 *  媒体播放工具
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class MediaUtil {

    private static final String TAG = "MediaUtil";

    private MediaPlayer player;
    private EventListener eventListener;

    private MediaUtil(){
        player = new MediaPlayer();
    }

    private static MediaUtil instance = new MediaUtil();

    public static MediaUtil getInstance(){
        return instance;
    }

    public MediaPlayer getPlayer() {
        return player;
    }


    public void setEventListener(final EventListener eventListener) {
        if (player != null){
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    eventListener.onStop();
                }
            });
        }
        this.eventListener = eventListener;
    }

    public void play(FileInputStream inputStream){
        try{
            if (eventListener != null){
                eventListener.onStop();
            }
            player.reset();
            player.setDataSource(inputStream.getFD());
            player.prepare();
            player.start();
        }catch (IOException e){
            MyLog.e(TAG, "play error:" + e);
        }


    }


    public void stop(){
        if (player != null && player.isPlaying()){
            player.stop();
        }
    }

    public long getDuration(String path){
        player = MediaPlayer.create(TianyiApplication.getContext(), Uri.parse(path));
        return player.getDuration();
    }


    /**
     * 播放器事件监听
     */
    public interface EventListener{
        /**
         * 停止监听
         */
        void onStop();
    }
}
