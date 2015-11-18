package com.example.android.slidingtabsbasic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.scheduler.DBS.TechAnnounce;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yuancui on 11/15/15.
 * Updated by yuanli on 11/16/15.
 * Updated by Bukunmi on 11/16/15.
 */
public class TechAnnounceDAO  {
    private DBHelper dbHelper;

    public int insert(TechAnnounce announcements, Context c) {


        //Open connection to write data
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.Column_Announcements_Title, announcements.getTitle());
        values.put(DBHelper.Column_Announcements_Link, announcements.getLink());
        values.put(DBHelper.Column_Announcements_Desc, announcements.getDescription());

        // Inserting Row
        long announcements_id = db.insert(DBHelper.Table_Announcements, null, values);
//        db.close(); // Closing database connection
        return (int) announcements_id;
    }

    public int delete(int announcements_id, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        int result = db.delete(DBHelper.Table_Announcements, announcements_id + "= ?", new String[] { String.valueOf(announcements_id) });
        //db.close(); // Closing database connection
        return result;
    }

    public int update(TechAnnounce announcements, int id, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.Column_Announcements_Title, announcements.getTitle());
        values.put(DBHelper.Column_Announcements_Link, announcements.getLink());
        values.put(DBHelper.Column_Announcements_Desc, announcements.getDescription());
        // TODO: 11/15/2015 UpdateSaved only

        // It's a good practice to use parameter ?, instead of concatenate string
        long announcement_id = db.update(DBHelper.Table_Announcements, values, DBHelper.Column_Announcements_ID + "= ?", new String[]{String.valueOf(id)});
        //db.close(); // Closing database connection
        return  (int) announcement_id;
    }

    public int updateSavedCol(TechAnnounce announcements, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.Column_Announcements_Saved, announcements.getSaved());
        // TODO: 11/15/2015 UpdateSaved only

        // It's a good practice to use parameter ?, instead of concatenate string
        long announcement_id = db.update(DBHelper.Table_Announcements, values, DBHelper.Column_Announcements_ID + "= ?", new String[]{String.valueOf(announcements.getId())});
        //db.close(); // Closing database connection
        return  (int) announcement_id;
    }

    public ArrayList<HashMap<String, String>> getAnnouncements(Context c) {
        //Open connection to read only

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_Announcements_ID + "," +
                DBHelper.Column_Announcements_Title + "," +
                DBHelper.Column_Announcements_Link + "," +
                DBHelper.Column_Announcements_Desc + "," +
                DBHelper.Column_Announcements_Saved + "," +
                DBHelper.Column_Announcements_Date_Added + "," +
                " FROM " + DBHelper.Table_Announcements +
                " ORDER BY " + DBHelper.Column_Announcements_Date_Added;

        //Announcements announcements = new Announcements();
        ArrayList<HashMap<String, String>> announcementsList = new ArrayList<HashMap<String, String>>();


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> announcements = new HashMap<String, String>();
                announcements.put("id", cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_ID)));
                announcements.put("announce_title", cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Title)));
                announcements.put("announce_link", cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Link)));
                announcements.put("description", cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Desc)));
                announcements.put("announce_saved", cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Saved)));
                announcements.put("date", cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Date)));
                announcements.put("a_date_added", cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Date_Added)));
                announcementsList.add(announcements);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return announcementsList;

    }

    public TechAnnounce getAnnouncementsById(int id,Context c ){
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_Announcements_ID + "," +
                DBHelper.Column_Announcements_Title + "," +
                DBHelper.Column_Announcements_Link + "," +
                DBHelper.Column_Announcements_Desc + "," +
                DBHelper.Column_Announcements_Saved + "," +
                DBHelper.Column_Announcements_Date_Added + "," +
                " FROM " + DBHelper.Table_Announcements
                + " WHERE " +
                DBHelper.Column_Announcements_ID + "=?"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        TechAnnounce announcements = new TechAnnounce();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id) } );

        if (cursor.moveToFirst()) {
            do {
                announcements.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Announcements_ID)));
                announcements.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Title)));
                announcements.setLink(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Link)));
                announcements.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Desc)));
                announcements.setSaved(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Announcements_Saved)));
                //announcements.a_date_added =cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_Date_Added));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return announcements;
    }

    public TechAnnounce getAnnouncementsB4Date(Timestamp weekOld, Context c){
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_Announcements_ID + "," +
                DBHelper.Column_Announcements_Title + "," +
                DBHelper.Column_Announcements_Link + "," +
                DBHelper.Column_Announcements_Desc + "," +
                DBHelper.Column_Announcements_Saved + "," +
                DBHelper.Column_Announcements_Date_Added +
                " FROM " + DBHelper.Table_Announcements
                + " WHERE " +
                DBHelper.Column_Announcements_Date_Added + "< ?"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        TechAnnounce announcements = new TechAnnounce();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(weekOld) } );

        if (cursor.moveToFirst()) {
            do {
                announcements.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Announcements_ID)));
                announcements.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Title)));
                announcements.setLink(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Link)));
                announcements.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Desc)));
                announcements.setSaved(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Announcements_Saved)));
                announcements.setDate(cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_Date_Added)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return announcements;
    }

    public TechAnnounce getAnnouncementsByLink(String link,Context c ){
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_Announcements_ID + "," +
                DBHelper.Column_Announcements_Title + "," +
                DBHelper.Column_Announcements_Link + "," +
                DBHelper.Column_Announcements_Desc + "," +
                DBHelper.Column_Announcements_Saved + "," +
                DBHelper.Column_Announcements_Date_Added +
                " FROM " + DBHelper.Table_Announcements
                + " WHERE " +
                DBHelper.Column_Announcements_Link + "= ?"; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        TechAnnounce announcements = new TechAnnounce();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { link } );

        if (cursor.moveToFirst()) {
            do {
                announcements.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Announcements_ID)));
                announcements.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Title)));
                announcements.setLink(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Link)));
                announcements.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Desc)));
                announcements.setSaved(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Announcements_Saved)));
                //announcements.a_date_added =cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_Date_Added));
            } while (cursor.moveToNext());
        }

        cursor.close();
//        db.close();
        return announcements;
    }

    public void checkCategoryList (TechAnnounce announcements){


    }

}
