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
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class      CategoryTable {

    public static final String CATEGORY_ID = "_id";
    public static final String CATEGORY_NAME = "c_name";
    public static final String CATEGORY_IMAGE = "c_image";
    private static final String TAG = "CategoryTable";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "CitysBest";
    private static final String CATEGORY_TABLE = "Category";
    private static final int DATABASE_VERSION = 4;

    private final Context mCtx;

    public static final String CATEGORY_CREATE =
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
            db.execSQL(ProductTable.PRODUCT_CREATE);
            Log.w(TAG, ProductTable.PRODUCT_CREATE);
            db.execSQL(AccountTable.ACCOUNT_CREATE);
            Log.w(TAG, AccountTable.ACCOUNT_CREATE);
            db.execSQL(StoreTable.STORE_CREATE);
            Log.w(TAG, StoreTable.STORE_CREATE);
            db.execSQL(OfferTable.OFFER_CREATE);
            Log.w(TAG, OfferTable.OFFER_CREATE);
            db.execSQL(CategoryTable.CATEGORY_CREATE);
            Log.w(TAG, CategoryTable.CATEGORY_CREATE);


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

    public String getCategory(int c_id)
    {
        String cname="";
        String selectQuery = "SELECT  * FROM " + CATEGORY_TABLE + " WHERE "+CATEGORY_ID+"="+c_id;
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                cname=cursor.getString(cursor.getColumnIndex(CategoryTable.CATEGORY_NAME));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        mDb.close();// returning lables
        return cname;
    }

    public List<String> getAllCategories(){
        List<String> labels = new ArrayList<String>();

        String selectQuery = "SELECT  * FROM " + CATEGORY_TABLE;
        Cursor cursor = mDb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(cursor.getColumnIndex(CategoryTable.CATEGORY_NAME)));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        mDb.close();// returning lables
        return labels;
    }

    public long AddNew(String c_name,byte[] image)
    {
        ContentValues cv=new ContentValues();
        cv.put(CATEGORY_NAME,c_name);
        cv.put(CATEGORY_IMAGE,image);
        Log.d("Success","Added");
        return mDb.insert(CATEGORY_TABLE, null,cv);
    }
}
