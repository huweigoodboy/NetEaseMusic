package com.huwei.neteasemusic.modules.search;

import com.huwei.neteasemusic.adapter.SrSongListAdapter;
import com.huwei.neteasemusic.bean.resp.NetEaseAPI;
import com.huwei.neteasemusic.bean.resp.ServerTip;
import com.huwei.neteasemusic.bean.resp.SrSongListResp;
import com.huwei.neteasemusic.util.network.HttpHandler;

/**
 * @author jerry
 * @date 2016/07/04
 */
public class SrSongListFragment extends SrBaseFragment{

    private SrSongListAdapter mAdapter;

    @Override
    public void onSearch(String keyword) {
        super.onSearch(keyword);

        NetEaseAPI.searchSong(keyword, 0, 15, new HttpHandler<SrSongListResp>() {
            @Override
            public void onSuccess(ServerTip serverTip, SrSongListResp srSongListResp) {
                mAdapter.setDataList(srSongListResp.songs);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initView() {
        super.initView();

        mAdapter = new SrSongListAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
    }
}
