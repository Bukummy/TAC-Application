package com.example.android.slidingtabsbasic.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.slidingtabsbasic.AppContent.TechAnnounce;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yuancui on 11/15/15.
 * Updated by yuanli on 11/16/15.
 */
public class AnnouncementDBFunc {
    private DBHelper dbHelper;


    public AnnouncementDBFunc(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(TechAnnounce announcements) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbHelper.Column_Announcements_Title, announcements.getTitle());
        values.put(dbHelper.Column_Announcements_Link, announcements.getLink());
        values.put(dbHelper.Column_Announcements_Desc, announcements.getDescription());

        // Inserting Row
        long announcements_id = db.insert(dbHelper.Table_Announcements, null, values);
        db.close(); // Closing database connection
        return (int) announcements_id;
    }

    public void delete(int announcements_id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(dbHelper.Table_Announcements, announcements_id + "= ?", new String[] { String.valueOf(announcements_id) });
        db.close(); // Closing database connection
    }
    public void update(TechAnnounce announcements) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbHelper.Column_Announcements_Title, announcements.getTitle());
        values.put(dbHelper.Column_Announcements_Link, announcements.getLink());
        values.put(dbHelper.Column_Announcements_Desc, announcements.getDescription());
        values.put(dbHelper.Column_Announcements_Date, announcements.getDate());
        // TODO: 11/15/2015 UpdateSaved only
        //values.put(dbHelper.Column_Announcements_Saved, announcements.getSaved());



        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(dbHelper.Table_Announcements, values, dbHelper.Column_Announcements_ID + "= ?", new String[]{String.valueOf(announcements.getId())});
        db.close(); // Closing database connection
    }
    public ArrayList<HashMap<String, String>>  getAnnouncementsList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                dbHelper.Column_Announcements_ID + "," +
                dbHelper.Column_Announcements_Title + "," +
                dbHelper.Column_Announcements_Link + "," +
                dbHelper.Column_Announcements_Desc + "," +
                dbHelper.Column_Announcements_Saved + "," +
                dbHelper.Column_Announcements_Date_Added + "," +
                " FROM " + dbHelper.Table_Announcements;

        //Announcements announcements = new Announcements();
        ArrayList<HashMap<String, String>> announcementsList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> announcements = new HashMap<String, String>();
                announcements.put("id", cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_ID)));
                announcements.put("announce_title", cursor.getString(cursor.getColumnIndex( dbHelper.Column_Announcements_Title)));
                announcements.put("announce_link", cursor.getString(cursor.getColumnIndex( dbHelper.Column_Announcements_Link)));
                announcements.put("description", cursor.getString(cursor.getColumnIndex( dbHelper.Column_Announcements_Desc)));
                announcements.put("announce_saved", cursor.getString(cursor.getColumnIndex( dbHelper.Column_Announcements_Saved)));
                announcements.put("date", cursor.getString(cursor.getColumnIndex( dbHelper.Column_Announcements_Date)));
                announcements.put("a_date_added", cursor.getString(cursor.getColumnIndex( dbHelper.Column_Announcements_Date_Added)));
                announcementsList.add(announcements);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return announcementsList;

    }

    public TechAnnounce getAnnouncementsById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                dbHelper.Column_Announcements_ID + "," +
                dbHelper.Column_Announcements_Title + "," +
                dbHelper.Column_Announcements_Link + "," +
                dbHelper.Column_Announcements_Desc + "," +
                dbHelper.Column_Announcements_Saved + "," +
                dbHelper.Column_Announcements_Date_Added + "," +
                " FROM " + dbHelper.Table_Announcements
                + " WHERE " +
                dbHelper.Column_Announcements_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        TechAnnounce announcements = new TechAnnounce();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id) } );

        if (cursor.moveToFirst()) {
            do {
                announcements.setId(cursor.getInt(cursor.getColumnIndex(dbHelper.Column_Announcements_ID)));
                announcements.setTitle(cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_Title)));
                announcements.setLink(cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_Link)));
                announcements.setSaved(cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_Desc)));
                announcements.setDate(cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_Saved)));
                //announcements.a_date_added =cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_Date_Added));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return announcements;
    }

}
