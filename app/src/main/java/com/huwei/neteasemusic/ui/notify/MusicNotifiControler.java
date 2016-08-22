package com.huwei.neteasemusic.ui.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.huwei.neteasemusic.MainActivity;
import com.huwei.neteasemusic.NetEaseApplication;
import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.bean.AbstractMusic;
import com.huwei.neteasemusic.constant.IMusicAction;
import com.huwei.neteasemusic.manager.ImageLoader;
import com.huwei.neteasemusic.util.LogUtils;

/**
 *
 * @author jerry
 * @date 2016-06-23
 */
public class MusicNotifiControler implements IMusicAction {

    public static final String TAG = "MusicNotifiControler";

    public static final int NT_PLAYBAR_ID = 0;

    NotificationManager mNoticationManager;
    Notification mNotification;
    RemoteViews reViews;

    private Context mContext;

    public MusicNotifiControler(Context mContext) {
        this.mContext = mContext;
    }

    public void show(AbstractMusic music){
        if(mNoticationManager == null){
            mNoticationManager = (NotificationManager) NetEaseApplication.get().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if(mNotification == null){
            mNotification = new Notification();
        }

        String title = music.getName();
        String artist = music.getArtist();
        if (reViews == null) {
            reViews = new RemoteViews(NetEaseApplication.get().getPackageName(), R.layout.notification_play);
        }

        mNotification.icon = R.drawable.logo;
        mNotification.tickerText = title + "-" +artist;
        mNotification.when = System.currentTimeMillis();
        mNotification.flags  = Notification.FLAG_NO_CLEAR;
        mNotification.contentView = reViews;

        reViews.setTextViewText(R.id.title, title);
        reViews.setTextViewText(R.id.text, artist);

        reViews.setImageViewResource(R.id.img_album, R.drawable.img_album_background);

        Intent intent = new Intent(NetEaseApplication.get(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        reViews.setOnClickPendingIntent(R.id.nt_container, pendingIntent);

        Intent exitIntent = new Intent(PLAYPRO_EXIT);
        PendingIntent exitPendingIntent = PendingIntent.getBroadcast(mContext, 0, exitIntent, 0);
        reViews.setOnClickPendingIntent(R.id.button_exit_notification_play, exitPendingIntent);

        Intent nextInent = new Intent(NEXTSONG);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(mContext, 0, nextInent, 0);
        reViews.setOnClickPendingIntent(R.id.button_next_notification_play, nextPendingIntent);

        Intent preInent = new Intent(PRESONG);
        PendingIntent prePendingIntent = PendingIntent.getBroadcast(mContext, 0, preInent, 0);
        reViews.setOnClickPendingIntent(R.id.button_previous_notification_play, prePendingIntent);

        Intent playInent = new Intent(PLAY_OR_PASUE);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(mContext, 0, playInent, 0);
        reViews.setOnClickPendingIntent(R.id.button_play_notification_play, playPendingIntent);
        reViews.setOnClickPendingIntent(R.id.button_pause_notification_play, playPendingIntent);


        final NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setContent(reViews).setSmallIcon(NT_PLAYBAR_ID).setTicker(title).setOngoing(true);

        update(music);

        mNoticationManager.notify(NT_PLAYBAR_ID,mNotification);
    }

    void update(AbstractMusic music) {
        ImageLoader.get(mContext).loadImage(music.getAlbumPic(), new ImageLoader.SimpleImageLoadCallback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                LogUtils.i(TAG,"onSuccessLoad bitmap:"+bitmap);

                if(reViews!=null) {
                    reViews.setImageViewBitmap(R.id.img_album, bitmap);
                    mNoticationManager.notify(NT_PLAYBAR_ID,mNotification);
                }
            }
        });
    }

    public void setIsPlay(boolean isPlay){
        if(isPlay) {
            reViews.setViewVisibility(R.id.button_play_notification_play, View.GONE);
            reViews.setViewVisibility(R.id.button_pause_notification_play, View.VISIBLE);

            mNoticationManager.notify(NT_PLAYBAR_ID, mNotification);
        }else{
            reViews.setViewVisibility(R.id.button_play_notification_play, View.VISIBLE);
            reViews.setViewVisibility(R.id.button_pause_notification_play, View.GONE);

            mNoticationManager.notify(NT_PLAYBAR_ID, mNotification);
        }
    }

    public void cancel(){
       mNoticationManager.cancel(NT_PLAYBAR_ID);
    }
}
