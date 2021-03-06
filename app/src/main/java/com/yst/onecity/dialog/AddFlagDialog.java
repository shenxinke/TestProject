package com.yst.onecity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.config.Const;
import com.yst.onecity.utils.ToastUtils;

/**
 * 增加基本信息标签
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class AddFlagDialog extends Dialog {

    public AddFlagDialog(@NonNull Context context) {
        super(context);
        this.setCancelable(false);
        this.show();
    }

    public AddFlagDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.setCancelable(false);
        this.show();
    }

    @Override
    public void show() {
        super.show();
        Window window = this.getWindow();
        window.setContentView(R.layout.dialog_add_flag);
        window.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.shape_solid_white));
        TextView tvCancle, tvOk;
        final EditText etContent;
        tvCancle = (TextView) window.findViewById(R.id.cancle);
        tvOk = (TextView) window.findViewById(R.id.sure);
        etContent = (EditText) window.findViewById(R.id.et_content);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etContent.getText().toString().trim();
                if (content == null || "".equals(content)) {
                    ToastUtils.show("标签不能为空");
                    return;
                }
                if (content.length() > Integer.parseInt(Const.STR6)) {
                    ToastUtils.show("标签最多能输入6个字");
                    return;
                }
                if (iGetScrollPosition != null) {
                    iGetScrollPosition.add(content);
                }
                dismiss();
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    private IGetScrollPosition iGetScrollPosition;

    public void setIScrollPositon(IGetScrollPosition iGetScrollPosition) {
        this.iGetScrollPosition = iGetScrollPosition;
    }

    public interface IGetScrollPosition {
        /**
         * 新增标签回掉
         * @param content
         */
        void add(String content);

    }
}
