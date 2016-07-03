package com.huwei.neteasemusic.ui.widget.divider;

/**
 * 分割线　显示范围  start <= x <= end
 *
 * @author jerry
 * @date 2016/04/14
 */
public class ShowRange {
    public int start;
    public int end;

    private ShowRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static final ShowRange get(int start, int end){
        return new ShowRange(start,end);
    }
}
