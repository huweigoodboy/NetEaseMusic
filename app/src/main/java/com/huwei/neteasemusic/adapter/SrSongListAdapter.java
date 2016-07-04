package com.huwei.neteasemusic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.base.BaseViewHolder;
import com.huwei.neteasemusic.bean.resp.SrSongListResp;

/**
 * @author jerry
 * @date 2016/07/04
 */
public class SrSongListAdapter extends SrBaseAdapter<SrSongListResp, SrSongListAdapter.SrSongListViewHolder> {

    public SrSongListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public SrSongListViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        return new SrSongListViewHolder(mLayoutInflater.inflate(R.layout.item_sr_song, null, true));
    }

    @Override
    protected void bindItemData(SrSongListViewHolder viewHolder, SrSongListResp data, int position) {
        if (data != null) {

        }
    }

    public class SrSongListViewHolder extends BaseViewHolder {

        TextView tvName, tvShow, tvAlias;
        ImageView ivMore;

        public SrSongListViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) findViewById(R.id.tv_name);
            tvShow = (TextView) findViewById(R.id.tv_show);
            tvAlias = (TextView) findViewById(R.id.tv_alias);
            ivMore = (ImageView) findViewById(R.id.iv_more);
        }
    }
}
