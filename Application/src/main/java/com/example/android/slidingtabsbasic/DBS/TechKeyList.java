package com.example.android.slidingtabsbasic.DBS;

import android.os.Parcel;
import android.os.Parcelable;

public class TechKeyList implements Parcelable{
    private int id;
    private String name;
    private int favorite;


    public TechKeyList(){

    }

    protected TechKeyList(Parcel in) {

    }

    public static final Parcelable.Creator<TechKeyList> CREATOR = new Creator<TechKeyList>() {
        @Override
        public TechKeyList createFromParcel(Parcel in) {
            return new TechKeyList(in);
        }

        @Override
        public TechKeyList[] newArray(int size) {
            return new TechKeyList[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(favorite);
    }
}
