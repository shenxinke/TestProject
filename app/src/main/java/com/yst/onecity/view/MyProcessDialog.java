package com.yst.onecity.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;

import com.yst.onecity.R;

/**
 * 自定义加载框. <br>
 * 类详细说明.
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/12/18
 */
public class MyProcessDialog extends Dialog implements DialogInterface.OnShowListener,DialogInterface.OnDismissListener{

    private ImageView animImg;
    /**
     * 帧动画对象
     */
    AnimationDrawable anim;

    /**
     * @param context
     */
    public MyProcessDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
        setCancelable(false);
        initUI();
    }

    /**
     * 初始化
     */
    private void initUI() {
        setContentView(R.layout.process_dlg);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        getWindow().getAttributes().gravity = Gravity.CENTER;
        animImg = (ImageView) findViewById(R.id.tv_process);
        animImg.setBackgroundResource(R.drawable.anim_progress);
        anim = (AnimationDrawable) animImg.getBackground();

        setOnShowListener(this);
        setOnDismissListener(this);
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        if (null != anim) {
            anim.stop();
            anim = null;
        }
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        if (null != anim) {
            anim.start();
        }
    }
}
