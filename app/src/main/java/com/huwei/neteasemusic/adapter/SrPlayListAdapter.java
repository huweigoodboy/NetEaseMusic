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
import com.huwei.neteasemusic.bean.resp.SrSongListResp;
import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.Utils;

/**
 * @author jerry
 * @date 2016/07/04
 */
public class SrPlayListAdapter extends SrBaseAdapter<Playlist, SrPlayListAdapter.SrPlayListViewHolder> {

    public SrPlayListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public SrPlayListViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        return new SrPlayListViewHolder(mLayoutInflater.inflate(R.layout.item_sr_playlist, null, true));
    }

    @Override
    protected void bindItemData(SrPlayListViewHolder viewHolder, Playlist playlist, int position) {
        if (playlist != null) {
            getImageLoader().loadImage(viewHolder.ivCover, playlist.coverImgUrl);

            if (StringUtils.isNotEmpty(playlist.name)) {
                viewHolder.tvName.setText(getSpannable(playlist.name));
            }

            String cteratorName = "未知";
            if (playlist.creator != null && StringUtils.isNotEmpty(playlist.creator.nickname)) {
                cteratorName = playlist.creator.nickname;
            }
            viewHolder.tvInfo.setText(getSpannable(
                    Utils.getResources().getString(R.string.playlist_info, playlist.trackCount, cteratorName, playlist.getPlayCountStr()
                    )));
        }
    }

    public class SrPlayListViewHolder extends BaseViewHolder {

        ImageView ivCover;
        TextView tvName, tvInfo;

        public SrPlayListViewHolder(View itemView) {
            super(itemView);

            ivCover = (ImageView) findViewById(R.id.iv_cover);
            tvName = (TextView) findViewById(R.id.tv_name);
            tvInfo = (TextView) findViewById(R.id.tv_info);
        }
    }
}
