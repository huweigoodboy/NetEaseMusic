package com.huwei.neteasemusic.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author jerry
 * @date 2016/07/03
 */
public class Artist implements SuggestItem, Parcelable {


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

    public int id;
    public String name;
    public String picUrl;
    public int albumSize;
    public long picId;
    public String img1v1Url;
    public long img1v1;
    public String trans;
    public List<String> alias;
    public List<String> transNames;



    @Override
    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.picUrl);
        dest.writeInt(this.albumSize);
        dest.writeLong(this.picId);
        dest.writeString(this.img1v1Url);
        dest.writeLong(this.img1v1);
        dest.writeString(this.trans);
        dest.writeStringList(this.alias);
        dest.writeStringList(this.transNames);
    }

    public Artist() {
    }

    protected Artist(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.picUrl = in.readString();
        this.albumSize = in.readInt();
        this.picId = in.readLong();
        this.img1v1Url = in.readString();
        this.img1v1 = in.readLong();
        this.trans = in.readString();
        this.alias = in.createStringArrayList();
        this.transNames = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}
