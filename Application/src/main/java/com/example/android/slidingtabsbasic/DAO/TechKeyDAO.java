package com.example.android.slidingtabsbasic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.slidingtabsbasic.DBS.DBHelper;
import com.example.android.slidingtabsbasic.DBS.TechAnnounce;
import com.example.android.slidingtabsbasic.DBS.TechKeyList;

import java.util.ArrayList;

/**
 * Created by Bukunmi on 11/18/2015.
 */
public class TechKeyDAO {
    private DBHelper dbHelper;


    public int insert(String keywordName, Context c) {
        //Open connection to write data
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.Column_KeyWords_Name, keywordName);

        // Inserting Row
        long keyList_id = db.insert(DBHelper.Table_KeyWords, null, values);
//        db.close(); // Closing database connection
        return (int) keyList_id;
    }

    public int delete(int keyList_id, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        int result = db.delete(DBHelper.Table_KeyWords, DBHelper.Column_KeyWords_ID + "= ?", new String[]{String.valueOf(keyList_id)});
        db.close(); // Closing database connection
        return  result;
    }

    public ArrayList<TechKeyList> getKeyList(Context c) {


        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_KeyWords_ID + "," +
                DBHelper.Column_KeyWords_Name + "" +
                " FROM " + DBHelper.Table_KeyWords +
                " ORDER BY " + DBHelper.Column_KeyWords_Date_Added + ", " +
                DBHelper.Column_KeyWords_ID + " ASC"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<TechKeyList> techKeyLists = new ArrayList<>();

        //if (cursor.moveToFirst()) {
        while (cursor.moveToNext()){

            TechKeyList keyword = new TechKeyList();

            keyword.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_KeyWords_ID)));
            keyword.setName(cursor.getString(cursor.getColumnIndex(DBHelper.Column_KeyWords_Name)));
            int val = cursor.getPosition() + 1;

            //if(cursor.getPosition() == iCount + 1){
            iCount++;
            techKeyLists.add(keyword);

        }



        cursor.close();
        //db.close();
        return techKeyLists;
    }

/*    public ArrayList<TechKeyList> getKeysByFav(int favorite,Context c ) {
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                DBHelper.Column_KeyWords_ID + "," +
                DBHelper.Column_KeyWords_Name + "," +
                DBHelper.Column_KeyWords_Favorites +
                " FROM " + DBHelper.Table_KeyWords
                + " WHERE " +
                DBHelper.Column_KeyWords_Favorites + "=?"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(favorite)});
        ArrayList<TechKeyList> techKeyLists = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                TechKeyList keyList = new TechKeyList();
                keyList.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_KeyWords_ID)));
                keyList.setName(cursor.getString(cursor.getColumnIndex(DBHelper.Column_KeyWords_Name)));
                keyList.setFavorite(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_KeyWords_Favorites)));

                techKeyLists.add(keyList);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return techKeyLists;
    }*/

    public TechKeyList getKeysByName(String name,Context c ){
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_KeyWords_ID + "," +
                DBHelper.Column_KeyWords_Name +
                " FROM " + DBHelper.Table_KeyWords
                + " WHERE " +
                DBHelper.Column_KeyWords_Name + "= ?"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        TechKeyList keyList = new TechKeyList();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(name)});

        if (cursor.moveToFirst()) {
            do {
                keyList.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_KeyWords_ID)));
                keyList.setName(cursor.getString(cursor.getColumnIndex(DBHelper.Column_KeyWords_Name)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return keyList;
    }

    public TechKeyList getKeysByID(int id,Context c ){
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_KeyWords_ID + "," +
                DBHelper.Column_KeyWords_Name +
                " FROM " + DBHelper.Table_KeyWords
                + " WHERE " +
                DBHelper.Column_KeyWords_ID + "= ?"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        TechKeyList keyList = new TechKeyList();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                keyList.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_KeyWords_ID)));
                keyList.setName(cursor.getString(cursor.getColumnIndex(DBHelper.Column_KeyWords_Name)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return keyList;
    }

    public void checkKeyList (TechAnnounce announcement, int announcement_id, Context c){

        String keywordList[] = announcement.getKeyList();

        for (String keywordName : keywordList){

            TechKeyList techKeyList = getKeysByName(keywordName, c);
            int cat_id = techKeyList.getId();

            if(cat_id > 1){
                loadAnnouncementKeyList(announcement_id, cat_id, c);

            }
        }

    }

    public void loadAnnouncementKeyList (int ann_id, int cat_id, Context c){
        TechAnnounceKeyDAO techAnnounceKeyDAO= new TechAnnounceKeyDAO();

        int insertedRow = techAnnounceKeyDAO.insert(ann_id, cat_id, c);
        String value = String.valueOf(insertedRow);
        //log progress
        if (insertedRow >= 1) {
            Log.i("Inserted AC Row NO:", value);
        } else {
            Log.i("No Inserted AC Row:", value);
        }

    }
}
