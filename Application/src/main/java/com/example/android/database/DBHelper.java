package com.example.android.slidingtabsbasic;

import android.database.sqlite.SQLiteOpenHelper;



import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import com.example.android.common.logger.Log;


public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    // column of the Announcements Table
    public static final String Table_Announcements = "announcements";
    public static final String Column_Announcements_ID = "id";
    public static final String Column_Announcements_Title = "announce_title";
    public static final String Column_Announcements_Link = "announce_link";
    public static final String Column_Announcements_Date = "data";
    public static final String Column_Announcements_Date_Added = "a_date_added";

    // column of the KeyWords Table
    public static final String Table_KeyWords = "keywords";
    public static final String Column_KeyWords_ID = "kw_id";
    public static final String Column_KeyWords_Name = "name";
    public static final String Column_KeyWords_Favorites ="kw_favorites";
    public static final String Column_KeyWords_Date_Added = "kw_date_added";

    //column of the AnnouncementsKeywords table creation
    public static final String Table_AnnouncementsKeyWords = "announce_keywords";
    public static final String Column_AnnouncementsKeyWords_ID ="akw_id";
    public static final String Column_AnnouncementsKeyWords_Announcements_ID ="id";
    public static final String Column_AnnouncementsKeyWords_KeyWords_ID ="kw_id";
    public static final String Column_AnnouncementsKeyWords_Date_Added = "aK_date_added";

    //column of the Categories Table
    public static final String Table_Categories = "categories";
    public static final String Column_Categories_ID ="c_id";
    public static final String Column_Categories_Favorites ="c_favorites";
    public static final String Column_Categories_Date_Added ="c_date_added";

    //column of the AnnouncementCategories Table
    public static final String Table_AnnouncementsCategories = "announce_categories";
    public static final String Column_AnnouncementsCategories_ID ="ac_id";
    public static final String Column_AnnouncementsCategories_Announcements_ID ="id";
    public static final String Column_AnnouncementsCategories_Categories_ID ="c_id";
    public static final String Column_AnnouncementsCategories_Date_Added ="ac_date_added";

    public static final String DATABASE_NAME = "tac.db";
    public static final int DATABASE_VERSION = 1;

    //SQL statements of Announcements table creation
    public static final String SQL_CREATE_TABLE_Announcements = "CREATE TABLE" + Table_Announcements +"("
            + Column_Announcements_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Column_Announcements_Title + "TEXT NOT NULL, "
            + Column_Announcements_Link + "TEXT NOT NULL, "
            + Column_Announcements_Date + "DATE, "
            + Column_Announcements_Date_Added + "TIMESTAMP, "
            +");";

    //SQL statements of KeyWords table creation
    public static final String SQL_CREATE_TABLE_KeyWords = "CREATE TABLE" + Table_KeyWords +"("
            + Column_KeyWords_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Column_KeyWords_Name + "TEXT NOT NULL, "
            + Column_KeyWords_Favorites + "TEXT NOT NULL, "
            + Column_KeyWords_Date_Added + "TIMESTAMP, "
            +");";

    //SQL statements of AnnouncementsKeywords table creation
    public static final String SQL_CREATE_TABLE_Table_AnnouncementsKeyWords = "CREATE TABLE" + Table_AnnouncementsKeyWords +"("
            + Column_AnnouncementsKeyWords_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Column_AnnouncementsKeyWords_Announcements_ID + "INTEGER FOREIGN KEY, "
            + Column_AnnouncementsKeyWords_KeyWords_ID + "INTEGER FOREIGN KEY, "
            + Column_AnnouncementsKeyWords_Date_Added + "TIMESTAMP"
            +");";

    //SQL statements of Categories table creation
    public static final String SQL_CREATE_TABLE_Categories = "CREATE TABLE" + Table_Categories +"("
            + Column_Categories_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Column_Categories_Favorites + "TEXT NOT NULL, "
            + Column_Categories_Date_Added + "TIMESTAMP, "
            +");";

    //SQL statements of AnnouncementCategories table creation
    public static final String SQL_CREATE_TABLE_Table_AnnouncementCategories = "CREATE TABLE" + Table_AnnouncementsCategories +"("
            + Column_AnnouncementsCategories_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Column_AnnouncementsCategories_Announcements_ID + "INTEGER FOREIGN KEY, "
            + Column_AnnouncementsCategories_Categories_ID + "INTEGER FOREIGN KEY, "
            + Column_AnnouncementsCategories_Date_Added + "TIMESTAMP, "
            +");";

    public DBHelper (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database)
    {
        database.execSQL(SQL_CREATE_TABLE_Announcements);
        database.execSQL(SQL_CREATE_TABLE_KeyWords);
        database.execSQL(SQL_CREATE_TABLE_Table_AnnouncementsKeyWords);
        database.execSQL(SQL_CREATE_TABLE_Categories);
        database.execSQL(SQL_CREATE_TABLE_Table_AnnouncementCategories);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to " + newVersion);
        //clear all data
        db.execSQL("DROP TABLE IF EXISTS " + Table_Announcements);
        db.execSQL("DROP TABLE IF EXISTS " + Table_KeyWords);
        db.execSQL("DROP TABLE IF EXISTS " + Table_AnnouncementsKeyWords);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Categories);
        db.execSQL("DROP TABLE IF EXISTS " + Table_AnnouncementsCategories);

        //create the tables
        onCreate (db);
    }

}
