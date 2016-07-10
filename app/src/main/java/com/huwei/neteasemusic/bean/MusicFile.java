package com.huwei.neteasemusic.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author jerry
 * @date 2016/07/10
 */
public class MusicFile implements Parcelable{

    public boolean canExtend;
    public String url;
    public double gain;
    public int fee;
    public int expi;
    public int id;
    public String uf;
    public int code;
    public int br;
    public String type;
    public int size;
    public int payed;
    public String md5;
    public int flag;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.canExtend ? (byte) 1 : (byte) 0);
        dest.writeString(this.url);
        dest.writeDouble(this.gain);
        dest.writeInt(this.fee);
        dest.writeInt(this.expi);
        dest.writeInt(this.id);
        dest.writeString(this.uf);
        dest.writeInt(this.code);
        dest.writeInt(this.br);
        dest.writeString(this.type);
        dest.writeInt(this.size);
        dest.writeInt(this.payed);
        dest.writeString(this.md5);
        dest.writeInt(this.flag);
    }

    public MusicFile() {
    }

    protected MusicFile(Parcel in) {
        this.canExtend = in.readByte() != 0;
        this.url = in.readString();
        this.gain = in.readDouble();
        this.fee = in.readInt();
        this.expi = in.readInt();
        this.id = in.readInt();
        this.uf = in.readString();
        this.code = in.readInt();
        this.br = in.readInt();
        this.type = in.readString();
        this.size = in.readInt();
        this.payed = in.readInt();
        this.md5 = in.readString();
        this.flag = in.readInt();
    }

    public static final Creator<MusicFile> CREATOR = new Creator<MusicFile>() {
        @Override
        public MusicFile createFromParcel(Parcel source) {
            return new MusicFile(source);
        }

        @Override
        public MusicFile[] newArray(int size) {
            return new MusicFile[size];
        }
    };
}
