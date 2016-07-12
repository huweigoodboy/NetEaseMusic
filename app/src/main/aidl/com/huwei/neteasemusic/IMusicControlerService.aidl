// IMusicControler.aidl
package com.huwei.neteasemusic;
import com.huwei.neteasemusic.bean.AbstractMusic;

// Declare any non-default types here with import statements

interface IMusicControlerService {

    int getPid();

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


    void play();

    void pause();

    void stop();

    void seekTo(int mesc);

    void preparePlayingList(in List  list);

    void preparePlayingListAndPlay(int musicIndex,in List  list);

    boolean isPlaying();

    int getPlayStatus();    //获取播放状态

    int getPlayingSongIndex();

    AbstractMusic getNowPlayingSong();

    boolean isForeground();

    void nextSong();

    void preSong();

    void randomSong();
}
