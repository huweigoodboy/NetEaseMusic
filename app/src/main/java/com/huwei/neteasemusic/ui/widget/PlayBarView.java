package com.huwei.neteasemusic.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.huwei.neteasemusic.R;

/**
 * e
 *
 * @author jerry
 * @date 2016-06-27
 */
public class PlayBarView extends RelativeLayout {

    private Context mContext;

    public PlayBarView(Context context) {
        this(context, null);
    }

    public PlayBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        LayoutInflater.from(mContext).inflate(R.layout.layout_play_bottom_bar, this, true);
    }


}
