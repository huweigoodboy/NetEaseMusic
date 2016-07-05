package com.huwei.neteasemusic.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.img.WebpDownsampler;

import java.io.InputStream;




/**
 * 对图片框架的封装  做到可以替换  new ImageLoader只允许写在BaseActivity中
 * <p/>
 * 1,对于本地图片的加载  暂时还未封装
 *
 * @author jerry
 * @date 2016/03/23
 * 修改 andy
 * 描述 全局单例
 * 时间 20160517
 */
public class ImageLoader {

    public static final String TAG = "ImageLoader";

    private Context mContext;

    private StreamBitmapDecoder mDecoder;

    private static ImageLoader mImageLoader;

    private ImageLoader(Context context) {
        mContext = context;
    }

    /**
     * 在Application中调用的初始化方法
     *
     * @param context
     */
    public static void init(Context context) {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(context);
        }
    }

    public static ImageLoader get(Context context) {
        return new ImageLoader(context);
    }

    private StreamBitmapDecoder getDecoder() {
        if (mDecoder == null) {
            mDecoder = new StreamBitmapDecoder(WebpDownsampler.AT_MOST, Glide.get(mContext).getBitmapPool(), DecodeFormat.PREFER_ARGB_8888);
        }
        return mDecoder;
    }

//    /**
//     * 载入头像
//     *
//     * @param imageView
//     * @param url
//     */
//    public void loadAvatar(final ImageView imageView, String url, int resWidth, int resHeight) {
//        if (imageView != null) {
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            loadImage(imageView, url, resWidth, resHeight, R.drawable.icon_default_avatar);
//        }
//    }

    /**
     * 加载图片
     * 回调显示
     * 原尺寸
     *
     * @param url
     * @param imageLoadCallback
     */
    public void loadImage(String url, final ImageLoadCallback imageLoadCallback) {
        loadImage(url, 0, 0, imageLoadCallback);
    }

    /**
     * 加载图片
     * 回调显示
     * 根据标注尺寸加载
     *
     * @param url
     * @param resWidth          资源ID
     * @param resHeight         资源ID
     * @param imageLoadCallback
     */
    public void loadImage(String url, int resWidth, int resHeight, final ImageLoadCallback imageLoadCallback) {
        int width, height;
        if (resWidth == 0 || resHeight == 0) {
            width = SimpleTarget.SIZE_ORIGINAL;
            height = SimpleTarget.SIZE_ORIGINAL;
        } else {
            width = mContext.getResources().getDimensionPixelOffset(resWidth);
            height = mContext.getResources().getDimensionPixelOffset(resHeight);
        }
        Glide.with(mContext).load(url).asBitmap().into(new SimpleTarget<Bitmap>(width, height) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (imageLoadCallback != null) {
                    if (resource != null) {
                        imageLoadCallback.onSuccess(resource);
                    } else {
                        imageLoadCallback.onFailure();
                    }
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                if (imageLoadCallback != null) {
                    imageLoadCallback.onFailure();
                }
            }
        });
    }

    @Deprecated
    public void load(String url, final ImageLoadCallback imageLoadCallback) {
        if (StringUtils.isNotEmpty(url)) {
            load(Uri.parse(url), imageLoadCallback);
        } else {
            imageLoadCallback.onFailure();
        }
    }

    public void load(Uri uri, final ImageLoadCallback imageLoadCallback) {
        //默认支持webP
        //todo 走默认的方法
        /*if (Build.VERSION.SDK_INT > 16) {
            commonLoad(uri, imageLoadCallback);
        } else {
            //走兼容方法
           under16Load(uri, imageLoadCallback);
        }*/

    }

    /**
     * 获取构造器
     * API 16以下
     *
     * @param url
     * @return
     */
    private GenericRequestBuilder<String, InputStream, Bitmap, Bitmap> getWebpBuilder(String url) {
        GenericRequestBuilder<String, InputStream, Bitmap, Bitmap> requestBuilder = Glide
                .with(mContext)
                .using(Glide.buildStreamModelLoader(String.class, mContext), InputStream.class)
                .load(url)
                .as(Bitmap.class)
                .sourceEncoder(new StreamEncoder())
                .decoder(getDecoder())
                .cacheDecoder(new FileToStreamDecoder<>(getDecoder()));
        return requestBuilder;
    }

    /**
     * 获取构造器
     * API 16以上
     *
     * @param url
     * @return
     */
    public BitmapRequestBuilder<String,Bitmap> getCommonBuilder(String url) {
        BitmapRequestBuilder<String,Bitmap> builder = Glide.with(mContext)
                .load(url)
                .asBitmap()
                .dontAnimate();
        return builder;
    }

    private GenericRequestBuilder getBuilder(String url) {
        if (Build.VERSION.SDK_INT >= 16) {
            return getCommonBuilder(url);
        } else {
            return getWebpBuilder(url);
        }
    }

    /**
     * 获取资源尺寸
     *
     * @param resSize
     * @return 默认返回原始尺寸
     */
    private int getSize(int resSize) {
        if (resSize == 0) {
            return SimpleTarget.SIZE_ORIGINAL;
        } else {
            return mContext.getResources().getDimensionPixelOffset(resSize);
        }
    }

    /**
     * 直接加载原图
     *
     * @param ivShow
     * @param url
     */
    public void loadImage(ImageView ivShow, String url) {
        if (ivShow == null || TextUtils.isEmpty(url)) {
            //提示
            return;
        }
        getBuilder(url).into(ivShow);
    }

    /**
     * 加载图片
     * 根据尺寸加载
     *
     * @param ivShow
     * @param url
     * @param resWidth
     * @param resHeight
     */
    public void loadImage(ImageView ivShow, String url, int resWidth, int resHeight) {
        if (ivShow == null || TextUtils.isEmpty(url)) {
            return;
        }
        getBuilder(url).override(getSize(resWidth), getSize(resHeight)).into(ivShow);
    }

    /**
     * 加载图片
     * 根据尺寸加载
     * 支持自定义占位图
     *
     * @param ivShow
     * @param url
     * @param resWidth
     * @param resHeight
     * @param defaultDrawable
     */
    public void loadImage(ImageView ivShow, String url, int resWidth, int resHeight, int defaultDrawable) {
        if (ivShow == null || TextUtils.isEmpty(url)) {
            return;
        }
        getBuilder(url).override(getSize(resWidth), getSize(resHeight)).placeholder(defaultDrawable).into(ivShow);
    }

    /**
     * 加载图片
     * 回调bitmap
     *
     * @param url
     * @param resWidth
     * @param resHeight
     * @param transformation
     * @param imageLoadCallback
     */
    public void loadImage(String url, int resWidth, int resHeight, BitmapTransformation transformation, final ImageLoadCallback imageLoadCallback) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        getBuilder(url).override(getSize(resWidth), getSize(resHeight)).transform(transformation).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                if (imageLoadCallback != null) {
                    imageLoadCallback.onSuccess(resource);
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                if (imageLoadCallback != null) {
                    imageLoadCallback.onFailure();
                }
            }
        });
    }


