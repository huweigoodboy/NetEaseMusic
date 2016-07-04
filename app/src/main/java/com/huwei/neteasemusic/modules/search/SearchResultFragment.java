package com.huwei.neteasemusic.modules.search;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.main.BaseFragment;
import com.huwei.neteasemusic.ui.widget.SearchBar;
import com.huwei.neteasemusic.util.ToastUtils;
import com.huwei.neteasemusic.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jerry
 * @date 2016/07/03
 */
public class SearchResultFragment extends BaseFragment implements SearchBar.SearchCallback {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private List<Fragment> mFragmentList = new ArrayList<>();

    private String mKeyword;

    private boolean isPrepared;  //fragment准备好 flag

    @Override
    public int getContentResId() {
        return R.layout.fragment_search_result;
    }

    @Override
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);


        //初始化fragment
        mFragmentList.add(new SrSongListFragment());
        mFragmentList.add(new SrArtistListFragment());
        mFragmentList.add(new SrAlbumListFragment());
        mFragmentList.add(new SrPlayListFragment());
        mFragmentList.add(new SrMvListFrament());

        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return Utils.getResources().getStringArray(R.array.search_result_tab)[position];
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);


        //
        isPrepared = true;
        onSearch(mKeyword);
    }

    /**
     * 这里传递过来可能是空 ""  在用户已经搜索 删除字符的情况下
     * @param keyword
     */
    @Override
    public void onSearch(String keyword) {

        mKeyword = keyword;
        ToastUtils.showShort(keyword);

        if(isPrepared) {

            int nowTabIndex = mViewPager.getCurrentItem();
            SearchBar.SearchCallback searchCallback = (SearchBar.SearchCallback) mFragmentList.get(nowTabIndex);
            searchCallback.onSearch(keyword);
        }
    }
}
