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


    private int insert(String keyName, Context c) {
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

    private TechKeyList getKeysByName(String name, Context c){
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_KeyWords_ID + "," +
                DBHelper.Column_KeyWords_Name +
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

    public void checkKeyList (ArrayList<String> keyList, int announcement_id, Context c){

        for (String keyName :keyList){

            TechKeyList techKeyList = getKeysByName(keyName, c);
            int key_list_id = techKeyList.getId();

            if(key_list_id >= 1){
                loadAnnouncementKeyList(announcement_id, key_list_id, c);
            }
            else if(key_list_id == 0){
                int key_id = insert(keyName,c);
                loadAnnouncementKeyList(announcement_id, key_id, c);
            }
            else {
                Log.i("Row Doesn't exist at", String.valueOf(key_list_id));
            }
        }

    }

    private void loadAnnouncementKeyList(int ann_id, int key_list_id, Context c){
        TechAnnounceKeyDAO techAnnounceKeyDAO= new TechAnnounceKeyDAO();

        int annKeyId = techAnnounceKeyDAO.getAnnKeyId(ann_id,key_list_id,c);
        if (annKeyId != 0){
            Log.i("AK Row NO: ", String.valueOf(annKeyId));
        }
        else {

            int insertedRow = techAnnounceKeyDAO.insert(ann_id, key_list_id, c);
            String value = String.valueOf(insertedRow);
            //log progress
            if (insertedRow >= 1) {
                Log.i("Inserted AK Row NO:", value);
            } else {
                Log.i("No AK Row available:", value);
            }
        }

    }
}
