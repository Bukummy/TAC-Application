package com.example.android.slidingtabsbasic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.slidingtabsbasic.DBS.DBHelper;
import com.example.android.slidingtabsbasic.DBS.TechAnnounce;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by yuancui on 11/15/15.
 * Updated by yuanli on 11/16/15.
 * Updated by Bukunmi on 11/16/15.
 */
public class TechAnnounceDAO  {
    private DBHelper dbHelper;
    private static final Calendar calendar = Calendar.getInstance();


    public int insert(TechAnnounce announcements, Context c) {
        //Open connection to write data
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.Column_Announcements_Title, announcements.getTitle());
        values.put(DBHelper.Column_Announcements_Link, announcements.getLink());
        values.put(DBHelper.Column_Announcements_Desc, announcements.getDescription());
        values.put(DBHelper.Column_Announcements_Date_Added, calendar.getTimeInMillis());

        // Inserting Row
        long announcements_id = db.insert(DBHelper.Table_Announcements, null, values);
//        db.close(); // Closing database connection
        return (int) announcements_id;
    }

    public int delete(int announcements_id, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        int result = db.delete(DBHelper.Table_Announcements, DBHelper.Column_Announcements_ID + "=?", new String[]{String.valueOf(announcements_id)});
        db.close(); // Closing database connection
        return result;
    }

    public int update(TechAnnounce announcementUpdate, int id, Context c) {
        TechAnnounce announcementInDB = getAnnouncementsById(id,c);
        //check content value
        if(announcementUpdate.getTitle().equals(announcementInDB.getTitle())  &&
                announcementUpdate.getDescription().equals(announcementInDB.getDescription())){
            return 0;
        }
        else {
            dbHelper = new DBHelper(c);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DBHelper.Column_Announcements_Title, announcementUpdate.getTitle());
            values.put(DBHelper.Column_Announcements_Desc, announcementUpdate.getDescription());
            values.put(DBHelper.Column_Announcements_Date_Added, calendar.getTimeInMillis());
            // TODO: 11/15/2015 UpdateSaved only

            // It's a good practice to use parameter ?, instead of concatenate string
            long announcement_id = db.update(DBHelper.Table_Announcements, values, DBHelper.Column_Announcements_ID + "= ?", new String[]{String.valueOf(id)});
            db.close(); // Closing database connection
            return (int) announcement_id;
        }
    }

    public int updateSavedCol(int savedTag, String link, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.Column_Announcements_Saved, savedTag);
        // TODO: 11/15/2015 UpdateSaved only

        // It's a good practice to use parameter ?, instead of concatenate string
        long result = db.update(DBHelper.Table_Announcements, values, DBHelper.Column_Announcements_Link + "= ?", new String[]{String.valueOf(link)});
        db.close(); // Closing database connection
        return  (int) result;
    }


    public TechAnnounce getAnnouncementsById(int id,Context c ){
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_Announcements_ID + ", " +
                DBHelper.Column_Announcements_Title + ", " +
                DBHelper.Column_Announcements_Link + ", " +
                DBHelper.Column_Announcements_Desc + ", " +
                DBHelper.Column_Announcements_Saved + ", " +
                DBHelper.Column_Announcements_Date_Added +
                " FROM " + DBHelper.Table_Announcements
                + " WHERE " +
                DBHelper.Column_Announcements_ID + "=? "; // It's a good practice to use parameter ?, instead of concatenate string

        //int iCount =0;
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

    public ArrayList<TechAnnounce> getAnnouncementsBySavedTag(int saved,Context c ){
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_Announcements_ID + ", " +
                DBHelper.Column_Announcements_Title + ", " +
                DBHelper.Column_Announcements_Link + ", " +
                DBHelper.Column_Announcements_Desc + ", " +
                DBHelper.Column_Announcements_Saved + ", " +
                DBHelper.Column_Announcements_Date_Added +
                " FROM " + DBHelper.Table_Announcements
                + " WHERE " +
                DBHelper.Column_Announcements_Saved + "=? "; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        ArrayList<TechAnnounce> announcementList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(saved) } );

        if (cursor.moveToFirst()) {
            do {
                TechAnnounce announcements = new TechAnnounce();

                announcements.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Announcements_ID)));
                announcements.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Title)));
                announcements.setLink(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Link)));
                announcements.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Desc)));
                announcements.setSaved(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Announcements_Saved)));
                //announcements.a_date_added =cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_Date_Added));
                announcementList.add(announcements);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return announcementList;
    }

    public ArrayList<TechAnnounce> getAllAnnouncements(Context c ){
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                DBHelper.Column_Announcements_ID + ", " +
                DBHelper.Column_Announcements_Title + ", " +
                DBHelper.Column_Announcements_Link + ", " +
                DBHelper.Column_Announcements_Desc + ", " +
                DBHelper.Column_Announcements_Saved + ", " +
                DBHelper.Column_Announcements_Date_Added +
                " FROM " + DBHelper.Table_Announcements +
                " ORDER BY " + DBHelper.Column_Announcements_Date_Added + " DESC "; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        ArrayList<TechAnnounce> announcementList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null );

        if (cursor.moveToFirst()) {
            do {
                TechAnnounce announcements = new TechAnnounce();

                announcements.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Announcements_ID)));
                announcements.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Title)));
                announcements.setLink(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Link)));
                announcements.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Desc)));
                announcements.setSaved(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Announcements_Saved)));
                //announcements.a_date_added =cursor.getString(cursor.getColumnIndex(dbHelper.Column_Announcements_Date_Added));
                announcementList.add(announcements);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return announcementList;
    }

    public TechAnnounce getAnnouncementsB4Date(long weekOld, long currentTime, Context c){
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
                DBHelper.Column_Announcements_Saved + " = " + 0 + " AND " +
                DBHelper.Column_Announcements_Date_Added +
                " NOT BETWEEN '" + weekOld + "' AND '" + currentTime + "' " ; // It's a good practice to use parameter ?, instead of concatenate string
        //String[] selectArg = {};
        int iCount =0;
        TechAnnounce announcements = new TechAnnounce();

    Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                announcements.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Announcements_ID)));
                announcements.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Title)));
                announcements.setLink(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Link)));
                announcements.setDescription(cursor.getString(cursor.getColumnIndex(DBHelper.Column_Announcements_Desc)));
                announcements.setSaved(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_Announcements_Saved)));
                announcements.setDateAdded(cursor.getLong(cursor.getColumnIndex(DBHelper.Column_Announcements_Date_Added)));
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
                DBHelper.Column_Announcements_Link + "= '" + link + "' "+
                " ORDER BY " + DBHelper.Column_Announcements_Date_Added + " DESC "; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        TechAnnounce announcements = new TechAnnounce();

        Cursor cursor = db.rawQuery(selectQuery, null );

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

}
