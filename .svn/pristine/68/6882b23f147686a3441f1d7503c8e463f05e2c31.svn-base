package com.yst.onecity.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.design.widget.TabLayout;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yst.onecity.R;
import com.yst.onecity.config.Const;
import com.yst.onecity.view.ContainsEmojiEditText;
import com.yst.onecity.view.MyRatingBar;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * 常用工具类
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class Utils {

    private static long lastClickTime;
    public static PopupWindow popupWindow;
    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * @return
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

    public static String getDeviceId(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        return deviceId;
    }

    /**
     * dp 转 px
     */
    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    /**
     * 获取文本长度
     */
    public static int getTextViewLength(TextView textView, String text) {
        TextPaint paint = textView.getPaint();
        // 得到使用该paint写上text的时候,像素为多少
        int textLength = (int) paint.measureText(text);
        return textLength;
    }

    public static int getLayoutParamsWidth(TextView textView) {
        int tvWidth = Utils.getTextViewLength(textView, "我是一只小小鸟小小鸟");
        return tvWidth;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int scaleWindowWidth(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().widthPixels;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 半角转为全角
     */
    public static String toDBC(String input) {
        if (input != null && !"".equals(input)) {
            char[] c = input.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == 12288) {
                    c[i] = (char) 32;
                    continue;
                }
                if (c[i] > 65280 && c[i] < 65375) {
                    c[i] = (char) (c[i] - 65248);
                }
            }
            return new String(c);
        }
        return "";
    }

    /**
     * 嵌套listview，完全显示
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void openPopupWindow(View view, ListView listView, Activity activity) {
        if (popupWindow != null && popupWindow.isShowing()) {
            MyLog.i("User", "---------11");
            popupWindow.dismiss();
        } else {
            MyLog.i("User", "---------2");
            popupWindow = new PopupWindow(view, view.getWidth() - 30, LayoutParams.WRAP_CONTENT);
            ColorDrawable cd = new ColorDrawable(0x00000000);
            popupWindow.setBackgroundDrawable(cd);
            /// popupWindow.setAnimationStyle(R.style.AnimTop2);
            popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            // 设置popupWindow可以接收焦点
            popupWindow.setFocusable(true);
            // 设置弹出窗体以外的区域可以点击，其实，就是，隐藏弹出窗体
            popupWindow.setOutsideTouchable(true);
            popupWindow.update();
            popupWindow.setContentView(listView);
            popupWindow.showAsDropDown(view);
        }
    }


    public static PopupWindow openPopupWindow1(View view, ListView listView, Activity activity) {
        PopupWindow popupWindow = new PopupWindow(view, view.getWidth(), LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(cd);
        /// popupWindow.setAnimationStyle(R.style.AnimTop2);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        // 设置popupWindow可以接收焦点
        popupWindow.setFocusable(true);
        // 设置弹出窗体以外的区域可以点击，其实，就是，隐藏弹出窗体
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        popupWindow.setContentView(listView);
        popupWindow.showAsDropDown(view);
        return popupWindow;
    }

    public static void openPopupWindow(PopupWindow popupWindow, View view, ListView listView, int x, int y) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            ColorDrawable cd = new ColorDrawable(0x00000000);
            popupWindow.setBackgroundDrawable(cd);
            /// popupWindow.setAnimationStyle(R.style.AnimTop2);
            popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            // 设置popupWindow可以接收焦点
            popupWindow.setFocusable(true);
            // 设置弹出窗体以外的区域可以点击，其实，就是，隐藏弹出窗体
            popupWindow.setOutsideTouchable(true);
            popupWindow.update();
            popupWindow.setContentView(listView);
            popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, y);
        }
    }


    /**
     * 正则匹配是否是手机号
     */
    private static Pattern p = compile("^((1[3,5,8][0-9])|(14[5,7])|(17[0,3,6,7,8]))\\d{8}$");

    public static boolean isMobileNO(String mobiles) {
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断身份证号码
     **/
    private static Pattern idNumPattern = compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");

    public static boolean isCard(String cardStr) {
        // 定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
        // 通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(cardStr);
        // 判断用户输入是否为身份证号
        if (idNumMatcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 将字符串转为时间戳
     *
     * @param userTime
     * @return
     */
    public static long getTime(String userTime) {
        String reTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
        Date d;
        try {
            d = sdf.parse(userTime);
            long l = d.getTime();
            String str = String.valueOf(l);
            reTime = str.substring(0, 10);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.parseLong(reTime);
    }

    public static String getStrTime1111(String ccTime) {
        String reStrTime = null;
        if (!Const.CONS_STR_NULL.equals(ccTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            long lccTime = Long.valueOf(ccTime);
            reStrTime = sdf.format(new Date(lccTime * 1000L));
        }
        return reStrTime;
    }

    /**
     * 将时间戳转为字符串
     *
     * @param ccTime
     * @return
     */
    public static String getStrTime(String ccTime) {
        String reStrTime = null;
        if (!Const.CONS_STR_NULL.equals(ccTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lccTime = Long.valueOf(ccTime);
            reStrTime = sdf.format(new Date(lccTime * 1000L));
        }
        return reStrTime;
    }

    /**
     * 将时间戳转为字符串
     *
     * @param time
     * @return
     */
    public static String getStrTimeLength10(String time) {
        String reStrTime = null;
        if (!Const.CONS_STR_NULL.equals(time)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long timestamp = Long.valueOf(time);
            reStrTime = sdf.format(new Date(timestamp));
        }
        return reStrTime;
    }

    /**
     * 将时间戳转为字符串
     *
     * @param time
     * @return
     */
    public static String getStrTime2(String time) {
        String reStrTime = null;
        if (!Const.CONS_STR_NULL.equals(time)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            long timestamp = Long.valueOf(time);
            reStrTime = sdf.format(new Date(timestamp * 1000L));
        }
        return reStrTime;
    }

    /**
     * 毫秒转换
     *
     * @param ms 毫秒
     * @return 时间
     */
    public static String formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        //天
        String strDay = day < 10 ? "0" + day : "" + day;
        //小时
        String strHour = hour < 10 ? "0" + hour : "" + hour;
        //分钟
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        //秒
        String strSecond = second < 10 ? "0" + second : "" + second;
        //毫秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strHour + "小时" + strMinute + " 分钟 ";
    }

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

    public static void setCommont(TextView view, String commont) {
        if (commont == null) {
            commont = "0";
        } else {
            if (Const.CONS_STR_NULL.equals(commont)) {
                commont = "0";
            }
            if ("".equals(commont)) {
                commont = "0";
            }
        }
        view.setText(commont + "好评");
    }

    public static void setCommontNo(TextView view, String commont) {
        if (commont == null) {
            commont = "0";
        } else {
            if (Const.CONS_STR_NULL.equals(commont)) {
                commont = "0";
            }
            if ("".equals(commont)) {
                commont = "0";
            }
        }
        view.setText(commont);
    }

    public static void setDistance(TextView view, String num) {
        if (num == null) {
            num = "0km";
        } else {
            if (Const.CONS_STR_NULL.equals(num)) {
                num = "0km";
            }
            if ("".equals(num)) {
                num = "0km";
            }
        }
        view.setText(num);
    }

    public static void setNum(TextView view, String num) {
        if (num == null) {
            num = "0";
        } else {
            if (Const.CONS_STR_NULL.equals(num)) {
                num = "0";
            }
            if ("".equals(num)) {
                num = "0";
            }
        }
        view.setText(num + "人找他");
    }

    public static void setNumNo(TextView view, String num) {
        if (num == null) {
            num = "0";
        } else {
            if (Const.CONS_STR_NULL.equals(num)) {
                num = "0";
            }
            if ("".equals(num)) {
                num = "0";
            }
        }
        view.setText(num);
    }

    public static void setHunterName(TextView view, String name) {
        if (null == name) {
            name = "";
        } else {
            if (Const.CONS_STR_NULL.equals(name)) {
                name = "";
            }
            if ("".equals(name)) {
                name = "";
            }
        }
        view.setText(name);
    }

    public static void setProductName(TextView view, String name) {
        if (null == name) {
            name = "";
        } else {
            if (Const.CONS_STR_NULL.equals(name)) {
                name = "";
            }
            if ("".equals(name)) {
                name = "";
            }
        }
        view.setText(name);
    }

    public static void setAppriseNum(TextView view, String num) {
        if (null == num) {
            num = "0";
        } else {
            if (Const.CONS_STR_NULL.equals(num)) {
                num = "0";
            }
            if ("".equals(num)) {
                num = "0";
            }
        }
        view.setText("用户评论(" + num + "条)");
    }

    /**
     * 设置专业显示
     *
     * @param view
     * @param major
     */
    public static void setMajor(TextView view, String major) {
        if (null == major) {
            major = "";
        } else {
            if (Const.CONS_STR_NULL.equals(major)) {
                major = "";
            }
            if ("".equals(major)) {
                major = "";
            }
        }
        view.setText(major);
    }

    /**
     * 判断字符串是否有效
     *
     * @param strr
     * @return 无效返回空字符串
     */
    public static String signString(String strr) {
        if (null == strr) {
            strr = "";
        } else {
            if (Const.CONS_STR_NULL.equals(strr)) {
                strr = "";
            }
            if ("".equals(strr)) {
                strr = "";
            }
        }
        return strr;
    }

    public static void setRatingBar(MyRatingBar view, String rating) {
        if (rating == null) {
            rating = "0";
        } else {
            if (Const.CONS_STR_NULL.equals(rating)) {
                rating = "0";
            }
            if ("".equals(rating)) {
                rating = "0";
            }
        }
        view.setRating(Integer.parseInt(rating));
    }

    /**
     * md5加密
     *
     * @param origin
     * @param charsetName
     * @return
     */
    public static String utilMD5Encode(String origin, String charsetName) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetName == null || "".equals(charsetName)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetName)));
            }
        } catch (Exception exception) {
        }
        return resultString;
    }

    /**
     * 字节数组转换成十六进制
     *
     * @param b
     * @return
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    /**
     * 字节转十六进制
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    /**
     * 接口验签，需要要验签的key和value
     *
     * @param strings
     * @return
     */
    public static String getSign(String... strings) {
        Map<String, String> map = new HashMap<>(16);
        boolean isNoEmpty = true;
        for (int i = 0; i < strings.length; i += Const.INTEGER_2) {
            if (strings[i + 1] == null) {
                isNoEmpty = false;
                Log.i("Ok", strings[i] + "-- " + strings[i + 1]);
                break;
            } else {
                map.put(strings[i], strings[i + 1]);
            }
        }
        map.put("client_type", "A");
        MyLog.e("@@ url ", SignUtils.map2LinkString(map));
        return isNoEmpty == true ? SignUtils.mapToSign(map) : null;
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 获取刷新列表顶部时间
     */
    public static String getXListViewTopTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH时mm分");
        return format.format(date);
    }

    /**
     * 文本末尾动态添加省略号
     *
     * @param str 要加省略号的内容
     * @return
     */
    public static String ellipsizeStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String s = "...";
        return str + s;
    }

    /**
     * 文本指定位置动态添加省略号
     *
     * @param str   要截取的内容
     * @param index 指定截取的下标
     * @return
     */
    public static String ellipsizeStrIndex(String str, int index) {
        if (!TextUtils.isEmpty(str) && str.length() > index && index != 0) {
            String s = "...";
            return str.substring(0, index) + s;
        } else {
            return str;
        }
    }

    /**
     * 获取指定字符串长度，最后以dot补充
     *
     * @param content 要截取的字符串内容
     * @param length  新的内容长度
     * @return 指定的内容
     */
    public static String getAssignDesc(String content, int length) {
        if (!TextUtils.isEmpty(content) && !Const.CONS_STR_NULL.equals(content)) {
            if (content.length() > length) {
                return content.substring(0, length) + "...";
            } else {
                return content;
            }
        }
        return "";
    }

    /**
     * 设置编辑内容的长度
     *
     * @param editText
     * @param length
     */
    public static void setEmojiEdittextLength(ContainsEmojiEditText editText, int length) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    /**
     * 设置文本小label
     *
     * @param view  要设置的控件
     * @param lable 指定label内容
     */
    public static void setSmallLable(TextView view, String lable) {
        if (lable == null) {
            lable = "大众";
        } else {
            if (Const.CONS_STR_NULL.equals(lable)) {
                lable = "大众";
            }
            if ("".equals(lable)) {
                lable = "大众";
            }
        }
        view.setText(lable);
        view.setBackgroundResource(R.drawable.headhunter_small_high_label);
    }


    /**
     * 设置tablayout下划线宽度
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    /**
     * 对字符串判空
     */
    public static String getStringNoEmpty(String string) {
        if (null == string || string.length() == 0 || Const.CONS_STR_NULL.equals(string)) {
            string = "";
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
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    public static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                return true;
            }
        }
        return false;
    }

    /**
     * //保留两位小数正则
     *
     * @param number
     * @return
     */
    public static boolean isOnlyPointNumber(String number) {
        String str = "^\\d+\\.?\\d{0,2}$";
        return matches(str, number);
    }


    /**
     * 两个Double数相加
     */
    public static Double doubleAdd(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).doubleValue();
    }

    /***
     * 两个Double数相减
     * @param v1
     * @param v2
     * @return
     */
    public static Double doubleSub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 两个Double数相乘
     *
     * @param v1
     * @param v2
     * @return
     */
    public static Double doubleMul(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).doubleValue();
    }

    public static String getStringDecimalFormat(String s) {
        if (!TextUtils.isEmpty(s)) {
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(Double.parseDouble(s));
        }
        return Const.CONS_STR_ZERO;
    }

    /**
     * 设置tab是否可以点击
     *
     * @param tabs       tab 控件
     * @param isTabClick 是否可点击
     */
    public static void setCusorIsClick(TabLayout tabs, boolean isTabClick) throws NoSuchFieldException, IllegalAccessException {
        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            if (null == tab) {
                return;
            }
            Class<? extends TabLayout.Tab> tabClass = tab.getClass();
            try {
                Field field = tabClass.getDeclaredField("mView");
                field.setAccessible(true);
                View view = (View) field.get(tab);
                if (null == view) {
                    return;
                }
                view.setTag(i);
                if (!isTabClick) {
                    view.setEnabled(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取设备的唯一标识，deviceId
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if (deviceId == null) {
            return "-";
        } else {
            return deviceId;
        }
    }

    /**
     * 匹配是否为数字
     * @param str 可能为中文，也可能是-19162431.1254，不使用BigDecimal的话，变成-1.91624311254E7
     * @return
     * @author yutao
     * @date 2016年11月14日下午7:41:22
     */
    public static boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = compile("-?[0-9]+(\\.[0-9]+)?");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            //异常 说明包含非数字。
            return false;
        }

        // matcher是全匹配
        Matcher isNum = pattern.matcher(bigStr);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
