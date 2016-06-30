package com.huwei.neteasemusic.inter;

/**
 * 布局改变监听
 *
 * @author jerry
 * @date 2016/03/17
 */
public interface IOnReSizeListener {
    /**
     * @param changeType
     * @param isSmaller  布局高度是否变小
     * @param whValue    wh 四个值封装到数组里面
     */
    void onReSize(ChangeType changeType, boolean isSmaller, int whValue[]);

    /**
     * 改变的类型
     */
    enum ChangeType {
        softInput,  //输入法
        virtualKey  //虚拟键盘
    }
}
