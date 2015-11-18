package com.example.android.slidingtabsbasic.DBS;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

/**
 * Created by Bukunmi on 10/16/2015.
 */

public class TechAnnounce implements Parcelable {



    private int id;
    private String title;
    private String link;
    private String description;
    private String[] categoryList;
    private Timestamp dateAdded;

    private String date;
    private int saved;

    protected TechAnnounce(Parcel in) {
        id = in.readInt();
        title = in.readString();
        link = in.readString();
        description = in.readString();
        categoryList = in.createStringArray();
        date = in.readString();
        saved = in.readInt();
    }

    public TechAnnounce() {
    }

    public static final Creator<TechAnnounce> CREATOR = new Creator<TechAnnounce>() {
        @Override
        public TechAnnounce createFromParcel(Parcel in) {
            return new TechAnnounce(in);
        }

        @Override
        public TechAnnounce[] newArray(int size) {
            return new TechAnnounce[size];
        }
    };

    String  getTableName(){
        return "announcement";
    }

    String[] getColumnName() {
        return new String[]{"id", "title", "link", "category", "description", "author", "date", "saved"};
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String[] getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(String[] categoryList) {
        this.categoryList = categoryList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String  getLink() {
        return link;
    }

    public void setLink(String  link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSaved() {
        return saved;
    }

    public void setSaved(int saved) {
        this.saved = saved;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(description);
        dest.writeStringArray(categoryList);
        dest.writeString(date);
        dest.writeInt(saved);
    }
}
