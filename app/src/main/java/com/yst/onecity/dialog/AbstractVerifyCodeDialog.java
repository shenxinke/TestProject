package com.yst.onecity.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yst.onecity.R;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;

/**
 * 验证码弹出框
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/1/2
 */
public abstract class AbstractVerifyCodeDialog {
    protected Dialog dialog;
    private ImageView ivVerifyCode;
    private EditText etVerifyCode;
    private TextView tvSure;
    private Activity activity;

    public AbstractVerifyCodeDialog(Activity activity) {
        this.activity = activity;
        if (dialog == null) {
            getDialog(activity);
        }
    }

    private Dialog getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.verify_code_dialog, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view, new LinearLayout.LayoutParams
                (ConstUtils.dip2px(activity, 300), ConstUtils.dip2px(activity, 180)));
        ivVerifyCode = (ImageView) view.findViewById(R.id.iv_verify_code);
        etVerifyCode = (EditText) view.findViewById(R.id.et_verify_code);
        tvSure = (TextView) view.findViewById(R.id.tv_sure);

        Window dialogWindow = dialog.getWindow();
        android.view.WindowManager.LayoutParams lp = dialogWindow
                .getAttributes();
        dialogWindow.setGravity(Gravity.TOP);
        lp.y = ConstUtils.dip2px(activity, 180);
        dialogWindow.setAttributes(lp);

        tvSure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String code = etVerifyCode.getText().toString().trim();
                onSureClick(code);
            }
        });
        ivVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flushClick();
            }
        });
        etVerifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 0) {
                    tvSure.setEnabled(false);
                    tvSure.setBackgroundResource(R.drawable.shape_verify_code_sure_gray);
                } else {
                    tvSure.setEnabled(true);
                    tvSure.setBackgroundResource(R.drawable.shape_verify_code_sure);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return dialog;
    }

    /**
     * 刷新图形验证码
     */
    protected abstract void flushClick();


    /**
     * 加载图形验证码
     *
     * @param url 图片地址
     */
    public void setUrl(String url) {
        MyLog.e("register_verify", "setUrl___" + url);
        Glide.with(activity)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivVerifyCode);
    }

    /**
     * 确认
     *
     * @param verifyCode 验证码
     */
    public abstract void onSureClick(String verifyCode);

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
