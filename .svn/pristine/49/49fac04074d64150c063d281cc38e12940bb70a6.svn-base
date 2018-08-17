package com.yst.onecity.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yst.onecity.R;

/**
 * 消息弹出框工具类
 *
 * @author WangJingWei
 * @version 4.0.0
 * @date 2018/3/21.
 */
public class CustomAlertDialogUtil {

    /**
     * 中奖规则提醒弹出框
     * @param context
     */
    public static void remindTicketsRule(Context context) {
        final android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(context).create();
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_remind_tickets_rule, null);
        window.setContentView(view);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.height = WindowUtils.getScreenHeight((Activity) context) * 5 /6 - WindowUtils.dip2px(context,20);
        lp.width = WindowUtils.getScreenWidth((Activity) context) - WindowUtils.dip2px(context,48);
        window.setAttributes(lp);

        ImageView imgCancel = (ImageView) view.findViewById(R.id.img_cancel);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
    }

}
