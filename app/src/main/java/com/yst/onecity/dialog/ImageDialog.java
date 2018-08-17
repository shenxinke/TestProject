package com.yst.onecity.dialog;

import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.yst.onecity.R;

import butterknife.BindView;

/**
 * 添加图片弹框
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/6/5
 */
public class ImageDialog extends AbstractBaseBottomDialog {
    @BindView(R.id.dialog_add_pic)
    RelativeLayout mDialogAddPic;
    private OnAddListener mListener;

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_img;
    }

    @Override
    public void bindView(View v) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mDialogAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addImg();
            }
        });
    }

    /**
     * 对象创建
     *
     * @param listener 点击监听
     */
    public static ImageDialog create(OnAddListener listener) {
        ImageDialog dialog = new ImageDialog();
        dialog.mListener = listener;
        return dialog;
    }

    public interface OnAddListener {

        /**
         * 添加图片
         */
        void addImg();
    }
}