package com.yst.onecity.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.yst.onecity.config.Const;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  图片工具类
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ImageCompress {

    private static String filePhthCache = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PuhuiStore/cache/";
    private static File fileFileCache = new File(filePhthCache);

    private static void createFile() {
        if (!fileFileCache.exists()) {
            fileFileCache.mkdirs();
        }
    }

    public static String compressImage(String requestUrl) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        //此时返回bm为空
        Bitmap bitmap = BitmapFactory.decodeFile(requestUrl, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        //这里设置高度为800f
        float hh = 800f;
        //这里设置宽度为480f
        float ww = 480f;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        //be=1表示不缩放
        int be = 1;
        //如果宽度大的话根据宽度固定大小缩放
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
            //如果高度高的话根据宽度固定大小缩放
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) {
            be = 1;
        }
        //设置缩放比例
        newOpts.inSampleSize = be;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(requestUrl, newOpts);
        String compressUrl = "";
        String[] split = requestUrl.split("/");
        String fileName = split[split.length - 1];
        try {
            compressUrl = saveMyBitmap(compressImage(bitmap), fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //压缩好比例大小后再进行质量压缩
        return compressUrl;
    }

    public static List<String> compressImage(List<String> requestUrl) {
        List<String> compressList = new ArrayList<>();
        for (String srcPath : requestUrl) {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            //开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            //此时返回bm为空
            Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
            //这里设置高度为800f
            float hh = 800f;
            //这里设置宽度为480f
            float ww = 400f;
            //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            //be=1表示不缩放
            int be = 1;
            //如果宽度大的话根据宽度固定大小缩放
            if (w > h && w > ww) {
                be = (int) (newOpts.outWidth / ww);
                //如果高度高的话根据宽度固定大小缩放
            } else if (w < h && h > hh) {
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0) {
                be = 1;
            }
            //设置缩放比例
            newOpts.inSampleSize = be;
            //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
            String compressUrl = "";
            String[] split = srcPath.split("/");
            String fileName = split[split.length - 1];
            try {
                compressUrl = saveMyBitmap(compressImage(bitmap), fileName);
                compressList.add(compressUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //压缩好比例大小后再进行质量压缩
        return compressList;
    }

    /**
     * 保存成BitMap
     *
     * @param image
     * @return
     */
    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / Const.INTEGER_1024 > Const.INTEGER_100) {
            //重置baos即清空baos
            baos.reset();
            //这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            //每次都减少20
            options -= 20;
        }
        //把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 保存图片 返回Url
     *
     * @param bmp
     * @param bitName
     * @return
     * @throws IOException
     */
    private static String saveMyBitmap(Bitmap bmp, String bitName) throws IOException {
        File dirFile = new File(filePhthCache);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File f = new File(filePhthCache + bitName);
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f.getAbsolutePath();
    }

}
