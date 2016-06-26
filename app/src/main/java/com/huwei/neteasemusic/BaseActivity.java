package com.huwei.neteasemusic;

/**
 *
 * @author jerry
 * @date 2016-06-23
 */
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huwei.neteasemusic.util.StatusBarUtil;
import com.huwei.neteasemusic.util.Utils;


public class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    protected Toolbar mToolBar;

    public static final int STATUS_BAR_COLOR = Utils.getResources().getColor(R.color.CD3D3A);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if(isDrawLayout()){
            ViewGroup rootView = (ViewGroup) ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
            if(rootView instanceof DrawerLayout){
                StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) rootView,STATUS_BAR_COLOR);
            }
        }else {
            StatusBarUtil.setColor(this,STATUS_BAR_COLOR);
        }

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
     * 是否是抽屉布局 如果是 沉浸状态栏需要特殊处理
     * @return
     */
    protected boolean isDrawLayout(){
        return false;
    }

    /**
     * 自定义actionbar 此方法肯定在onCreate()之后调用
     *
     * @param toolbar
     */
    protected void onInitToolbar(Toolbar toolbar) {

    }
}