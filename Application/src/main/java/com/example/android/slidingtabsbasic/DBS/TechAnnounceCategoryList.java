package com.example.android.slidingtabsbasic.DBS;

import android.os.Parcel;
import android.os.Parcelable;

public class TechAnnounceCategoryList implements Parcelable {
    /**
     * @var TechCategoryList
     */
    private TechCategoryList techCategoryList;

    /**
     * @var TechAnnounce
     */
    private TechAnnounce techAnnounce;


    /**
     * @var TechCategoryList id
     */
    private int Id;
    private int a_Id;
    private int c_Id;

    protected TechAnnounceCategoryList(Parcel in) {
        techAnnounce = in.readParcelable(TechAnnounce.class.getClassLoader());
        Id = in.readInt();
        a_Id = in.readInt();
        c_Id = in.readInt();
    }

    public static final Creator<TechAnnounceCategoryList> CREATOR = new Creator<TechAnnounceCategoryList>() {
        @Override
        public TechAnnounceCategoryList createFromParcel(Parcel in) {
            return new TechAnnounceCategoryList(in);
        }

        @Override
        public TechAnnounceCategoryList[] newArray(int size) {
            return new TechAnnounceCategoryList[size];
        }
    };

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getA_Id() {
        return a_Id;
    }

    public void setA_Id(int a_Id) {
        this.a_Id = a_Id;
    }

    /**
     * @return TechCategoryList
     */
    public int getC_Id() {
        return c_Id;
    }

    /**
     *
     */
    public void setC_Id(int c_Id) {
        this.c_Id = c_Id;
    }

    String getTableName() {
        return "announce_categories";
    }

    String[] getColumnNames() {
        return new String[]{"id", "a_id", "c_id"};
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(techAnnounce, flags);
        dest.writeInt(Id);
        dest.writeInt(a_Id);
        dest.writeInt(c_Id);
    }
}
