package com.example.android.slidingtabsbasic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.slidingtabsbasic.DBS.TechAnnounce;
import com.example.android.slidingtabsbasic.DBS.TechCategoryList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yuancui on 11/15/15.
 * Updated by yuanli on 11/16/15.
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
        int result = db.delete(DBHelper.Table_Categories, categories_id + "= ?", new String[]{String.valueOf(categories_id)});
        db.close(); // Closing database connection
        return  result;
    }

    public int update(int favorite, int id, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.Column_Categories_Favorites, favorite);
        // TODO: 11/15/2015 UpdateSaved only
        //values.put(dbHelper.Column_Announcements_Saved, categories.getSaved());



        // It's a good practice to use parameter ?, instead of concatenate string
        int result = db.update(DBHelper.Table_Categories, values, DBHelper.Column_Categories_ID + "= ?", new String[]{String.valueOf(id)});
        db.close(); // Closing database connection
        return result;
    }

    public ArrayList<HashMap<String, String>> getCategories(Context c) {
        //Open connection to read only

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_Categories_ID + "," +
                DBHelper.Column_Categories_Name + "," +
                DBHelper.Column_Categories_Favorites + "," +
                DBHelper.Column_Categories_Date_Added +
                " FROM " + DBHelper.Table_Categories +
                " ORDER BY " + DBHelper.Column_Categories_Date_Added+ " ASC";


        Cursor cursor = db.rawQuery(selectQuery, null);

        //Announcements categories = new Announcements();
        ArrayList<HashMap<String, String>> categoriesList = new ArrayList<HashMap<String, String>>();


        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> categories = new HashMap<String, String>();
                categories.put("id", cursor.getString(cursor.getColumnIndex(DBHelper.Column_Categories_ID)));
                categories.put("name", cursor.getString(cursor.getColumnIndex(DBHelper.Column_Categories_Name)));
                categories.put("favorite", cursor.getString(cursor.getColumnIndex(DBHelper.Column_Categories_Favorites)));
                categories.put("description", cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Desc)));
                categories.put("announce_saved", cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Saved)));
                categories.put("date", cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Date)));
                categories.put("a_date_added", cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Date_Added)));
                categoriesList.add(categories);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return categoriesList;

    }


    public ArrayList<TechCategoryList> getCategoryList2(Context c) {

        TechCategoryList category = new TechCategoryList();

        ArrayList<TechCategoryList> techCategoryLists = new ArrayList<>();
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

        //if (cursor.moveToFirst()) {

            while (cursor.moveToNext()){
                category.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Categories_ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Categories_Name)));
                category.setFavorite(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Categories_Favorites)));
                //categories.setDateAdded(cursor.getString(cursor.getColumnIndex(dbHelper.Column_Category_Date_Added)));
                int val = cursor.getPosition() + 1;
                iCount++;
                //if(cursor.getPosition() == iCount + 1){

                    techCategoryLists.add(category);

            }


        cursor.close();
        db.close();
        return techCategoryLists;
    }

    public TechCategoryList getCategoriesById(int id,Context c ) {
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                DBHelper.Column_Categories_ID + "," +
                DBHelper.Column_Categories_Name + "," +
                DBHelper.Column_Categories_Favorites +
                " FROM " + DBHelper.Table_Categories
                + " WHERE " +
                DBHelper.Column_Categories_ID + "=?"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
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

    public TechCategoryList getCategoriesByName(String name,Context c ){
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

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(name)});

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

    public void checkCategoryList (TechAnnounce announcement, Context c){

        String categoryList[] = announcement.getCategoryList();

        for (String categoryName : categoryList){
            if(getCategoriesByName(categoryName, c) != null){
                //// TODO: 11/16/2015 TechAnnouncCategoryDAO
            }
        }

    }
}
