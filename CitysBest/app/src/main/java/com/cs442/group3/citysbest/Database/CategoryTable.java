package com.cs442.group3.citysbest.Database;

/**
 * Created by Rash on 11/2/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class      CategoryTable {

    public static final String CATEGORY_ID = "_id";
    public static final String CATEGORY_NAME = "c_name";
    public static final String CATEGORY_IMAGE = "c_image";
    private static final String TAG = "CategoryTable";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "CitysBest";
    private static final String CATEGORY_TABLE = "Category";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + CATEGORY_TABLE + " (" +
                    CATEGORY_ID + " integer PRIMARY KEY autoincrement," +
                    CATEGORY_NAME + " TEXT," +
                    CATEGORY_IMAGE+ "  BLOB);";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
            onCreate(db);
        }
    }

    public CategoryTable(Context ctx) {
        this.mCtx = ctx;
    }

    public CategoryTable open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        //createDummy("rash","rash1234",1,1);
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }
}
