package com.huwei.neteasemusic.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StringUtils {
    final static String TAG = "StringUtils";

    public static final String AZ09 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final int LEN_AZ09 = AZ09.length();

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

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean equals(Object a, Object b) {
        if (a == null) {
            return false;
        }
        return a.equals(b);
    }

    /**
     * 产生一个随机的字符串，适用于JDK 1.7
     */
    public static String random(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append((char) (ThreadLocalRandom.current().nextInt(33, 128)));
        }
        return builder.toString();
    }

    /**
     * 产生一个随机的字符串，适用于JDK 1.7
     */
    public static String randomAZ09(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(AZ09.charAt(random.nextInt(LEN_AZ09)));
        }
        return builder.toString();
    }

    /**
     * 原理是使用异或交换字符串
     * a=a^b;
     * b=b^a;
     * a=b^a;
     *
     * @param s
     * @return
     */
    public static String reverse(String s) {
        char[] array = s.toCharArray();

        int begin = 0;
        int end = s.length() - 1;

        while (begin < end) {
            array[begin] = (char) (array[begin] ^ array[end]);
            array[end] = (char) (array[end] ^ array[begin]);
            array[begin] = (char) (array[end] ^ array[begin]);
            begin++;
            end--;
        }

        return new String(array);
    }

    /* c 要填充的字符
   *  length 填充后字符串的总长度
   *  content 要格式化的字符串
   *  格式化字符串，右对齐
   * */
    public static String flushRight(char c, long length, String content) {
        StringBuilder builder = new StringBuilder(content);
        while (builder.length() < length) {
            builder.insert(0,c);
        }

        return builder.toString();
    }
}
