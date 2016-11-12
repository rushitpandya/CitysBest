package com.cs442.group3.citysbest.Vendor;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs442.group3.citysbest.Client.LoginActivity;
import com.cs442.group3.citysbest.Database.AccountTable;
import com.cs442.group3.citysbest.Database.SessionManager;
import com.cs442.group3.citysbest.R;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ManageAccountDetails extends VendorBaseActivity {

    @InjectView(R.id.txtUsername1)
    EditText u_name;
    @InjectView(R.id.txtNewPassword1)
    EditText u_newpassword;
    @InjectView(R.id.txtOldPassword1)
    EditText u_oldpassword;
    @InjectView(R.id.newusername)
    Button edit_username;
    @InjectView(R.id.btnLogin1)
    Button edit_password;
    int sid,aid;
    String oldusername,oldpassword,newusername,newpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_manage_account_details, frameLayout1);
        ButterKnife.inject(this);
        SessionManager sm=new SessionManager(this);
        HashMap<String,String> storedetails=sm.getUserDetails();
        sid=Integer.parseInt(storedetails.get(SessionManager.KEY_STORE_ID));
        aid=Integer.parseInt(storedetails.get(SessionManager.KEY_AID));
        oldusername=storedetails.get(SessionManager.KEY_NAME);
        oldpassword=storedetails.get(SessionManager.KEY_PASSWORD);

    }

    public void ChangeUsername(View v)
    {
        AccountTable accountTable=new AccountTable(this);
        accountTable.open();
        accountTable.changeUsername(aid,u_name.getText().toString());
        Toast.makeText(getApplicationContext(),"Username Updated!!! Please Login Again with your new username",Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

    public void ChangePassword(View v)
    {
        if(oldpassword.equals(u_oldpassword.getText().toString())) {
            AccountTable accountTable = new AccountTable(this);
            accountTable.open();
            accountTable.changePassword(aid, u_newpassword.getText().toString());
            Toast.makeText(getApplicationContext(), "Password Updated!!! Please Login again with your new password", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please Enter Correct Old Password", Toast.LENGTH_SHORT).show();

        }
    }
}
