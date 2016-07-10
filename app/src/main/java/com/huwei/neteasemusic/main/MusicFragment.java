package com.huwei.neteasemusic.main;

import android.view.View;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.bean.resp.NetEaseAPI;


import java.util.Arrays;

/**
 * @author jerry
 * @date 2016-06-26
 */
public class MusicFragment extends BaseFragment {
    @Override
    public int getContentResId() {
        return R.layout.fragment_music;
    }

    @Override
    public void initView() {

        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
