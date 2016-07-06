package com.huwei.neteasemusic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.base.BaseViewHolder;
import com.huwei.neteasemusic.bean.Mv;
import com.huwei.neteasemusic.bean.resp.SrSongListResp;

/**
 * @author jerry
 * @date 2016/07/04
 */
public class SrMvListAdapter extends SrBaseAdapter<Mv, SrMvListAdapter.SrMvViewHolder> {

    public SrMvListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public SrMvViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        return new SrMvViewHolder(mLayoutInflater.inflate(R.layout.item_sr_mv, null, true));
    }

    @Override
    protected void bindItemData(SrMvViewHolder viewHolder, Mv mv, int position) {
        if (mv != null) {
            viewHolder.tvPlayCount.setText(mv.getPlayCountStr());
            viewHolder.tvDuration.setText();
        }
    }

    public class SrMvViewHolder extends BaseViewHolder {

        TextView tvPlayCount, tvDuration, tvArtist, tvName;
        ImageView ivCover;

        public SrMvViewHolder(View itemView) {
            super(itemView);

            tvPlayCount = (TextView) findViewById(R.id.tv_playCount);
            tvDuration = (TextView) findViewById(R.id.tv_duration);
            tvArtist = (TextView) findViewById(R.id.tv_artist);
            tvName = (TextView) findViewById(R.id.tv_name);
            ivCover = (ImageView) findViewById(R.id.iv_cover);
        }
    }
}
