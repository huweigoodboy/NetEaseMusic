package com.huwei.neteasemusic.ui.widget.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;

import com.huwei.neteasemusic.util.LogUtils;


/**
 * 默认绘制right 和　bottom
 */
public class DividerItemDecoration extends BaseItemDecoration {

    public static final String TAG = "DividerItemDecoration";

    private Drawable mDivider;

    public DividerItemDecoration(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.listDivider});
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    public DividerItemDecoration(Drawable divider) {
        mDivider = divider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mDivider == null) return;
        int itemPos = parent.getChildAdapterPosition(view);
//        int itemPos = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

        if (checkShowRange(itemPos, outRect)) {
            if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                outRect.bottom = mDivider.getIntrinsicHeight();
            } else {
                outRect.right = mDivider.getIntrinsicWidth();
            }
        }

        LogUtils.i(TAG, itemPos + "    rect:" + outRect.left + " " + outRect.top + " " + outRect.right + " " + outRect.bottom);
    }

    /**
     * 分割线画在item上面
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mDivider == null) {
            super.onDrawOver(c, parent, state);
            return;
        }

        LogUtils.i(TAG, "childCount:" + parent.getChildCount());


        if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();

            for (int i = 0; i < childCount; i++) {

                int cIndex = parent.getChildAdapterPosition(parent.getChildAt(i));

                if (checkShowRange(cIndex)) {
                    final View child = parent.getChildAt(i);
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    final int size = mDivider.getIntrinsicHeight();
                    final int top = child.getBottom();
                    final int bottom = top + params.bottomMargin + size;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        } else { //horizontal
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();
            final int childCount = parent.getChildCount();

            for (int i = 0; i < childCount; i++) {

                int cIndex = parent.getChildAdapterPosition(parent.getChildAt(i));

                if (checkShowRange(cIndex)) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int size = mDivider.getIntrinsicWidth();
                final int left = child.getRight();
                final int right = left + params.rightMargin + size;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
                }
            }
        }
    }

    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            return layoutManager.getOrientation();
        } else
            throw new IllegalStateException("DividerItemDecoration can only be used with a LinearLayoutManager.");
    }

}
