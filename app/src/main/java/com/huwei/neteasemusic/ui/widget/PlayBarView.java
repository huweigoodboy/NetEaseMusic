package com.huwei.neteasemusic.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huwei.neteasemusic.BaseActivity;
import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.bean.AbstractMusic;
import com.huwei.neteasemusic.inter.IMusicUpdate;
import com.huwei.neteasemusic.inter.IPlayStatus;
import com.huwei.neteasemusic.manager.ImageLoader;
import com.huwei.neteasemusic.manager.MusicManager;
import com.huwei.neteasemusic.modules.play.PlayActivity;
import com.huwei.neteasemusic.ui.menu.PlayingListActivity;
import com.huwei.neteasemusic.util.StringUtils;

/**
 * e
 *
 * @author jerry
 * @date 2016-06-27
 */
public class PlayBarView extends RelativeLayout implements IMusicUpdate, View.OnClickListener {

    private Context mContext;
    private ImageView mIvPic;
    private ImageView mIvNext;
    private CheckableImageView mIvPlay;
    private ImageView mIvList;
    private TextView mTvTitle;
    private TextView mTvArtist;
    private ProgressBar mProgressBar;

    private View mViewContainer;

    private ImageLoader mImageLoader;

    private MusicManager mMusicManager;

    public PlayBarView(Context context) {
        this(context, null);
    }

    public PlayBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        LayoutInflater.from(mContext).inflate(R.layout.layout_play_bottom_bar, this, true);
        initView();
        initListener();

        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).addMpUpdateListener(this);
            mImageLoader = ((BaseActivity) mContext).getImageLoader();
        } else {
            mImageLoader = ImageLoader.get(mContext);
        }

        mMusicManager = MusicManager.get();


        //自动调用一下更新状态信息
        updateMusicInfo(mMusicManager.getNowPlayingSong());
        updatePlayStatus(mMusicManager.getPlayStatus());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_container:
                if(mContext instanceof Activity){
                    mMusicManager.launchPlayerAct((Activity)mContext);
                }
                break;
            case R.id.iv_play:
                if (mMusicManager.isPlaying()) {
                    mMusicManager.pause();
                } else {
                    mMusicManager.play();
                }
                break;
            case R.id.iv_next:
                mMusicManager.nextSong();
                break;
            case R.id.iv_list:
                if(mContext instanceof Activity){
                    ((Activity)mContext).startActivity(PlayingListActivity.getStartActIntent(mContext));
                }
                break;
        }
    }

    void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTvArtist = (TextView) findViewById(R.id.tv_artist);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvList = (ImageView) findViewById(R.id.iv_list);
        mIvPlay = (CheckableImageView) findViewById(R.id.iv_play);
        mIvNext = (ImageView) findViewById(R.id.iv_next);
        mIvPic = (ImageView) findViewById(R.id.iv_album);

        mViewContainer = findViewById(R.id.rl_container);
    }

    void initListener() {
        mViewContainer.setOnClickListener(this);
        mIvPlay.setOnClickListener(this);
        mIvNext.setOnClickListener(this);
        mIvList.setOnClickListener(this);
    }

    @Override
    public void updateProgress(int currentTime, int bufferTime, int duration) {
        mProgressBar.setMax(duration);
        mProgressBar.setProgress(currentTime);
    }

    @Override
    public void updatePlayStatus(int playStatus) {
        mIvPlay.setChecked(playStatus == IPlayStatus.PLAYING);
    }

    @Override
    public void updateMusicInfo(AbstractMusic music) {

        if(music != null) {
            setVisibility(VISIBLE);
            mImageLoader.loadImage(mIvPic, music.getAlbumPic());
            if (StringUtils.isNotEmpty(music.getName())) {
                mTvTitle.setText(music.getName());
            }

            if (StringUtils.isNotEmpty(music.getArtist())) {
                mTvArtist.setText(music.getArtist());
            }
        }else{
            setVisibility(GONE);
        }
    }
}
