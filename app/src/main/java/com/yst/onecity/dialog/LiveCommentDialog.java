package com.yst.onecity.dialog;

import android.text.InputFilter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.view.ContainsEmojiEditText;

import butterknife.BindView;

/**
 * 直播详情页面评论框
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/15
 */

public class LiveCommentDialog extends AbstractBaseBottomDialog implements TextView.OnEditorActionListener {
    @BindView(R.id.et_live_comment)
    ContainsEmojiEditText mEtLiveComment;
    @BindView(R.id.txt_live_send)
    TextView mTxtLiveSend;
    OnLiveSendListener mListener;

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_live_comment;
    }

    @Override
    public void bindView(View v) {
        mEtLiveComment.setOnEditorActionListener(this);
        mEtLiveComment.requestFocus();
        mEtLiveComment.setFilters(new InputFilter[]{new InputFilter.LengthFilter(150)});
        mTxtLiveSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = mEtLiveComment.getText().toString().trim();
                if (TextUtils.isEmpty(comment)){
                    ToastUtils.show("说点好听的~");
                    return;
                }else{
                    mListener.send(comment);
                    dismiss();
                    mEtLiveComment.setText("");
                }
            }
        });
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    /**
     * 评论框初始化
     *
     * @param listener 发送监听
     * @return 弹框对象
     */
    public static LiveCommentDialog create(OnLiveSendListener listener) {
        LiveCommentDialog dialog = new LiveCommentDialog();
        dialog.mListener = listener;
        return dialog;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        String comment = v.getText().toString().replace(" ", "");
        if ("".equals(comment)) {
            ToastUtils.show("说点好听的~");
            return true;
        } else {
            mListener.send(comment);
            dismiss();
            v.setText("");
            return false;
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
