package com.huwei.neteasemusic.util;

import android.content.res.Resources;

import com.huwei.neteasemusic.NetEaseApplication;

/**
 *
 * @author jerry
 * @date 2016-06-23
 */
public class Utils {
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = NetEaseApplication.CONTEXT.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = NetEaseApplication.CONTEXT.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static Resources getResources(){
        return NetEaseApplication.CONTEXT.getResources();
    }
}
