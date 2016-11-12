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

public class AccountTable {
    public static final String ACCOUNT_ID = "_id";
    public static final String ACCOUNT_USERNAME = "a_username";
    public static final String ACCOUNT_PASSWORD = "a_password";
    public static final String ACCOUNT_STORE_ID = "s_id";
    public static final String ACCOUNT_CATEGORY_ID = "c_id";

    private static final String TAG = "AccountTable";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "CitysBest";
    private static final String ACCOUNT_TABLE = "Accounts";
    private static final int DATABASE_VERSION = 4;

    private final Context mCtx;

    public static final String ACCOUNT_CREATE =
            "CREATE TABLE if not exists " + ACCOUNT_TABLE + " (" +
                    ACCOUNT_ID + " integer PRIMARY KEY autoincrement," +
                    ACCOUNT_USERNAME + " TEXT," +
                    ACCOUNT_PASSWORD + " TEXT," +
                    ACCOUNT_STORE_ID + " integer,"+
                    ACCOUNT_CATEGORY_ID + " integer)";

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
            db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
            onCreate(db);
        }
    }

    public AccountTable(Context ctx) {
        this.mCtx = ctx;
    }

    public AccountTable open() throws SQLException {
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

    public Cursor checkCredentials(String username,String password)
    {
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + ACCOUNT_TABLE + " WHERE "+ACCOUNT_USERNAME+"=? AND " +ACCOUNT_PASSWORD+"=?", new String[]{username,password});
        return cursor;
    }

    public long addNewAccount(String name,String pass,
                           int s_id,int c_id) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(ACCOUNT_USERNAME,name);
        initialValues.put(ACCOUNT_PASSWORD,pass);
        initialValues.put(ACCOUNT_STORE_ID,s_id);
        initialValues.put(ACCOUNT_CATEGORY_ID,c_id);
        return mDb.insert(ACCOUNT_TABLE, null, initialValues);
    }

    public long changeUsername(int aid,String name)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(ACCOUNT_USERNAME,name);
        long id=mDb.update(ACCOUNT_TABLE, initialValues, "_id="+aid, null);
        return id;
    }

    public long changePassword(int aid,String password)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(ACCOUNT_PASSWORD,password);
        long id=mDb.update(ACCOUNT_TABLE, initialValues, "_id="+aid, null);
        return id;
    }

}
