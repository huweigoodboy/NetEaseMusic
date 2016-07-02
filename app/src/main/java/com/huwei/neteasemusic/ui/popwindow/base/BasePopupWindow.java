package com.huwei.neteasemusic.ui.popwindow.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * PopupWindow的base类
 *
 * @author jerry
 * @date 2016/03/14
 */
public class BasePopupWindow extends PopupWindow {

    protected Context mContext;

    protected List<OnShowStatusChangeListener> mListenerList = new ArrayList<>();

    public BasePopupWindow(Context context) {
        this(context, null);
    }

    public BasePopupWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasePopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                onShowChange(false);
            }
        });
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);

        onShowChange(true);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);

        onShowChange(true);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);

        onShowChange(true);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);

        onShowChange(true);
    }

    private void onShowChange(boolean isShow) {
        for (OnShowStatusChangeListener listener : mListenerList) {
            if (listener != null) {
                listener.onShowChange(this, isShow);
            }
        }
    }

    protected void setContentView(@LayoutRes int layoutRes) {
        setContentView(LayoutInflater.from(mContext).inflate(layoutRes, null,true));
    }

    protected View findViewById(int resId) {
        View contentView = getContentView();
        if (contentView != null) {
            return contentView.findViewById(resId);
        }
        return null;
    }

    public void setOutSideDismiss() {
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(true);
        setOutsideTouchable(true);
    }

    public void addOnShowStatusChangeListener(OnShowStatusChangeListener mOnShowStatusChangeListener) {
        if (mOnShowStatusChangeListener != null) {
            mListenerList.add(mOnShowStatusChangeListener);
        }
    }

    /**
     * show change变化listener
     */
    public interface OnShowStatusChangeListener {
        void onShowChange(PopupWindow popupWindow, boolean isShow);
    }
}
