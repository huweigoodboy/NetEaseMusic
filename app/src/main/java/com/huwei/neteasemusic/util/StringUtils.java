package com.huwei.neteasemusic.util;


import android.graphics.Paint;


import java.math.BigDecimal;

public class StringUtils {
    final static String TAG = "StringUtils";

    public static String truncateZero(String s) {
        try {
            double d = Double.parseDouble(s);
            if (d * 10 % 10 == 0) {
                return "" + ((int) d);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        }
        return s;
    }

    public static boolean isEmpty(String s) {
        return null == s || "".equals(s);
    }

    public static boolean isNotEmpty(String s){
        return !isEmpty(s);
    }

    public static boolean equals(Object a, Object b){
        if(a == null){
            return false;
        }
        return a.equals(b);
    }


    /**
     * @return 返回指定笔和指定字符串的长度
     */
    public static float getFontlength(Paint paint, String str) {
        return paint.measureText(str);
    }

    /**
     * @return 返回指定笔的文字高度
     */
    public static float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * @return 返回指定笔离文字顶部的基准距离
     */
    public static float getFontLeading(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.leading - fm.ascent;
    }

    /**
     * 获取top到baseline的距离
     *
     * @param paint
     * @return
     */
    public static float getDistanceTopToBase(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return -fm.top;
    }

    /**
     * 获取汉字的高度  从top到bottom
     * @param paint
     * @return
     */
    public static float getTextHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.bottom - fm.top;
    }


    /**
     * 截掉小数点后面的0
     * @param d
     * @return
     */
    public static String getFloatString(double d){
        String str = String.format("%.2f", d);
        if(str.endsWith(".00") || str.endsWith(".0")){
            return String.format("%.0f", d);
        } else if(str.contains(".") && str.endsWith("0")){
            return String.format("%.1f", d);
        } else {
            return str;
        }
    }

    public static String formatWithTimes(int times){
        if (times >= 10000){
            if (times < 1000000) {
                return new StringBuilder(String.format("%.1f", (float) times / 10000f)).append("万").toString();
            }else {
                return new StringBuilder(String.valueOf(new BigDecimal(times / 10000.0).setScale(0, BigDecimal.ROUND_HALF_UP))).append("万").toString();
            }
        }else {
            return String.valueOf(times);
        }
    }
}
