package com.huwei.neteasemusic.bean;

import java.util.List;

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
    /**
     * songs : []
     * paid : false
     * onSale : false
     * alias : ["Jay Chou's Bedtime Stories"]
     * artists : [{"img1v1Id":0,"alias":[],"picId":0,"briefDesc":"","picUrl":"http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg","albumSize":0,"img1v1Url":"http://p4.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg","trans":"","musicSize":0,"name":"周杰伦","id":6452}]
     * briefDesc :
     * picUrl : http://p3.music.126.net/cUTk0ewrQtYGP2YpPZoUng==/3265549553028224.jpg
     * commentThreadId : R_AL_3_34720827
     * company : 杰威尔
     * tags :
     * blurPicUrl : http://p4.music.126.net/cUTk0ewrQtYGP2YpPZoUng==/3265549553028224.jpg
     * companyId : 0
     * pic : 3265549553028224
     * type : 专辑
     * description :
     */

    public boolean paid;
    public boolean onSale;
    public String briefDesc;
    public String picUrl;
    public String commentThreadId;
    public String company;
    public String tags;
    public String blurPicUrl;
    public int companyId;
    public long pic;
    public String type;
    public String description;
    public List<?> songs;
    public List<String> alias;
    /**
     * img1v1Id : 0
     * alias : []
     * picId : 0
     * briefDesc :
     * picUrl : http://p3.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg
     * albumSize : 0
     * img1v1Url : http://p4.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg
     * trans :
     * musicSize : 0
     * name : 周杰伦
     * id : 6452
     */

    public List<Artist> artists;

}
