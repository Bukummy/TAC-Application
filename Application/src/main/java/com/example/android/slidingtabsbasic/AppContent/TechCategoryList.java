package com.example.android.slidingtabsbasic.AppContent;

/**
 * Created by Bukunmi on 10/16/2015.
 */
public class TechCategoryList {
// TOdo this class structure

    private int id;
    private String name;
    private int favorite;

    String  getTableName(){
        return "category";
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
}

