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
import com.huwei.neteasemusic.bean.LrcContent;
import com.huwei.neteasemusic.bean.Song;
import com.huwei.neteasemusic.bean.resp.LrcResp;
import com.huwei.neteasemusic.bean.resp.NetEaseAPI;
import com.huwei.neteasemusic.bean.resp.ServerTip;
import com.huwei.neteasemusic.constant.ILrcStateContain;
import com.huwei.neteasemusic.inter.IMusicUpdate;
import com.huwei.neteasemusic.inter.IPlayStatus;
import com.huwei.neteasemusic.manager.MusicManager;
import com.huwei.neteasemusic.ui.widget.CheckableImageView;
import com.huwei.neteasemusic.ui.widget.LrcView;
import com.huwei.neteasemusic.util.LrcUtil;
import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.TimeUtil;
import com.huwei.neteasemusic.util.ToolBarUtil;
import com.huwei.neteasemusic.util.network.UHttpHandler;

import java.util.Collections;
import java.util.List;

/**
 * @author jerry
 * @date 2016/07/11
 */
public class PlayActivity extends BaseActivity implements IMusicUpdate, View.OnClickListener, ILrcStateContain {

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

    private LrcView playpage_lrcview;

    private AbstractMusic mNowMusic;

    public static Intent getStartActIntent(Context from) {
        Intent intent = new Intent(from, PlayActivity.class);
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
        switch (v.getId()) {
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

    private void initToolBar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);

        ToolBarUtil.setBackStyle(this, mToolBar);
        mToolBar.setTitle("Name");
        mToolBar.setSubtitle("Artist");
        mToolBar.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    private void initView() {
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

        playpage_lrcview = (LrcView) findViewById(R.id.playpage_lrcview);
    }


    private void initListener() {
        mIvPlayNext.setOnClickListener(this);
        mIvPlay.setOnClickListener(this);
        mIvPlayPrev.setOnClickListener(this);

        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    MusicManager.get().seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void updateProgress(int currentTime, int bufferTime, int duration) {
        mSeekbar.setMax(duration);
        mSeekbar.setProgress(currentTime);
        mSeekbar.setSecondaryProgress(bufferTime);

        //时间处理
        mTvTimeCurrent.setText(TimeUtil.getDuration(currentTime / 1000));
        mTvTimeDuration.setText(TimeUtil.getDuration(duration / 1000));

        updateLrcView(currentTime);
    }

    @Override
    public void updatePlayStatus(int playStatus) {
        mIvPlay.setChecked(playStatus == IPlayStatus.PLAYING);
    }

    @Override
    public void updateMusicInfo(AbstractMusic music) {
        if(music!=null) {
            //加载模糊图片
            music.loadBlurPic(mIvBlur);

            mToolBar.setTitle(music.getName());
            mToolBar.setSubtitle(music.getArtist());

            mTvTimeDuration.setText(TimeUtil.getDuration(music.getDuration()));

            if (mNowMusic != music) {
                mNowMusic = music;
                loadLrcView();
            }
        }
    }

    void loadLrcView() {
        AbstractMusic song = MusicManager.get().getNowPlayingSong();
        List<LrcContent> lrcLists = null;
        if (song instanceof Song) {
            loadLrcBySongId((Song) song);
        }
        lrcLists = LrcUtil.loadLrc(song);
        playpage_lrcview.setLrcLists(lrcLists);
        playpage_lrcview.setLrcState(lrcLists.size() == 0 ? READ_LOC_FAIL : READ_LOC_OK);
    }

    void updateLrcView(int currentTime) {
        int tempIndex = playpage_lrcview.getIndexByLrcTime(currentTime);
        if (tempIndex != playpage_lrcview.getIndex()) {
            playpage_lrcview.setIndex(tempIndex);
            playpage_lrcview.invalidate();
        }
    }

    //加载网络歌曲歌词
    private void loadLrcBySongId(final Song song) {
        if (song != null) {
            NetEaseAPI.getLrc(song.id, new UHttpHandler<LrcResp>() {
                @Override
                public void onSuccess(ServerTip serverTip, LrcResp lrcResp) {
                    if (lrcResp != null && lrcResp.lrc != null && StringUtils.isNotEmpty(lrcResp.lrc.lyric)) {
                        List<LrcContent> lrcLists = LrcUtil.parseLrcStr(lrcResp.lrc.lyric);
//                    // 按时间排序
//                        Collections.sort(lrcLists, new LrcComparator());
                        playpage_lrcview.setLrcLists(lrcLists);
                        playpage_lrcview.setLrcState(lrcLists.size() == 0 ? QUERY_ONLINE_NULL : QUERY_ONLINE_OK);

                        LrcUtil.writeLrcToLoc(song.getName(), song.getArtist(), lrcResp.lrc.lyric);
                    }
                }
            });
        }
    }
}
