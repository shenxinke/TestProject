package com.yst.onecity.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.yst.onecity.R;
import com.yst.onecity.TianyiApplication;
import com.yst.onecity.config.Const;
import com.yst.onecity.config.Constants;
import com.yst.onecity.view.ContainsEmojiEditText;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.nereo.multi_image_selector.MultiImageSelector;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 常用工具类
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class ConstUtils {

    private static long lastClickTime;
    private static final int INDEX1 = 4;
    private static final int INDEX2 = 4;
    private static final String APP_SIGN = "308201dd30820146020101300d06092a864886f70d010105050030373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b3009060355040613025553301e170d3137303732313038323633355a170d3437303731343038323633355a30373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b300906035504061302555330819f300d06092a864886f70d010101050003818d00308189028181008849ee582ccc36bd1032718215e93c52ccf4af83307bce7d3fcd4204e0cbae8769d8612ff09458733983fcf7362ca06548dbd908ac467e47b5f0fed36e8e894b9c0db2e18ace7c6f73295864204cff4b13b8782f49e3adcf29690abd56c6baf13b7033f0dd1857b4b27cd1e8a6d4a05dba62994a8ceee4bdf297b24b49f335ab0203010001300d06092a864886f70d01010505000381810058fb12d07d380e3ab02d0504b75dcd5630688e5932b86d27ac94a4582b52b34bb21646a8677de68bb9112a5a343b9ea5940e241a31a3e97d022d64e0c0405cfcd69b242d376129eacefc3ee05ce4537ff64ffecb066f8b8299f1685f9a9594ab587c79a4e3c9e2bf341023bb488e3e875b4e26eeb1f6f6162511dec1348b09cb";

    /**
     * 按钮防爆点击
     *
     * @return b
     */
    public static boolean isClickable() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < Const.INTEGER_800) {
            return false;
        }
        lastClickTime = time;
        return true;
    }

    /**
     * 设置文本
     */
    public static void setTextString(String string, TextView textView) {
        if (!TextUtils.isEmpty(getStringNoEmpty(string))) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(string);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置文本
     */
    public static void setMoneyTextString(String string, TextView textView) {
        if (!TextUtils.isEmpty(getStringNoEmpty(string))) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("¥" + string);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    /**
     * 对字符串判空
     */
    public static String getStringNoEmpty(String string) {
        if (null == string || string.length() == 0 || Const.CONS_STR_NULL.equals(string)) {
            string = " ";
        }
        return string;
    }

    /**
     * 空string改0
     */
    public static String changeEmptyStringToZero(String string) {
        if (null == string || string.length() == 0 || Const.CONS_STR_NULL.equals(string)) {
            string = "0";
        }
        return string;
    }



    /**
     * 设置Edittext字数限制
     *
     * @param editText
     * @param length
     */
    public static void setEmojiEdittextLength(ContainsEmojiEditText editText, int length) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    public static ViewGroup.LayoutParams get16B9f(int width) {
        FrameLayout.LayoutParams llParam = new FrameLayout.LayoutParams(width, width * 9 / 16);
        return llParam;
    }

    public static LinearLayout.LayoutParams getLineanrLayoutParams16B9f(int width) {
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(width, width * 9 / 16);
        return llParam;
    }

    /**
     * 获得屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 银行卡部分用*显示
     *
     * @param str s
     * @return s
     */
    public static String replaceSubString(String str) {
        if (str == null) {
            return "";
        }
        String subTop;
        String subCenter;
        String subBottom;
        try {
            subTop = str.substring(0, INDEX2);
            subCenter = str.substring(INDEX2, str.length() - INDEX1);
            subBottom = str.substring(str.length() - INDEX1, str.length());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < subCenter.length(); i++) {
                sb = sb.append("*");
            }
            str = "";
            str += subTop;
            str += sb.toString();
            str += subBottom;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 手机号用****号隐藏中间数字
     *
     * @param phone
     * @return
     */
    public static String settingPhone(String phone) {
        String phoneStr = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phoneStr;
    }

    /**
     * 身份证除第一位和最后一位其余显示*
     *
     * @param str s
     * @return s
     */
    public static String showIdNumber(String str) {
        if (str == null) {
            return "";
        }
        String subTop;
        String subCenter;
        String subBottom;
        try {
            subTop = str.substring(0, 1);
            subCenter = str.substring(1, str.length() - 1);
            subBottom = str.substring(str.length() - 1, str.length());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < subCenter.length(); i++) {
                sb = sb.append("*");
            }
            str = "";
            str += subTop;
            str += sb.toString();
            str += subBottom;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 获取版本号
     */
    public static int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            Log.d("Exception", e.getMessage());
        }
        return version;
    }

    /**
     * 开始一个按钮的倒计时
     *
     * @param button                   控件(TextView类型)
     * @param normalTextColorResources 正常字体颜色资源id
     * @param unableTextColorResources 不能点击是字体颜色资源id
     */
    public static void startCountDown(final TextView button, final int normalTextColorResources,
                                      final int unableTextColorResources) {
        final int normalTextColor = ContextCompat.getColor(button.getContext(), normalTextColorResources);
        final int unableTextColor = ContextCompat.getColor(button.getContext(), unableTextColorResources);
        button.setEnabled(false);
        button.setTextColor(unableTextColor);
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                button.setText((millisUntilFinished / 1000) + "s后重新获取");
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setTextColor(normalTextColor);
                button.setText("重新获取");
            }
        }.start();
    }

    /**
     * 开始一个按钮的倒计时
     *
     * @param button                    控件(TextView类型)
     * @param normalBackGroundResources 正常的控件背景资源id
     * @param normalTextColorResources  正常字体颜色资源id
     * @param unableBackGroundResources 不能点击时背景资源id
     * @param unableTextColorResources  不能点击是字体颜色资源id
     */
    public static void startCountDown(final TextView button, final int normalBackGroundResources,
                                      final int normalTextColorResources,
                                      final int unableBackGroundResources, final int unableTextColorResources) {
        final int normalTextColor = ContextCompat.getColor(button.getContext(), normalTextColorResources);
        final int unableTextColor = ContextCompat.getColor(button.getContext(), unableTextColorResources);
        button.setEnabled(false);
        button.setBackgroundResource(unableBackGroundResources);
        button.setTextColor(unableTextColor);
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                button.setText((millisUntilFinished / 1000) + "s后重新获取");
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setBackgroundResource(normalBackGroundResources);
                button.setTextColor(normalTextColor);
                button.setText("重新获取");
            }
        }.start();
    }

    /**
     * 调用系统打电话界面
     *
     * @param context
     * @param phoneNumber
     */
    public static void callDial(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
    }

    /**
     * 检查微信是否存在
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        // 获取PackageManager
        PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                System.out.println(pinfo.get(i).packageName);
                if ("com.tencent.mm".equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 时间戳转换成字符串
     *
     * @param time 时间戳
     * @return 字符串
     */
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    /**
     * 判断是不是手机号
     */
    private static Pattern patterns = Pattern.compile("^((1[3,5,6,7,8,9][0-9])|(14[5,7])|(17[0,3,6,7,8]))\\d{8}$");

    public static boolean isMobileNO(String mobiles) {
        Matcher m = patterns.matcher(mobiles);
        return m.matches();
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 手机屏幕宽度
     */
    public static int getWindowWidth(Context ctx) {
        Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics.widthPixels;
    }

    /**
     * 手机屏幕高度
     */
    public static int getWindowHeight(Context ctx) {

        Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics.heightPixels;
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * scrollview自动滑动底部问题
     */
    public static void disableAutoScrollToBottom(ScrollView scrollView) {
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
    }

    /**
     * 获取手机ip地址
     */
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            //当前使用2G/3G/4G网络
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                try {
                    ///Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                //当前使用无线网络
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                //得到IPV4地址
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    public static String getVerCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "no";
        }
    }

    /**
     * 获取应用签名
     *
     * @return
     */
    public static String getSignature() {
        Context ctx = TianyiApplication.getContext();
        try {
            /** 通过包管理器获得指定包名包含签名的包信息 **/
            PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            /******* 通过返回的包信息获得签名数组 *******/
            Signature[] signatures = packageInfo.signatures;
            /******* 循环遍历签名数组拼接应用签名 *******/
            StringBuilder builder = new StringBuilder();
            for (Signature signature : signatures) {
                builder.append(signature.toCharsString());
            }
            /************** 得到应用签名 **************/
            return builder.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 反调试判断是否二次打包
     *
     * @return
     */
    public static boolean isOwnApp() {
        String signStr = getSignature();
        return APP_SIGN.equals(signStr);
    }

    /**
     * 设置余额明细类型文本显示
     * type 类型
     */
    public static void setTypeText(int type, int status, TextView textView) {

        switch (type) {
            case 0:
                setTextString("余额支付", textView);
                break;
            case 1:
                if (status == Const.INTEGER_0) {
                    setTextString("提现处理中", textView);
                } else if (status == Const.INTEGER_1) {
                    setTextString("提现成功", textView);
                } else {
                    setTextString("提现失败", textView);
                }

                break;
            case 2:
                setTextString("充值", textView);
                break;
            case 3:
                setTextString("结算", textView);
                break;
            case 4:
                setTextString("余额撤销", textView);
                break;
            case 5:
                setTextString("花呗支付", textView);
                break;
            case 6:
                setTextString("红包", textView);
                break;
            case 7:
                setTextString("花呗还款", textView);
                break;
            case 8:
                setTextString("花呗撤销", textView);
                break;
            case 9:
                setTextString("奖券 " + Html.fromHtml("<font color=\"#FF8A00\"> " + "[一等奖]" + "</font>"), textView);
                break;
            case 10:
                setTextString("奖券 " + Html.fromHtml("<font color=\"#FF8A00\"> " + "[二等奖]" + "</font>"), textView);
                break;
            case 11:
                setTextString("奖券 " + Html.fromHtml("<font color=\"#FF8A00\"> " + "[三等奖]" + "</font>"), textView);
                break;
            case 12:
                setTextString("奖券 " + Html.fromHtml("<font color=\"#FF8A00\"> " + "[四等奖]" + "</font>"), textView);
                break;
            case 13:
                setTextString("奖券 " + Html.fromHtml("<font color=\"#FF8A00\"> " + "[五等奖]" + "</font>"), textView);
                break;
            case 14:
                setTextString("奖券 " + Html.fromHtml("<font color=\"#FF8A00\"> " + "[六等奖]" + "</font>"), textView);
                break;
            case 15:
                setTextString("奖券 " + Html.fromHtml("<font color=\"#FF8A00\"> " + "[七等奖]" + "</font>"), textView);
                break;
            case 16:
                setTextString("奖券 " + Html.fromHtml("<font color=\"#FF8A00\"> " + "[八等奖]" + "</font>"), textView);
                break;
            case 17:
                setTextString("新业态补贴金额", textView);
                break;
            case 18:
                setTextString("新业态余额消费", textView);
                break;
            case 30:
                setTextString("绑卡手续费", textView);
                break;
            case 31:
                setTextString("分享奖励", textView);
                break;
            case 32:
                setTextString("创作奖励", textView);
                break;
            case 33:
                setTextString("旗舰店专员佣金", textView);
                break;
            case 34:
                setTextString("卫星店专员佣金", textView);
                break;
            default:
                break;
        }
    }

    /**
     * 设置余额明细类型图片显示
     * type 类型
     */
    public static void setTypeImage(int type, ImageView imageView) {
        switch (type) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 17:
            case 18:
            case 31:
            case 32:
            case 33:
            case 34:
                imageView.setImageResource(R.drawable.account_zhifu);
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                imageView.setImageResource(R.drawable.account_tickets);
                break;
            default:
                break;
        }
    }

    /**
     * 设置余额明细运算符号及颜色显示
     * type 类型
     * status  0申请中 1成功  2失败
     */
    public static void setTypeSymbol(int type, int status, String content, TextView textView, Context context) {
        switch (type) {
            case 1:
                if (status == Const.INTEGER_2) {
                    textView.setTextColor(ContextCompat.getColor(context, R.color.color_FF8A00));
                    setTextString("+" + content, textView);
                } else {
                        textView.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
                    setTextString("-" + content, textView);
                }
                break;
            case 0:
            case 5:
            case 7:
            case 8:
            case 18:
            case 30:
                textView.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
                setTextString("-" + content, textView);
                break;
            case 2:
            case 3:
            case 4:
            case 6:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 31:
            case 32:
            case 33:
            case 34:
                textView.setTextColor(ContextCompat.getColor(context, R.color.color_FF8A00));
                setTextString("+" + content, textView);
                break;
            default:
                break;
        }
    }

    /**
     * 设置余额明细类型图片显示
     * type 类型
     */
    public static String changeAwardsContent(int type) {
        String content = "";
        switch (type) {
            case 1:
                content = "一等奖";
                break;
            case 2:
                content = "二等奖";
                break;
            case 3:
                content = "三等奖";
                break;
            case 4:
                content = "四等奖";
                break;
            case 5:
                content = "五等奖";
                break;
            case 6:
                content = "六等奖";
                break;
            case 7:
                content = "七等奖";
                break;
            case 8:
                content = "八等奖";
                break;
            default:
                break;
        }
        return content;
    }

    /**
     * 判断服务器返回的图片路径是否包含阿里云图片前缀
     *
     * @param url
     * @return
     */
    public static String matchingImageUrl(String url) {
        if (ConstUtils.getStringNoEmpty(url).contains(Constants.URL_IMAGE_HEAD) || ConstUtils.getStringNoEmpty(url).contains(Const.CONS_STR_HTTP) || ConstUtils.getStringNoEmpty(url).contains(Const.CONS_STR_HTTPS)) {
            return url;
        } else {
            return Constants.URL_IMAGE_HEAD + url;
        }
    }

    /**
     * 获取当前时间戳
     *
     * @return 时间戳string
     */
    public static String getCurrentDateFormat() {
        //设置日期显示格式
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-HH:mm:ss");
        //获取当前时间
        Date curDate = new Date(System.currentTimeMillis());
        // 将时间装换为设置好的格式
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 控制按钮可点击延时
     *
     * @param view 控件
     * @param ms   时间
     */
    public static void setFilter(View view, long ms) {
        try {
            Field field = View.class.getDeclaredField("mListenerInfo");
            field.setAccessible(true);
            Class listInfoType = field.getType();
            Object listInfo = field.get(view);
            Field onclickField = listInfoType.getField("mOnClickListener");
            View.OnClickListener origin = (View.OnClickListener) onclickField.get(listInfo);
            onclickField.set(listInfo, new ClickProxy(origin, ms));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 控制按钮点击代理
     */
    private static class ClickProxy implements View.OnClickListener {

        private View.OnClickListener origin;
        private long lastClick = 0;
        private long time;

        public ClickProxy(View.OnClickListener origin, long time) {
            this.origin = origin;
            this.time = time;
        }

        @Override
        public void onClick(View v) {
            if (System.currentTimeMillis() - lastClick >= time) {
                if (origin != null) {
                    origin.onClick(v);
                    lastClick = System.currentTimeMillis();
                }
            }
        }
    }

    /**
     * 选择多张图片
     *
     * @param activity  activity
     * @param maxCount  图片最大数量
     * @param imgCount  已选择图片数量
     * @param imageCode code
     */
    public static void selectThreeImg(Activity activity, int maxCount, int imgCount, int imageCode) {
        if (EasyPermissions.hasPermissions(activity, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ImageSelectorUtils.openPhoto(activity, imageCode, false, maxCount - imgCount);
        } else {
            EasyPermissions.requestPermissions(activity, "请打开拍照和读取照片权限", 300,
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * 选择单图
     *
     * @param activity  activity
     * @param isCamera  true为开启拍照
     * @param imageCode code
     */
    public static void selectOneImg(Activity activity, boolean isCamera, int imageCode) {
        if (EasyPermissions.hasPermissions(activity, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            MultiImageSelector.create().single().showCamera(isCamera).start(activity, imageCode);
        } else {
            EasyPermissions.requestPermissions(activity, "请打开拍照和读取照片权限", 300,
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }
}
