package com.huwei.neteasemusic;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.huwei.neteasemusic.ui.widget.PlayBarView;

/**
 *
 * @author jerry
 * @date 2016-06-23
 */
public class NetEaseApplication extends Application{

    public static NetEaseApplication CONTEXT;

    private PlayBarView mPlayBarView;

    private static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = this;

        mHandler = new Handler(Looper.getMainLooper());
    }

    public PlayBarView getPlayBarView(){
        if(mPlayBarView == null){
            mPlayBarView = new PlayBarView(this);
        }
        return mPlayBarView;
    }

    public static NetEaseApplication get(){
        return CONTEXT;
    }

    public static void runUiThread(Runnable runnable) {
        mHandler.post(runnable);
    }

    public static void runUiThread(Runnable runnable, long delay) {
        mHandler.postDelayed(runnable, delay);
    }
}
