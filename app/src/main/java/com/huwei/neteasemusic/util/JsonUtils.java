package com.huwei.neteasemusic.util;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Json工具类
 *
 * @author carlos
 * @date 2016/4/21
 *
 * 修订: 2016/05/05 carlos
 *      出现异常时, log中增加对异常信息的打印.
 */
public class JsonUtils {
    private static final String TAG = "JsonError";

    /*********************************************************/

    public static String toJSONString(Object object) {
        try {
            String json = JSON.toJSONString(object);
            LogUtils.i(TAG,"json = "+ json);
            return json;
        }
        catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "Bean-Json错误. object = " + object);
            ToastUtils.showDebug("Bean-Json错误");
        }
        return null;
    }

    /**********************************************************/

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        try {
            return JSON.parseArray(text, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "Json解析错误. " + e.toString());
            LogUtils.e(TAG, "json =  " + text);
            ToastUtils.showDebug("Json-Bean错误.");
        }
        return null;
    }

    public static List<Object> parseArray(String text, Type[] types) {
        try {
            return JSON.parseArray(text, types);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "Json解析错误. " + e.toString());
            LogUtils.e(TAG, "json =  " + text);
            ToastUtils.showDebug("Json-Bean错误");
        }
        return null;
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            T result = JSON.parseObject(text, clazz);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "Json解析错误. " + e.toString());
            LogUtils.e(TAG, "json =  " + text);
            ToastUtils.showDebug("Json-Bean错误");
        }
        return null;
    }

    public static <T> T parseObject(String text, Type clazz) {
        try {
            return JSON.parseObject(text, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "Json解析错误. " + e.toString());
            LogUtils.e(TAG, "json =  " + text);
            ToastUtils.showDebug("Json-Bean错误");
        }
        return null;
    }
}