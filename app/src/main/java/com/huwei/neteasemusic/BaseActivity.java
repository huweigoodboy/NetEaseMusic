package com.huwei.neteasemusic;

/**
 *
 * @author jerry
 * @date 2016-06-23
 */
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.huwei.neteasemusic.util.Utils;

public class BaseActivity extends AppCompatActivity {

    private Context mContext;

    protected Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);

        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //如果需要actionbar  在布局中加入actionbar
        if (isNeedToolBar()) {
            initToolBar();

            ViewGroup decor = (ViewGroup) getWindow().getDecorView();
            LinearLayout linearLayout = (LinearLayout) decor.getChildAt(0);

            View statuebarSpaceView = new View(mContext);
            LinearLayout.LayoutParams spacelayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.getStatusBarHeight());
            linearLayout.addView(statuebarSpaceView,0,spacelayoutParams);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            linearLayout.addView(mToolBar, 1, layoutParams);

            onInitToolbar(mToolBar);
        }
    }

    private void initToolBar() {
        mToolBar = (Toolbar) LayoutInflater.from(mContext).inflate(R.layout.toolbar,null,true);
        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
        }
    }


    /*
   * 是否需要actionbar
    */
    protected boolean isNeedToolBar() {
        return true;
    }


    /**
     * 自定义actionbar 此方法肯定在onCreate()之后调用
     *
     * @param toolbar
     */
    protected void onInitToolbar(Toolbar toolbar) {

    }
}