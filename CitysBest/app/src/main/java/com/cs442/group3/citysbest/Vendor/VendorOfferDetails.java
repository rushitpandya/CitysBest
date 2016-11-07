package com.cs442.group3.citysbest.Vendor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cs442.group3.citysbest.R;

public class VendorOfferDetails extends VendorBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_vendor_offer_details, frameLayout1);
    }

    public void AddOffer(View v)
    {
        Intent i=new Intent(this,VendorAddOffer.class);
        startActivity(i);
    }
    public void EditOfferDetails(View v)
    {
        Intent i=new Intent(this,VendorEditOffer.class);
        startActivity(i);
    }
}
