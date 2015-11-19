package com.example.android.slidingtabsbasic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.slidingtabsbasic.DBS.TechAnnounceKeyList;

import java.util.ArrayList;

/**
 * Created by Bukunmi on 11/16/2015.
 */
public class TechAnnounceKeyDAO {
    private DBHelper dbHelper;


    public int insert(int a_Id, int c_Id, Context c) {
        //Open connection to write data
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.Column_AnnouncementsKeyWords_Announcements_ID, a_Id);
        values.put(DBHelper.Column_AnnouncementsKeyWords_KeyWords_ID, c_Id);

        // Inserting Row
        long announcementCategories_id = db.insert(DBHelper.Table_AnnouncementsKeyWords, null, values);
        db.close(); // Closing database connection
        return (int) announcementCategories_id;
    }

    public int delete(int announcement_id, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        int result = db.delete(DBHelper.Table_AnnouncementsKeyWords, DBHelper.Column_AnnouncementsKeyWords_Announcements_ID + "= ?", new String[]{String.valueOf(announcement_id)});
        db.close(); // Closing database connection
        return result;
    }

    public int update(TechAnnounceKeyList announceKeyList, int id, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.Column_AnnouncementsKeyWords_KeyWords_ID, announceKeyList.getK_id());
        values.put(DBHelper.Column_AnnouncementsKeyWords_Announcements_ID, announceKeyList.getA_id());
        //values.put(dbHelper.Column_Announcements_Saved, annoucementCategories.getSaved());

        // It's a good practice to use parameter ?, instead of concatenate string
        int result = db.update(DBHelper.Table_AnnouncementsKeyWords, values, DBHelper.Column_AnnouncementsCategories_ID + "= ?", new String[]{String.valueOf(id)});
        db.close(); // Closing database connection
        return result;
    }

    public ArrayList<TechAnnounceKeyList> getAnnByKeyId(int cat_id, Context c) {


        ArrayList<TechAnnounceKeyList> techA_KeyLists = new ArrayList<>();

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                DBHelper.Column_AnnouncementsKeyWords_ID + "," +
                DBHelper.Column_AnnouncementsKeyWords_Announcements_ID + "," +
                DBHelper.Column_AnnouncementsKeyWords_KeyWords_ID +
                " FROM " + DBHelper.Table_AnnouncementsKeyWords +
                " WHERE " + DBHelper.Column_AnnouncementsKeyWords_KeyWords_ID + " =?"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(cat_id)});

        //if (cursor.moveToFirst()) {

        while (cursor.moveToNext()) {
            TechAnnounceKeyList techAnnounceKeyList = new TechAnnounceKeyList();

            techAnnounceKeyList.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsCategories_ID)));
            techAnnounceKeyList.setA_id(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsKeyWords_Announcements_ID)));
            techAnnounceKeyList.setK_id(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsKeyWords_KeyWords_ID)));

            int val = cursor.getPosition() + 1;

            //if(cursor.getPosition() == iCount + 1){
            iCount++;
            techA_KeyLists.add(cursor.getPosition(), techAnnounceKeyList);

        }

        cursor.close();
        db.close();
        return techA_KeyLists;
    }

}

