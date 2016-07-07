package com.huwei.neteasemusic.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.Toast;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.util.DisplayUtils;

/**
 * @author jerry
 * @version 4.6
 * @date 2015-10-27
 */
public class SwipeLayout extends LinearLayout {
    public static final String TAG = "SwipeLayout";

    private View mEmptyView;
    private View mContentView;

    private int mTopEdge;
    private int mWindowHeight;
    private int mMaxScrollDistance;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker = null;
    private int mMaxFlingVelocity;
    private int mLastY;

    ViewGroup.LayoutParams childParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    private Context mContext;
    public static final int DURATION = 1500;   //满屏滑动时间
    public static final int OPEN_ANIM_DURATION = 1000;
    public static int SNAP_VELOCITY = 600;  //最小的滑动速率

    private OnFinishListener mOnFinishListener;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        init();
    }

    public void setOnFinishListener(OnFinishListener mOnFinishListener) {
        this.mOnFinishListener = mOnFinishListener;
    }

    void init() {
        mScroller = new Scroller(mContext);
        mMaxFlingVelocity = ViewConfiguration.get(mContext).getScaledMaximumFlingVelocity();

        mWindowHeight = DisplayUtils.getWindowHeight(mContext) * 2;
        mMaxScrollDistance = mWindowHeight / 2;
        mTopEdge = mMaxScrollDistance - mMaxScrollDistance /6;

        setOrientation(LinearLayout.VERTICAL);

        childParams.height = DisplayUtils.getWindowHeight(mContext);

        mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.view_translate, null);

        addView(mEmptyView, childParams);
         }

    public void setContentView(View contentView) {
        if (mContentView != null) {
            removeView(mContentView);
        }
        mContentView = contentView;
        addView(contentView, childParams);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                openActivityAnimation();
            }
        }, 200);
    }

    /**
     * 获取速度追踪器
     *
     * @return
     */
    private VelocityTracker getVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        return mVelocityTracker;
    }

    /**
     * 回收速度追踪器
     */
    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //1.获取速度追踪器
        getVelocityTracker();
        //2.将当前事件纳入到追踪器中
        mVelocityTracker.addMovement(ev);

        int pointId = -1;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //如果屏幕的动画还没结束，你就按下了，我们就结束上一次动画，即开始这次新ACTION_DOWN的动画
//                clearScrollHis();
                mLastY = (int) ev.getY();
                pointId = ev.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                int nextScrollY = (int) (mLastY - ev.getY() + getScrollY());

                if (scrollTo(nextScrollY)) {
                    mLastY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                //3.计算当前速度
                mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                //获取x y方向上的速度
                float vY = mVelocityTracker.getYVelocity(pointId);

                Log.i(TAG, "mVelocityY:" + vY);

                //大于某个速率 直接滑动
                if (vY > SNAP_VELOCITY) {
                    scrollToTop();
                } else if (vY < -SNAP_VELOCITY) {
                    scrollToBottom();
                } else {
                    snapToDestation();
                }


                //4.回收速度追踪器
                recycleVelocityTracker();
                break;
        }
        return true;
    }

    private void openActivityAnimation() {
        clearScrollHis();
        mScroller.startScroll(0,0 , 0, mMaxScrollDistance - getScrollY(), OPEN_ANIM_DURATION);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    public void closeActivityAnimation() {
        clearScrollHis();
        mScroller.startScroll(0, getScrollY(), 0, -getScrollY(), OPEN_ANIM_DURATION);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    private void clearScrollHis() {
        if (mScroller != null) {
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }
        }
    }

    /**
     * 根据现在的滚动位置判断
     */
    private void snapToDestation() {
        int scrollY = getScrollY();
        if (scrollY > 0 && scrollY <= mTopEdge) {
            smoothScrollTo(0);
        } else if (scrollY > mTopEdge) {
            smoothScrollTo(mMaxScrollDistance);
        }
    }

    /**
     * 直接滚动
     *
     * @param y
     * @return
     */
    public boolean scrollTo(int y) {
        if (y < 0) {
            scrollTo(0, 0);
        } else if (y > mMaxScrollDistance) {
            scrollTo(0, mMaxScrollDistance);
        } else {
            scrollTo(0, y);
        }
        return true;
    }

//    public void scrollToRight() {
//        smoothScrollTo(mMaxScrollDistance);
//    }
//
//    public void scrollToLeft() {
//        smoothScrollTo(0);
//    }

    public void scrollToTop() {
        smoothScrollTo(mMaxScrollDistance);
    }

    public void scrollToBottom() {
        smoothScrollTo(0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        Log.d(TAG, "top:" + t);

        if (t == 0) {
            Log.d(TAG, "OnFinish");

            Toast.makeText(mContext, "Finished", Toast.LENGTH_SHORT).show();

            if (mOnFinishListener != null) {
                mOnFinishListener.onFinish();
            }
        }
    }

    public void smoothScrollTo(int fy) {
        if (fy < 0) {
            smoothScrollTo(0, 0);
        } else if (fy > mMaxScrollDistance) {
            smoothScrollTo(0, mMaxScrollDistance);
        } else {
            smoothScrollTo(0, fy);
        }
    }

    //    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - getScrollX();
        int dy = fy - getScrollY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(0, getScrollY(), dx, dy, Math.abs(dy* DURATION / mMaxScrollDistance));
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }

    /**
     * fragment或者activity 结束的接口
     */
    public interface OnFinishListener {
        void onFinish();
    }
}
