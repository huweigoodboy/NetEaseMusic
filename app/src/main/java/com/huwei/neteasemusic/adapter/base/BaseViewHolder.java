package com.huwei.neteasemusic.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author jerry
 * @date 2016/03/24
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public View findViewById(int rid) {
        if (itemView != null) {
            return itemView.findViewById(rid);
        }
        return null;
    }
}
