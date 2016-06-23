package com.huwei.neteasemusic;

import android.app.Application;
import android.content.Context;

/**
 *
 * @author jerry
 * @date 2016-06-23
 */
public class NetEaseApplication extends Application{

    public static Context CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = this;
    }
}
