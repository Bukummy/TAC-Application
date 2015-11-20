package com.example.android.slidingtabsbasic.DBS;

public class TechAnnounceCategoryList {

    private int Id;
    private int a_Id;
    private int c_Id;

    public TechAnnounceCategoryList() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    /**
     * @return a_Id as TechAnnounceID
     */
    public int getA_Id() {
        return a_Id;
    }

    /**
     * @param a_Id as TechAnnounceID
     */
    public void setA_Id(int a_Id) {
        this.a_Id = a_Id;
    }

    /**
     * @return c_Id as TechCategoryListID
     */
    public int getC_Id() {
        return c_Id;
    }

    /**
     *@param c_Id as TechCategoryID
     */
    public void setC_Id(int c_Id) {
        this.c_Id = c_Id;
    }

}
