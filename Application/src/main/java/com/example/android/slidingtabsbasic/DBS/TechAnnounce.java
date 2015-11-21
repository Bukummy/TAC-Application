package com.example.android.slidingtabsbasic.DBS;

/**
 * Created by Bukunmi on 10/16/2015.
 */

public class TechAnnounce {

    private int id;
    private String title;
    private String link;
    private String description;
    private int saved;
    private String[] KeyList;
    private String[] categoryList;
    private long dateAdded;

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String[] getKeyList() {
        return KeyList;
    }

    public void setKeyList(String[] keyList) {
        KeyList = keyList;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSaved() {
        return saved;
    }

    public void setSaved(int saved) {
        this.saved = saved;
    }

}
