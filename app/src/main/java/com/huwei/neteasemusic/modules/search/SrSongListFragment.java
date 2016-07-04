package com.huwei.neteasemusic.modules.search;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.main.BaseFragment;
import com.huwei.neteasemusic.ui.widget.SearchBar;

/**
 * @author jerry
 * @date 2016/07/04
 */
public class SrSongListFragment extends SrBaseFragment{
    @Override
    public int getContentResId() {
        return R.layout.fragment_sr_songlist;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onSearch(String keyword) {
        super.onSearch(keyword);
    }
}
