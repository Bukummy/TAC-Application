package com.example.android.slidingtabsbasic.DBS;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bukunmi on 10/16/2015.
 */
public class TechCategoryList implements Parcelable {
// TOdo this class structure


    protected int id;
    protected String name;
    protected int favorite;

    public TechCategoryList(int id, String name, int favorite) {
        this.id = id;
        this.name = name;
        this.favorite = favorite;
    }

    protected TechCategoryList(Parcel in) {
        id = in.readInt();
        name = in.readString();
        favorite = in.readInt();
    }

    public static final Creator<TechCategoryList> CREATOR = new Creator<TechCategoryList>() {
        @Override
        public TechCategoryList createFromParcel(Parcel in) {
            return new TechCategoryList(in);
        }

        @Override
        public TechCategoryList[] newArray(int size) {
            return new TechCategoryList[size];
        }
    };

    public TechCategoryList() {
    }

    String  getTableName(){
        return "categories";
    }
    String[] getColumnsName(){
        return new String[]{"id", "name", "favorite"};
    }


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

