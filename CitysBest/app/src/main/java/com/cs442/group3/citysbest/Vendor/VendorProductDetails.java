package com.cs442.group3.citysbest.Vendor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cs442.group3.citysbest.R;

public class VendorProductDetails extends VendorBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_vendor_product_details, frameLayout1);
    }

    public void AddProduct(View v)
    {
        Intent i=new Intent(this,VendorAddProduct.class);
        startActivity(i);
    }
    public void EditProductDetails(View v)
    {
        Intent i=new Intent(this,VendorEditProduct.class);
        startActivity(i);
    }
}
