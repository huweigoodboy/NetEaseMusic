package com.huwei.neteasemusic.bean.resp;

import com.huwei.neteasemusic.bean.Album;
import com.huwei.neteasemusic.bean.Artist;
import com.huwei.neteasemusic.bean.Mv;
import com.huwei.neteasemusic.bean.Playlist;
import com.huwei.neteasemusic.bean.Song;

import java.util.List;

/**
 * @author jerry
 * @date 2016/07/03
 */
public class SearchSuggestResp {

    /**
     * id : 2646285
     * name : 安和桥北
     * artist : {"id":5073,"name":"宋冬野","picUrl":"http://p4.music.126.net/X0OxWhqJBnlwmnvXZ_hjeA==/208907209294846.jpg","alias":[],"albumSize":4,"picId":208907209294846,"img1v1Url":"http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg","img1v1":0,"trans":null}
     * publishTime : 1377446400007
     * size : 12
     * copyrightId : 5003
     * status : 0
     * picId : 1984618488161733
     */

    public List<Album> albums;
    /**
     * id : 46487
     * name : Adele
     * picUrl : http://p3.music.126.net/_cSzHw9F-Nkgy9gb2QPI_A==/3263350518850889.jpg
     * alias : ["Adele Laurie Blue Adkins"]
     * albumSize : 28
     * picId : 3263350518850889
     * img1v1Url : http://p3.music.126.net/6iuRSwLHGrjp3RJH86tfuQ==/3261151495434554.jpg
     * img1v1 : 3261151495434554
     * transNames : ["阿黛尔"]
     * trans : 阿黛尔
     */

    public List<Artist> artists;
    /**
     * id : 29947420
     * name : Fade
     * artists : [{"id":1045123,"name":"Alan Walker","picUrl":null,"alias":[],"albumSize":0,"picId":0,"img1v1Url":"http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg","img1v1":0,"trans":null}]
     * album : {"id":3087270,"name":"Fade","artist":{"id":0,"name":"","picUrl":null,"alias":[],"albumSize":0,"picId":0,"img1v1Url":"http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg","img1v1":0,"trans":null},"publishTime":1407945600007,"size":1,"copyrightId":0,"status":0,"picId":2540971374738594}
     * duration : 264254
     * copyrightId : 0
     * status : 0
     * alias : []
     * rtype : 0
     * ftype : 0
     * mvid : 0
     * fee : 0
     * rUrl : null
     */

    public List<Song> songs;
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

    public List<Playlist> playlists;
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

    public List<Mv> mvs;
    public List<String> order;
}
