package com.huwei.neteasemusic;

/**
 * @author jerry
 * @date 2016-06-23
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.huwei.neteasemusic.util.StatusBarUtil;
import com.huwei.neteasemusic.util.Utils;


public class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    protected Toolbar mToolBar;

    protected Handler mHandler;

    public static final int STATUS_BAR_COLOR = Utils.getResources().getColor(R.color.CD3D3A);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (isDrawLayout()) {
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            if (rootView instanceof DrawerLayout) {
                StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) rootView, STATUS_BAR_COLOR);
            }
        } else {
            StatusBarUtil.setColor(this, STATUS_BAR_COLOR);
        }

        //如果需要actionbar  在布局中加入actionbar
        if (isNeedToolBar()) {
            initToolBar();

            ViewGroup decor = (ViewGroup) getWindow().getDecorView();
            LinearLayout linearLayout = (LinearLayout) decor.getChildAt(0);

            View statuebarSpaceView = new View(mContext);
            LinearLayout.LayoutParams spacelayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.getStatusBarHeight());
            linearLayout.addView(statuebarSpaceView, 0, spacelayoutParams);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            linearLayout.addView(mToolBar, 1, layoutParams);

            onInitToolbar(mToolBar);
        }
    }

    //判断是否应该隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public Handler getHandler() {
        if(mHandler == null){
            mHandler = new Handler();
        }
        return mHandler;
    }

    private void initToolBar() {
        mToolBar = (Toolbar) LayoutInflater.from(mContext).inflate(R.layout.toolbar, null, true);
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
     *
     * @return
     */
    protected boolean isDrawLayout() {
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