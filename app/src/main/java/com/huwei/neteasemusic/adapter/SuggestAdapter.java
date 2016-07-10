package com.huwei.neteasemusic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.base.BaseViewHolder;
import com.huwei.neteasemusic.adapter.base.HeaderBottomAdapter;
import com.huwei.neteasemusic.bean.SuggestItem;
import com.huwei.neteasemusic.ui.widget.DrawableTextView;
import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.Utils;

/**
 * @author jerry
 * @date 2016/07/02
 */
public class SuggestAdapter extends HeaderBottomAdapter<SuggestItem, SuggestAdapter.SuggesetViewHolder> {

    public SuggestAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public SuggesetViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        return new SuggesetViewHolder(mLayoutInflater.inflate(R.layout.item_suggest, null, true));
    }

    @Override
    protected void bindItemData(SuggesetViewHolder viewHolder, SuggestItem data, int position) {
        if (data != null) {
            if(StringUtils.isNotEmpty(data.getName())) {
                viewHolder.tv_suggest.setText(data.getName());
            }
        }
    }

    public class SuggesetViewHolder extends BaseViewHolder {

        private DrawableTextView tv_suggest;

        public SuggesetViewHolder(View itemView) {
            super(itemView);

            tv_suggest = (DrawableTextView) itemView;

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.getResources().getDimensionPixelOffset(R.dimen.suggest_item_height));
            tv_suggest.setLayoutParams(params);
        }
    }
}
