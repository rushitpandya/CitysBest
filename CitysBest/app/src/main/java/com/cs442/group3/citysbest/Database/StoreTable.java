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
import android.net.Uri;
import android.util.Log;

import java.io.InputStream;

import static com.cs442.group3.citysbest.Database.AccountTable.ACCOUNT_USERNAME;

public class StoreTable {
    public static final String STORE_ID = "_id";
    public static final String STORE_NAME = "s_name";
    public static final String STORE_ADDRESS = "s_address";
    public static final String STORE_HOURS = "s_timings";
    public static final String STORE_CONTACT = "s_contactno";
    public static final String STORE_CATEGORY_ID = "c_id";
    public static final String STORE_IMAGE = "s_image";
    private static final String TAG = "StoreTable";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "CitysBest";
    private static final String STORE_TABLE = "Store";
    private static final int DATABASE_VERSION = 4;

    private final Context mCtx;

    public static final String STORE_CREATE =
            "CREATE TABLE if not exists " + STORE_TABLE + " (" +
                    STORE_ID + " integer PRIMARY KEY autoincrement," +
                    STORE_NAME + " TEXT," +
                    STORE_ADDRESS + " TEXT," +
                    STORE_HOURS + " TEXT," +
                    STORE_IMAGE + " BLOB," +
                    STORE_CONTACT + " REAL," +
                    STORE_CATEGORY_ID+ "  integer);";

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
            db.execSQL("DROP TABLE IF EXISTS " + STORE_TABLE);
            onCreate(db);
        }
    }

    public StoreTable(Context ctx) {
        this.mCtx = ctx;
    }

    public StoreTable open() throws SQLException {
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


    public Cursor getStoreDetails(int s_id)
    {
        String q="SELECT * FROM " + STORE_TABLE + " WHERE "+STORE_ID+"="+s_id;
        Cursor cursor = mDbHelper.getWritableDatabase().rawQuery(q,null);
        return cursor;
    }

    public long addNewStore(String name,String address,double cno,int c_id,String hours,byte[] data)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(STORE_NAME,name);
        initialValues.put(STORE_ADDRESS,address);
        initialValues.put(STORE_CONTACT,cno);
        initialValues.put(STORE_CATEGORY_ID,c_id);
        initialValues.put(STORE_HOURS,hours);
        initialValues.put(STORE_IMAGE,data);
        Log.d("Success","Added");
        return mDb.insert(STORE_TABLE, null, initialValues);


    }

    public long updateStoreDetails(String name,String address,double cno,int c_id,String hours,byte[] data,int s_id)
    {
        /*String q="UPDATE " + STORE_TABLE + " SET "+STORE_NAME+" = \""+name+"\","+STORE_ADDRESS+" = \""+address+"\","+STORE_CONTACT+"="+cno+","+STORE_HOURS+" = \""+hours+"\","+STORE_IMAGE+"="+data+" WHERE "+STORE_ID+"="+s_id;
       Cursor cursor  = mDbHelper.getWritableDatabase().rawQuery(q,null);
        */

        ContentValues initialValues = new ContentValues();
        initialValues.put(STORE_NAME,name);
        initialValues.put(STORE_ADDRESS,address);
        initialValues.put(STORE_CONTACT,cno);
        initialValues.put(STORE_CATEGORY_ID,c_id);
        initialValues.put(STORE_HOURS,hours);
        initialValues.put(STORE_IMAGE,data);
        long id=mDb.update(STORE_TABLE, initialValues, "_id="+s_id, null);
        return id;
    }
}
