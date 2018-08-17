package com.yst.onecity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.view.ContainsEmojiEditText;

/**
 * 红包
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ReplyDialog extends Dialog {
    private Context context;
    private String name;
    private int consultId;

    public ReplyDialog(@NonNull Context context, String name,int consultId) {
        super(context);
        this.context = context;
        this.name = name;
        this.consultId = consultId;
        this.setCancelable(true);
        this.show();
    }

    public ReplyDialog(Context context, int theme, String name,int consultId) {
        super(context, theme);
        this.context = context;
        this.name = name;
        this.consultId = consultId;
        this.setCancelable(true);
        this.show();
    }
    @Override
    public void show() {
        super.show();
        Window window = this.getWindow();
        window.setContentView(R.layout.dialog_reply);
///        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_solid_white));
        final TextView tvContent, tvReply ,wordNum;
        final ContainsEmojiEditText etReplyContent;
        tvContent = (TextView) window.findViewById(R.id.tv_reply_someone);
        tvReply = (TextView) window.findViewById(R.id.btn_replay);
        wordNum = (TextView) window.findViewById(R.id.tv_word_num);
        etReplyContent = (ContainsEmojiEditText) window.findViewById(R.id.et_reply_content);
        etReplyContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(60)});
        etReplyContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                wordNum.setText(etReplyContent.getText().length()+"/60字");
            }
        });
        tvContent.setText(String.format("回复:%1$s",name));

        tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReplyListener != null) {
                    onReplyListener.reply(String.valueOf(consultId),etReplyContent.getText().toString().trim());
                }
                dismiss();
            }
        });
    }

    private OnReplyListener onReplyListener;

    public void setReplyListener(OnReplyListener onReplyListener) {
        this.onReplyListener = onReplyListener;
    }
    public interface OnReplyListener {
        /**
         * 回复监听
         * @param consultId
         * @param content
         */
        void reply(String consultId,String content);
    }

}
