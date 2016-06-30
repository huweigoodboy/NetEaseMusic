package com.huwei.neteasemusic.util;

import android.util.Log;



public class LogUtils {

    public static final boolean M_DEBUG_LOG = true;

    public static void i(String tag, String msg) {
        try {
            if (M_DEBUG_LOG) {
                Log.i(tag, msg);

            }
        } catch (Exception e) {
        }
    }

    public static void d(String tag, String msg) {
        try {
            if (M_DEBUG_LOG) {
                Log.d(tag, msg);

            }
        } catch (Exception e) {
        }
    }

    public static void w(String tag, String msg) {
        try {
            if (M_DEBUG_LOG) {
                Log.w(tag, msg);

            }
        } catch (Exception e) {
        }
    }

    public static void e(String tag, String msg) {
        try {
            if (M_DEBUG_LOG) {
                Log.e(tag, msg);

            }
        } catch (Exception e) {
        }
    }

    public static void e(String tag, Throwable throwable, String additionalMsg) {
        try {
            if (M_DEBUG_LOG) {
                e(tag, throwable, additionalMsg, false);
            }
        } catch (Exception e) {
        }
    }

    public static void e(String tag, Throwable throwable) {
        try {
            if (M_DEBUG_LOG) {
                e(tag, throwable, false);
            }
        } catch (Exception e) {
        }
    }

    public static void e(String tag, Throwable throwable, boolean upload) {
        try {
            if (M_DEBUG_LOG) {
                e(tag, throwable, null, false);
            }
        } catch (Exception e) {
        }
    }

    public static void e(String tag, Throwable throwable, String additionalMsg, boolean upload) {
        try {
            if (M_DEBUG_LOG && additionalMsg != null) {
                Log.e(tag, additionalMsg);
            }


        } catch (Exception e) {
        }
    }

    public static String getLogString(String tag, String msg) {
        return tag + "   " + msg;
    }
}
