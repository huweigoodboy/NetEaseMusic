package com.huwei.neteasemusic.modules.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.main.BaseFragment;
import com.huwei.neteasemusic.ui.widget.SearchBar;
import com.huwei.neteasemusic.util.LogUtils;
import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.Utils;

/**
 * @author jerry
 * @date 2016/07/04
 */
public abstract class SrBaseFragment extends BaseFragment implements SearchBar.SearchCallback {

    protected boolean isFragmentPrepared;
    protected String mKeyword;
    protected RecyclerView mRecyclerView;

    @Override
    public int getContentResId() {
        return R.layout.fragment_sr_list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = super.onCreateView(inflater, container, savedInstanceState);
        isFragmentPrepared = true;

        //容器初始化时搜索第一次
        onSearch(mKeyword);

        return mRootView;
    }

    @Override
    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    /**
     * @param keyword keywora在某种情况下可能是空
     */
    @Override
    public void onSearch(String keyword) {
        LogUtils.i(TAG, "viewRoot:" + mRootView);

        mKeyword = keyword;

        if (isFragmentPrepared) {
            if (StringUtils.isNotEmpty(keyword)) {
                mRootView.setVisibility(View.VISIBLE);
            } else {
                mRootView.setVisibility(View.GONE);
            }
        }
    }

    public void setKeyword(String mKeyword) {
        this.mKeyword = mKeyword;
    }
}
