package com.huwei.neteasemusic.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.base.BaseViewHolder;
import com.huwei.neteasemusic.bean.Song;
import com.huwei.neteasemusic.util.Utils;


/**
 * @author jerry
 * @date 2016/07/04
 */
public class SrSongListAdapter extends SrBaseAdapter<Song, SrSongListAdapter.SrSongListViewHolder> {

    public SrSongListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public SrSongListViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        return new SrSongListViewHolder(mLayoutInflater.inflate(R.layout.item_sr_song, null, true));
    }

    @Override
    protected void bindItemData(SrSongListViewHolder viewHolder, Song song, int position) {
        if (song != null) {
            viewHolder.tvName.setText(song.name);
            if (Utils.canFetchFirst(song.artists) && song.album != null) {
                viewHolder.tvShow.setText(song.artists.get(0).name + " - " + song.album.name);
            }
            if (Utils.canFetchFirst(song.alias)) {
                viewHolder.tvAlias.setVisibility(View.VISIBLE);

                viewHolder.tvAlias.setText(song.alias.get(0));
            } else {
                viewHolder.tvAlias.setVisibility(View.GONE);
            }

            //mv
            if(song.mvid !=0){
                viewHolder.ivMv.setVisibility(View.VISIBLE);
            }else{
                viewHolder.ivMv.setVisibility(View.GONE);
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public class SrSongListViewHolder extends BaseViewHolder {

        TextView tvName, tvShow, tvAlias;
        ImageView ivMore,ivMv;

        public SrSongListViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) findViewById(R.id.tv_name);
            tvShow = (TextView) findViewById(R.id.tv_show);
            tvAlias = (TextView) findViewById(R.id.tv_alias);
            ivMore = (ImageView) findViewById(R.id.iv_more);
            ivMv = (ImageView) findViewById(R.id.iv_mv);
        }
    }
}