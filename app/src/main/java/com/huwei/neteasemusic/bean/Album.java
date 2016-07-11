package com.huwei.neteasemusic.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jerry
 * @date 2016/07/03
 */
public class Album implements SuggestItem, Parcelable {

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

    public int id;
    public String name;
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
    public List<Song> songs;
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


    public Album() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.artist, flags);
        dest.writeLong(this.publishTime);
        dest.writeInt(this.size);
        dest.writeInt(this.copyrightId);
        dest.writeInt(this.status);
        dest.writeLong(this.picId);
        dest.writeByte(this.paid ? (byte) 1 : (byte) 0);
        dest.writeByte(this.onSale ? (byte) 1 : (byte) 0);
        dest.writeString(this.briefDesc);
        dest.writeString(this.picUrl);
        dest.writeString(this.commentThreadId);
        dest.writeString(this.company);
        dest.writeString(this.tags);
        dest.writeString(this.blurPicUrl);
        dest.writeInt(this.companyId);
        dest.writeLong(this.pic);
        dest.writeString(this.type);
        dest.writeString(this.description);
        dest.writeList(this.songs);
        dest.writeStringList(this.alias);
        dest.writeTypedList(this.artists);
    }

    protected Album(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.artist = in.readParcelable(Artist.class.getClassLoader());
        this.publishTime = in.readLong();
        this.size = in.readInt();
        this.copyrightId = in.readInt();
        this.status = in.readInt();
        this.picId = in.readLong();
        this.paid = in.readByte() != 0;
        this.onSale = in.readByte() != 0;
        this.briefDesc = in.readString();
        this.picUrl = in.readString();
        this.commentThreadId = in.readString();
        this.company = in.readString();
        this.tags = in.readString();
        this.blurPicUrl = in.readString();
        this.companyId = in.readInt();
        this.pic = in.readLong();
        this.type = in.readString();
        this.description = in.readString();
        this.songs = new ArrayList<Song>();
        in.readList(this.songs, Song.class.getClassLoader());
        this.alias = in.createStringArrayList();
        this.artists = in.createTypedArrayList(Artist.CREATOR);
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @Override
    public String getName() {
        return name;
    }
}
