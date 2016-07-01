package com.huwei.neteasemusic.util.network;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


import com.huwei.neteasemusic.NetEaseApplication;
import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.bean.resp.ErrorType;
import com.huwei.neteasemusic.bean.resp.ServerTip;
import com.huwei.neteasemusic.util.JsonUtils;
import com.huwei.neteasemusic.util.LogUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 标准接口的处理
 *
 * @author jerry
 * @date 2015/05/24
 */
public abstract class HttpHandler<T extends ServerTip> implements Callback {

    public static final String TAG = "HTTP";

    protected Context mAppContext;

    protected Class<T> entityClass;   //T.class 泛型的class类型  用于json解析

    public HttpHandler() {
        this.mAppContext = NetEaseApplication.CONTEXT;
        try {
            entityClass = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (Exception e) {
            e.printStackTrace();
            entityClass = (Class<T>) ServerTip.class;


        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        LogUtils.e(TAG, "onFailure " + e.getMessage());
        if(e instanceof UnknownHostException){
            onFailureOnUIThread(new ServerTip(ErrorType.E_LOCAL, "host is error"));
        }else{
            onFailureOnUIThread(new ServerTip(ErrorType.E_LOCAL, e.getLocalizedMessage()));
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response != null) {
            if (response.code() == 200) {
                //请求码成功
                String respBodyStr = response.body().string();
                LogUtils.i(TAG, "respBodyStr    " + " :" + respBodyStr);

                if (!TextUtils.isEmpty(respBodyStr)) {
                    parseResult(respBodyStr);
                } else {
                    onFailureOnUIThread(new ServerTip(ErrorType.E_NETWORK, mAppContext.getString(R.string.tips_network_error, response.code())));
                }
            } else {
                onFailureOnUIThread(new ServerTip(ErrorType.E_NETWORK, mAppContext.getString(R.string.tips_network_error, response.code())));
            }
        }
    }

    /**
     * 解析请求结果
     *
     * @param respBodyStr
     */
    protected void parseResult(String respBodyStr) {
        try {
//            BaseResult resp = JsonUtils.parseObject(respBodyStr, BaseResult.class);
//            if (resp != null) {
//                if (resp.errno() == ErrorType.E_OK) {
//                    //请求成功
//                    //后台没有返回data类型
//                    if (resp.data == null) {
//                        onSuccessOnUIThread(resp, null);
//                    } else {
//                        T data = JsonUtils.parseObject(resp.data, entityClass);
//                        if (data != null) {
//                            onSuccessOnUIThread(resp, data);
//                        } else {
//                            onFailureOnUIThread(new ServerTip(ErrorType.E_JSON_PARSE, mAppContext.getString(cn.youcaitv.live.R.string.tips_json_parse_error, respBodyStr)));
//                        }
//                    }
//                } else {
//                    onFailureOnUIThread(resp);
//                }
//            } else {
//                onFailureOnUIThread(new ServerTip(ErrorType.E_JSON_PARSE, mAppContext.getString(cn.youcaitv.live.R.string.tips_json_parse_error, respBodyStr)));
//            }

            ServerTip base = JsonUtils.parseObject(respBodyStr,entityClass);
            if(base != null){
                if(base.code() == ErrorType.E_OK){
                    onSuccessOnUIThread(base, (T) base);
                }else{
                    onFailureOnUIThread(new ServerTip(ErrorType.E_JSON_PARSE, mAppContext.getString(R.string.tips_json_parse_error, respBodyStr)));
                }
            }else{
                onFailureOnUIThread(new ServerTip(ErrorType.E_JSON_PARSE, mAppContext.getString(R.string.tips_json_parse_error, respBodyStr)));
            }

        } catch (Exception e) {
            e.printStackTrace();
            onFailureOnUIThread(new ServerTip(ErrorType.E_JSON_PARSE, mAppContext.getString(R.string.tips_json_parse_error, respBodyStr)));
        }
    }

    private final void onSuccessOnUIThread(final ServerTip serverTip, final T t) {
        NetEaseApplication.runUiThread(new Runnable() {
            @Override
            public void run() {
                onSuccess(t);
                onFinish();
            }
        });
    }

    private final void onFailureOnUIThread(final ServerTip serverTip) {
        NetEaseApplication.runUiThread(new Runnable() {
            @Override
            public void run() {
//                if (serverTip.errno == ErrorType.E_TOKEN || serverTip.errno == ErrorType.E_DELETED_USER) {
//                    //无Token或者被拉黑
//                    final Intent intent = PreLoginActivity.getStartActIntent(NetEaseApplication.get());
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    AppUtils.launchApp(NetEaseApplication.get(), intent);
//                    //被踢下线,弹出提示
//                    ToastUtils.showShort(mAppContext, serverTip.desc() + "");
//                } else {
                    onFailure(serverTip);
//                }
                onFinish();
            }
        });
    }

    public abstract void onSuccess(T t);

    /**
     * 需要对错误进行特殊处理 请覆写这个方法
     *
     * @param serverTip
     */
    public void onFailure(ServerTip serverTip) {
        LogUtils.e(TAG, "Code:" + serverTip.code() + "  Msg:" + serverTip.msg());
        if (ErrorType.isNeedTipToUser(serverTip.code())) {
            Toast.makeText(mAppContext, serverTip.msg() + "", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 网络请求开始
     */
    public void onStart() {

    }

    /**
     * 网络请求结束
     */
    public void onFinish() {

    }

//    /**
//     * data 解析类型
//     */
//    public enum ParseType {
//        object, array
//    }
}
