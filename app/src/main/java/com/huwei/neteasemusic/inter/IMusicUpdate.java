package com.huwei.neteasemusic.inter;

import com.huwei.neteasemusic.bean.AbstractMusic;

/**
 * @author jerry
 * @date 2016/07/10
 */
public interface IMusicUpdate {
    /**
     * 进度更新
     * @param currentTime
     * @param bufferTime   //todo 暂时没有传值
     * @param duration
     */
    void updateProgress(int currentTime,int bufferTime,int duration);

    /**
     * 播放状态更新
     * @param playStatus
     */
    void updatePlayStatus(int playStatus);

    /**
     * 歌曲改变 更新 播放信息
     * @param music
     */
    void updateMusicInfo(AbstractMusic music);
}
