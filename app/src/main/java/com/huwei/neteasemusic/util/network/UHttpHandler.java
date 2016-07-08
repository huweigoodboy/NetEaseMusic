package com.huwei.neteasemusic.util.network;

import com.huwei.neteasemusic.R;
import com.huwei.neteasemusic.bean.resp.ErrorType;
import com.huwei.neteasemusic.bean.resp.ServerTip;
import com.huwei.neteasemusic.util.JsonUtils;

/**
 * serverTip 和 data传同一个参数
 *
 * @author jerry
 * @date 2016/07/08
 */
public abstract class UHttpHandler<T> extends AbsHttpHandler<T> {

    @Override
    protected void parseResult(String respBodyStr) {
        try{
        ServerTip base = (ServerTip) JsonUtils.parseObject(respBodyStr,entityClass);
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

}
