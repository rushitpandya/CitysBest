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

import java.util.Date;

public class OfferTable {
    public static final String OFFER_ID = "_id";
    public static final String OFFER_NAME = "o_name";
    public static final String OFFER_OLDPRICE = "o_oldprice";
    public static final String OFFER_NEWPRICE = "o_newprice";
    public static final String OFFER_IMAGE = "o_image";
    public static final String OFFER_DATE = "o_date";
    public static final String OFFER_STORE_ID = "s_id";

    private static final String TAG = "OfferTable";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "CitysBest";
    private static final String OFFER_TABLE = "Offer";
    private static final int DATABASE_VERSION = 4;

    private final Context mCtx;

    public static final String OFFER_CREATE =
            "CREATE TABLE if not exists " + OFFER_TABLE + " (" +
                    OFFER_ID + " integer PRIMARY KEY autoincrement," +
                    OFFER_NAME + " TEXT," +
                    OFFER_OLDPRICE + " REAL," +
                    OFFER_NEWPRICE + " REAL," +
                    OFFER_DATE + " DEFAULT CURRENT_TIMESTAMP," +
                    OFFER_IMAGE + " BLOB," +
                    OFFER_STORE_ID + " integer)" ;

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
            db.execSQL("DROP TABLE IF EXISTS " + OFFER_TABLE);
            onCreate(db);
        }
    }

    public OfferTable(Context ctx) {
        this.mCtx = ctx;
    }

    public OfferTable open() throws SQLException {
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

    public long addNewOffer(String name, String newprice, String oldprice, String date, byte[] data, int s_id)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(OFFER_NAME,name);
        initialValues.put(OFFER_OLDPRICE,Double.parseDouble(oldprice));
        initialValues.put(OFFER_NEWPRICE,Double.parseDouble(newprice));
        initialValues.put(OFFER_IMAGE,data);
        initialValues.put(OFFER_DATE,date);
        initialValues.put(OFFER_STORE_ID,s_id);
        Log.d("Success","Added");
        return mDb.insert(OFFER_TABLE, null, initialValues);
    }

    public Cursor getOffer(int o_id)
    {
        String q="SELECT * FROM " + OFFER_TABLE + " WHERE "+OFFER_ID+"="+o_id;
        Cursor cursor = mDb.rawQuery(q,null);
        return cursor;
    }

    public Cursor getAllOffers(int s_id)
    {
        String q="SELECT * FROM " + OFFER_TABLE + " WHERE "+OFFER_STORE_ID+"="+s_id;
        Cursor cursor = mDb.rawQuery(q,null);
        return cursor;
    }

    public boolean deleteOffer(int oid)
    {
        /*String q="DELETE  FROM " + PRODUCT_TABLE + " WHERE "+PRODUCT_ID+"="+pid;
        Cursor cursor = mDb.rawQuery(q,null);
        return cursor;*/
        return mDb.delete(OFFER_TABLE, OFFER_ID + "=" + oid, null) > 0;

    }

    public long updateOfferDetails(String name, String newprice, String oldprice, String date, byte[] data, int s_id,int o_id)
    {
        /*String q="UPDATE " + STORE_TABLE + " SET "+STORE_NAME+" = \""+name+"\","+STORE_ADDRESS+" = \""+address+"\","+STORE_CONTACT+"="+cno+","+STORE_HOURS+" = \""+hours+"\","+STORE_IMAGE+"="+data+" WHERE "+STORE_ID+"="+s_id;
       Cursor cursor  = mDbHelper.getWritableDatabase().rawQuery(q,null);
        */

        ContentValues initialValues = new ContentValues();
        initialValues.put(OFFER_NAME,name);
        initialValues.put(OFFER_OLDPRICE,Double.parseDouble(oldprice));
        initialValues.put(OFFER_NEWPRICE,Double.parseDouble(newprice));
        initialValues.put(OFFER_IMAGE,data);
        initialValues.put(OFFER_DATE,date);
        initialValues.put(OFFER_STORE_ID,s_id);
        long id=mDb.update(OFFER_TABLE, initialValues, "_id="+o_id, null);
        return id;
    }
}
