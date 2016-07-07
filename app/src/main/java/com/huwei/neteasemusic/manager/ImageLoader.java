package com.huwei.neteasemusic.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GenericRequestBuilder;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.huwei.neteasemusic.BaseActivity;
import com.huwei.neteasemusic.NetEaseApplication;
import com.huwei.neteasemusic.util.LogUtils;
import com.huwei.neteasemusic.util.StringUtils;

import java.io.InputStream;





/**
 * 对图片框架的封装  做到可以替换  new ImageLoader只允许写在BaseActivity中
 * <p/>
 * 1,对于本地图片的加载  暂时还未封装
 *
 * @author jerry
 * @date 2016/03/23
 */
public class ImageLoader {

    public static final String TAG = "ImageLoader";

    private Context mContext;

    private StreamBitmapDecoder mDecoder;


    public ImageLoader(Context context) {
        mContext = context;
    }

    /**
     * 获取imageLoader实例 (最大程度上的保证同一个activity 中view 等 只有同一个imageloader)
     * 注意：这不是一个单例  很有可能会new一个对象 不要当作单例使用
     * <p/>
     * <p/>
     * 1,如果context是属于baseActivity 直接获取activity中的imageLoader
     * 2,如果不是，创建一个imageLoader
     *
     * @param context
     * @return
     */
    public static ImageLoader get(Context context) {
        if (context instanceof BaseActivity) {
            return ((BaseActivity) context).getImageLoader();
        }
        return new ImageLoader(context);
    }

    private StreamBitmapDecoder getDecoder(){
        if(mDecoder == null) {
            mDecoder = new StreamBitmapDecoder(Downsampler.AT_MOST,Glide.get(mContext).getBitmapPool(), DecodeFormat.PREFER_ARGB_8888);
        }
        return mDecoder;
    }




    /**
     * 载入默认图标
     *
     * @param imageView
     * @param url
     * @param drawableRes
     */
    public void loadImage(final ImageView imageView, String url, int drawableRes) {
        if (imageView == null) {
            return;
        }

        LogUtils.i(TAG, "url:" + url + "     tag:" + imageView.getTag(imageView.getId()));
        //不为空　相等时　不加载图片
        if (StringUtils.isNotEmpty(url) && StringUtils.equals(imageView.getTag(imageView.getId()), url)) {
            return;
        }

        Drawable drawable = ContextCompat.getDrawable(mContext, drawableRes);
        imageView.setImageDrawable(drawable);
        if (!TextUtils.isEmpty(url)) {
            loadImage(imageView, url);
        }
    }

    /**
     * 支持普通imageView的图片加载
     *
     * @param imageView
     * @param url
     */
    public void loadImage(final ImageView imageView, final String url) {
        if (StringUtils.isNotEmpty(url)) {
            if (imageView != null) {
                loadImage(url, new SimpleImageLoadCallback() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        if (bitmap != null) {
                            //设置TAG 防止图片重新加载
                            imageView.setTag(imageView.getId(), url);

                            imageView.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        }
    }

//    /**
//     * 支持普通imageView的图片加载
//     *
//     * @param imageView
//     * @param uri
//     */
//    public void load(final ImageView imageView, final Uri uri) {
//
//    }

    public void loadImage(String url, final ImageLoadCallback imageLoadCallback) {
        if (StringUtils.isNotEmpty(url)) {
            loadImage(Uri.parse(url), imageLoadCallback);
        }
    }

    public void loadImage(Uri uri, final ImageLoadCallback imageLoadCallback) {
        if (mContext instanceof BaseActivity) {
            if (((BaseActivity) mContext).isDestroyed()) {
                //如果activity 已经被销毁 不要再进行 图片加载操作 防止内存泄露
                return;
            }
        }


        //默认支持webP
        //todo 走默认的方法
        if (Build.VERSION.SDK_INT > 16) {

        }else {
            //走兼容方法
        }

        under16Load(uri, imageLoadCallback);
    }

    /**
     * 普通的加载  （API > 16 以上android原生对于webP有良好的支持）
     *
     * @param uri
     * @param imageLoadCallback
     */
    private void commonLoad(Uri uri, final ImageLoadCallback imageLoadCallback) {
        DrawableTypeRequest drawableTypeRequest = Glide.with(mContext).load(uri);
        drawableTypeRequest.asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(final Bitmap bitmap, GlideAnimation glideAnimation) {
                if (bitmap != null) {
                    NetEaseApplication.runUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (imageLoadCallback != null) {
                                imageLoadCallback.onSuccess(bitmap);
                            }
                        }
                    });
                } else {
                    onLoadFailed(null, null);
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                NetEaseApplication.runUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (imageLoadCallback != null) {
                            imageLoadCallback.onFailure();
                        }
                    }
                });
            }
        });
    }

    /**
     * 对于API16及以下webP的兼容支持
     *
     * @param uri
     * @param imageLoadCallback
     */
    private void under16Load(Uri uri, final ImageLoadCallback imageLoadCallback) {
        GenericRequestBuilder<Uri, InputStream, Bitmap, Bitmap> requestBuilder  =
                Glide.with(mContext).using(Glide.buildStreamModelLoader(Uri.class, mContext), InputStream.class).from(Uri.class)
                        .as(Bitmap.class).
                        sourceEncoder(new StreamEncoder()).
                        decoder(getDecoder()).
                        cacheDecoder(new FileToStreamDecoder<Bitmap>(getDecoder()));

        requestBuilder.load(uri).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(final Bitmap bitmap, GlideAnimation glideAnimation) {
                if (bitmap != null) {
                    NetEaseApplication.runUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (imageLoadCallback != null) {
                                imageLoadCallback.onSuccess(bitmap);
                            }
                        }
                    });
                } else {
                    onLoadFailed(null, null);
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                NetEaseApplication.runUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (imageLoadCallback != null) {
                            imageLoadCallback.onFailure();
                        }
                    }
                });
            }
        });
    }

    /**
     * 缓存图片的disk
     *
     * @param url
     */
    public void cacheTodisk(String url) {
//        ImagePipeline imagePipeline = Fresco.getImagePipeline();
//        ImageRequest imageRequest = ImageRequest.fromUri(Uri.parse(url));
//        imagePipeline.prefetchToDiskCache(imageRequest, CallerThreadExecutor.getInstance());

        Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.ALL);
    }


    public static abstract class SimpleImageLoadCallback implements ImageLoadCallback {

        public SimpleImageLoadCallback() {
        }

        @Override
        public void onFailure() {

        }
    }

    /**
     * imageLoader加载完图片后的回调
     */
    public interface ImageLoadCallback {
        void onSuccess(Bitmap bitmap);

        void onFailure();
    }

}
