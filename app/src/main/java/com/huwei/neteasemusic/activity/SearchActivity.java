package com.huwei.neteasemusic.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.huwei.neteasemusic.PlayBarBaseActivity;
import com.huwei.neteasemusic.R;

/**
 * @author jerry
 * @date 2016/06/29
 */
public class SearchActivity extends PlayBarBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initToolBar();
    }

    @Override
    protected boolean isNeedToolBar() {
        return false;
    }

    private void initToolBar(){
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        if(mToolBar != null){
            setSupportActionBar(mToolBar);
        }
    }
}
