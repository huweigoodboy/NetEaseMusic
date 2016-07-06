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

import java.util.HashMap;
import java.util.Map;


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

    private boolean mDrawFirstColum;
    private boolean mDrawFirstRow;
    private boolean mDrawLastColum;
    private boolean mDrawLastRow;

    private Map<Integer, Rect> mOffestRect = new HashMap<>();

    public DividerGridItemDecoration(Drawable drawable) {
        mDivider = drawable;
    }

    /**
     * @param mDivider
     * @param mDrawFirstColum
     * @param mDrawFirstRow
     * @param mDrawLastColum
     * @param mDrawLastRow
     */
    public DividerGridItemDecoration(Drawable mDivider, boolean mDrawFirstColum, boolean mDrawFirstRow, boolean mDrawLastColum, boolean mDrawLastRow) {
        this.mDivider = mDivider;
        this.mDrawFirstColum = mDrawFirstColum;
        this.mDrawFirstRow = mDrawFirstRow;
        this.mDrawLastColum = mDrawLastColum;
        this.mDrawLastRow = mDrawLastRow;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, State state) {

        if(parent.getChildCount() <= 0){
            return;
        }

        drawLeft(c, parent, state);
        drawTop(c, parent, state);
        drawRight(c, parent, state);
        drawBottom(c, parent, state);


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

    public void drawLeft(Canvas c, RecyclerView parent, State state) {
        final int childCount = parent.getChildCount();
        int firstPos  = parent.getChildAdapterPosition(parent.getChildAt(0));  //目前 显示的 第一个 在adpter中的位置

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final Rect offsetRect = mOffestRect.get(i + firstPos);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int right = child.getLeft() - params.leftMargin;
            final int left = right - offsetRect.left;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawTop(Canvas c, RecyclerView parent, State state) {
        final int childCount = parent.getChildCount();
        int firstPos  = parent.getChildAdapterPosition(parent.getChildAt(0));  //目前 显示的 第一个 在adpter中的位置

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final Rect offsetRect = mOffestRect.get(i+firstPos);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin - offsetRect.left;
            final int right = child.getRight() + params.rightMargin
                    + offsetRect.right;
            final int bottom = child.getTop() - params.topMargin;
            final int top = bottom - offsetRect.top;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawRight(Canvas c, RecyclerView parent, State state) {
        final int childCount = parent.getChildCount();
        int firstPos  = parent.getChildAdapterPosition(parent.getChildAt(0));  //目前 显示的 第一个 在adpter中的位置

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final Rect offsetRect = mOffestRect.get(i+firstPos);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + offsetRect.right;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }


    public void drawBottom(Canvas c, RecyclerView parent, State state) {
        int childCount = parent.getChildCount();
        int firstPos  = parent.getChildAdapterPosition(parent.getChildAt(0));  //目前 显示的 第一个 在adpter中的位置

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final Rect offsetRect = mOffestRect.get(i+firstPos);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin - offsetRect.left;
            final int right = child.getRight() + params.rightMargin
                    + offsetRect.right;
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + offsetRect.bottom;
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

            mOffestRect.put(itemPosition, new Rect(outRect));


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
        rect[1] = mDivider.getIntrinsicHeight() / 2;
        rect[2] = mDivider.getIntrinsicWidth() / 2;
        rect[3] = mDivider.getIntrinsicHeight() / 2;

        if (isFirstColum) {
            if (mDrawFirstColum) {
                rect[0] = mDivider.getIntrinsicWidth();
            } else {
                rect[0] = 0;
            }
        }

        if (isFirstRaw) {
            if (mDrawFirstRow) {
                rect[1] = mDivider.getIntrinsicHeight();
            } else {
                rect[1] = 0;
            }
        }

        if (isLastColum) {
            if (mDrawLastColum) {
                rect[2] = mDivider.getIntrinsicWidth();
            } else {
                rect[2] = 0;
            }
        }

        if (isLastRaw) {
            if (mDrawLastRow) {
                rect[3] = mDivider.getIntrinsicHeight();
            } else {
                rect[3] = 0;
            }
        }

        return rect;
    }


}