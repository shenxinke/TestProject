package com.yst.onecity.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;

/**
 * 红包
 *
 * @author Chenxiaowei
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class RedPackgetDialog {
    private Context context;
    private Dialog cartDialog;
    private ImageView imgRedPacket;
    private TextView tvCancle;
    private TextView money;
    private LinearLayout llRedPacket;

    public RedPackgetDialog(Activity context, RedPackageCallBack mRedPackageCallBack) {
        this.context = context;
        this.mRedPackageCallBack = mRedPackageCallBack;
        initDialog(context);
        initView();
    }

    public interface RedPackageCallBack {
        /**
         * 打开
         */
        void toOpen();

        /**
         * 取消
         */
        void toCancle();
    }

    private RedPackageCallBack mRedPackageCallBack;

    public void setmRedPackageCallBack(RedPackageCallBack mRedPackageCallBack) {
        this.mRedPackageCallBack = mRedPackageCallBack;
    }

    private void initDialog(final Activity activity) {
        if (cartDialog == null) {
            cartDialog = new Dialog(context, R.style.hongbao_dialog_style);
            cartDialog.setCancelable(true);
            cartDialog.setCanceledOnTouchOutside(true);
            cartDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            cartDialog.setContentView(R.layout.dialog_red_packet);
            cartDialog.setCanceledOnTouchOutside(false);
            Window window = cartDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            // 添加动画
            window.setWindowAnimations(R.style.dialogWindowAnim);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }

        cartDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog,
                                 int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    activity.finish();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void initView() {
        llRedPacket = (LinearLayout) cartDialog.findViewById(R.id.ll_red_packet);
        imgRedPacket = (ImageView) cartDialog.findViewById(R.id.img_red_packet);
        money = (TextView) cartDialog.findViewById(R.id.tv_money);
        imgRedPacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRedPackageCallBack != null) {
                    mRedPackageCallBack.toOpen();
                }
            }
        });

        tvCancle = (TextView) cartDialog.findViewById(R.id.tv_cancle);
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRedPackageCallBack != null) {
                    mRedPackageCallBack.toCancle();
                }
            }
        });
    }

    public void showCartDialog() {
        cartDialog.show();
    }

    public void dismissCartDialog() {
        if (cartDialog.isShowing()) {
            cartDialog.dismiss();
        }
    }

    public void setMoney(String m) {
        llRedPacket.setVisibility(View.VISIBLE);
        money.setText(m);
    }

    public void setImageStyle(int imageResourse) {
        imgRedPacket.setImageResource(imageResourse);
    }

    public void setEnable(boolean enable) {
        imgRedPacket.setEnabled(enable);
    }

    public void setCancleText(String s){
        tvCancle.setText(s);
    }
}
