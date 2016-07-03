package com.huwei.neteasemusic.ui.widget.divider;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

import com.huwei.neteasemusic.util.LogUtils;


/**
 * 分割线Decoration  只适用于gridItem
 * <p/>
 * 默认绘制left right bottom
 * <p/>
 * <p/>
 * 暂时只支持GridLayoutManager
 *
 * @author jerry
 * @date 2016/03/16
 */
public class DividerGridItemDecoration extends BaseItemDecoration {

    public static final String TAG = "DividerGridItemDecoration";

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;

    public DividerGridItemDecoration(Drawable drawable) {
        mDivider = drawable;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, State state) {

        drawLeft(c, parent);
        drawRight(c, parent);
        drawBottom(c, parent);

    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    public void drawLeft(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getLeft() - params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth() / 2;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }


    public void drawBottom(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + mDivider.getIntrinsicWidth();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawRight(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth() / 2;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition,
                               RecyclerView parent) {
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();

            int spanCount = getSpanCount(parent);
            int childCount = parent.getAdapter().getItemCount();

            //判断位置
            boolean isFirstRaw = spanSizeLookup.getSpanGroupIndex(itemPosition, spanCount) == 0;
            boolean isFirstColum = spanSizeLookup.getSpanIndex(itemPosition, spanCount) == 0;
            boolean isLastRaw = spanSizeLookup.getSpanGroupIndex(itemPosition, spanCount) == spanSizeLookup.getSpanGroupIndex(childCount - 1, spanCount);
            boolean isLastColum = (spanSizeLookup.getSpanIndex(itemPosition, spanCount) + 1) % spanCount == 0;

            if (checkShowRange(itemPosition, outRect)) {
                int rect[] = getRect(isFirstRaw, isFirstColum, isLastRaw, isLastColum);
                outRect.set(rect[0], rect[1], rect[2], rect[3]);
            }


            LogUtils.i(TAG, itemPosition + "  rect: " + outRect.left + " " + outRect.top + " " + outRect.right + " " + outRect.bottom);
            LogUtils.i(TAG, isFirstColum + "  " + isFirstRaw + "  " + isLastColum + " " + isLastRaw);
        }
    }

    /**
     * 获取rect数组
     *
     * @param isFirstRaw
     * @param isFirstColum
     * @param isLastRaw
     * @param isLastColum
     * @return
     */
    private int[] getRect(boolean isFirstRaw, boolean isFirstColum, boolean isLastRaw, boolean isLastColum) {
        int[] rect = new int[4];

        rect[0] = mDivider.getIntrinsicWidth() / 2;
        rect[1] = 0;
        rect[2] = mDivider.getIntrinsicWidth() / 2;
        rect[3] = mDivider.getIntrinsicHeight();

        if (isFirstColum) {
            rect[0] = 0;
        }

        if (isLastColum) {
            rect[2] = 0;
        }

        if (isLastRaw) {
            rect[3] = 0;
        }

        return rect;
    }


}