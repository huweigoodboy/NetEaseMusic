package com.huwei.neteasemusic.inter;

import com.huwei.neteasemusic.bean.AbstractMusic;

/**
 * @author jerry
 * @date 2016/07/10
 */
public interface IMusicUpdate {
    void updateProgress(int currentTime,int bufferTime,int duration);

    void updatePlayStatus(IPlayStatus playStatus);

    /**
     * 歌曲改变 更新 播放信息
     * @param music
     */
    void updateMusicInfo(AbstractMusic music);
}
