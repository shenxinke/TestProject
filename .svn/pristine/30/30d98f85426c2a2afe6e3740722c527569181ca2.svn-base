package com.yst.onecity.activity.chat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMManager;
import com.tencent.TIMUserStatusListener;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.utils.FileUtil;
import com.yst.onecity.utils.WindowUtils;

import java.io.IOException;

/**
 * 聊天图片显示页面
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/07/06
 */
public class ImageViewActivity extends Activity {

    /**
     * 登出时弹出取消对话框
     */
    private PopupWindow mCancelPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_view);
        TianyiApplication.instance.addActivity(ImageViewActivity.this);
        RelativeLayout root = (RelativeLayout) findViewById(R.id.root);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String file = getIntent().getStringExtra("filename");
        ImageView imageView = (ImageView) findViewById(R.id.image);
        Bitmap bitmap = getImage(FileUtil.getCacheFilePath(file));
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
        TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                //被踢下线
                showCancelDialog();
            }

            @Override
            public void onUserSigExpired() {
                //票据过期，需要换票后重新登录
            }
        });
    }

    private Bitmap getImage(String path) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int reqWidth, reqHeight, width = options.outWidth, height = options.outHeight;
        if (width > height) {
            reqWidth = WindowUtils.getScreenWidth(this) / 2;
            reqHeight = (reqWidth * height) / width;
        } else {
            reqHeight = WindowUtils.getScreenHeight(this) / 2;
            reqWidth = (width * reqHeight) / height;
        }
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        try {
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;
            Matrix mat = new Matrix();
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            if (bitmap == null) {
                Toast.makeText(this, getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
                return null;
            }
            ExifInterface ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    mat.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    mat.postRotate(180);
                    break;
                default:
                    break;
            }
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 退出提示框
     */
    private void showCancelDialog() {
        final View mCancelView = getLayoutInflater().inflate(R.layout.activity_logout_alert_view, null);
        mCancelPopWindow = new PopupWindow(mCancelView, RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT, true);
        TextView mCancelTv = (TextView) mCancelView.findViewById(R.id.activity_logout_cancel_tv);
        TextView mLogoutTv = (TextView) mCancelView.findViewById(R.id.activity_logout_tv);
        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleMyPopWindow();
            }
        });

        // 退出确定按钮
        mLogoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleMyPopWindow();
            }
        });
        mCancelPopWindow.setAnimationStyle(R.style.AnimTop2);
        mCancelPopWindow.update();
        // 设置可点击
        mCancelPopWindow.setTouchable(true);
        // 设置popupwindow外部可点击
        mCancelPopWindow.setOutsideTouchable(true);
        // 如果有edittext，阻挡输入法遮挡
        mCancelPopWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mCancelPopWindow.showAtLocation(mCancelView, Gravity.CENTER, 0, 0);
        mCancelPopWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mCancelPopWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 关闭弹框提示，并跳转到登录页面
     */
    private void cancleMyPopWindow() {
        if (mCancelPopWindow != null && mCancelPopWindow.isShowing()) {
            mCancelPopWindow.dismiss();
        }
//        TianyiApplication.instance.exit();
//        App.mAppCommonManager.quitLogin();
//        TIMManager.getInstance().logout();
//        Intent intent = new Intent(ConstantListActivity.ChangePage);
//        LocalBroadcastManager.getInstance(ImageViewActivity.this).sendBroadcast(intent);
//        startActivity(new Intent(ImageViewActivity.this, SubPageActivity.class).putExtra(SubPageActivity.CLASSFRAMENT, LoginPasswordFragment.class));
    }
}
