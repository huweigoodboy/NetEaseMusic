package com.huwei.neteasemusic.ui.popwindow;

import android.content.Context;


import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.ui.popwindow.base.BasePopupWindow;

/**
 * @author jerry
 * @date 2016/07/01
 */
public class SuggestPopWindow extends BasePopupWindow {
    public SuggestPopWindow(Context context) {
        super(context);

        setContentView(R.layout.pop_suggest);
        setOutSideDismiss();
    }


}
