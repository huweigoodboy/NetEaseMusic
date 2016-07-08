package com.huwei.neteasemusic;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.huwei.neteasemusic.main.BaseFragment;

/**
 * @author jerry
 * @date 2016/07/08
 */
public class RecyclerViewBaseFragment extends BaseFragment {


    protected RecyclerView mRecyclerView;

    @Override
    public int getContentResId() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    public void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }
}
