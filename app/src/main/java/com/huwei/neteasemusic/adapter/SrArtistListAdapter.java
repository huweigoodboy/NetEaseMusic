package com.huwei.neteasemusic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.base.BaseViewHolder;
import com.huwei.neteasemusic.adapter.base.HeaderBottomAdapter;
import com.huwei.neteasemusic.bean.resp.SrSongListResp;

/**
 * @author jerry
 * @date 2016/07/04
 */
public class SrArtistListAdapter extends HeaderBottomAdapter<SrSongListResp, SrArtistListAdapter.SrSongListViewHolder> {

    public SrArtistListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public SrSongListViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        return new SrSongListViewHolder(mLayoutInflater.inflate(R.layout.item_sr_artist, null, true));
    }

    @Override
    protected void bindItemData(SrSongListViewHolder viewHolder, SrSongListResp data, int position) {
        if (data != null) {

        }
    }

    public class SrSongListViewHolder extends BaseViewHolder {
        public SrSongListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
