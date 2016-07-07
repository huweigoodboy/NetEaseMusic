package com.huwei.neteasemusic.bean.resp;

/**
 * 公共返回码
 *
 * @author jerry
 * @date 2016/03/21
 */
public class ErrorType {

    public static final int E_LOCAL = -1; //本地网络框架报告的异常
    public static final int E_NETWORK = -2;  //网络错误
    public static final int E_JSON_PARSE = -3; //json解析错误

    public static final int E_OK = 200; //正常


    /**
     * 需要提示给用户
     *
     * @param errno
     * @return
     */
    public static boolean isNeedTipToUser(int errno) {
        return errno > 0;
    }
}
