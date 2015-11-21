package com.example.android.slidingtabsbasic.DBS;

/**
 * Created by Bukunmi on 10/16/2015.
 */
public class TechAnnounceKeyList {


    private int id;
    private int a_Id;
    private int k_Id;

    public TechAnnounceKeyList() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return a_id  as as TechAnnounceID
     */
    public int getA_id() {
        return a_Id;
    }

    /**
     * @param a_Id as TechAnnounceID
     */
    public void setA_id(int a_Id) {
        this.a_Id = a_Id;
    }

    /**
     * @return k_id as TechKeyID
     */
    public int getK_Id() {
        return k_Id;
    }

    /**
     * @param k_Id as TechKeyListID
     */
    public void setK_Id(int k_Id) {
        this.k_Id = k_Id;
    }

}

