package com.example.android.slidingtabsbasic.AppContent;

/**
 * Created by Bukunmi on 10/16/2015.
 */
public class TechAnnounceKeyList {


    /**
     * @var TechAnnounce
     */
    private TechAnnounce techAnnounce;

    /**
     * @var TechCategoryList
     */
    private TechCategoryList techCategoryList;

    private int id;
    private int announcementId;
    private int keyId;

    public TechAnnounceKeyList(TechAnnounce techAnnounce, TechCategoryList techCategoryList, int id) {
        this.techAnnounce = techAnnounce;
        this.techCategoryList = techCategoryList;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return TechAnnounce
     */
    public int getAnnouncementId() {
        return announcementId;
    }

    /**
     * @param TechAnnounce id
     */
    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }

    /**
     * @return TechKeyList
     */
    public int getKeyId() {
        return keyId;
    }

    /**
     * @param TechKeyList id
     */
    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    private String favorite;

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    String  getTableName(){

        return "announcement_category";
    }

    public TechAnnounce getTechAnnounce() {

        return techAnnounce;
    }

    public void setTechAnnounce(TechAnnounce techAnnounce) {

        this.techAnnounce = techAnnounce;
        if(techAnnounce){
            this.announcementId = techAnnounce.getId();
        }this.techAnnounce = techAnnounce;
    }

    public TechCategoryList getTechCategoryList() {
        return techCategoryList;
    }

    public void setTechCategoryList(TechCategoryList techCategoryList) {
        this.techCategoryList = techCategoryList;
    }
}

