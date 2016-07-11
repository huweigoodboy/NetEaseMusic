package com.huwei.neteasemusic.bean;

import android.net.Uri;
import android.os.Parcel;

import com.huwei.neteasemusic.util.StringUtils;
import com.huwei.neteasemusic.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jerry
 * @date 2016/07/03
 */
public class Song extends AbstractMusic implements SuggestItem  {

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

    public int id;
    public String name;
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
    public String  rUrl;
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
    public List<String> alias;

    //add by search/pc

    public String mp3Url;       //优先播放这个字段的地址   没有就播放通过 接口获取具体码率的地址

    @Override
    public AbstractMusic createFromParcel(Parcel source) {
        return null;
    }

    @Override
    public AbstractMusic[] newArray(int size) {
        return new AbstractMusic[0];
    }

    @Override
    public Uri getDataSoure() {
        if (preParePlayUrl()) {
            if(StringUtils.isNotEmpty(mp3Url)){
                return Uri.parse(mp3Url);
            }
            return Uri.parse(musicFile.url);
        }
        return null;
    }

    @Override
    public Integer getDuration() {
        return duration;
    }

    @Override
    public MusicType getType() {
        return MusicType.Online;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getArtist() {
        if (Utils.canFetchFirst(artists)) {
            return artists.get(0).name;
        }
        return null;
    }

    @Override
    public String getAlbumPic() {
        if(album!=null){
            return album.picUrl;
        }
        return null;
    }

    /*******************************
     * 自构建 字段
     ******************************/
    public MusicFile musicFile;

    public boolean preParePlayUrl() {
        if (musicFile != null && StringUtils.isNotEmpty(musicFile.url)  || StringUtils.isNotEmpty(mp3Url)) {
            return true;
        }
        return false;
    }

    public static List<AbstractMusic> getMusicSourceList(List<Song> songList) {
        List<AbstractMusic> musicSourceList = new ArrayList<>();
        for (Song song : songList) {
            musicSourceList.add(song);
        }
        return musicSourceList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.album, flags);
        dest.writeInt(this.duration);
        dest.writeInt(this.copyrightId);
        dest.writeInt(this.status);
        dest.writeInt(this.rtype);
        dest.writeInt(this.ftype);
        dest.writeInt(this.mvid);
        dest.writeInt(this.fee);
        dest.writeString(this.rUrl);
        dest.writeList(this.artists);
        dest.writeStringList(this.alias);
        dest.writeParcelable(this.musicFile, flags);
    }

    public Song() {
    }

    protected Song(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.album = in.readParcelable(Album.class.getClassLoader());
        this.duration = in.readInt();
        this.copyrightId = in.readInt();
        this.status = in.readInt();
        this.rtype = in.readInt();
        this.ftype = in.readInt();
        this.mvid = in.readInt();
        this.fee = in.readInt();
        this.rUrl = in.readParcelable(Object.class.getClassLoader());
        this.artists = new ArrayList<Artist>();
        in.readList(this.artists, Artist.class.getClassLoader());
        this.alias = in.createStringArrayList();
        this.musicFile = in.readParcelable(MusicFile.class.getClassLoader());
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };



}
