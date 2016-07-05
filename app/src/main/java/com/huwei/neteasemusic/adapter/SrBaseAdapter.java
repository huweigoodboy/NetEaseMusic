package com.huwei.neteasemusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.base.HeaderBottomAdapter;
import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.Utils;

/**
 * @author jerry
 * @date 2016/07/04
 */
public abstract class SrBaseAdapter<T, VH extends RecyclerView.ViewHolder> extends HeaderBottomAdapter<T, VH> {

    protected String mKeyword;

    public SrBaseAdapter(Context mContext) {
        super(mContext);
    }

    public void setKeyword(String mKeyword) {
        this.mKeyword = mKeyword;
    }

    public SpannableString getSpannable(String str) {
        SpannableString spannableString = new SpannableString(str);
        //目前算法 只检测 一个关键字
        if (StringUtils.isNotEmpty(mKeyword)) {
            int index = str.indexOf(mKeyword);
            if (index != -1) {
                spannableString.setSpan(new ForegroundColorSpan(Utils.getResources().getColor(R.color.color_417BBA)), index, index + mKeyword.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }
}
