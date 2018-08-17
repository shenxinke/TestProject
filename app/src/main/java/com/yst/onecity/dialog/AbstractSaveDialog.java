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
 * 红包
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractSaveDialog {
    protected Dialog dialog;
    private TextView title;
    private TextView sure;
    private TextView cancle;

    public AbstractSaveDialog(Activity activity) {
        if (dialog == null) {
            getDialog(activity);
        }
    }

    private Dialog getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.delete_dialog, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view, new LinearLayout.LayoutParams
                (ConstUtils.dip2px(activity, 300), ConstUtils.dip2px(activity, 136)));
        title = (TextView) view.findViewById(R.id.title);
        sure = (TextView) view.findViewById(R.id.sure);
        cancle = (TextView) view.findViewById(R.id.cancle);

        Window dialogWindow = dialog.getWindow();
        android.view.WindowManager.LayoutParams lp = dialogWindow
                .getAttributes();
        dialogWindow.setGravity(Gravity.TOP);
        // lp.x = 266; // 新位置X坐标
        lp.y = ConstUtils.dip2px(activity, 180);
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
                onCancleClick();
            }
        });
        return dialog;
    }

    /**
     * 确定按钮点击事件
     */
    public abstract void onSureClick();

    /**
     * 关闭按钮点击事件
     */
    public abstract void onCancleClick();

    public void setText(String s1) {
        title.setText(s1);
//        sure.setText(s2);
//        cancle.setText(s3);
    }

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
        dialog.setCancelable(false);
    }

    public void dismissDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
