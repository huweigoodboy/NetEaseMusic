package com.huwei.neteasemusic.service;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;


import com.huwei.neteasemusic.IMusicControlerService;
import com.huwei.neteasemusic.bean.AbstractMusic;
import com.huwei.neteasemusic.bean.Bitrate;
import com.huwei.neteasemusic.bean.MusicFile;
import com.huwei.neteasemusic.bean.Song;
import com.huwei.neteasemusic.bean.resp.MusicFileResp;
import com.huwei.neteasemusic.bean.resp.NetEaseAPI;
import com.huwei.neteasemusic.bean.resp.ServerTip;
import com.huwei.neteasemusic.constant.IContain;
import com.huwei.neteasemusic.constant.IMusicAction;
import com.huwei.neteasemusic.constant.IntentExtra;
import com.huwei.neteasemusic.inter.IPlayStatus;
import com.huwei.neteasemusic.manager.IMusicUpdateBoradCastManager;
import com.huwei.neteasemusic.ui.notify.MusicNotifiControler;
import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.Utils;
import com.huwei.neteasemusic.util.network.UHttpHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 后台控制播放音乐的service
 */
public class MusicControlerService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, IContain,IMusicAction {
    private String TAG = "MusicControlerService";

    public static final int MSG_CURRENT = 0;
    public static final int MSG_BUFFER_UPDATE = 1;

    public static final int MSG_NOTICATION_UPDATE = 2;

    public static final int MSG_PLAY = 101;


    private int musicIndex = -1;
    private long lastSongID = -1;
    private List<AbstractMusic> musicList;

    private MediaPlayer mp;
    private AbstractMusic mLastPlayMusic;
    private MusicNotifiControler mNotifiControler;

    private int mPlayStatus;

    private boolean isForeground;
    private boolean isPrepared;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Intent intent;

