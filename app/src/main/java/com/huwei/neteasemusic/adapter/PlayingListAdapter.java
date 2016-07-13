package com.huwei.neteasemusic.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.base.BaseRecyclerAdapter;
import com.huwei.neteasemusic.adapter.base.BaseViewHolder;
import com.huwei.neteasemusic.bean.AbstractMusic;
import com.huwei.neteasemusic.bean.Song;
import com.huwei.neteasemusic.manager.MusicManager;
import com.huwei.neteasemusic.util.DensityUtil;
import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.Utils;

/**
 * @author jerry
 * @date 2016/07/14
 */
public class PlayingListAdapter extends BaseRecyclerAdapter<AbstractMusic, PlayingListAdapter.PlayingListViewHolder> {

    public PlayingListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public PlayingListViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        return new PlayingListViewHolder(mLayoutInflater.inflate(R.layout.item_playing_list,null,true));
    }

    @Override
    protected void bindItemData(PlayingListViewHolder viewHolder, AbstractMusic music, final int position) {
        if (music != null) {

            String title = music.getName() + "-" + music.getArtist();
            SpannableString spannableString = new SpannableString(title);
            spannableString.setSpan(new AbsoluteSizeSpan(DensityUtil.sp2px(mContext, 16)), 0, music.getName().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

            if (position == MusicManager.get().getNowPlayingIndex()) {
                viewHolder.ivDelete.setVisibility(View.VISIBLE);
                viewHolder.ivPlaying.setVisibility(View.VISIBLE);

                spannableString.setSpan(new ForegroundColorSpan(Utils.getResources().getColor(R.color.CD3D3A)), 0, title.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {
                viewHolder.ivDelete.setVisibility(View.GONE);
                viewHolder.ivPlaying.setVisibility(View.GONE);

                spannableString.setSpan(new ForegroundColorSpan(Utils.getResources().getColor(R.color.primaryTextColor)), 0, music.getName().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }

            if (StringUtils.isNotEmpty(title)) {
                viewHolder.tvTitle.setText(spannableString);
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MusicManager.get().preparePlayingListAndPlay(position, getDataList());
                }
            });
        }
    }

    public class PlayingListViewHolder extends BaseViewHolder {

        TextView tvTitle;
        ImageView ivDelete;
        ImageView ivPlaying;

        public PlayingListViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) findViewById(R.id.tv_title);
            ivPlaying = (ImageView) findViewById(R.id.iv_playing);
            ivDelete = (ImageView) findViewById(R.id.iv_delete);
        }
    }
}
