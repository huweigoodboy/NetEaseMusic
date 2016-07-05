package com.huwei.neteasemusic.modules.search;

import com.huwei.neteasemusic.adapter.SrArtistListAdapter;
import com.huwei.neteasemusic.bean.resp.NetEaseAPI;
import com.huwei.neteasemusic.bean.resp.ServerTip;
import com.huwei.neteasemusic.bean.resp.SrArtistListResp;
import com.huwei.neteasemusic.util.network.HttpHandler;

/**
 * @author jerry
 * @date 2016/07/04
 */
public class SrArtistListFragment extends SrBaseFragment{

    private SrArtistListAdapter mAdapter;

    @Override
    public void initView() {
        super.initView();

        mAdapter = new SrArtistListAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSearch(final String keyword) {
        super.onSearch(keyword);

        NetEaseAPI.searchArtist(keyword, 0, 15, new HttpHandler<SrArtistListResp>() {
            @Override
            public void onSuccess(ServerTip serverTip, SrArtistListResp srArtistListResp) {
                if(srArtistListResp !=null){
                    mAdapter.setKeyword(keyword);
                    mAdapter.setDataList(srArtistListResp.artists);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
