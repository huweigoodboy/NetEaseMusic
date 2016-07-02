package com.huwei.neteasemusic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.base.BaseViewHolder;
import com.huwei.neteasemusic.adapter.base.HeaderBottomAdapter;
import com.huwei.neteasemusic.ui.widget.DrawableTextView;

/**
 * @author jerry
 * @date 2016/07/02
 */
public class SuggestAdapter extends HeaderBottomAdapter<String,SuggestAdapter.SuggesetViewHolder> {

    public SuggestAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public SuggesetViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        return new SuggesetViewHolder(mLayoutInflater.inflate(R.layout.item_suggest,null,true));
    }

    @Override
    protected void bindItemData(SuggesetViewHolder viewHolder, String data, int position) {

    }

    public class SuggesetViewHolder extends BaseViewHolder{

        private DrawableTextView tv_suggest;

        public SuggesetViewHolder(View itemView) {
            super(itemView);

            tv_suggest = (DrawableTextView) itemView;
        }
    }
}
