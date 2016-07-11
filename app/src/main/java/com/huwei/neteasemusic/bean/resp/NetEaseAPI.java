package com.huwei.neteasemusic.bean.resp;


import android.util.Base64;

import com.huwei.neteasemusic.bean.inter.SType;
import com.huwei.neteasemusic.bean.req.EncryptUrlReq;
import com.huwei.neteasemusic.util.ToastUtils;
import com.huwei.neteasemusic.util.security.AESUtils;
import com.huwei.neteasemusic.util.security.CHexConver;
import com.huwei.neteasemusic.util.JsonUtils;
import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.network.HttpHandler;
import com.huwei.neteasemusic.util.network.HttpParams;
import com.huwei.neteasemusic.util.network.HttpUtil;
import com.huwei.neteasemusic.util.network.UHttpHandler;
import com.huwei.neteasemusic.util.security.RSAUtils;
import com.huwei.neteasemusic.util.security.SecuityRequest;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.List;

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

    public static final String SEARCH_SUB = "/search/pc";  ///search/pc 比 /search/get 返回的 信息要全面

    public static final String FIRST_AES_KEY = "0CoJUm6Qyw8W8jud";
    public static final String UTF8 = "utf-8";


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
     *
     * @param s
     * @param limit
     * @param handler
     */
    public static void suggest(String s, int limit, HttpHandler<SearchSuggestResp> handler) {
        String path = getCompleteUrl("/search/suggest/web");
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s", s);
        httpParams.add("limit", limit);
        HttpUtil.post(path, httpParams, handler);
    }


    // 搜索单曲(1)，歌手(100)，专辑(10)，歌单(1000)，用户(1002) *(type)*

    /**
     * 搜索歌曲
     *
     * @param s
     * @param offset
     * @param limit
     * @param httpHandler
     */
    public static void searchSong(String s, int offset, int limit, HttpHandler<SrSongListResp> httpHandler) {
        String path = getCompleteUrl(SEARCH_SUB);
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s", s);
        httpParams.add("offset", offset);
        httpParams.add("limit", limit);
        httpParams.add("total", false);
        httpParams.add("type", SType.SONG);
        HttpUtil.post(path, httpParams, httpHandler);
    }

    /**
     * 搜索歌手
     *
     * @param s
     * @param offset
     * @param limit
     * @param httpHandler
     */
    public static void searchArtist(String s, int offset, int limit, HttpHandler<SrArtistListResp> httpHandler) {
        String path = getCompleteUrl(SEARCH_SUB);
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s", s);
        httpParams.add("offset", offset);
        httpParams.add("limit", limit);
        httpParams.add("total", false);
        httpParams.add("type", SType.ARTIST);
        HttpUtil.post(path, httpParams, httpHandler);
    }

    /**
     * 搜索专辑
     *
     * @param s
     * @param offset
     * @param limit
     * @param httpHandler
     */
    public static void searchAlbum(String s, int offset, int limit, HttpHandler<SrAlbumListResp> httpHandler) {
        String path = getCompleteUrl(SEARCH_SUB);
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s", s);
        httpParams.add("offset", offset);
        httpParams.add("limit", limit);
        httpParams.add("total", false);
        httpParams.add("type", SType.ALBUM);
        HttpUtil.post(path, httpParams, httpHandler);
    }

    /**
     * 搜索歌单
     *
     * @param s
     * @param offset
     * @param limit
     * @param httpHandler
     */
    public static void searchPlaylist(String s, int offset, int limit, HttpHandler<SrPlayListResp> httpHandler) {
        String path = getCompleteUrl(SEARCH_SUB);
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s", s);
        httpParams.add("offset", offset);
        httpParams.add("limit", limit);
        httpParams.add("total", false);
        httpParams.add("type", SType.PLAYLIST);
        HttpUtil.post(path, httpParams, httpHandler);
    }

    /**
     * 搜索MV
     *
     * @param s
     * @param offset
     * @param limit
     * @param httpHandler
     */
    public static void searchMv(String s, int offset, int limit, HttpHandler<SrMvListResp> httpHandler) {
        String path = getCompleteUrl(SEARCH_SUB);
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s", s);
        httpParams.add("offset", offset);
        httpParams.add("limit", limit);
        httpParams.add("total", false);
        httpParams.add("type", SType.MV);
        HttpUtil.post(path, httpParams, httpHandler);
    }

    /**
     * 搜索最佳匹配
     *
     * @param s
     * @param httpHandler
     */
    public static void multimatch(String s, HttpHandler httpHandler) {
        String path = getCompleteUrl("search/suggest/multimatch");
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("s", s);
        HttpUtil.post(path, httpParams, httpHandler);
    }


    /**
     * 热门/最新 歌单
     *
     * @param category
     * @param order    hot/new
     * @param offset
     * @param limit
     */
    public static void playlists(String category, String order, int offset, int limit, UHttpHandler<PlaylistsResp> httpHandler) {
        String path = getCompleteUrl("/playlist/list");
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();
        httpParams.add("cat", category);
        httpParams.add("order", order);
        httpParams.add("offset", offset);
        httpParams.add("limit", limit);
        HttpUtil.get(path, httpParams, httpHandler);
    }

    public static void getSongUrls(List<Integer> ids, int bitrate,UHttpHandler<MusicFileResp> httpHandler) {
        String path = getCompleteUrl_WE("/song/enhance/player/url");
        HttpParams httpParams = HttpParams.getNetEaseHttpParams();

        EncryptUrlReq encryptUrlReq = new EncryptUrlReq(ids, bitrate);
        String params[] = SecuityRequest.encrypt_request(encryptUrlReq);

        httpParams.add("params", params[0]);
        httpParams.add("encSecKey", params[1]);

        HttpUtil.post(path, httpParams,httpHandler);
    }
}
