package com.cs442.group3.citysbest.Vendor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cs442.group3.citysbest.Database.ProductTable;
import com.cs442.group3.citysbest.R;

public class VendorDeleteProduct extends VendorBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_vendor_delete_product, frameLayout1);
        Intent i=getIntent();
        int pid=i.getIntExtra("id",0);
        Log.d("Produid",pid+"");
        ProductTable producttable=new ProductTable(this);
        producttable.open();
        producttable.deleteProduct(pid);
        Intent in=new Intent(this,VendorProductDetails.class);
        startActivity(in);

    }
}
