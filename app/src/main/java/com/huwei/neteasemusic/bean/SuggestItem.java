package com.huwei.neteasemusic.bean;

import com.huwei.neteasemusic.bean.inter.SType;

/**
 * @author jerry
 * @date 2016/07/03
 */
public class SuggestItem {
    public int id;
    public String name;

    public int getSearchType(){
        if(this instanceof Song){
            return SType.SONG;
        }else if(this instanceof Artist){
            return SType.ARTIST;
        }else if(this instanceof Album){
            return SType.ALBUM;
        }else if(this instanceof Playlist){
            return SType.PLAYLIST;
        }else if(this instanceof Mv){
            return SType.MV;
        }
        return SType.SONG;
    }
}
