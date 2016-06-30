package com.huwei.neteasemusic.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.huwei.neteasemusic.PlayBarBaseActivity;
import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.ui.widget.SearchBar;
import com.huwei.neteasemusic.util.Utils;

/**
 * @author jerry
 * @date 2016/06/29
 */
public class SearchActivity extends PlayBarBaseActivity {

    public static final String TAG = "SearchActivity";

    private SearchBar mSearchBar;
    private Toolbar mToolbar;
//    private ReSizeLinearLayout mLlRoot;

    public static Intent getStartActIntent(Context from) {
        Intent intent = Utils.getActIntent(from, SearchActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        initToolBar();
        initView();
        initListener();

        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.showSoftInput(mContext);
            }
        }, 200);
    }

    @Override
    protected boolean isNeedToolBar() {
        return false;
    }

    private void initToolBar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);

        mToolBar.setNavigationIcon(R.drawable.actionbar_back);

        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
        }

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void initView(){
//        mLlRoot = (ReSizeLinearLayout) findViewById(R.id.ll_root);
        mSearchBar = (SearchBar) findViewById(R.id.search_bar);
    }

    void initListener(){

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(mSearchBar.isFocusedR()){
            mSearchBar.clearFocusR();
        }
    }
}
