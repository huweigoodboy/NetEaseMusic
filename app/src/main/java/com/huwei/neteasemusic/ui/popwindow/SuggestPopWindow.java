package com.huwei.neteasemusic.ui.popwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.adapter.SuggestAdapter;
import com.huwei.neteasemusic.ui.popwindow.base.BasePopupWindow;
import com.huwei.neteasemusic.ui.widget.DrawableTextView;
import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.Utils;

/**
 * @author jerry
 * @date 2016/07/01
 */
public class SuggestPopWindow extends BasePopupWindow {

    private RecyclerView mRecyclerView;
    private SuggestAdapter mAdapter;
    private DrawableTextView mTvKeyword;

    private View mLlContainer;

    public SuggestPopWindow(Context context) {
        super(context);

        setContentView(R.layout.pop_suggest);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);

        setSoftInputMode(INPUT_METHOD_NEEDED);

        initView();
//        initListener();
    }

    void initView() {
        mLlContainer = findViewById(R.id.ll_container);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mAdapter = new SuggestAdapter(mContext);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    void initListener(){
        mLlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShowing()){
                    dismiss();
                }
            }
        });
    }

    public void setKeyWord(String keyWord) {
        if (StringUtils.isEmpty(keyWord)) {
            return;
        }

        if (mAdapter.getHeadSize() == 0) {
            mTvKeyword = (DrawableTextView) LayoutInflater.from(mContext).inflate(R.layout.item_suggest, null, true);
            mTvKeyword.setCompoundDrawables(null, null, null, null);
            mTvKeyword.setTextColor(Utils.getResources().getColor(R.color.color_417BBA));

            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.getResources().getDimensionPixelOffset(R.dimen.suggest_item_height));
            mTvKeyword.setLayoutParams(params);

            mAdapter.addHeader(mTvKeyword);
        }

        mTvKeyword.setText(Utils.getResources().getString(R.string.suggest_keyword, keyWord));
        mAdapter.notifyItemChanged(0);
    }
}
