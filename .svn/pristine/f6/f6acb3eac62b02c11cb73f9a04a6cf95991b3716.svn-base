package com.yst.onecity.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.utils.WindowUtils;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 发布成功弹框
 *
 * @author jiaofan
 * @version 4.2.0
 * @date 2018/6/4
 */

public abstract class AbstractPublishSuccessDialog {
    protected Dialog dialog;
    private TextView title;
    private Button sure;

    protected AbstractPublishSuccessDialog(Activity activity) {
        if (dialog == null) {
            getDialog(activity);
        }
    }

    private Dialog getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_success, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view, new LinearLayout.LayoutParams
                (WindowUtils.dip2px(activity, 230), WRAP_CONTENT));
        title = (TextView) view.findViewById(R.id.title);
        sure = (Button) view.findViewById(R.id.sure);

        Window dialogWindow = dialog.getWindow();
        android.view.WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sureClick();
            }
        });
        return dialog;
    }

    /**
     * 确定按钮
     */
    public abstract void sureClick();

    /**
     * 设置弹出框中的文字
     *
     * @param s1 标题
     * @param s2 确认
     */
    public void setText(String s1, String s2) {
        title.setText(s1);
        sure.setText(s2);
    }

    public void showDialog() {
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
