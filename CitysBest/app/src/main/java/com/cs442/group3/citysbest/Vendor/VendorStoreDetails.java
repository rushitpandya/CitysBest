package com.cs442.group3.citysbest.Vendor;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs442.group3.citysbest.Database.CategoryTable;
import com.cs442.group3.citysbest.Database.SessionManager;
import com.cs442.group3.citysbest.Database.StoreTable;
import com.cs442.group3.citysbest.Database.Utils;
import com.cs442.group3.citysbest.R;

import java.util.HashMap;

public class VendorStoreDetails extends VendorBaseActivity {
    String storename,storeaddress,storeworkinghours,contact;
    int c_id;
    byte[] image;
    ImageView img;
    TextView sname,saddress,shours,contactno,category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_vendor_store_details, frameLayout1);
        img=(ImageView)findViewById(R.id.v_store_image);
        sname=(TextView)findViewById(R.id.v_store_name);
        saddress=(TextView)findViewById(R.id.v_store_address);
        shours=(TextView)findViewById(R.id.v_store_timings);
        contactno=(TextView)findViewById(R.id.v_store_contact);
        category=(TextView)findViewById(R.id.v_category);
        StoreTable storeTable=new StoreTable(this);
        storeTable.open();
        SessionManager sm=new SessionManager(this);
        HashMap<String,String> storedetails=sm.getUserDetails();
        int s_id=Integer.parseInt(storedetails.get(SessionManager.KEY_STORE_ID));

        Cursor c=storeTable.getStoreDetails(s_id);
        c.moveToFirst();
        storename=c.getString(c.getColumnIndex(StoreTable.STORE_NAME));
        storeaddress=c.getString(c.getColumnIndex(StoreTable.STORE_ADDRESS));
        storeworkinghours=c.getString(c.getColumnIndex(StoreTable.STORE_HOURS));
        contact=c.getLong(c.getColumnIndex(StoreTable.STORE_CONTACT))+"";
        c_id=c.getInt(c.getColumnIndex(StoreTable.STORE_CATEGORY_ID));
        image=c.getBlob(c.getColumnIndex(StoreTable.STORE_IMAGE));
        img.setImageBitmap(Utils.getImage(image));
        CategoryTable categorytable=new CategoryTable(this);
        categorytable.open();
        sname.setText(storename);
        saddress.setText(storeaddress);
        shours.setText(storeworkinghours);
        contactno.setText(contact);
        category.setText(categorytable.getCategory(c_id));

    }

    public void EditStoreDetails(View v)
    {
        Intent i=new Intent(this,VendorEditStoreDetails.class);
        i.putExtra("sname",storename);
        i.putExtra("saddress",storeaddress);
        i.putExtra("shours",storeworkinghours);
        i.putExtra("scontact",contact);
        i.putExtra("c_id",c_id);
        i.putExtra("img",image);
        startActivity(i);
    }
}
