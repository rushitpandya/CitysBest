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

public class ProductTable {
    public static final String PRODUCT_ID = "_id";
    public static final String PRODUCT_NAME = "p_name";
    public static final String PRODUCT_PRICE = "p_price";
    public static final String PRODUCT_IMAGE = "p_image";
    public static final String PRODUCT_STORE_ID = "s_id";


    private static final String TAG = "ProductTable";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "CitysBest";
    private static final String PRODUCT_TABLE = "Product";
    private static final int DATABASE_VERSION = 4;

    private final Context mCtx;

    public static final String PRODUCT_CREATE =
            "CREATE TABLE if not exists " + PRODUCT_TABLE + " (" +
                    PRODUCT_ID + " integer PRIMARY KEY autoincrement," +
                    PRODUCT_NAME + " TEXT," +
                    PRODUCT_PRICE + " REAL," +
                    PRODUCT_IMAGE + " BLOB," +
                    PRODUCT_STORE_ID + " integer)";


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
            db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE);
            onCreate(db);
        }
    }

    public ProductTable(Context ctx) {
        this.mCtx = ctx;
    }

    public ProductTable open() throws SQLException {
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

    public long addNewProduct(String name,String price,byte[] data,int s_id)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PRODUCT_NAME,name);
        initialValues.put(PRODUCT_PRICE,Double.parseDouble(price));
        initialValues.put(PRODUCT_IMAGE,data);
        initialValues.put(PRODUCT_STORE_ID,s_id);
        Log.d("Success","Added");
        return mDb.insert(PRODUCT_TABLE, null, initialValues);
    }

    public Cursor getAllProducts(int s_id)
    {
        String q="SELECT * FROM " + PRODUCT_TABLE + " WHERE "+PRODUCT_STORE_ID+"="+s_id;
        Cursor cursor = mDb.rawQuery(q,null);
        return cursor;
    }
    public Cursor getProduct(int p_id)
    {
        String q="SELECT * FROM " + PRODUCT_TABLE + " WHERE "+PRODUCT_ID+"="+p_id;
        Cursor cursor = mDb.rawQuery(q,null);
        return cursor;
    }

    public long updateProductDetails(String name,String price,byte[] data,int p_id,int s_id)
    {
        /*String q="UPDATE " + STORE_TABLE + " SET "+STORE_NAME+" = \""+name+"\","+STORE_ADDRESS+" = \""+address+"\","+STORE_CONTACT+"="+cno+","+STORE_HOURS+" = \""+hours+"\","+STORE_IMAGE+"="+data+" WHERE "+STORE_ID+"="+s_id;
       Cursor cursor  = mDbHelper.getWritableDatabase().rawQuery(q,null);
        */

        ContentValues initialValues = new ContentValues();
        initialValues.put(PRODUCT_NAME,name);
        initialValues.put(PRODUCT_PRICE,price);
        initialValues.put(PRODUCT_IMAGE,data);
        initialValues.put(PRODUCT_STORE_ID,s_id);
        long id=mDb.update(PRODUCT_TABLE, initialValues, "_id="+p_id, null);
        return id;
    }

    public boolean deleteProduct(int pid)
    {
        /*String q="DELETE  FROM " + PRODUCT_TABLE + " WHERE "+PRODUCT_ID+"="+pid;
        Cursor cursor = mDb.rawQuery(q,null);
        return cursor;*/
        return mDb.delete(PRODUCT_TABLE, PRODUCT_ID + "=" + pid, null) > 0;

    }



}
