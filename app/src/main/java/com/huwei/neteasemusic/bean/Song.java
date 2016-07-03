package com.huwei.neteasemusic.bean;

import java.util.List;

/**
 * @author jerry
 * @date 2016/07/03
 */
public class Song extends SuggestItem{

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

//    public int id;
//    public String name;
    /**
     * id : 3087270
     * name : Fade
     * artist : {"id":0,"name":"","picUrl":null,"alias":[],"albumSize":0,"picId":0,"img1v1Url":"http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg","img1v1":0,"trans":null}
     * publishTime : 1407945600007
     * size : 1
     * copyrightId : 0
     * status : 0
     * picId : 2540971374738594
     */

    public Album album;
    public int duration;
    public int copyrightId;
    public int status;
    public int rtype;
    public int ftype;
    public int mvid;
    public int fee;
    public Object rUrl;
    /**
     * id : 1045123
     * name : Alan Walker
     * picUrl : null
     * alias : []
     * albumSize : 0
     * picId : 0
     * img1v1Url : http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg
     * img1v1 : 0
     * trans : null
     */

    public List<Artist> artists;
    public List<?> alias;
}
