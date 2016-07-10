package com.huwei.neteasemusic.bean.req;

import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.network.HttpUtil;

import java.util.List;

import okhttp3.Cookie;

/**
 * @author jerry
 * @date 2016/07/09
 */
public class EncryptUrlReq {
    public List<Integer> ids;
    public int br;
    public String csrf_token;

    public EncryptUrlReq(List<Integer> idList, int br) {
        this.ids = idList;
        this.br = br;

        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < idList.size(); i++) {
            stringBuilder.append(idList.get(i));
            if (i != idList.size() - 1) {
                stringBuilder.append(",");
            } else {
                stringBuilder.append("]");
            }
        }

        //读取cookie
        List<Cookie> cookies = HttpUtil.persistor.loadAll();
        if (cookies != null) {
            for(Cookie cookie : cookies){
                if(StringUtils.equals("music.163.com",cookie.domain())&&StringUtils.equals("__csrf",cookie.name())){
                    csrf_token = cookie.value();
                }
            }
        }
    }
}
