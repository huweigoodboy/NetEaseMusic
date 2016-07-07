package com.huwei.neteasemusic.util;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

/**
 * @author jerry
 * @version 4.6
 * @date 2015-10-27
 */
public class DisplayUtils {



    public static int getWindowHeight(Context context){
//        DisplayMetrics  dm = new DisplayMetrics();
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        //取得窗口属性
//        wm.getDefaultDisplay().getMetrics(dm);
        if(context instanceof Activity){
            WindowManager.LayoutParams layoutParams = ((Activity)context).getWindow().getAttributes();
            return layoutParams.height;
        }
        return 0;
    }
}
