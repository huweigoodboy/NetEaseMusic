package com.huwei.neteasemusic.modules.search;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.SrPlayListAdapter;
import com.huwei.neteasemusic.bean.resp.NetEaseAPI;
import com.huwei.neteasemusic.bean.resp.ServerTip;
import com.huwei.neteasemusic.bean.resp.SrPlayListResp;
import com.huwei.neteasemusic.main.BaseFragment;
import com.huwei.neteasemusic.ui.widget.SearchBar;
import com.huwei.neteasemusic.util.network.HttpHandler;

/**
 * @author jerry
 * @date 2016/07/04
 */
public class SrPlayListFragment extends SrBaseFragment{

    private SrPlayListAdapter mAdapter;

    @Override
    public void initView() {
        super.initView();

        mAdapter = new SrPlayListAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSearch(final String keyword) {
        super.onSearch(keyword);

        NetEaseAPI.searchPlaylist(keyword, 0, 15, new HttpHandler<SrPlayListResp>() {
            @Override
            public void onSuccess(ServerTip serverTip, SrPlayListResp srPlayListResp) {
                mAdapter.setKeyword(keyword);
                mAdapter.setDataList(srPlayListResp.playlists);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
