package com.yst.onecity.view;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.KeyBoardUtils;
import com.yst.onecity.utils.SoftKeyBoardListener;
import com.yst.onecity.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 评论弹框
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/30
 */

public class CommentPopupWindow extends PopupWindow {
    private Activity mContext;
    private OnLiveSendListener mListener;
    private View view;
    private ContainsEmojiEditText et;

    public CommentPopupWindow(Activity context, OnLiveSendListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
        initPopupWindow();
    }

    private void initPopupWindow() {
        view = View.inflate(mContext, R.layout.dialog_live_comment, null);
        et = (ContainsEmojiEditText) view.findViewById(R.id.et_live_comment);
        TextView txtSend = (TextView) view.findViewById(R.id.txt_live_send);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置弹出动画
        setAnimationStyle(R.style.AnimTop2);
        //使其聚集，要想监听菜单里控件的事件就必须要调用此方法
        setFocusable(true);
        //设置允许在外点击消失
        setOutsideTouchable(true);
        //这个是为了点击“Back”也能使其消失，不会影响背景
        setBackgroundDrawable(new BitmapDrawable());
        //显示在键盘上方
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        txtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = et.getText().toString().trim();
                if (comment.length() <= Const.INTEGER_0) {
                    ToastUtils.show("说点好听的~");
                } else {
                    mListener.send(comment);
                    dismiss();
                }
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                KeyBoardUtils.hintKeyboard(mContext);
                et.setText("");
            }
        });

        SoftKeyBoardListener.setListener(mContext, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
            }

            @Override
            public void keyBoardHide(int height) {
                dismiss();
            }
        });
    }

    /**
     * 外部调用显示
     */
    public void showPop() {
        if (view == null) {
            ToastUtils.show("弹框创建失败");
        } else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    KeyBoardUtils.openKeybord(et, mContext);
                }
            }, 200);
            //显示并设置位置
            showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * 发送监听
     */
    public interface OnLiveSendListener {
        /**
         * 发送
         *
         * @param comment 输入的内容
         */
        void send(String comment);
    }
}
