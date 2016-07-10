package com.huwei.neteasemusic.util.network;

import android.util.Log;


import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.huwei.neteasemusic.NetEaseApplication;
import com.huwei.neteasemusic.util.JsonUtils;
import com.huwei.neteasemusic.util.StringUtils;


import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 对于网络请求框架volley的封装
 *
 * @author jayce
 * @date 2015/05/24
 */
public class HttpUtil {

    public static final String URLROOT = "";   //目前不需要请求自己的服务器 暂时空出来
    public static final String HTTP = "http";
    public static final String HTTPS = "https";


    public static final String TAG = "HTTP";

    public static SharedPrefsCookiePersistor persistor = new SharedPrefsCookiePersistor(NetEaseApplication.CONTEXT);

    /**
     * OkHttpClient实例
     */
    private static OkHttpClient client;

    private static OkHttpClient getClient() {
        if (client == null) {
            ClearableCookieJar cookieJar =
                    new PersistentCookieJar(new SetCookieCache(), persistor);
            client = new OkHttpClient.Builder().cookieJar(cookieJar).build();
//            client.interceptors().add(new RetryInterceptor());
//            client.setConnectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
//            client.setReadTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
//            client.setWriteTimeout(WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        }
        return client;
    }



    /**
     * 封装的Post请求
     *
     * @param url
     * @param params
     * @param handler
     */
    public static void post(String url, final HttpParams params, AbsHttpHandler handler) {
        url = handleurl(url);

        Log.i(TAG, "request post url:" + url + "\n" + JsonUtils.toJSONString(params.getParams()));

        notifyHandlerStart(handler);

        FormBody.Builder formBuilder = new FormBody.Builder();

        for (String key : params.getParams().keySet()) {
            formBuilder.add(key, params.getParams().get(key));
        }

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(formBuilder.build());

        Map<String,String> headers = params.getHeaders();
        if (headers != null) {
            for (String key : headers.keySet()) {
                if (StringUtils.isNotEmpty(key)) {
                    requestBuilder.addHeader(key, headers.get(key));
                }
            }
        }

        getClient().newCall(requestBuilder.build()).enqueue(handler);
    }


    /**
     * 封装的get请求
     *
     * @param url
     * @param params
     * @param handler
     */
    public static void get(String url, final HttpParams params,final AbsHttpHandler handler) {
        url = handleurl(url);
        url = addParamsToUrl(url, params); //拼接参数

        Log.i(TAG, "request get url:" + url);
        notifyHandlerStart(handler);

        Request.Builder requestBuilder = new Request.Builder()
                .url(url);

        Map<String,String> headers = params.getHeaders();
        if (headers != null) {
            for (String key : headers.keySet()) {
                if (StringUtils.isNotEmpty(key)) {
                    requestBuilder.addHeader(key, headers.get(key));
                }
            }
        }

        getClient().newCall(requestBuilder.build()).enqueue(handler);
    }

    /**
     * 同步get请求
     *
     * @param url    地址
     * @param params 参数
     * @return
     */
    public static String getSync(String url, final HttpParams params) {
        url = handleurl(url);
        url = addParamsToUrl(url, params); //拼接参数
        Log.i(TAG, "request getSync url:" + url);

        Call call;

        Request build = new Request.Builder()
                .url(url)
                .build();

        call = getClient().newCall(build);

        Response response = null;
        String respBodyStr = "";
        try {
            response = call.execute();

            respBodyStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "response:" + response);
        return respBodyStr;
    }


    public static String getCompleteUrl(String addurl) {
        return URLROOT + addurl;
    }

    public static String handleurl(String url) {
        //判断是否为完整地址，如果不是，自动指向app服务器并补全
        if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
            url = getCompleteUrl(url);
        }

        //自动在地址末尾添加"/"
//        if(!url.endsWith("/")){
//            url+="/";
//        }
        return url;
    }

    /**
     * 通知handler请求已经开始
     *
     * @param handler
     */
    static void notifyHandlerStart(AbsHttpHandler handler) {
        if (handler != null) {
            handler.onStart();
        }
    }

    static void notifyHandlerFailure(AbsHttpHandler handler, Call call, IOException e) {
        if (handler != null) {
            handler.onFailure(call, e);

            //这里还需要手动 调finish
            handler.onFinish();
        }
    }

    /**
     * 拼接参数到请求地址
     *
     * @param params
     */
    static String addParamsToUrl(String url, HttpParams params) {
        boolean isFirst = true;
        url += "?";
        Set<String> keys = params.getParams().keySet();
        for (String key : keys) {
            if (!isFirst) {
                url += "&";
            }
            url += key + "=" + params.getParams().get(key);
            isFirst = false;
        }
        String spaceStr = url.replaceAll(" ", "%20");
        return spaceStr;
    }

    static String paramsToStr(HttpParams params) {
        StringBuffer buffer = new StringBuffer();
        Set<String> keys = params.getParams().keySet();
        for (String key : keys) {
            buffer.append("&" + key + "=" + params.getParams().get(key));
        }
        return buffer.toString();
    }
}
