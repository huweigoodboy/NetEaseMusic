package com.huwei.neteasemusic;

import android.app.Application;
import android.content.Context;

import com.huwei.neteasemusic.ui.widget.PlayBarView;

/**
 *
 * @author jerry
 * @date 2016-06-23
 */
public class NetEaseApplication extends Application{

    public static NetEaseApplication CONTEXT;

    private PlayBarView mPlayBarView;

    @Override
    public void onCreate() {
        super.onCreate();

        CONTEXT = this;
    }

    public PlayBarView getPlayBarView(){
        if(mPlayBarView == null){
            mPlayBarView = new PlayBarView(this);
        }
        return mPlayBarView;
    }
}
