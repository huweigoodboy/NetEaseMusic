package com.huwei.neteasemusic.ui.menu;

import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;


import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.SwipeBackBottomActivity;
import com.huwei.neteasemusic.util.DensityUtil;

/**
 * @author jerry
 * @date 2016/07/08
 */
public class BaseBottomMenuActivity extends SwipeBackBottomActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme_BottomMenu);

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参值
        p.height = DensityUtil.dip2px(mContext,370);
        getWindow().setAttributes(p);
        getWindow().setGravity(Gravity.BOTTOM);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }
}
