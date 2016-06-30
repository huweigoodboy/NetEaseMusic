package com.huwei.neteasemusic.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.huwei.neteasemusic.inter.IOnReSizeListener;
import com.huwei.neteasemusic.util.LogUtils;


/**
 * 可以监听到相对布局reSize操作的RelativeLayout
 *
 * @author jerry
 * @date 2016/03/17
 */
public class ReSizeRelativeLayout extends RelativeLayout {

    private static final String TAG = ReSizeRelativeLayout.class.getSimpleName();

    public static final int KEYBOARD_MIN_HEIGHT = 200; //键盘弹起的最小高度 用于区分键盘和虚拟键

    public static final byte KEYBOARD_STATE_SHOW = -3;
    public static final byte KEYBOARD_STATE_HIDE = -2;
    public static final byte KEYBOARD_STATE_INIT = -1;
    private boolean mHasInit;
    private boolean mHasKeybord;
    private int mHeight;

    public ReSizeRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ReSizeRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReSizeRelativeLayout(Context context) {
        this(context, null);
    }

    public void setOnResizeListener(IOnReSizeListener l) {
        mListener = l;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!mHasInit) {
            mHasInit = true;
            mHeight = b;
            if (mListener != null) {
                LogUtils.d(TAG, "KEYBOARD_STATE_INIT");
            }
        } else {
            mHeight = mHeight < b ? b : mHeight;
        }
        if (mHasInit && mHeight > b) {
            mHasKeybord = true;
            if (mListener != null) {
                LogUtils.d(TAG, "KEYBOARD_STATE_SHOW");
            }
        }
        if (mHasInit && mHasKeybord && mHeight == b) {
            mHasKeybord = false;
            if (mListener != null) {
                LogUtils.d(TAG, "KEYBOARD_STATE_SHOW");
            }

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtils.d("onSizeChange", w + "/" + h + "/" + oldw + "/" + oldh);
        if (mListener != null) {
            int wh[] = new int[4];

            wh[0] = w;
            wh[1] = h;
            wh[2] = oldw;
            wh[3] = oldh;

            //变化大于软键盘的最小高度  视为输入法的软键盘变化
            if (Math.abs(h - oldh) > KEYBOARD_MIN_HEIGHT) {
                mListener.onReSize(IOnReSizeListener.ChangeType.softInput, isSmaller(h, oldh), wh);
            }   //视为虚拟按键变化
            else {
                mListener.onReSize(IOnReSizeListener.ChangeType.virtualKey, isSmaller(h, oldh), wh);
            }
        }
    }

    private boolean isSmaller(int h, int oldh) {
        return h < oldh;
    }

    private IOnReSizeListener mListener;
}
