package com.huwei.neteasemusic.util.network;


import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.bean.resp.CommonResp;
import com.huwei.neteasemusic.bean.resp.ErrorType;
import com.huwei.neteasemusic.bean.resp.ServerTip;
import com.huwei.neteasemusic.util.JsonUtils;




/**
 * 标准接口的处理
 *
 * @author jerry
 * @date 2015/05/24
 */
public abstract class HttpHandler<T> extends AbsHttpHandler<T> {

    public HttpHandler() {

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

            CommonResp commonResp = JsonUtils.parseObject(respBodyStr, CommonResp.class);

            if (commonResp != null) {
                if (commonResp.code() == ErrorType.E_OK) {
                    T data = (T) JsonUtils.parseObject(commonResp.getData(), entityClass);
                    if (data != null) {
                        onSuccessOnUIThread(commonResp, data);
                    } else {
                        onFailureOnUIThread(new ServerTip(ErrorType.E_JSON_PARSE, mAppContext.getString(R.string.tips_json_parse_error, respBodyStr)));
                    }
                } else {
                    onFailureOnUIThread(new ServerTip(ErrorType.E_JSON_PARSE, mAppContext.getString(R.string.tips_json_parse_error, respBodyStr)));
                }
            } else {
                onFailureOnUIThread(new ServerTip(ErrorType.E_JSON_PARSE, mAppContext.getString(R.string.tips_json_parse_error, respBodyStr)));
            }

        } catch (Exception e) {
            e.printStackTrace();
            onFailureOnUIThread(new ServerTip(ErrorType.E_JSON_PARSE, mAppContext.getString(R.string.tips_json_parse_error, respBodyStr)));
        }
    }


}