//    /**
//     * 加载图片
//     * 根据尺寸加载
//     * 支持自定义占位图
//     *
//     * @param ivShow
//     * @param url
//     * @param resWidth
//     * @param resHeight
//     * @param defaultDrawable
//     */
//    public void loadBlurImage(ImageView ivShow, String url, int resWidth, int resHeight, int defaultDrawable) {
//        if (ivShow == null || TextUtils.isEmpty(url)) {
//            return;
//        }
//        getBuilder(url).override(getSize(resWidth), getSize(resHeight)).placeholder(defaultDrawable).transform(new BlurTransFormation(mContext)).into(ivShow);
//    }
//
//    /**
//     * 加载图片
//     * 根据尺寸加载
//     * 支持自定义占位图
//     *
//     * @param ivShow
//     * @param url
//     * @param resWidth
//     * @param resHeight
//     */
//    public void loadBlurImage(ImageView ivShow, String url, int resWidth, int resHeight) {
//        if (ivShow == null || TextUtils.isEmpty(url)) {
//            return;
//        }
//        getBuilder(url).override(getSize(resWidth), getSize(resHeight)).transform(new BlurTransFormation(mContext)).into(ivShow);
//    }

    /**
     * 加载背景
     *
     * @param ivShow
     * @param url
     * @param resWidth
     * @param resHeight
     * @param transformation
     */
    public void loadBackgroud(final View ivShow, String url, int resWidth, int resHeight, BitmapTransformation transformation) {
        if (ivShow == null || TextUtils.isEmpty(url)) {
            return;
        }
        getBuilder(url).override(getSize(resWidth), getSize(resHeight)).transform(transformation).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                final BitmapDrawable bpdrawable = new BitmapDrawable(mContext.getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ivShow.setBackground(bpdrawable);
                } else {
                    ivShow.setBackgroundDrawable(bpdrawable);
                }
            }
        });
    }

    /**
     * 缓存图片的disk
     *
     * @param url
     */
    public void cacheTodisk(String url) {
        Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    public void clear(View v) {
        Glide.clear(v);
    }

    /**
     * imageLoader加载完图片后的回调
     */
    public interface ImageLoadCallback {
        void onSuccess(Bitmap bitmap);
        void onFailure();
    }

}
