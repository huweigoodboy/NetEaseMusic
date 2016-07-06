package com.huwei.neteasemusic.modules.search;

import android.support.v7.widget.GridLayoutManager;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.SrMvListAdapter;
import com.huwei.neteasemusic.bean.resp.NetEaseAPI;
import com.huwei.neteasemusic.bean.resp.ServerTip;
import com.huwei.neteasemusic.bean.resp.SrMvListResp;
import com.huwei.neteasemusic.ui.widget.divider.DividerGridItemDecoration;
import com.huwei.neteasemusic.util.DividerUtils;
import com.huwei.neteasemusic.util.Utils;
import com.huwei.neteasemusic.util.network.HttpHandler;

/**
 * @author jerry
 * @date 2016/07/04
 */
public class SrMvListFrament extends SrBaseFragment {

    private SrMvListAdapter mAdapter;

    @Override
    public void initView() {
        super.initView();

        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(
                DividerUtils.getDrawableByDimen(R.dimen.mv_divider_height, R.dimen.mv_divider_width)
                ,true,true,true,true));

        mAdapter = new SrMvListAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSearch(final String keyword) {
        super.onSearch(keyword);

        NetEaseAPI.searchMv(keyword, 0, 15, new HttpHandler<SrMvListResp>() {
            @Override
            public void onSuccess(ServerTip serverTip, SrMvListResp srMvListResp) {
                mAdapter.setKeyword(keyword);
                mAdapter.setDataList(srMvListResp.mvs);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
