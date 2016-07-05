package com.huwei.neteasemusic.bean;

/**
 * @author jerry
 * @date 2016/07/03
 */
public class Playlist extends SuggestItem {

    /**
     * id : 8418150
     * name : 安静看书的背景音乐
     * coverImgUrl : http://p1.music.126.net/bWOsIhgHonr92dXIN2pMcQ==/6030821278383854.jpg
     * creator : null
     * subscribed : false
     * trackCount : 27
     * userId : 80086
     * playCount : 12546533
     * bookCount : 560044
     * highQuality : false
     */

//    public int id;
//    public String name;
    public String coverImgUrl;
    public Creator creator;
    public boolean subscribed;
    public int trackCount;
    public int userId;
    public int playCount;
    public int bookCount;
    public boolean highQuality;

    public String getPlayCountStr() {
        if (playCount > 10000) {
            return playCount / 10000 + "万次";
        }
        return playCount + "次";
    }

}
