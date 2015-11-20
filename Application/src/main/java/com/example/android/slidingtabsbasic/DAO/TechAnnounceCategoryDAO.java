package com.example.android.slidingtabsbasic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.slidingtabsbasic.DBS.DBHelper;
import com.example.android.slidingtabsbasic.DBS.TechAnnounceCategoryList;

import java.util.ArrayList;

/**
 * Updated by Bukunmi on 11/19/15.
 */
public class TechAnnounceCategoryDAO {
    private DBHelper dbHelper;


    public int insert(int a_Id, int c_Id, Context c) {
        //Open connection to write data
        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.Column_AnnouncementsCategories_Announcements_ID, a_Id);
        values.put(DBHelper.Column_AnnouncementsCategories_Categories_ID, c_Id);

        // Inserting Row
        long announcementCategories_id = db.insert(DBHelper.Table_AnnouncementsCategories, null, values);
        db.close(); // Closing database connection
        return (int) announcementCategories_id;
    }

    public int delete(int announcement_id, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        int result = db.delete(DBHelper.Table_AnnouncementsCategories, DBHelper.Column_AnnouncementsCategories_Announcements_ID + "= ?", new String[]{String.valueOf(announcement_id)});
        db.close(); // Closing database connection
        return result;
    }

    public int update(TechAnnounceCategoryList announceCategoryList, int id, Context c) {

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.Column_AnnouncementsCategories_Categories_ID, announceCategoryList.getC_Id());
        values.put(DBHelper.Column_AnnouncementsCategories_Announcements_ID, announceCategoryList.getA_Id());
        //values.put(dbHelper.Column_Announcements_Saved, annoucementCategories.getSaved());

        // It's a good practice to use parameter ?, instead of concatenate string
        int result = db.update(DBHelper.Table_AnnouncementsCategories, values, DBHelper.Column_AnnouncementsCategories_ID + "= ?", new String[]{String.valueOf(id)});
        db.close(); // Closing database connection
        return result;
    }

    public ArrayList<TechAnnounceCategoryList> getAnnByCatId(int cat_id, Context c) {


        ArrayList<TechAnnounceCategoryList> techA_CategoryLists = new ArrayList<>();

        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                DBHelper.Column_AnnouncementsCategories_ID + "," +
                DBHelper.Column_AnnouncementsCategories_Announcements_ID + "," +
                DBHelper.Column_AnnouncementsCategories_Categories_ID +
                " FROM " + DBHelper.Table_AnnouncementsCategories +
                " WHERE " + DBHelper.Column_AnnouncementsCategories_Categories_ID + " =? "; // It's a good practice to use parameter ?, instead of concatenate string

        int iCount = 0;
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(cat_id)});

        //if (cursor.moveToFirst()) {

        while (cursor.moveToNext()) {
            TechAnnounceCategoryList techAnnounceCategoryList = new TechAnnounceCategoryList();

            techAnnounceCategoryList.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsCategories_ID)));
            techAnnounceCategoryList.setA_Id(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsCategories_Announcements_ID)));
            techAnnounceCategoryList.setC_Id(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsCategories_Categories_ID)));

            int val = cursor.getPosition() + 1;

            //if(cursor.getPosition() == iCount + 1){
            iCount++;
            techA_CategoryLists.add(cursor.getPosition(), techAnnounceCategoryList);

        }

        cursor.close();
        db.close();
        return techA_CategoryLists;
    }

    public int getAnnCatId(int ann_id, int cat_id, Context c) {


        dbHelper = new DBHelper(c);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                DBHelper.Column_AnnouncementsCategories_ID + "," +
                DBHelper.Column_AnnouncementsCategories_Announcements_ID + "," +
                DBHelper.Column_AnnouncementsCategories_Categories_ID +
                " FROM " + DBHelper.Table_AnnouncementsCategories +
                " WHERE " + DBHelper.Column_AnnouncementsCategories_Categories_ID + " = " + cat_id +
                " AND " + DBHelper.Column_AnnouncementsCategories_Announcements_ID + " = " + ann_id ; // It's a good practice to use parameter ?, instead of concatenate string

         int ann_cat_id = 0;
        Cursor cursor = db.rawQuery(selectQuery, null);

        //if (cursor.moveToFirst()) {

        while (cursor.moveToNext()) {
            TechAnnounceCategoryList techAnnounceCategoryList = new TechAnnounceCategoryList();

            ann_cat_id = cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsCategories_ID));

            techAnnounceCategoryList.setA_Id(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsCategories_Announcements_ID)));
            techAnnounceCategoryList.setC_Id(cursor.getInt(cursor.getColumnIndex(DBHelper.Column_AnnouncementsCategories_Categories_ID)));

            //if(cursor.getPosition() == iCount + 1){
        }

        cursor.close();
        db.close();
        return ann_cat_id;
    }

}
