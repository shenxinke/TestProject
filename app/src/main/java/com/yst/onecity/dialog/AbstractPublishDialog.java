package com.yst.onecity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.onecity.R;

/**
 * 发布文章的弹框
 *
 * @author wuxiaofang
 * @version 4.2.0
 * @date 2018/6/2
 */

public abstract class AbstractPublishDialog {
    private Context context;
    private ImageView ivDiss;
    private TextView tvPublish;
    private Dialog dialog;

    public AbstractPublishDialog(Context context) {
        this.context = context;
        if (null == dialog) {
            setDialog(context);
        }
    }

    /**
     * 初始化弹框
     *
     * @param context 上下文
     */
    public void setDialog(Context context) {
        dialog = new Dialog(context, R.style.prompt_progress_dailog_dimenabled);
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_publish, null);
        dialog.setContentView(inflate);
        ivDiss = (ImageView) inflate.findViewById(R.id.iv_dissmiss);
        tvPublish = (TextView) inflate.findViewById(R.id.tv_publish);
        Window window = dialog.getWindow();
        if (null != window) {
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams attributes = window.getAttributes();
            window.setAttributes(attributes);
        }
        tvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPublish();
            }
        });
        ivDiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != dialog) {
                    dialog.dismiss();
                }
            }
        });
    }

    /**
     * 发布的方法
     */
    public abstract void onPublish();

    /**
     * 弹框消失
     */
    public void diaLogDiss() {
        if (null != dialog) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    /**
     * 显现弹框
     */
    public void show() {
        if (null != dialog) {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
    }
}
