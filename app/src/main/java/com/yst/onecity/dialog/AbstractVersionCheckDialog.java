package com.yst.onecity.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.utils.ConstUtils;

/**
 * 版本更新弹框提示
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractVersionCheckDialog {
    protected Dialog dialog;
    private TextView title;
    private TextView sure;
    private TextView cancle;
    private Activity context;
    private boolean flag;
    public AbstractVersionCheckDialog(Activity activity, boolean flag) {
        this.context=activity;
        this.flag=flag;
        if (dialog == null) {
            getDialog(activity);
        }
    }
    private Dialog getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_verson_update, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        if (flag){
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
        dialog.setContentView(view, new LinearLayout.LayoutParams
                (ConstUtils.dip2px(activity, 300), ConstUtils.dip2px(activity, 200)));
        title = (TextView) view.findViewById(R.id.txt_msg);
        sure = (TextView) view.findViewById(R.id.txt_dialog_ok);
        cancle = (TextView) view.findViewById(R.id.txt_dialog_cancle);
        title.setText("版本更新");
        Window dialogWindow = dialog.getWindow();
        android.view.WindowManager.LayoutParams lp = dialogWindow
                .getAttributes();
        // 设置生效
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);

        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
                onSureClick();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {context.finish();}
                dialog.dismiss();
            }
        });
        return dialog;
    }

    /**
     * 确定点击事件
     */
    public abstract void onSureClick();
    /**
     * 设置取消按钮的样式
     * @param resId 背景资源
     * @param textColor 文本颜色
     * @param content 按钮显示的文本内容
     */
    public void setCancleStyle(int resId, int textColor, String content) {
        cancle.setBackgroundResource(resId);
        cancle.setTextColor(textColor);
        cancle.setText(content);
    }
    /**
     * 设置确定按钮的样式
     * @param resId 背景资源
     * @param textColor 文本颜色
     * @param content 按钮显示文本
     */
    public void setSureStyle(int resId, int textColor, String content) {
        sure.setBackgroundResource(resId);
        sure.setTextColor(textColor);
        sure.setText(content);
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
