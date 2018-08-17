package com.yst.onecity.activity.member;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RelativeLayout;

import com.yst.onecity.R;
import com.yst.onecity.base.BaseActivity;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.PhotoUtil;
import com.yst.onecity.utils.ToastUtils;
import com.yst.onecity.view.clipview.ClipImageLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 图片裁剪页面
 *
 * @author chenjiadi
 * @version 3.2.1
 * @date 2017/12/18
 */
public class ClipImageActivity extends BaseActivity {

    @BindView(R.id.id_clipImageLayout)
    ClipImageLayout idClipImageLayout;

    /***
     * @param imagePath   将要被裁剪的图片路径
     * @param widthRatio  裁剪框宽度比
     * @param heightRatio 裁剪框高度比
     * @param requestCode 请求码
     */
    public static void navClipImageActivity(Activity activity, String imagePath, int widthRatio, int heightRatio, int requestCode) {
        Intent intent = new Intent(activity, ClipImageActivity.class);
        intent.putExtra("widthRatio", widthRatio);
        intent.putExtra("heightRatio", heightRatio);
        intent.putExtra("imagePath", imagePath);
        activity.startActivityForResult(intent, requestCode);
    }


    @OnClick(R.id.cj)
    public void clipImage() {
        try {
            //裁剪
            Bitmap bitmap = idClipImageLayout.clip();
            //保存裁剪后的图片
            String savedPath = PhotoUtil.savePngAfter(bitmap, System.currentTimeMillis() + ".png");
            if (savedPath == null) {
                ToastUtils.show("图片保存失败");
            } else {
                setResult(RESULT_OK, new Intent().putExtra("savedPath", savedPath));
            }
            finish();
        } catch (Exception e) {
            ToastUtils.show("图片裁剪失败");
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_img_clip;
    }

    @Override
    public void initData() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) idClipImageLayout.getLayoutParams();
        params.width = ConstUtils.getScreenWidth(this);
        params.height = params.width * getIntent().getIntExtra("heightRatio", 1) / getIntent().getIntExtra("widthRatio", 1);
        idClipImageLayout.setLayoutParams(params);
        idClipImageLayout.setClipSize(params.width, params.height);
        idClipImageLayout.setClipImage(PhotoUtil.compressImageBitmap(getIntent().getStringExtra("imagePath"), params.width, params.height));
    }
}
