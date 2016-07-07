package com.huwei.neteasemusic;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.huwei.neteasemusic.ui.widget.SwipeLayout;

/**
 * 能够滑动退出的Activity
 * @author jerry
 * @date 2015-10-26
 *
 */
public class SwipeBackBottomActivity extends BaseActivity {
    private SwipeLayout mSwipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        mSwipeLayout = new SwipeLayout(mContext);
        mSwipeLayout.setOnFinishListener(new SwipeLayout.OnFinishListener() {
            @Override
            public void onFinish() {
                finish();
            }
        });
//        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getWindow().getDecorView().setBackgroundDrawable(null);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        /**
         * 设置decor的背景色为透明，decorChild的颜色为windowBackground
         */
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        ViewGroup decorChild = ((ViewGroup) decor.getChildAt(0));

        TypedArray a = getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.windowBackground
        });

        int background = a.getResourceId(0, 0);
        a.recycle();

        decorChild.setBackgroundResource(background);

        //移除decorChild并加入swipeLayout
        decor.removeView(decorChild);
        mSwipeLayout.setContentView(decorChild);
        decor.addView(mSwipeLayout);
    }

    @Override
    public void onBackPressed() {
        mSwipeLayout.closeActivityAnimation();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // If we've received a touch notification that the user has touched
        // outside the app, finish the activity.
        if (event.getY() < 0) {
            mSwipeLayout.scrollToBottom();
            return true;
        }

        // Delegate everything else to Activity.
        return super.onTouchEvent(event);
    }
}
