package com.example.android.slidingtabsbasic;

/**
 * Created by yuan on 11/14/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;

public class listCurd {
    private DBHelper dbHelper;

    public listCurd(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(AnnouncementsList list) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AnnouncementsList.KEY_age, list.age);
        values.put(AnnouncementsList.KEY_email,list.email);
        values.put(AnnouncementsList.KEY_name, list.name);

        // Inserting Row
        long announcements_Id = db.insert(AnnouncementsList.TABLE, null, values);
        db.close(); // Closing database connection
        return (int)  announcements_Id;
    }

    public void delete(int student_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(AnnouncementsList.TABLE, AnnouncementsList.KEY_ID + "= ?", new String[] { String.valueOf(student_Id) });
        db.close(); // Closing database connection
    }

    public void update(AnnouncementsList list) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(AnnouncementsList.KEY_age, list.age);
        values.put(AnnouncementsList.KEY_email,list.email);
        values.put(AnnouncementsList.KEY_name, list.name);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(AnnouncementsList.TABLE, values, AnnouncementsList.KEY_ID + "= ?", new String[] { String.valueOf(list.student_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>>  getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                AnnouncementsList.KEY_ID + "," +
                AnnouncementsList.KEY_name + "," +
                AnnouncementsList.KEY_email + "," +
                AnnouncementsList.KEY_age +
                " FROM " + AnnouncementsList.TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> list = new HashMap<String, String>();
                list.put("id", cursor.getString(cursor.getColumnIndex(AnnouncementsList.KEY_ID)));
                list.put("name", cursor.getString(cursor.getColumnIndex(AnnouncementsList.KEY_name)));
                studentList.add(list);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }

    public AnnouncementsList getStudentById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                AnnouncementsList.KEY_ID + "," +
                AnnouncementsList.KEY_name + "," +
                AnnouncementsList.KEY_email + "," +
                AnnouncementsList.KEY_age +
                " FROM " + AnnouncementsList.TABLE
                + " WHERE " +
                AnnouncementsList.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        AnnouncementsList student = new AnnouncementsList();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                student.student_ID =cursor.getInt(cursor.getColumnIndex(AnnouncementsList.KEY_ID));
                student.name =cursor.getString(cursor.getColumnIndex(AnnouncementsList.KEY_name));
                student.email  =cursor.getString(cursor.getColumnIndex(AnnouncementsList.KEY_email));
                student.age =cursor.getInt(cursor.getColumnIndex(AnnouncementsList.KEY_age));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }

}