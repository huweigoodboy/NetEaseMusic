package com.huwei.neteasemusic.bean;

import android.net.Uri;
import android.os.Parcelable;
import android.widget.ImageView;


/**
 * 定义播放 数据接口
 *
 * @author Jerry
 * @date 2015/8/21
 */
public abstract class AbstractMusic implements Parcelable, Parcelable.Creator<AbstractMusic>{

    public static Creator<AbstractMusic> CREATOR;

    public AbstractMusic() {
        //给CREATOR赋值
        CREATOR = this;
    }

    public static final int NONE = -1;

    /**
     * 获取播放源
     * @return
     */
    public abstract Uri getDataSoure();

    /**
     * 获取 音乐时间
     * @return
     */
    public abstract Integer getDuration();   //以s为单位

    /**
     * 获取音乐类型
     * @return
     */
    public abstract MusicType getType();

    /**
     * 获取歌曲名
     */
    public abstract String getName();

    /**
     * 获取歌手名
     * @return
     */
    public abstract String getArtist();

    public abstract String getAlbumPic();

    public abstract void loadBlurPic(ImageView imageView);

    public enum MusicType {
        Local, Online
    }

}
