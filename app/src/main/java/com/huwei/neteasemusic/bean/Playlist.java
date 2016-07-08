package com.huwei.neteasemusic.bean;

import java.util.List;

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
    /**
     * subscribers : []
     * artists : null
     * tracks : null
     * tags : ["华语","民谣","轻音乐"]
     * coverImgId : 3394192403329360
     * subscribedCount : 200
     * trackUpdateTime : 1467873211747
     * newImported : false
     * totalDuration : 0
     * trackNumberUpdateTime : 1465119828587
     * cloudTrackCount : 0
     * description : 他们在创作这些歌曲的时候，或许只是刚饮下一碗烈酒，于是，言语也变得无力和多余，于是，便有了这些没有歌词的即兴民谣。兴起了就随意哼唱两句，发泄完了就低头solo，不管明天到不到来，你爱不爱听，总之，他们也不知道他们在唱什么，总之，当下的音符就是最美的妙音。于是，这些曲子便多了几分随性与灵性，而最有意思的便是，你的每一次聆听都会有新的诠释。
     * status : 0
     * adType : 0
     * updateTime : 1466144473312
     * createTime : 1465103684490
     * specialType : 0
     * commentThreadId : A_PL_0_395379290
     * shareCount : 12
     * commentCount : 9
     */

    public List<Artist> artists;
    public Object tracks;
    public long coverImgId;
    public int subscribedCount;
    public long trackUpdateTime;
    public boolean newImported;
    public int totalDuration;
    public long trackNumberUpdateTime;
    public int cloudTrackCount;
    public String description;
    public int status;
    public int adType;
    public long updateTime;
    public long createTime;
    public int specialType;
    public String commentThreadId;
    public int shareCount;
    public int commentCount;
    public List<?> subscribers;
    public List<String> tags;


    public String getPlayCountStr() {
        if (playCount > 10000) {
            return playCount / 10000 + "万次";
        }
        return playCount + "次";
    }

}
