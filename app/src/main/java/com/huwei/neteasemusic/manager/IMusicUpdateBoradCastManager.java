package com.huwei.neteasemusic.manager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.huwei.neteasemusic.bean.AbstractMusic;
import com.huwei.neteasemusic.constant.IntentExtra;
import com.huwei.neteasemusic.inter.IMusicUpdate;
import com.huwei.neteasemusic.util.LogUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jerry
 * @date 2016/07/10
 */
public class IMusicUpdateBoradCastManager {

    public static final String TAG = "IMusicUpdateBoradCastManager";

    private Set<IMusicUpdate> updateSet = new HashSet<>();
    private Activity mAct;


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case UpdateAction.UPDATE_PROGRESS:
                    int currentTime = intent.getIntExtra(IntentExtra.MP_CURRENT_TIME, 0);
                    int bufferTime = intent.getIntExtra(IntentExtra.MP_BUFFER_TIME, 0);
                    int duration = intent.getIntExtra(IntentExtra.MP_DURATION, 0);

                    //过滤掉 duration 为0的情况
                    if(duration == 0){
                        return;
                    }

                    for (IMusicUpdate up : updateSet) {
                        if (up != null) {
                            up.updateProgress(currentTime, bufferTime, duration);
                        }
                    }

                    LogUtils.i(TAG, "UPDATE_PROGRESS --》 currentTime:" + currentTime + " bufferTime:" + bufferTime + "   duration:" + duration);

                    break;
                case UpdateAction.UPDATE_MUSICINFO:

                    AbstractMusic music = intent.getParcelableExtra(IntentExtra.MP_MUSICINFO);

                    for (IMusicUpdate up : updateSet) {
                        if (up != null) {
                            up.updateMusicInfo(music);
                        }
                    }

                    LogUtils.i(TAG, "UPDATE_MUSICINFO --》 music name:" + music.getName() + " artist:" + music.getArtist());

                    break;
                case UpdateAction.UPDATE_STATUS:
                    int playStatus = intent.getIntExtra(IntentExtra.MP_PLAY_STATUS, 0);

                    for (IMusicUpdate up : updateSet) {
                        if (up != null) {
                            up.updatePlayStatus(playStatus);
                        }
                    }

                    LogUtils.i(TAG, "UPDATE_STATUS --》playStatus:" + playStatus);

                    break;
            }
        }
    };


    public void bind(Activity activity) {
        this.mAct = activity;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UpdateAction.UPDATE_PROGRESS);
        intentFilter.addAction(UpdateAction.UPDATE_STATUS);
        intentFilter.addAction(UpdateAction.UPDATE_MUSICINFO);
        mAct.registerReceiver(mReceiver, intentFilter);
    }

    public void addListener(IMusicUpdate update) {
        updateSet.add(update);
    }

    public void unbind() {
        mAct.unregisterReceiver(mReceiver);

        mAct = null;
    }

    public interface UpdateAction {
        public static final String UPDATE_PROGRESS = "UPDATE_PROGRESS";
        public static final String UPDATE_STATUS = "UPDATE_STATUS";
        public static final String UPDATE_MUSICINFO = "UPDATE_MUSICINFO";
    }

}
