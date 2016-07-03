package com.huwei.neteasemusic.bean;

/**
 * @author jerry
 * @date 2016/07/03
 */
public class Album extends SuggestItem{

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

//    public int id;
//    public String name;
    public Artist artist;
    public long publishTime;
    public int size;
    public int copyrightId;
    public int status;
    public long picId;
}
