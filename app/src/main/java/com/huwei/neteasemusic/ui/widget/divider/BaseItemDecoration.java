package com.huwei.neteasemusic.ui.widget.divider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * mShowRange 用于设置分割线　显示范围
 *
 * @author jerry
 * @date 2016/04/14
 */
public class BaseItemDecoration extends RecyclerView.ItemDecoration {
    protected List<ShowRange> mShowRange = new ArrayList<>();

    public List<ShowRange> getShowRange() {
        return mShowRange;
    }

    public void setShowRange(List<ShowRange> showRange) {
        this.mShowRange = showRange;
    }

    public void setShowRange(ShowRange... mShowRange) {
        this.mShowRange = Arrays.asList(mShowRange);
    }

    /**
     * 是否显示范围内
     *
     * @param itemPos
     * @param rect
     * @return
     */
    public boolean checkShowRange(int itemPos, Rect rect) {
        if (mShowRange.size() == 0) {
            return true;
        }

        for (ShowRange range : mShowRange) {
            if (itemPos >= range.start && itemPos <= range.end) {
                return true;
            }
        }
        rect.set(0, 0, 0, 0);
        return false;
    }

    public boolean checkShowRange(int itemPos){
        if (mShowRange.size() == 0) {
            return true;
        }

        for (ShowRange range : mShowRange) {
            if (itemPos >= range.start && itemPos <= range.end) {
                return true;
            }
        }
        return false;
    }
}
