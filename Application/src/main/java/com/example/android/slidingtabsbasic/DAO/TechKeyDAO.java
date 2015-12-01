package com.example.android.slidingtabsbasic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.slidingtabsbasic.DBS.DBHelper;
import com.example.android.slidingtabsbasic.DBS.TechKeyList;

import java.util.ArrayList;

/**
 * Created by Bukunmi on 11/18/2015.
 */
public class TechKeyDAO {
    private DBHelper dbHelper;


    public int insert(String keyName, Context c) {
        //Open connection to write data
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.Column_KeyWords_Name, keyName);

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

    //selecting favorites
    public int updateFavTag(int favorite, int id, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //String selector = "id";
        String[] selectorArg = {String.valueOf(id)};
        values.put(DBHelper.Column_KeyWords_Favorites, favorite);
        // TODO: 11/15/2015 UpdateSaved only

        // It's a good practice to use parameter ?, instead of concatenate string
        db.close(); // Closing database connection
        return db.update(DBHelper.Table_KeyWords,
                values,
                DBHelper.Column_KeyWords_ID + "=?",
                selectorArg);
    }

    //un-selecting favorites
    public int updateFavTag(int favorite, String name, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //String selector = "id";
        String[] selectorArg = {String.valueOf(name)};
        values.put(DBHelper.Column_KeyWords_Favorites, favorite);
        // TODO: 11/15/2015 UpdateSaved only

        // It's a good practice to use parameter ?, instead of concatenate string
        db.close(); // Closing database connection
        return db.update(DBHelper.Table_KeyWords,
                values,
                DBHelper.Column_KeyWords_Name +"=?",
                selectorArg);
    }


    public ArrayList<TechKeyList> getKeyList(Context c) {


        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_KeyWords_ID + "," +
                DBHelper.Column_KeyWords_Name + "," +
                DBHelper.Column_KeyWords_Favorites +
                " FROM " + DBHelper.Table_KeyWords +
                " ORDER BY " + DBHelper.Column_KeyWords_Date_Added + ", " +
                DBHelper.Column_KeyWords_ID + " ASC"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<TechKeyList> techKeyLists = new ArrayList<>();

        //if (cursor.moveToFirst()) {
        while (cursor.moveToNext()){

            TechKeyList keyList = new TechKeyList();

            keyList.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_KeyWords_ID)));
            keyList.setName(cursor.getString(cursor.getColumnIndex(DBHelper.Column_KeyWords_Name)));
            keyList.setFavorite(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_KeyWords_Favorites)));
            //keyList.setDateAdded(cursor.getString(cursor.getColumnIndex(dbHelper.Column_Key_Date_Added)));
            int val = cursor.getPosition() + 1;

            //if(cursor.getPosition() == iCount + 1){
            iCount++;
            techKeyLists.add(keyList);

        }

        cursor.close();
        db.close();
        return techKeyLists;
    }

    public TechKeyList getKeysByName(String name,Context c ){
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_KeyWords_ID + "," +
                DBHelper.Column_KeyWords_Name + "," +
                DBHelper.Column_KeyWords_Favorites +
                " FROM " + DBHelper.Table_KeyWords
                + " WHERE " +
                DBHelper.Column_KeyWords_Name + "= '"+name+"'"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        TechKeyList keyList = new TechKeyList();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                keyList.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_KeyWords_ID)));
                keyList.setName(cursor.getString(cursor.getColumnIndex(DBHelper.Column_KeyWords_Name)));
                keyList.setFavorite(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_KeyWords_Favorites)));
                //keyList.a_date_added =cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_Date_Added));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return keyList;
    }

    public TechKeyList getKeyByID(int id, Context c){
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_KeyWords_ID + "," +
                DBHelper.Column_KeyWords_Name + "," +
                DBHelper.Column_KeyWords_Favorites +
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
                keyList.setFavorite(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_KeyWords_Favorites)));
                //keyList.a_date_added =cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_Date_Added));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return keyList;
    }

    public void checkKeyList (ArrayList<String> keyList, int announcement_id, Context c){

       ArrayList keys = keyList;

        for (String keyName :keyList){

            TechKeyList techKeyList = getKeysByName(keyName, c);
            int key_list_id = techKeyList.getId();

            if(key_list_id > 1){
                loadAnnouncementKeyList(announcement_id, key_list_id, c);
            }
            if(key_list_id == 0){
                int key_id = insert(keyName,c);
                loadAnnouncementKeyList(announcement_id, key_id, c);
            }
        }

    }

    public void loadAnnouncementKeyList (int ann_id, int key_list_id, Context c){
        TechAnnounceKeyDAO techAnnounceKeyDAO= new TechAnnounceKeyDAO();

        int insertedRow = techAnnounceKeyDAO.insert(ann_id, key_list_id, c);
        String value = String.valueOf(insertedRow);
        //log progress
        if (insertedRow >= 1) {
            Log.i("Inserted AK Row NO:", value);
        } else {
            Log.i("No Inserted AK Row:", value);
        }

    }
}
