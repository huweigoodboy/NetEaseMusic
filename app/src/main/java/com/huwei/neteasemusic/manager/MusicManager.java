package com.huwei.neteasemusic.manager;

import android.os.RemoteException;

import com.huwei.neteasemusic.IMusicControlerService;
import com.huwei.neteasemusic.bean.AbstractMusic;
import com.huwei.neteasemusic.inter.IMusicControl;
import com.huwei.neteasemusic.inter.IPlayStatus;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by huwei on 15-1-27.
 */
public class MusicManager implements IMusicControl {
    private static MusicManager instance = new MusicManager();
    private IMusicControlerService mMusicControlService;
    private List<AbstractMusic> mSourceList = new ArrayList<>();

    private MusicManager() {
        super();
    }

    public static MusicManager get() {
        return instance;
    }

    public void bindStub(IMusicControlerService mMusicControlService) {
        this.mMusicControlService = mMusicControlService;
    }

    @Override
    public void play() {
        if (mMusicControlService != null) {
            try {
                mMusicControlService.play();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void pause() {
        if (mMusicControlService != null) {
            try {
                mMusicControlService.pause();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void toggle(){
        if(isPlaying()){
            pause();
        }else{
            play();
        }
    }

    @Override
    public void stop() {
        if (mMusicControlService != null) {
            try {
                mMusicControlService.stop();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void seekTo(final int mesc) {
        if (mMusicControlService != null) {
            try {
                mMusicControlService.seekTo(mesc);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void preparePlayingList(List<AbstractMusic> list) {
        if (mMusicControlService != null) {
            try {
                mMusicControlService.preparePlayingList( list);
                mSourceList = list;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void preparePlayingListAndPlay(final int index, final List<AbstractMusic> list) {
        if (mMusicControlService != null) {
            try {
                mMusicControlService.preparePlayingListAndPlay(index, list);
                mSourceList = list;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPlayStatus(){
        if (mMusicControlService != null) {
            try {
                return mMusicControlService.getPlayStatus();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return IPlayStatus.STOP;
    }


    @Override
    public boolean isPlaying() {
        if (mMusicControlService != null) {
            try {
                return mMusicControlService.isPlaying();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public int getNowPlayingIndex() {
        if (mMusicControlService != null) {
            try {
                return mMusicControlService.getPlayingSongIndex();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return AbstractMusic.NONE;
    }

    @Override
    public AbstractMusic getNowPlayingSong() {
        if (mMusicControlService != null) {
            try {
                return mMusicControlService.getNowPlayingSong();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean isForeground() {
        if (mMusicControlService != null) {
            try {
                return mMusicControlService.isForeground();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    @Override
    public void nextSong() {
        if(mMusicControlService!=null) {
            try {
                mMusicControlService.nextSong();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void preSong() {
        if(mMusicControlService!=null) {
            try {
                mMusicControlService.preSong();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void randomSong() {
        if(mMusicControlService!=null) {
            try {
                mMusicControlService.randomSong();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public void updateMusicQueue() {
//        t.updateMusicQueue();
//    }

    public List<AbstractMusic> getPlayingList() {
        return mSourceList;
    }


    /**
     * 判断是否为同一个播放列表，都为null判断false，否则比较两者的hashcode
     *
     * @param list2
     * @return
     */
    public static boolean isListEqual(List list2) {
        List list1 = get().getPlayingList();
        if (list1 == null || list2 == null) {
            return false;
        } else {
            return list1.hashCode() == list2.hashCode();
        }
    }

    /**
     * 判断当前列表的歌曲是不是正在播放的歌曲   1,列表是否是正在播放的列表 2,位置是否在正在播放的列表
     *
     * @param list
     * @param pos
     * @return
     */
    public static boolean isIndexNowPLayng(List list, int pos) {
        return isListEqual(list) && get().getNowPlayingIndex() == pos;
    }
}
