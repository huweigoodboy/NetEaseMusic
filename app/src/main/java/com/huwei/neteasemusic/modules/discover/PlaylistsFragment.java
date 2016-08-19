package com.huwei.neteasemusic.modules.discover;

import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.RecyclerViewBaseFragment;
import com.huwei.neteasemusic.adapter.PlayListsAdapter;
import com.huwei.neteasemusic.bean.resp.NetEaseAPI;
import com.huwei.neteasemusic.bean.resp.PlaylistsResp;
import com.huwei.neteasemusic.bean.resp.ServerTip;
import com.huwei.neteasemusic.ui.HeaderBottomSpanSizeLookup;
import com.huwei.neteasemusic.ui.widget.divider.DividerGridItemDecoration;
import com.huwei.neteasemusic.util.DividerUtils;
import com.huwei.neteasemusic.util.network.UHttpHandler;


/**
 * 全部歌单
 *
 * @author jerry
 * @date 2016/07/08
 */
public class PlaylistsFragment extends RecyclerViewBaseFragment {

    private PlayListsAdapter mAdapter;

    private View mViewHeader;

    @Override
    public void initView() {
        super.initView();

        mAdapter = new PlayListsAdapter(mContext);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new HeaderBottomSpanSizeLookup(mRecyclerView));

        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(
                DividerUtils.getDrawableByDimen(R.dimen.mv_divider_height, R.dimen.mv_divider_width)
                , true, true, true, true));
        mRecyclerView.setAdapter(mAdapter);

        mViewHeader = LayoutInflater.from(mContext).inflate(R.layout.header_playlists, null, true);
        mAdapter.addHeader(mViewHeader);
    }

    @Override
    public void initData() {
        super.initData();

        NetEaseAPI.playlists("全部", "hot", 0, 15, new UHttpHandler<PlaylistsResp>() {
            @Override
            public void onSuccess(ServerTip serverTip, PlaylistsResp playlistsResp) {
                mAdapter.setDataList(playlistsResp.playlists);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
