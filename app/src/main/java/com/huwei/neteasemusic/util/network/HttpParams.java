package com.huwei.neteasemusic.util.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;

/**
 * @author jayce
 * @date 2015/05/24
 * Add parameter for http request , can only be the kind included in bases[] , or throw exception !
 */
public class HttpParams {
    Map<String, String> params = new HashMap<>();

    Map<String, String> headers = new HashMap<>();

    Class bases[] = {Integer.class, Long.class, Short.class, Float.class, Double.class, String.class,Boolean.class};

    private void HttpParams() {

    }

    public static HttpParams getNetEaseHttpParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.addHeader("Host", "music.163.com");
        httpParams.addHeader("Referer", "http://music.163.com");
        httpParams.addHeader("Connection", "keep-alive");
        httpParams.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36");

        //加入cookie


        //只需要设置 name value
//        Cookie cookie = new Cookie.Builder().name("appver").value("osx").build();
//        cookies.add(cookie);
//        cookie = new Cookie.Builder().name("os").value("osx").build();
//        cookies.add(cookie);
        Map<String,String> cookies = new HashMap<>();
        cookies.put("appver","1.2.1");
        cookies.put("os","osx");

        httpParams.addHeader("Cookie", cookieHeader(cookies));

        return httpParams;
    }

    public void add(String key, Object param) {
        try {
            if (isValidate(param)) {
                params.put(key, param + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public boolean isValidate(Object param) throws Exception {
        for (Class c : bases) {
            if (param.getClass() == c)
                return true;
        }
        throw new Exception("param " + param + " is not allowed.");
    }

    private static String cookieHeader(Map<String, String> cookies) {
        if (cookies == null) {
            return "";
        }

        StringBuilder cookieHeader = new StringBuilder();
        boolean isFirst = true;
        for (String name : cookies.keySet()) {
            if (!isFirst) {
                cookieHeader.append("; ");
            }

            isFirst = false;

            cookieHeader.append(name).append('=').append(cookies.get(name));
        }
        return cookieHeader.toString();
    }
}
