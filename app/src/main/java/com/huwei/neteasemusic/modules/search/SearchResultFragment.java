package com.huwei.neteasemusic.modules.search;

import android.support.v4.view.ViewPager;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.main.BaseFragment;

/**
 * @author jerry
 * @date 2016/07/03
 */
public class SearchResultFragment extends BaseFragment {

    private ViewPager mViewPager;

    @Override
    public int getContentResId() {
        return R.layout.fragment_search_result;
    }

    @Override
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
    }
}
