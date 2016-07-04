package com.huwei.neteasemusic.bean.resp;



import com.huwei.neteasemusic.bean.inter.SType;
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
    public static void suggest(String s, int limit, HttpHandler<SearchSuggestResp> handler){
        String path = getCompleteUrl("/search/suggest/web");
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s",s);
        httpParams.add("limit",limit);
        HttpUtil.post(path,httpParams,handler);
    }


    // 搜索单曲(1)，歌手(100)，专辑(10)，歌单(1000)，用户(1002) *(type)*

    /**
     * 搜索歌曲
     * @param s
     * @param offset
     * @param limit
     * @param httpHandler
     */
    public static void searchSong(String s,int offset,int limit,HttpHandler<SrSongListResp> httpHandler){
        String path = getCompleteUrl("/search/get");
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s",s);
        httpParams.add("offset",offset);
        httpParams.add("limit",limit);
        httpParams.add("total",false);
        httpParams.add("type", SType.SONG);
        HttpUtil.post(path,httpParams,httpHandler);
    }

    /**
     * 搜索歌手
     * @param s
     * @param offset
     * @param limit
     * @param httpHandler
     */
    public static void searchArtist(String s,int offset,int limit,HttpHandler<SrArtistListResp> httpHandler){
        String path = getCompleteUrl("/search/get");
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s",s);
        httpParams.add("offset",offset);
        httpParams.add("limit",limit);
        httpParams.add("total",false);
        httpParams.add("type", SType.ARTIST);
        HttpUtil.post(path,httpParams,httpHandler);
    }

    /**
     * 搜索专辑
     * @param s
     * @param offset
     * @param limit
     * @param httpHandler
     */
    public static void searchAlbum(String s,int offset,int limit,HttpHandler<SrAlbumListResp> httpHandler){
        String path = getCompleteUrl("/search/get");
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s",s);
        httpParams.add("offset",offset);
        httpParams.add("limit",limit);
        httpParams.add("total",false);
        httpParams.add("type", SType.ALBUM);
        HttpUtil.post(path,httpParams,httpHandler);
    }

    /**
     * 搜索歌单
     * @param s
     * @param offset
     * @param limit
     * @param httpHandler
     */
    public static void searchPlaylist(String s,int offset,int limit,HttpHandler<SrPlayListResp> httpHandler){
        String path = getCompleteUrl("/search/get");
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s",s);
        httpParams.add("offset",offset);
        httpParams.add("limit",limit);
        httpParams.add("total",false);
        httpParams.add("type", SType.PLAYLIST);
        HttpUtil.post(path,httpParams,httpHandler);
    }

    /**
     * 搜索MV
     * @param s
     * @param offset
     * @param limit
     * @param httpHandler
     */
    public static void searchMv(String s,int offset,int limit,HttpHandler<SrMvListResp> httpHandler){
        String path = getCompleteUrl("/search/get");
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s",s);
        httpParams.add("offset",offset);
        httpParams.add("limit",limit);
        httpParams.add("total",false);
        httpParams.add("type", SType.MV);
        HttpUtil.post(path,httpParams,httpHandler);
    }

    /**
     * 搜索最佳匹配
     * @param s
     * @param httpHandler
     */
    public static void multimatch(String s,HttpHandler httpHandler){
        String path = getCompleteUrl("search/suggest/multimatch");
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s",s);
        HttpUtil.post(path,httpParams,httpHandler);
    }
}
