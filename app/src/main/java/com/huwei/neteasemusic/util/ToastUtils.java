package com.huwei.neteasemusic.util;

import android.widget.Toast;

import com.huwei.neteasemusic.NetEaseApplication;
import com.huwei.neteasemusic.constant.Config;


/**
 * 作者 : andy
 * 时间 : 15-10-26.
 * 邮箱 : byhook@163.com
 * 功能 :
 * 整理的工具类
 * 用来管理Toast显示
 *
 * 修订: 2016/05/04  carlos 增加 showDebug(String), showShort(CharSequence), showLong(CharSequence),
 * showShort(int), showLong(int) 方法
 */
public class ToastUtils {

    private final static String TAG = ToastUtils.class.getSimpleName();

    /**
     * 构造函数
     */
    private ToastUtils() {
        throw new UnsupportedOperationException(TAG + " cannot be instantiated");
    }

//    /**
//     * activity参数传递错误 提示
//     *
//     * @param ctx
//     */
//    public static void showActParamsError(Context ctx) {
//        showShort(ctx, R.string.act_params_error);
//    }

    /**
     * 调试用的 toast
     * @param msg
     */
    public static void showDebug(String msg) {
        if (Config.IS_LOG_ON) {
            NetEaseApplication appContext = NetEaseApplication.get();
            Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showShort(CharSequence msg) {
        if (Config.IS_LOG_ON) {
            NetEaseApplication appContext = NetEaseApplication.get();
            Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showLong(CharSequence msg) {
        if (Config.IS_LOG_ON) {
            NetEaseApplication appContext = NetEaseApplication.get();
            Toast.makeText(appContext, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static void showShort(int strResId) {
        if (Config.IS_LOG_ON) {
            NetEaseApplication appContext = NetEaseApplication.get();
            CharSequence msg = appContext.getText(strResId);
            showShort(msg);
        }
    }

    public static void showLong(int strResId) {
        if (Config.IS_LOG_ON) {
            NetEaseApplication appContext = NetEaseApplication.get();
            CharSequence msg = appContext.getText(strResId);
            showLong(msg);
        }
    }
}
