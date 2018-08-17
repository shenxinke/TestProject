package com.yst.onecity.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;

/**
 * 退出登录
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractLogoutDialog {
    private Context context;
    protected Dialog dialog;
    private TextView title;
    private TextView content;
    private TextView sure;
    private View dialogLine;
    private TextView cancle;


    public AbstractLogoutDialog(Activity activity) {
        if (dialog == null) {
            this.context = activity;
            getDialog(activity);
        }
    }

    private Dialog getDialog(Activity activity) {
        View view = null;
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setCanceledOnTouchOutside(false);
        view = activity.getLayoutInflater().inflate(R.layout.logout_dialog, null);
        dialog.setContentView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        title = (TextView) view.findViewById(R.id.title);
        content = (TextView) view.findViewById(R.id.content);
        sure = (TextView) view.findViewById(R.id.sure);
        dialogLine = (View) view.findViewById(R.id.dialog_line);
        cancle = (TextView) view.findViewById(R.id.cancle);
        sure.setEnabled(true);

        Window dialogWindow = dialog.getWindow();
        android.view.WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
 ///    lp.x = 266; // 新位置X坐标
///     lp.y = Utils.dip2px(activity, 300);
        dialogWindow.setAttributes(lp);

        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                onSureClick();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onCancelClick();
            }
        });
        return dialog;
    }

    /**
     * 确定点击事件
     */
    public abstract void onSureClick();

    /**
     * 取消点击事件
     */
    public abstract void onCancelClick();

    public void setText(String s1, String s2) {
        content.setText(s2);
    }

    public void setCancleButtonVisibility(int visibility) {
        cancle.setVisibility(visibility);
        dialogLine.setVisibility(visibility);
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
