package com.team2.bukunmioyedeji.tac;

/**
 * Created by Bukunmi on 10/16/2015.
 */

public class TechAnnounce {


    private int id;
    private String title;
    private String link;
    private String description;
    private String[] categoryList;

    private String author;
    private String date;
    private String saved;

    String  getTableName(){
        return "announcement";
    }

    String[] getColumnName() {
        return new String[]{"id", "title", "link", "category", "description", "author", "date", "saved"};
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSaved() {
        return saved;
    }

    public void setSaved(String saved) {
        this.saved = saved;
    }



}
