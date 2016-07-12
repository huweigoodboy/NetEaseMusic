package com.huwei.neteasemusic.modules.play;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.huwei.neteasemusic.BaseActivity;
import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.bean.AbstractMusic;
import com.huwei.neteasemusic.inter.IMusicUpdate;
import com.huwei.neteasemusic.inter.IPlayStatus;
import com.huwei.neteasemusic.manager.MusicManager;
import com.huwei.neteasemusic.ui.widget.CheckableImageView;
import com.huwei.neteasemusic.util.TimeUtil;
import com.huwei.neteasemusic.util.ToolBarUtil;

/**
 * @author jerry
 * @date 2016/07/11
 */
public class PlayActivity extends BaseActivity implements IMusicUpdate ,View.OnClickListener{

    private ImageView mIvBlur;
    private Toolbar mToolbar;
    private CheckableImageView mIvHeart;
    private ImageView mIvDownload;
    private ImageView mIvCmt;
    private ImageView mIvMore;
    private TextView mTvTimeCurrent;
    private SeekBar mSeekbar;
    private TextView mTvTimeDuration;
    private ImageView mIvLoopMode;
    private ImageView mIvPlayPrev;
    private CheckableImageView mIvPlay;
    private ImageView mIvPlayNext;
    private ImageView mIvPlaySrc;

    public static Intent getStartActIntent(Context from) {
        Intent intent = new Intent(from,PlayActivity.class);
        return intent;
    }

    @Override
    protected boolean isNeedToolBar() {
        return false;
    }

    @Override
    protected boolean isNeedDrawStatusBar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play);

        initToolBar();
        initView();

        initListener();

        addMpUpdateListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        //主动更新一下 面板信息
        updateMusicInfo(MusicManager.get().getNowPlayingSong());
        updatePlayStatus(MusicManager.get().getPlayStatus());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_play:
                MusicManager.get().toggle();
                break;
            case R.id.iv_play_next:
                MusicManager.get().nextSong();
                break;
            case R.id.iv_play_prev:
                MusicManager.get().preSong();
                break;
        }
    }

    private void initToolBar(){
        mToolBar = (Toolbar) findViewById(R.id.toolbar);

        ToolBarUtil.setBackStyle(this,mToolBar);
        mToolBar.setTitle("Name");
        mToolBar.setSubtitle("Artist");
        mToolBar.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    private void initView(){
        mIvPlaySrc = (ImageView) findViewById(R.id.iv_play_src);
        mIvPlayNext = (ImageView) findViewById(R.id.iv_play_next);
        mIvPlay = (CheckableImageView) findViewById(R.id.iv_play);
        mIvPlayPrev = (ImageView) findViewById(R.id.iv_play_prev);
        mIvLoopMode = (ImageView) findViewById(R.id.iv_loop_mode);
        mTvTimeDuration = (TextView) findViewById(R.id.tv_time_duration);
        mSeekbar = (SeekBar) findViewById(R.id.seekbar);
        mTvTimeCurrent = (TextView) findViewById(R.id.tv_time_current);
        mIvMore = (ImageView) findViewById(R.id.iv_more);
        mIvCmt = (ImageView) findViewById(R.id.iv_cmt);
        mIvDownload = (ImageView) findViewById(R.id.iv_download);
        mIvHeart = (CheckableImageView) findViewById(R.id.iv_heart);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mIvBlur = (ImageView) findViewById(R.id.iv_blur);
    }



    private void initListener(){
        mIvPlayNext.setOnClickListener(this);
        mIvPlay.setOnClickListener(this);
        mIvPlayPrev.setOnClickListener(this);
    }

    @Override
    public void updateProgress(int currentTime, int bufferTime, int duration) {
        mSeekbar.setMax(duration);
        mSeekbar.setProgress(currentTime);
        mSeekbar.setSecondaryProgress(bufferTime);

        //时间处理
        mTvTimeCurrent.setText(TimeUtil.getDuration(currentTime));
        mTvTimeDuration.setText(TimeUtil.getDuration(duration));
    }

    @Override
    public void updatePlayStatus(int playStatus) {
        mIvPlay.setChecked(playStatus == IPlayStatus.PLAYING);
    }

    @Override
    public void updateMusicInfo(AbstractMusic music) {
        //加载模糊图片
        music.loadBlurPic(mIvBlur);

        mToolBar.setTitle(music.getName());
        mToolBar.setSubtitle(music.getArtist());

        mTvTimeDuration.setText(TimeUtil.getDuration(music.getDuration()));
    }
}
