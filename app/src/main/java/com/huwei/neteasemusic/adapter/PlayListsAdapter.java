package com.huwei.neteasemusic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.base.BaseViewHolder;
import com.huwei.neteasemusic.adapter.base.HeaderBottomAdapter;
import com.huwei.neteasemusic.bean.Playlist;
import com.huwei.neteasemusic.util.StringUtils;


/**
 * @author jerry
 * @date 2016/07/08
 */
public class PlayListsAdapter extends HeaderBottomAdapter<Playlist, PlayListsAdapter.PlaylistsViewHolder> {

    public PlayListsAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected boolean isNeedRipple() {
        return false;
    }

    @Override
    public PlaylistsViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        return new PlaylistsViewHolder(mLayoutInflater.inflate(R.layout.item_playlist_grid, null, true));
    }

    @Override
    protected void bindItemData(PlaylistsViewHolder viewHolder, Playlist playlist, int position) {
        if (playlist != null) {
            getImageLoader().loadImage(viewHolder.ivCover, playlist.coverImgUrl);

            if (StringUtils.isNotEmpty(playlist.name)) {
                viewHolder.tvName.setText(playlist.name);
            }

            if (playlist.creator != null && StringUtils.isNotEmpty(playlist.creator.nickname)) {
                viewHolder.tvNickName.setText(playlist.creator.nickname);
            }

            viewHolder.tvPlayCount.setText(String.valueOf(playlist.playCount));
        }
    }

    public class PlaylistsViewHolder extends BaseViewHolder {

        TextView tvPlayCount, tvNickName, tvName;
        ImageView ivCover;

        public PlaylistsViewHolder(View itemView) {
            super(itemView);

            tvPlayCount = (TextView) findViewById(R.id.tv_playCount);
            tvNickName = (TextView) findViewById(R.id.tv_nickname);
            tvName = (TextView) findViewById(R.id.tv_name);
            ivCover = (ImageView) findViewById(R.id.iv_cover);
        }
    }
}
