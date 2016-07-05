package com.huwei.neteasemusic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.base.BaseViewHolder;
import com.huwei.neteasemusic.adapter.base.HeaderBottomAdapter;
import com.huwei.neteasemusic.bean.Artist;
import com.huwei.neteasemusic.bean.resp.SrSongListResp;
import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.Utils;

/**
 * @author jerry
 * @date 2016/07/04
 */
public class SrArtistListAdapter extends HeaderBottomAdapter<Artist, SrArtistListAdapter.SrSongListViewHolder> {

    public SrArtistListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public SrSongListViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        return new SrSongListViewHolder(mLayoutInflater.inflate(R.layout.item_sr_artist, null, true));
    }

    @Override
    protected void bindItemData(SrSongListViewHolder viewHolder, Artist artist, int position) {
        if (artist != null) {
            String showText = artist.name;
            if (Utils.canFetchFirst(artist.alias)) {
                showText += "(" + artist.alias.get(0) + ")";
            }

            if(StringUtils.isNotEmpty(showText)) {
                viewHolder.tvArtist.setText(showText);
            }
        }
    }

    public class SrSongListViewHolder extends BaseViewHolder {

        ImageView ivArtist;
        TextView tvArtist;

        public SrSongListViewHolder(View itemView) {
            super(itemView);

            ivArtist = (ImageView) findViewById(R.id.iv_artist);
            tvArtist = (TextView) findViewById(R.id.tv_artist);
        }
    }
}
