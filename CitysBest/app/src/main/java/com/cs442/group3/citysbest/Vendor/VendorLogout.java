package com.cs442.group3.citysbest.Vendor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cs442.group3.citysbest.Database.SessionManager;
import com.cs442.group3.citysbest.R;

public class VendorLogout extends VendorBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_vendor_logout, frameLayout1);

        SessionManager sm=new SessionManager(this);
        sm.logoutUser();
    }
}
