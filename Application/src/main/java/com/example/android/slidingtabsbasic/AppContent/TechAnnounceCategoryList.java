package com.example.android.slidingtabsbasic.AppContent;

public class TechAnnounceCategoryList extends TechAnnounceKeyList {
    /**
     * @var TechCategoryList
     */
    private TechCategoryList techCategoryList;


    /**
     * @var TechCategoryList id
     */
    private int categoryId;

    /**
     * @return TechCategoryList
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param TechCategoryList id
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    String getTableName() {
        return "announcement_keyList";
    }
}
