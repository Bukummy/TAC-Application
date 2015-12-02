package com.example.android.slidingtabsbasic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.slidingtabsbasic.DBS.DBHelper;
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

    public ArrayList<TechAnnounceKeyList> getAllKeyByAnnId(int announcement_id, Context c) {


        ArrayList<TechAnnounceKeyList> techA_KeyLists = new ArrayList<>();

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                DBHelper.Column_AnnouncementsKeyWords_ID + "," +
                DBHelper.Column_AnnouncementsKeyWords_Announcements_ID + "," +
                DBHelper.Column_AnnouncementsKeyWords_KeyWords_ID +
                " FROM " + DBHelper.Table_AnnouncementsKeyWords +
                " WHERE " + DBHelper.Column_AnnouncementsKeyWords_Announcements_ID + " = " + announcement_id; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        Cursor cursor = db.rawQuery(selectQuery,null);

        //if (cursor.moveToFirst()) {

        while (cursor.moveToNext()) {
            TechAnnounceKeyList techAnnounceKeyList = new TechAnnounceKeyList();

            techAnnounceKeyList.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsKeyWords_ID)));
            techAnnounceKeyList.setA_id(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsKeyWords_Announcements_ID)));
            techAnnounceKeyList.setK_Id(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsKeyWords_KeyWords_ID)));

           techA_KeyLists.add(techAnnounceKeyList);

        }

        cursor.close();
        db.close();
        return techA_KeyLists;
    }

    public int getAnnKeyId(int ann_id, int keyList_id, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                DBHelper.Column_AnnouncementsKeyWords_ID + "," +
                DBHelper.Column_AnnouncementsKeyWords_Announcements_ID + "," +
                DBHelper.Column_AnnouncementsKeyWords_KeyWords_ID +
                " FROM " + DBHelper.Table_AnnouncementsKeyWords +
                " WHERE " + DBHelper.Column_AnnouncementsKeyWords_KeyWords_ID + " = " + keyList_id +
                " AND " + DBHelper.Column_AnnouncementsKeyWords_Announcements_ID + " = " + ann_id ; // It's a good practice to use parameter ?, instead of concatenate string

        int ann_keyList_id = 0;
        Cursor cursor = db.rawQuery(selectQuery, null);

        //if (cursor.moveToFirst()) {

        while (cursor.moveToNext()) {
            TechAnnounceKeyList techAnnounceKeyList = new TechAnnounceKeyList();

            ann_keyList_id = cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsKeyWords_ID));

            techAnnounceKeyList.setA_id(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsKeyWords_Announcements_ID)));
            techAnnounceKeyList.setK_Id(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsKeyWords_KeyWords_ID)));

            //if(cursor.getPosition() == iCount + 1){
        }

        cursor.close();
        db.close();
        return ann_keyList_id;
    }
}

