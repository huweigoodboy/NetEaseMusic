package com.huwei.neteasemusic.bean.resp;

import com.huwei.neteasemusic.util.StringUtils;

/**
 * @author jerry
 * @date 2016/03/09
 */
public class CommonResp extends ServerTip {
    //    "errno":xxx, //错误码
//            "desc":"xxxx", //错误信息
//            "data":{
//    }

    public String result;
    public String data;

    public String getData() {
        if(StringUtils.isNotEmpty(result)){
            return result;
        }else  if(StringUtils.isNotEmpty(data)){
            return data;
        }
        return data;
    }
}
