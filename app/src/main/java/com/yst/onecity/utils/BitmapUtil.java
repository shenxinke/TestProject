package com.yst.onecity.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.yst.onecity.config.Const;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static android.graphics.Color.BLACK;

/**
 *  bitmap图片处理工具类
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class BitmapUtil {
    /**
     * 以最省内存的方式读取本地资源的图片
     */
    public static Bitmap readBitmap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }

    /**
     * 省内存方式从本地读取图片 ，避免OOM,并且压缩。
     */
    public static Bitmap readBitmapScale(Context context, int resId) {

        InputStream is = context.getResources().openRawResource(resId);
        ByteArrayOutputStream baos = null;

        byte[] buffer = new byte[1024 * 8];
        int len = 0;
        try {
            baos = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
                baos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        // 不返回实际bitmap。不分配内存空间 但是可以得到一些图片信息
        options.inJustDecodeBounds = true;

        double size = baos.toByteArray().length / 102400.0;
        if (size > 1) {
            options.inSampleSize = (int) size;
        } else {
            options.inSampleSize = 1;
        }

        options.inPreferredConfig = Bitmap.Config.RGB_565;
        // 让系统能及时回收内存
        options.inPurgeable = true;
        // 让系统能及时回收内存
        options.inInputShareable = true;

        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);

        return bitmap;
    }

    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     */
    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        //optional
        onlyBoundsOptions.inDither = true;
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1)) {
            return null;
        }
        //图片分辨率以480x800为标准
        //这里设置高度为800f
        float hh = 800f;
        //这里设置宽度为480f
        float ww = 480f;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        //be=1表示不缩放
        int be = 1;
        //如果宽度大的话根据宽度固定大小缩放
        if (originalWidth > originalHeight && originalWidth > ww) {
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {
            //如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0) {
            be = 1;
        }
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        //设置缩放比例
        bitmapOptions.inSampleSize = be;
        //optional
        bitmapOptions.inDither = true;
        //optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        //再进行质量压缩
        return compressImage(bitmap);
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / Const.INTEGER_1024 > Const.INTEGER_100) {
            //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            //这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            //每次都减少10
            options -= 10;
        }
        //把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        //把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 根据传进来的文件路径 创建一个新的压缩处理过的临时文件并返回(用于返回上传服务器的临时图片文件)
     *
     * @param path
     * @return
     */
    private static String LOCAL_TEMPFILE_HEAD = "JQ_LOCAL_jpeg_";
    public static File scal(String path) {
        //Uri fileUri
        File outputFile = new File(path);
        long fileSize = outputFile.length();
        //创建的临时文件大小暂时定上限200K
        final long fileMaxSize = 200 * 1024;
        if (fileSize >= fileMaxSize) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int height = options.outHeight;
            int width = options.outWidth;
            double scale = Math.sqrt((float) fileSize / fileMaxSize);
            options.outHeight = (int) (height / scale);
            options.outWidth = (int) (width / scale);
            options.inSampleSize = (int) (scale + 0.5);
            options.inJustDecodeBounds = false;

            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            outputFile = new File(BitmapUtil.createImageFile().getPath());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos);
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.i("BitmapUtils", "compress_OK" + outputFile.length());
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            } else {
                File tempFile = outputFile;
                outputFile = new File(BitmapUtil.createImageFile().getPath());
                BitmapUtil.copyFileUsingFileChannels(tempFile, outputFile);
            }
        }
        return outputFile;
    }

    public static Uri createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = LOCAL_TEMPFILE_HEAD + timeStamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            if (!storageDir.isDirectory()) {
                File mTempstorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                image = File.createTempFile(imageFileName, ".jpg", mTempstorageDir);
            } else {
                image = File.createTempFile(imageFileName, ".jpg", storageDir);
            }

//			image = File.createTempFile(
//					imageFileName,  /* prefix */
//					.jpg,         /* suffix */
//					storageDir      /* directory */
//			);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        return Uri.fromFile(image);
    }

    public static void copyFileUsingFileChannels(File source, File dest) {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            try {
                inputChannel = new FileInputStream(source).getChannel();
                outputChannel = new FileOutputStream(dest).getChannel();
                outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            try {
                inputChannel.close();
                outputChannel.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    /**
     * 生成二维码图片（不带图片）
     * @param url
     * @param widthAndHeight
     * @return
     * @throws WriterException
     */
    public static Bitmap createQRCode(String url, int widthAndHeight)
            throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix matrix = new MultiFormatWriter().encode("lvu",
                BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);

        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        //画黑点
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    //0xff000000
                    pixels[y * width + x] = BLACK;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 带图片的二维码
     * @param content
     * @param heightPix
     * @param logoBm
     * @return
     */
    public static Bitmap createQRImage(String content, int heightPix, Bitmap logoBm) {
        try {
            //配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>(16);
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, heightPix, heightPix, hints);
            int[] pixels = new int[heightPix * heightPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < heightPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * heightPix + x] = 0xff000000;
                    } else {
                        pixels[y * heightPix + x] = 0xffffffff;
                    }
                }
            }

            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(heightPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, heightPix, 0, 0, heightPix, heightPix);

            if (logoBm != null) {
                bitmap = addLogo(bitmap, logoBm);
            }

            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 在二维码中间添加Logo图案
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }

        if (logo == null) {
            return src;
        }

        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }

}
