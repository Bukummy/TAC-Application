package com.example.android.slidingtabsbasic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.slidingtabsbasic.DBS.TechAnnounce;
import com.example.android.slidingtabsbasic.DBS.TechCategoryList;

import java.util.ArrayList;

/**
 * Updated by Bukunmi on 11/16/15.
 */
public class TechCategoryDAO {
    private DBHelper dbHelper;


    public int insert(String categoryName, Context c) {
        //Open connection to write data
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.Column_Categories_Name, categoryName);

        // Inserting Row
        long categories_id = db.insert(DBHelper.Table_Categories, null, values);
//        db.close(); // Closing database connection
        return (int) categories_id;
    }

    public int delete(int categories_id, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        int result = db.delete(DBHelper.Table_Categories, DBHelper.Column_Categories_ID + "= ?", new String[]{String.valueOf(categories_id)});
        db.close(); // Closing database connection
        return  result;
    }

    //selecting favorites
    public int updateFavTag(int favorite, int id, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //String selector = "id";
        String[] selectorArg = {String.valueOf(id)};
        values.put(DBHelper.Column_Categories_Favorites, favorite);
        // TODO: 11/15/2015 UpdateSaved only

        // It's a good practice to use parameter ?, instead of concatenate string
        int result = db.update(DBHelper.Table_Categories,
                values,
                DBHelper.Column_Categories_ID + "=?",
                selectorArg);
        db.close(); // Closing database connection
        return result;
    }

    //un-selecting favorites
    public int updateFavTag(int favorite, String name, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //String selector = "id";
        String[] selectorArg = {String.valueOf(name)};
        values.put(DBHelper.Column_Categories_Favorites, favorite);
        // TODO: 11/15/2015 UpdateSaved only

        // It's a good practice to use parameter ?, instead of concatenate string
        int result = db.update(DBHelper.Table_Categories,
                values,
                DBHelper.Column_Categories_Name +"=?",
                selectorArg);
        db.close(); // Closing database connection
        return result;
    }

    public ArrayList<TechCategoryList> getCategoryList(Context c) {


        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_Categories_ID + "," +
                DBHelper.Column_Categories_Name + "," +
                DBHelper.Column_Categories_Favorites +
                " FROM " + DBHelper.Table_Categories +
                " ORDER BY " + DBHelper.Column_Categories_Date_Added + ", " +
                dbHelper.Column_Categories_ID + " ASC"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<TechCategoryList> techCategoryLists = new ArrayList<>();

        //if (cursor.moveToFirst()) {
        while (cursor.moveToNext()){

            TechCategoryList category = new TechCategoryList();

            category.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Categories_ID)));
            category.setName(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Categories_Name)));
            category.setFavorite(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Categories_Favorites)));
            //categories.setDateAdded(cursor.getString(cursor.getColumnIndex(dbHelper.Column_Category_Date_Added)));
            int val = cursor.getPosition() + 1;

            //if(cursor.getPosition() == iCount + 1){
            iCount++;
            techCategoryLists.add(category);

        }



        cursor.close();
        db.close();
        return techCategoryLists;
    }

    public ArrayList<TechCategoryList> getCategoriesByFav(int favorite,Context c ) {
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                DBHelper.Column_Categories_ID + "," +
                DBHelper.Column_Categories_Name + "," +
                DBHelper.Column_Categories_Favorites +
                " FROM " + DBHelper.Table_Categories
                + " WHERE " +
                DBHelper.Column_Categories_Favorites + "=?"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(favorite)});
        ArrayList<TechCategoryList> techCategoryLists = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                TechCategoryList categories = new TechCategoryList();
                categories.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Categories_ID)));
                categories.setName(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Categories_Name)));
                categories.setFavorite(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Categories_Favorites)));

                techCategoryLists.add(categories);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return techCategoryLists;
    }

    public TechCategoryList   getCategoriesByName(String name,Context c ){
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_Categories_ID + "," +
                DBHelper.Column_Categories_Name + "," +
                DBHelper.Column_Categories_Favorites +
                " FROM " + DBHelper.Table_Categories
                + " WHERE " +
                DBHelper.Column_Categories_Name + "= ?"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        TechCategoryList categories = new TechCategoryList();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{name});

        if (cursor.moveToFirst()) {
            do {
                categories.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Categories_ID)));
                categories.setName(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Categories_Name)));
                categories.setFavorite(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Categories_Favorites)));
                //categories.a_date_added =cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_Date_Added));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return categories;
    }

    public TechCategoryList getCategoriesByID(int id,Context c ){
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_Categories_ID + "," +
                DBHelper.Column_Categories_Name + "," +
                DBHelper.Column_Categories_Favorites +
                " FROM " + DBHelper.Table_Categories
                + " WHERE " +
                DBHelper.Column_Categories_ID + "= ?"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        TechCategoryList categories = new TechCategoryList();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                categories.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Categories_ID)));
                categories.setName(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Categories_Name)));
                categories.setFavorite(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Categories_Favorites)));
                //categories.a_date_added =cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_Date_Added));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return categories;
    }

    public void checkCategoryList (TechAnnounce announcement, int announcement_id, Context c){

        String categoryList[] = announcement.getCategoryList();

        for (String categoryName : categoryList){


            TechCategoryList techCategoryList = getCategoriesByName(categoryName, c);
            int cat_id = techCategoryList.getId();

            if(cat_id > 1){
                loadAnnouncementCategoryList(announcement_id, cat_id, c);

            }
        }

    }

    public void loadAnnouncementCategoryList (int ann_id, int cat_id, Context c){
        TechAnnounceCategoryDAO techAnnounceCategoryDAO= new TechAnnounceCategoryDAO();

        int insertedRow = techAnnounceCategoryDAO.insert(ann_id, cat_id, c);
        String value = String.valueOf(insertedRow);
        //log progress
        if (insertedRow >= 1) {
            Log.i("Inserted AC Row NO:", value);
        } else {
            Log.i("No Inserted AC Row:", value);
        }

    }
}
