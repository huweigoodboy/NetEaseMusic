package com.huwei.neteasemusic.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.huwei.neteasemusic.PlayBarBaseActivity;
import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.bean.resp.NetEaseAPI;
import com.huwei.neteasemusic.bean.resp.ServerTip;
import com.huwei.neteasemusic.ui.widget.SearchBar;
import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.Utils;
import com.huwei.neteasemusic.util.network.HttpHandler;

/**
 * @author jerry
 * @date 2016/06/29
 */
public class SearchActivity extends PlayBarBaseActivity {

    public static final String TAG = "SearchActivity";

    private SearchBar mSearchBar;


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
        mSearchBar = (SearchBar) findViewById(R.id.search_bar);
    }

    void initListener(){
        mSearchBar.getEtinput().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if(StringUtils.isNotEmpty(keyword)){
                    NetEaseAPI.suggest(keyword, 10, new HttpHandler() {
                        @Override
                        public void onSuccess(ServerTip serverTip) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(mSearchBar.isFocusedR()){
            mSearchBar.clearFocusR();
        }
    }
}
