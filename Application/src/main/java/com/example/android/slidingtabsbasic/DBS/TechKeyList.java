package com.example.android.slidingtabsbasic.DBS;

import android.os.Parcel;
import android.os.Parcelable;

public class TechKeyList extends TechCategoryList implements Parcelable{

    public TechKeyList(int id, String name, int favorite) {
        super(id, name, favorite);
    }

    protected TechKeyList(Parcel in) {

    }

    public static final Creator<TechKeyList> CREATOR = new Creator<TechKeyList>() {
        @Override
        public TechKeyList createFromParcel(Parcel in) {
            return new TechKeyList(in);
        }

        @Override
        public TechKeyList[] newArray(int size) {
            return new TechKeyList[size];
        }
    };

    @Override
    String getTableName() {
        return "keywords";
    }

    @Override
    String[] getColumnsName(){
        return new String[]{"id", "name", "favorite"};
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
