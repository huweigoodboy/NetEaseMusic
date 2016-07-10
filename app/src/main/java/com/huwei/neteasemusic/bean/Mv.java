package com.huwei.neteasemusic.bean;

import java.util.List;

/**
 * @author jerry
 * @date 2016/07/03
 */
public class Mv implements SuggestItem {

    /**
     * id : 5308307
     * cover : http://p4.music.126.net/Bhug9AiM2_FEGFrBhxXdYw==/1377688074855882.jpg
     * name : 愛をちょうだい
     * playCount : 116004
     * briefDesc : AOA全新日单PV释出
     * desc : null
     * artistName : AOA
     * artistId : 126312
     * duration : 341000
     * mark : 0
     * subed : false
     * artists : [{"id":126312,"name":"AOA"},{"id":15557,"name":"西川貴教"}]
     */
//
    public int id;
    public String cover;
    public String name;
    public int playCount;
    public String briefDesc;
    public Object desc;
    public String artistName;
    public int artistId;
    public int duration;
    public int mark;
    public boolean subed;
    /**
     * id : 126312
     * name : AOA
     */

    public List<Artist> artists;

    public String getPlayCountStr() {
        if (playCount > 100000) {
            return playCount / 10000 + "万次";
        }
        return playCount + "次";
    }

    @Override
    public String getName() {
        return name;
    }
}
