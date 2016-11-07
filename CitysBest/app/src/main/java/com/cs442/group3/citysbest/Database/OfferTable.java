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
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + OFFER_TABLE + " (" +
                    OFFER_ID + " integer PRIMARY KEY autoincrement," +
                    OFFER_NAME + " TEXT," +
                    OFFER_OLDPRICE + " REAL," +
                    OFFER_NEWPRICE + " REAL," +
                    OFFER_DATE + " DATE," +
                    OFFER_IMAGE + " BLOB," +
                    OFFER_STORE_ID + " integer)" ;

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

}
