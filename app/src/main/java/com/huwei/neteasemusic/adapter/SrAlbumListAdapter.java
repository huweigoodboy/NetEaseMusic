package com.huwei.neteasemusic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.base.BaseViewHolder;
import com.huwei.neteasemusic.adapter.base.HeaderBottomAdapter;
import com.huwei.neteasemusic.bean.Album;
import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.Utils;

/**
 * @author jerry
 * @date 2016/07/04
 */
public class SrAlbumListAdapter extends SrBaseAdapter<Album, SrAlbumListAdapter.SrAlbumListViewHolder> {

    public SrAlbumListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public SrAlbumListViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        return new SrAlbumListViewHolder(mLayoutInflater.inflate(R.layout.item_sr_album, null, true));
    }

    @Override
    protected void bindItemData(SrAlbumListViewHolder viewHolder, Album album, int position) {
        if (album != null) {
            getImageLoader().loadImage(viewHolder.ivAlbum, album.picUrl);

            String name = album.name;
            if (Utils.canFetchFirst(album.alias)) {
                name += "(" + album.alias.get(0) + ")";
            }

            if (StringUtils.isNotEmpty(name)) {
                viewHolder.tvName.setText(getSpannable(name));
            }

            if (Utils.canFetchFirst(album.artists) && StringUtils.isNotEmpty(album.artists.get(0).name)) {
                viewHolder.tvArtist.setText(getSpannable(album.artists.get(0).name));
            }

            viewHolder.tvOnsale.setVisibility(album.onSale ? View.VISIBLE : View.GONE);
        }
    }

    public class SrAlbumListViewHolder extends BaseViewHolder {

        ImageView ivAlbum;
        TextView tvName, tvArtist, tvOnsale;


        public SrAlbumListViewHolder(View itemView) {
            super(itemView);

            ivAlbum = (ImageView) findViewById(R.id.iv_album);
            tvName = (TextView) findViewById(R.id.tv_name);
            tvArtist = (TextView) findViewById(R.id.tv_artist);
            tvOnsale = (TextView) findViewById(R.id.tv_rcd);
        }
    }
}
