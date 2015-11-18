package com.example.android.slidingtabsbasic.DBS;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bukunmi on 10/16/2015.
 */
public class TechAnnounceKeyList implements Parcelable {


    /**
     * @var TechAnnounce
     */
    private TechAnnounce techAnnounce;

    /**
     * @var TechKeyist
     */
    private TechKeyList techKeyList;

    private int id;
    private int a_id;
    private int k_id;


    protected TechAnnounceKeyList(Parcel in) {
        techAnnounce = in.readParcelable(TechAnnounce.class.getClassLoader());
        id = in.readInt();
        a_id = in.readInt();
        k_id = in.readInt();
    }

    public static final Creator<TechAnnounceKeyList> CREATOR = new Creator<TechAnnounceKeyList>() {
        @Override
        public TechAnnounceKeyList createFromParcel(Parcel in) {
            return new TechAnnounceKeyList(in);
        }

        @Override
        public TechAnnounceKeyList[] newArray(int size) {
            return new TechAnnounceKeyList[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return TechAnnounce
     */
    public int getA_id() {
        return a_id;
    }

    /**
     * @param /TechAnnounce id
     */
    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    /**
     * @return TechKeyList
     */
    public int getK_id() {
        return k_id;
    }

    /**
     * @param /TechKeyList id
     */
    public void setK_id(int k_id) {
        this.k_id = k_id;
    }

    String  getTableName(){

        return "announcement_keywords";
    }

    String[] getColumnNames(){

        return new String[]{"id", "a_id", "k_id"};
    }

    public TechAnnounce getTechAnnounce() {

        return techAnnounce;
    }

    public void setTechAnnounce(TechAnnounce techAnnounce) {

        this.techAnnounce = techAnnounce;
        /*if(techAnnounce){
            this.a_id = techAnnounce.getId();
        }this.techAnnounce = techAnnounce;*/
    }

    public TechKeyList getTechKeyList() {
        return techKeyList;
    }

    public void setTechKeyList(TechKeyList techKeyList) {
        this.techKeyList = techKeyList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(techAnnounce, flags);
        dest.writeInt(id);
        dest.writeInt(a_id);
        dest.writeInt(k_id);
    }
}

