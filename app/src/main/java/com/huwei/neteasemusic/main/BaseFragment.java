package com.huwei.neteasemusic.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Fragment基类
 * 默认的Fragment不需要actionbar，特殊情况下需要actionbar请覆盖isNeedActionBar（）方法
 *
 * @author jerry
 * @date 2016/03/10
 */
public abstract class BaseFragment extends Fragment {

    protected String TAG;

    protected Context mContext;

    protected View mRootView;
    protected Toolbar mToolBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = getClass().getSimpleName();
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            View view = inflater.inflate(getContentResId(), null);
            if (isNeedToolBar()) {
                mRootView = new LinearLayout(mContext);
                ((LinearLayout) mRootView).setOrientation(LinearLayout.VERTICAL);

                //加入actionbar
                initActionBar();

                LinearLayout.LayoutParams layoutParamsOfActionBar = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams layoutParamsOfContent = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                ((LinearLayout) mRootView).addView(mToolBar, layoutParamsOfActionBar);
                ((LinearLayout) mRootView).addView(view, layoutParamsOfContent);

                beforeInit();
                initExtras();
                initView();
                initData();
                initListener();
                onInitActionbar(mToolBar);
            } else {
                mRootView = view;
                beforeInit();
                initExtras();
                initView();
                initData();
                initListener();
            }
        }
        return mRootView;
    }

    /**
     * 获取内容的布局文件
     *
     * @return
     */
    public abstract
    @LayoutRes
    int getContentResId();

    /**
     * 在initView之前调用的方法 一般用于特殊扩展
     */
    public void beforeInit() {

    }

    /**
     * 初始化获取参数
     */
    public void initExtras() {

    }

    /**
     * 初始化view
     */
    public abstract void initView();

    /**
     * 初始化data 需要时覆盖
     */
    public void initData() {

    }

    /**
     * 设置监听  需要时覆盖
     */
    public void initListener() {

    }

    private void initActionBar() {
        if (mToolBar == null) {
            mToolBar = new Toolbar(mContext);
        }
    }

    /**
     * 按返回键 或者返回按钮
     */
    public void onBack() {
        getActivity().finish();
    }

//    /**
//     * 获取ImageLoader
//     *
//     * @return
//     */
//    public ImageLoader getImageLoader() {
//        return ImageLoader.get(getActivity());
//    }

    /**
     * @return
     */
    public View findViewById(@IdRes int resId) {
        return mRootView.findViewById(resId);
    }

    public boolean isNeedToolBar() {
        return false;
    }

    /**
     * 自定义actionbar 此方法肯定在onCreateView()之后调用  只有isNeedActionBar为true才会调用
     *
     * @param toolbar
     */
    protected void onInitActionbar(Toolbar toolbar) {

    }
}