            switch (msg.what) {
                case MSG_CURRENT:

                    intent = new Intent(IMusicUpdateBoradCastManager.UpdateAction.UPDATE_PROGRESS);
                    int currentTime = 0;
                    int duration = getDuration();
                    currentTime = mp.getCurrentPosition();

                    if (duration != 0) {
                        Log.i("currentTime", currentTime + "");
                        intent.putExtra(IntentExtra.MP_CURRENT_TIME, currentTime);
                        intent.putExtra(IntentExtra.MP_DURATION, duration);
                        sendBroadcast(intent);
                    }

                    handler.sendEmptyMessageDelayed(MSG_CURRENT, 500);

                    break;
                case MSG_BUFFER_UPDATE:

                    intent = new Intent(IMusicUpdateBoradCastManager.UpdateAction.UPDATE_BUFFER_PROGRESS);
                    int bufferTime = msg.arg1;
                    Log.i("bufferTime", bufferTime + "");
                    intent.putExtra(IntentExtra.MP_BUFFER_TIME, bufferTime);
                    intent.putExtra(IntentExtra.MP_DURATION, getDuration());
                    sendBroadcast(intent);
                    break;
                case MSG_NOTICATION_UPDATE:
//                    reViews.setImageViewBitmap(R.id.img_album, (Bitmap) msg.obj);
                    break;
                case MSG_PLAY:
                    AbstractMusic music = (AbstractMusic) msg.obj;
                    play(music);
                    break;
            }
        }
    };

    private BroadcastReceiver controlReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case PLAYPRO_EXIT:
                    stopSelf();

                    mNotifiControler.cancel();

                    Process.killProcess(Process.myPid());
                    break;
                case NEXTSONG:
                    try {
                        mBinder.nextSong();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                case PRESONG:
                    try {
                        mBinder.preSong();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case PLAY_OR_PASUE:
                    try {
                        if (mBinder.isPlaying()) {
                            //暂停

                            mBinder.pause();
                        } else {
                            //播放

                            mBinder.play();
                        }

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    private IMusicControlerService.Stub mBinder = new IMusicControlerService.Stub() {
        @Override
        public int getPid() throws RemoteException {
            return Process.myPid();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void play() throws RemoteException {
            mNotifiControler.setIsPlay(true);

            //准备播放源，准备后播放
            AbstractMusic music = mBinder.getNowPlayingSong();
            updatePlayStatus(IPlayStatus.PLAYING);

            Log.i(TAG, "play()->" + music.getName());
            if (!mp.isPlaying()) {
                Log.i(TAG, "Enterplay()");
                mp.start();
                updatePlayStaute(true);
            }
        }

        @Override
        public void pause() throws RemoteException {
            mNotifiControler.setIsPlay(false);

            updatePlayStatus(IPlayStatus.PAUSE);

            mp.pause();
//            handler.removeMessages(MSG_CURRENT);

            updatePlayStaute(false);
        }

        @Override
        public void stop() throws RemoteException {
            updatePlayStatus(IPlayStatus.STOP);

            stopForeground(true);
        }

        @Override
        public void seekTo(int mesc) throws RemoteException {
            mp.seekTo(mesc);
        }

        @Override
        public void preparePlayingList(List list) throws RemoteException {
            musicList = list;
        }

        @Override
        public void preparePlayingListAndPlay(int index, List list) throws RemoteException {
            musicIndex = index;
            musicList = list;

            Log.d(TAG, "musicList:" + list + " musicIndex:" + index + "now title:" + ((AbstractMusic) list.get(index)).getName());

            if (musicList == null || musicList.size() == 0) {
                Toast.makeText(getBaseContext(), "播放列表为空", Toast.LENGTH_LONG).show();
                return;
            }

            AbstractMusic song = musicList.get(musicIndex);
            prepareSong(song);
        }

        @Override
        public int getPlayStatus() throws RemoteException {
            if (isPlaying()) {
                return IPlayStatus.PLAYING;
            }
            return mPlayStatus;
        }

        @Override
        public boolean isPlaying() {
            return mp != null && mp.isPlaying();
        }

        @Override
        public int getPlayingSongIndex() throws RemoteException {
            return musicIndex;
        }

        @Override
        public AbstractMusic getNowPlayingSong() throws RemoteException {
            if (Utils.canFetchFirst(musicList) && musicIndex < musicList.size()) {
                return musicList.get(musicIndex);
            }
            return null;
        }

        @Override
        public boolean isForeground() throws RemoteException {
            return isForeground;
        }

        @Override
        public void nextSong() throws RemoteException {
            musicIndex = (musicIndex + 1 + musicList.size()) % musicList.size();
            prepareSong(musicList.get(musicIndex));
        }

        @Override
        public void preSong() throws RemoteException {
            musicIndex = (musicIndex + musicList.size() - 1) % musicList.size();
            prepareSong(musicList.get(musicIndex));
        }

        @Override
        public void randomSong() throws RemoteException {
            musicIndex = new Random().nextInt(musicList.size());
            prepareSong(musicList.get(musicIndex));
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        mNotifiControler = new MusicNotifiControler(getBaseContext());

        if (mp != null) {
            mp.release();
            mp.reset();
        }

        mp = getMediaPlayer(getBaseContext());
        mp.setOnCompletionListener(this);
        mp.setOnBufferingUpdateListener(this);
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.i(TAG, "onPrepared");
                isPrepared = true;

                handler.sendEmptyMessage(MSG_CURRENT);

                try {
                    mBinder.play();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(PLAYPRO_EXIT);
        filter.addAction(PRESONG);
        filter.addAction(NEXTSONG);
        filter.addAction(PLAY_OR_PASUE);
        registerReceiver(controlReceiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "mBinder:" + mBinder);
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mp.release();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        isForeground = false;

        unregisterReceiver(controlReceiver);
        super.onDestroy();
    }

    private void updateMusicInfo(AbstractMusic music) {
        if (mLastPlayMusic != music) {
            Intent intent = new Intent(IMusicUpdateBoradCastManager.UpdateAction.UPDATE_MUSICINFO);
            intent.putExtra(IntentExtra.MP_MUSICINFO, music);

            sendBroadcast(intent);
        }
        mLastPlayMusic = music;
    }

    private void updatePlayStatus(int playStatus) {
        mPlayStatus = playStatus;

        Intent intent = new Intent(IMusicUpdateBoradCastManager.UpdateAction.UPDATE_STATUS);
        intent.putExtra(IntentExtra.MP_PLAY_STATUS, playStatus);

        sendBroadcast(intent);
    }

    private int  getDuration(){
        int duration = 0;
        if (isPrepared) {

            duration = mp.getDuration();
        } else {

            if (musicList.size() > musicIndex && musicIndex != -1) {
                duration = musicList.get(musicIndex).getDuration();
            }
        }
        return duration;
    }


    /**
     * 和上一次操作的歌曲不同，代表新播放的歌曲
     *
     * @param isNewPlayMusic
     */
    private void updatePlayBar(boolean isNewPlayMusic) {
        Intent intent = new Intent(PLAYBAR_UPDATE);
        intent.putExtra("isNewPlayMusic", isNewPlayMusic);

        sendBroadcast(intent);
    }

    private void updatePlayStaute(boolean isPlaying) {
        Intent intent = new Intent(PLAY_STATUS_UPDATE);
        intent.putExtra("isPlaying", isPlaying);

        sendBroadcast(intent);
    }

    /**
     * 准备音乐并播放
     *
     * @param music
     */
    private void prepareSong(final AbstractMusic music) {
        updateMusicInfo(music);
        updatePlayStatus(IPlayStatus.PLAYING);

        mNotifiControler.show(music);
        updatePlayBar(true);

        //如果是网络歌曲,而且未从网络获取详细信息，则需要获取歌曲的详细信息
        if (music.getType() == AbstractMusic.MusicType.Online) {
            final Song song = (Song) music;
            if (!song.preParePlayUrl()) {

                //同步请求到歌曲信息
                NetEaseAPI.getSongUrls(Arrays.asList(song.id), Bitrate.KBS_320, new UHttpHandler<MusicFileResp>() {
                            @Override
                            public void onSuccess(ServerTip serverTip, MusicFileResp musicFileResp) {
                                if (musicFileResp != null && Utils.canFetchFirst(musicFileResp.data)) {
                                    MusicFile musicFile = musicFileResp.data.get(0);
                                    if (StringUtils.isNotEmpty(musicFile.url)) {
                                        song.musicFile = musicFile;

                                        play(music);
                                    }
                                }
                            }
                        }
                );
            } else {
                play(music);
            }
        } else {
            play(music);
        }
    }

    private void play(AbstractMusic music) {
        if (mp != null) {
            isPrepared = false;
            mp.reset();
        }

        try {
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            Log.i(TAG, "datasoure:" + music.getDataSoure());
            mp.setDataSource(getBaseContext(), music.getDataSoure());

            mp.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        AbstractMusic music = null;
        try {
            music = mBinder.getNowPlayingSong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Message msg = Message.obtain();
        msg.what = MSG_BUFFER_UPDATE;
        if(percent == 100) {
            msg.arg1 = music.getDuration();
        }else {
            msg.arg1 = percent * music.getDuration() / 100;
        }

        handler.sendMessage(msg);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        try {
            Log.i(TAG, "onCompletion");

            mBinder.nextSong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    static MediaPlayer getMediaPlayer(Context context) {

        MediaPlayer mediaplayer = new MediaPlayer();

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
            return mediaplayer;
        }

        try {
            Class<?> cMediaTimeProvider = Class.forName("android.media.MediaTimeProvider");
            Class<?> cSubtitleController = Class.forName("android.media.SubtitleController");
            Class<?> iSubtitleControllerAnchor = Class.forName("android.media.SubtitleController$Anchor");
            Class<?> iSubtitleControllerListener = Class.forName("android.media.SubtitleController$Listener");

            Constructor constructor = cSubtitleController.getConstructor(new Class[]{Context.class, cMediaTimeProvider, iSubtitleControllerListener});

            Object subtitleInstance = constructor.newInstance(context, null, null);

            Field f = cSubtitleController.getDeclaredField("mHandler");

            f.setAccessible(true);
            try {
                f.set(subtitleInstance, new Handler());
            } catch (IllegalAccessException e) {
                return mediaplayer;
            } finally {
                f.setAccessible(false);
            }

            Method setsubtitleanchor = mediaplayer.getClass().getMethod("setSubtitleAnchor", cSubtitleController, iSubtitleControllerAnchor);

            setsubtitleanchor.invoke(mediaplayer, subtitleInstance, null);
            //Log.e("", "subtitle is setted :p");
        } catch (Exception e) {
        }

        return mediaplayer;
    }


}
