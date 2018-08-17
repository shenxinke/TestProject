package com.yst.onecity.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.utils.ConstUtils;

/**
 * 发布资讯 添加资讯内容
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/9/21.
 */
public abstract class AbstractCameraDialog {
    protected Dialog dialog;
    private TextView tvCamera;
    private TextView tvPhoto;
    private TextView cancle;

    public AbstractCameraDialog(Activity activity) {
        if (dialog == null) {
            getDialog(activity);
        }
    }

    @SuppressWarnings("deprecation")
    protected Dialog getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.camera_dialog, null);
        //默认样式
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ConstUtils.dip2px(activity,300), ConstUtils.dip2px(activity,140)));

        tvCamera = (TextView) view.findViewById(R.id.tv_camera);
        tvPhoto = (TextView) view.findViewById(R.id.tv_photo);
        cancle = (TextView) view.findViewById(R.id.tv_cancle);

        Window dialogWindow = dialog.getWindow();
        android.view.WindowManager.LayoutParams lp = dialogWindow
                .getAttributes();
        dialogWindow.setGravity(Gravity.TOP);
        // lp.x = 266; // 新位置X坐标
        lp.y = ConstUtils.dip2px(activity, 180);
        dialogWindow.setAttributes(lp);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);

        tvCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onCameraClick();
            }
        });
        tvPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onPhotoClick();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

    /**
     * 拍照按钮点击事件
     */
    public abstract void onCameraClick();

    /**
     * 图片按钮点击事件
     */
    public abstract void onPhotoClick();
//	public abstract void onCancleClick();

    public void setText(String s1,String s2){
        tvCamera.setText(s1);
        tvPhoto.setText(s2);
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
