package com.cs442.group3.citysbest.Vendor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cs442.group3.citysbest.R;

public class ManageAccountDetails extends VendorBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_manage_account_details, frameLayout1);
    }
}
