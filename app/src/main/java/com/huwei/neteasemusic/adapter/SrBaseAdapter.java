package com.huwei.neteasemusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;

import com.huwei.neteasemusic.adapter.base.HeaderBottomAdapter;

/**
 * @author jerry
 * @date 2016/07/04
 */
public abstract class SrBaseAdapter<T, VH extends RecyclerView.ViewHolder> extends HeaderBottomAdapter<T,VH> {

    protected String mKeyword;

    public SrBaseAdapter(Context mContext) {
        super(mContext);
    }

    public void setKeyword(String mKeyword) {
        this.mKeyword = mKeyword;
    }

    public SpannableString getSpannable(String str){
        SpannableString spannableString = new SpannableString(str);

        //
        int index = str.indexOf(mKeyword);
        return spannableString;
    }
}
