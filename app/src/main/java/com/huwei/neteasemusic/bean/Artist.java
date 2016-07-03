package com.huwei.neteasemusic.bean;

import java.util.List;

/**
 * @author jerry
 * @date 2016/07/03
 */
public class Artist extends SuggestItem{


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

//    public int id;
//    public String name;
    public String picUrl;
    public int albumSize;
    public long picId;
    public String img1v1Url;
    public long img1v1;
    public String trans;
    public List<String> alias;
    public List<String> transNames;
}
