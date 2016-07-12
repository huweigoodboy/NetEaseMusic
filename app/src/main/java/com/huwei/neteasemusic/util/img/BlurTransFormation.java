package com.huwei.neteasemusic.util.img;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.huwei.neteasemusic.util.BitmapUtils;

/**
 * 高斯模糊特效处理类
 */
public class BlurTransFormation extends BitmapTransformation {

    private Context mContext;

    public BlurTransFormation(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return BitmapUtils.fastBlur(mContext, toTransform, 200);
    }

    @Override
    public String getId() {
        return "blur";
    }
}