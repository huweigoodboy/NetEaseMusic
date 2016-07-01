package com.huwei.neteasemusic.bean.resp;



import com.huwei.neteasemusic.util.network.HttpHandler;
import com.huwei.neteasemusic.util.network.HttpParams;
import com.huwei.neteasemusic.util.network.HttpUtil;

/**
 * 网易云音乐的若干请求的包装
 *
 * @author jerry
 * @date 2016/07/01
 */
public class NetEaseAPI {

    public static final String URL = "http://music.163.com/api";
    public static final String URL_WE = "http://music.163.com/weapi";
    public static final String URL_V1 = "http://music.163.com/weapi/v1";

    public static String getCompleteUrl(String subPath) {
        return URL + subPath;
    }

    public static String getCompleteUrl_WE(String subPath) {
        return URL_WE + subPath;
    }

    public static String getCompleteUrl_V1(String subPath) {
        return URL_V1 + subPath;
    }

    /**
     * 搜索建议
     * @param s
     * @param limit
     * @param handler
     */
    public static void suggest(String s, int limit, HttpHandler handler){
        String path = getCompleteUrl("/search/suggest/web");
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s",s);
        httpParams.add("limit",limit);
        HttpUtil.post(path,httpParams,handler);
    }
}
