package com.cs442.group3.citysbest.Vendor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cs442.group3.citysbest.R;

public class VendorEditProduct extends VendorBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_vendor_edit_product, frameLayout1);
    }
    public void EditProduct(View v)
    {
        Intent i=new Intent(this,VendorProductDetails.class);
        startActivity(i);
    }
}
