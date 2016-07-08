package com.huwei.neteasemusic.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.huwei.neteasemusic.adapter.base.HeaderBottomAdapter;

/**
 * @author jerry
 * @date 2016/07/08
 */
public class HeaderBottomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private RecyclerView mRecyclerView;

    public HeaderBottomSpanSizeLookup(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    public int getSpanSize(int position) {

        if (mRecyclerView != null && mRecyclerView.getAdapter() != null) {
            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                int type = mRecyclerView.getAdapter().getItemViewType(position);
                if (type == HeaderBottomAdapter.TYPE_HEADER || type == HeaderBottomAdapter.TYPE_FOOTER) {
                    return ((GridLayoutManager)layoutManager).getSpanCount(); //独占一行
                } else {
                    return 1; //只占一行中的一列，
                }
            }
        }
        return 0;
    }
}
