package com.huwei.neteasemusic.util;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.DimenRes;

import com.huwei.neteasemusic.NetEaseApplication;
import com.huwei.neteasemusic.R;


/**
 * 生成分割线drawable工具类
 *
 * @author jerry
 * @date 2016/03/23
 */
public class DividerUtils {

    public static final float LINE_DP = 0.5f;

    /**
     * 纵向分割线
     *
     * @return
     */
    public static Drawable getVerticalLine() {
        return getDrawable(DensityUtil.dip2px(NetEaseApplication.get(), LINE_DP), 0, NetEaseApplication.get().getResources().getColor(R.color.black_10));
    }

    /**
     * 横向分割线
     *
     * @return
     */
    public static Drawable getHorizontalLine() {
        return getDrawable(0, DensityUtil.dip2px(NetEaseApplication.get(), LINE_DP), NetEaseApplication.get().getResources().getColor(R.color.black_10));
    }

    public static Drawable getHorizontalLine(int color) {
        return getDrawable(0, DensityUtil.dip2px(NetEaseApplication.get(), LINE_DP), color);
    }

    /**
     * 用dimen生成带颜色的drawable
     *
     * @param width
     * @param height
     * @param color
     * @return
     */
    public static Drawable getDrawableByDimen(@DimenRes int width, @DimenRes int height, int color) {
        return getDrawable(NetEaseApplication.get().getResources().getDimensionPixelOffset(width)
                , NetEaseApplication.get().getResources().getDimensionPixelOffset(height), color);
    }

    /**
     * 用dimen来生成
     *
     * @param width
     * @param height
     * @return
     */
    public static Drawable getDrawableByDimen(@DimenRes int width, @DimenRes int height) {
        return getDrawableByDimen(width, height, 0);
    }

    /**
     * 生成透明的drawable
     *
     * @param width
     * @param height
     * @return
     */
    public static Drawable getDrawable(int width, int height) {
        return getDrawable(width, height, Color.TRANSPARENT);
    }

    /**
     * 生成一张带有颜色的drawable
     *
     * @param width
     * @param height
     * @param color
     * @return
     */
    public static Drawable getDrawable(int width, int height, int color) {
//        Bitmap bitmap = BitmapUtil.createBitmapSafely(width, height);
        ShapeDrawable drawable = new ShapeDrawable();
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        drawable.getPaint().setColor(color);
        drawable.setIntrinsicWidth(width);
        drawable.setIntrinsicHeight(height);
//        bitmap.eraseColor(color);
        return drawable;
    }
}
