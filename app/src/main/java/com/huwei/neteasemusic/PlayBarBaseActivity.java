package com.huwei.neteasemusic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.huwei.neteasemusic.ui.widget.PlayBarView;

/**
 * @author jerry
 * @date 2016-06-27
 */
public class PlayBarBaseActivity extends BaseActivity {

    protected FrameLayout mFlPlayBarContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mFlPlayBarContainer = initPlayBarContainer();

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM;

        //从父容器移除
        PlayBarView playBarView = new PlayBarView(this);
        ViewGroup viewGroup = (ViewGroup) playBarView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(playBarView);
        }

        mFlPlayBarContainer.addView(playBarView, layoutParams);
    }



    protected FrameLayout getPlayBarContainer() {
        return mFlPlayBarContainer;
    }

    /**
     * 初始化 playbar 的容器  可以复写  默认使用decorView下面的FrameLayout
     *
     * @return
     */
    protected FrameLayout initPlayBarContainer() {
        ViewGroup mDecorView = (ViewGroup) getWindow().getDecorView();
        return (FrameLayout) ((ViewGroup) mDecorView.getChildAt(0)).getChildAt(1);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
