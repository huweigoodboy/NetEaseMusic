package com.huwei.neteasemusic.modules.search;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.SrAlbumListAdapter;
import com.huwei.neteasemusic.bean.resp.NetEaseAPI;
import com.huwei.neteasemusic.bean.resp.ServerTip;
import com.huwei.neteasemusic.bean.resp.SrAlbumListResp;
import com.huwei.neteasemusic.main.BaseFragment;
import com.huwei.neteasemusic.ui.widget.SearchBar;
import com.huwei.neteasemusic.util.network.HttpHandler;

/**
 * @author jerry
 * @date 2016/07/04
 */
public class SrAlbumListFragment extends SrBaseFragment{

    private SrAlbumListAdapter mAdapter;

    @Override
    public void initView() {
        super.initView();

        mAdapter = new SrAlbumListAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSearch(String keyword) {
        super.onSearch(keyword);

        NetEaseAPI.searchAlbum(keyword, 0, 15, new HttpHandler<SrAlbumListResp>() {
            @Override
            public void onSuccess(ServerTip serverTip, SrAlbumListResp srAlbumListResp) {
                mAdapter.setDataList(srAlbumListResp.albums);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
