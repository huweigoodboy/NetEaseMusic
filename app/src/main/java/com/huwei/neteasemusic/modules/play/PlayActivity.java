package com.huwei.neteasemusic.modules.play;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.huwei.neteasemusic.BaseActivity;
import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.bean.AbstractMusic;
import com.huwei.neteasemusic.inter.IMusicUpdate;
import com.huwei.neteasemusic.manager.MusicManager;
import com.huwei.neteasemusic.util.ToolBarUtil;

/**
 * @author jerry
 * @date 2016/07/11
 */
public class PlayActivity extends BaseActivity implements IMusicUpdate{

    public static Intent getStartActIntent(Context from){
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

        addMpUpdateListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        //主动更新一下 面板信息
        updateMusicInfo(MusicManager.get().getNowPlayingSong());
    }

    private void initToolBar(){
        mToolBar = (Toolbar) findViewById(R.id.toolbar);

        ToolBarUtil.setBackStyle(this,mToolBar);
        mToolBar.setTitle("Name");
        mToolBar.setSubtitle("Artist");
        mToolBar.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    @Override
    public void updateProgress(int currentTime, int bufferTime, int duration) {

    }

    @Override
    public void updatePlayStatus(int playStatus) {

    }

    @Override
    public void updateMusicInfo(AbstractMusic music) {
        mToolBar.setTitle(music.getName());
        mToolBar.setSubtitle(music.getArtist());
    }
}
