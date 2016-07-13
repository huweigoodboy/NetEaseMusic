package com.huwei.neteasemusic.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.PlayingListAdapter;
import com.huwei.neteasemusic.bean.AbstractMusic;
import com.huwei.neteasemusic.inter.IMusicUpdate;
import com.huwei.neteasemusic.manager.MusicManager;
import com.huwei.neteasemusic.ui.widget.divider.DividerItemDecoration;
import com.huwei.neteasemusic.util.DividerUtils;


/**
 * @author jerry
 * @date 2016/07/08
 */
public class PlayingListActivity extends BaseBottomMenuActivity implements IMusicUpdate {

    private TextView mTvPlayingCount;
    private RecyclerView mRecycleView;
    private PlayingListAdapter mAdapter;

    public static Intent getStartActIntent(Context from) {
        Intent intent = new Intent(from, PlayingListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu_playing_list);

        initView();
        initData();

        addMpUpdateListener(this);
    }

    void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.recycleView);
        mTvPlayingCount = (TextView) findViewById(R.id.tv_playing_count);

        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleView.addItemDecoration(new DividerItemDecoration(DividerUtils.getHorizontalLine()));

//        mRecycleView.setOnTouchListener(new View.OnTouchListener() {
//
//            int lastY;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        lastY = (int) event.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        break;
//                }
//
//                return false;
//            }
//        });
    }

    void initData() {
        mAdapter = new PlayingListAdapter(mContext);
        mRecycleView.setAdapter(mAdapter);

        updateMusicInfo(MusicManager.get().getNowPlayingSong());
    }

    @Override
    public void updateProgress(int currentTime, int bufferTime, int duration) {

    }

    @Override
    public void updatePlayStatus(int playStatus) {

    }

    @Override
    public void updateMusicInfo(AbstractMusic music) {
        mAdapter.setDataList(MusicManager.get().getPlayingList());
        mAdapter.notifyDataSetChanged();

        mTvPlayingCount.setText(getString(R.string.title_playing_count, MusicManager.get().getPlayingList().size()));
    }
}
