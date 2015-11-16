package com.bukunmioyedeji.tac.ContentRetrival;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    // column of the Announcements Table
    public static final String Table_Announcements = "announcements";
    public static final String Column_Announcements_ID = "id";
    public static final String Column_Announcements_Title = "announce_title";
    public static final String Column_Announcements_Link = "announce_link";
    public static final String Column_Announcements_Desc = "description";
    public static final String Column_Announcements_Saved = "announce_saved";
    public static final String Column_Announcements_Date = "date";
    public static final String Column_Announcements_Date_Added = "a_date_added";

    // column of the KeyWords Table
    public static final String Table_KeyWords = "keywords";
    public static final String Column_KeyWords_ID = "id";
    public static final String Column_KeyWords_Name = "name";
    public static final String Column_KeyWords_Favorites ="kw_favorites";
    public static final String Column_KeyWords_Date_Added = "kw_date_added";

    //column of the Categories Table
    public static final String Table_Categories = "categories";
    public static final String Column_Categories_ID = "id";
    public static final String Column_Categories_Name = "name";
    public static final String Column_Categories_Favorites ="c_favorites";
    public static final String Column_Categories_Date_Added ="c_date_added";

    //column of the AnnouncementsKeywords table creation
    public static final String Table_AnnouncementsKeyWords = "announce_keywords";
    public static final String Column_AnnouncementsKeyWords_ID = "id";
    public static final String Column_AnnouncementsKeyWords_Announcements_ID ="a_id";
    public static final String Column_AnnouncementsKeyWords_KeyWords_ID ="kw_id";
    public static final String Column_AnnouncementsKeyWords_Date_Added = "aKw_date_added";

    //column of the AnnouncementCategories Table
    public static final String Table_AnnouncementsCategories = "announce_categories";
    public static final String Column_AnnouncementsCategories_ID ="id";
    public static final String Column_AnnouncementsCategories_Announcements_ID ="a_id";
    public static final String Column_AnnouncementsCategories_Categories_ID ="c_id";
    public static final String Column_AnnouncementsCategories_Date_Added ="aC_date_added";

    public static final String DATABASE_NAME = "announcementsDB";
    public static final int DATABASE_VERSION = 2;

    //SQL statements of Announcements table creation
    public static final String SQL_CREATE_TABLE_Announcements = "CREATE TABLE " + Table_Announcements +"("
            + Column_Announcements_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Column_Announcements_Title + " TEXT NOT NULL, "
            + Column_Announcements_Link + " TEXT NOT NULL, "
            + Column_Announcements_Desc + " TEXT NOT NULL, "
            + Column_Announcements_Saved + " INTEGER, "
            + Column_Announcements_Date_Added + " TEXT default CURRENT_TIMESTAMP "
            +");";

    //SQL statements of KeyWords table creation
    public static final String SQL_CREATE_TABLE_KeyWords = "CREATE TABLE " + Table_KeyWords +" ("
            + Column_KeyWords_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Column_KeyWords_Name + " TEXT NOT NULL, "
            + Column_KeyWords_Favorites + " INTEGER, "
            + Column_KeyWords_Date_Added + " TEXT default CURRENT_TIMESTAMP "
            + "); ";

    //SQL statements of Categories table creation
    public static final String SQL_CREATE_TABLE_Categories = "CREATE TABLE " + Table_Categories +"("
            + Column_Categories_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Column_Categories_Name + " TEXT NOT null, "
            + Column_Categories_Favorites + " INTEGER, "
            + Column_Categories_Date_Added + " TEXT default CURRENT_TIMESTAMP "
            +"); ";

    //SQL statements of AnnouncementsKeywords table creation
    public static final String SQL_CREATE_TABLE_Table_AnnouncementsKeyWords = "CREATE TABLE " + Table_AnnouncementsKeyWords +"("
            + Column_AnnouncementsKeyWords_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Column_AnnouncementsKeyWords_Announcements_ID + " INTEGER, "
            + Column_AnnouncementsKeyWords_KeyWords_ID + " INTEGER, "
            + Column_AnnouncementsKeyWords_Date_Added + " TEXT default CURRENT_TIMESTAMP, "

            + "FOREIGN KEY(" + Column_AnnouncementsKeyWords_Announcements_ID + ") REFERENCES "
            + Table_Announcements + "(id) ON DELETE CASCADE ON UPDATE CASCADE, "
            + "FOREIGN KEY(" + Column_AnnouncementsKeyWords_KeyWords_ID + ") REFERENCES "
            + Table_KeyWords + "(id) ON DELETE CASCADE ON UPDATE CASCADE"
            +"); ";

    //SQL statements of AnnouncementCategories table creation
    public static final String SQL_CREATE_TABLE_Table_AnnouncementCategories = "CREATE TABLE " + Table_AnnouncementsCategories +"("
            + Column_AnnouncementsCategories_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Column_AnnouncementsCategories_Announcements_ID + " INTEGER, "
            + Column_AnnouncementsCategories_Categories_ID + " INTEGER, "
            + Column_AnnouncementsCategories_Date_Added + " TEXT default CURRENT_TIMESTAMP, "

            + "FOREIGN KEY(" + Column_AnnouncementsCategories_Announcements_ID + ") REFERENCES "
            + Table_Announcements + "(id) ON DELETE CASCADE ON UPDATE CASCADE, "
            + "FOREIGN KEY(" + Column_AnnouncementsCategories_Categories_ID + ") REFERENCES "
            + Table_Categories + "(id)  ON DELETE CASCADE ON UPDATE CASCADE "
            +"); ";




    public DBHelper (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }


    @Override
    public void onCreate (SQLiteDatabase database)
    {
        database.execSQL(SQL_CREATE_TABLE_Announcements);
        database.execSQL(SQL_CREATE_TABLE_KeyWords);
        database.execSQL(SQL_CREATE_TABLE_Categories);
        database.execSQL(SQL_CREATE_TABLE_Table_AnnouncementCategories);
        database.execSQL(SQL_CREATE_TABLE_Table_AnnouncementsKeyWords);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to " + newVersion);
        //clear all data
        db.execSQL("DROP TABLE IF EXISTS " + Table_Announcements);
        db.execSQL("DROP TABLE IF EXISTS " + Table_KeyWords);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Categories);
        db.execSQL("DROP TABLE IF EXISTS " + Table_AnnouncementsCategories);
        db.execSQL("DROP TABLE IF EXISTS " + Table_AnnouncementsKeyWords);

        //create the tables
        onCreate(db);
    }


    private static DBHelper instance;
    public static synchronized DBHelper getHelper(Context context) {
        if (instance == null)
            instance = new DBHelper(context);
        return instance;
    }

    public Cursor selectCategoriesDB(String column_name){
        SQLiteDatabase db = this.getReadableDatabase();

        final String[] selectionArg = new String[] {column_name};

        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                Column_Categories_ID,
                Column_Categories_Name,
                Column_Categories_Favorites,
                Column_Categories_Date_Added
        };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                Column_Categories_Date_Added + " DESC";

        Cursor c = db.query(
                Table_Categories,        // The table to query
                projection,              // The columns to return
                Column_Categories_Name,  // The columns for the WHERE clause
                selectionArg,             // The values for the WHERE clause
                null,                    // don't group the rows
                null,                    // don't filter by row groups
                sortOrder                // The sort order
        );


        return c;
    }

    public  boolean insertCategoriesDB(String[] categories ) {
        SQLiteDatabase db = this.getWritableDatabase();
        long newInsert = 0 ;
        for (String category : categories) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Column_Categories_Name, category);

            newInsert = db.insert(Table_Categories, null, contentValues);
            String newResult = String.valueOf(newInsert);

            Log.d("Inserted", newResult);

        }

        if (newInsert == -1)
            return false;
        else
            return true;

    }
}