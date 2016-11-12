package com.cs442.group3.citysbest.Client;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.DialogInterface;

import com.cs442.group3.citysbest.Database.AccountTable;
import com.cs442.group3.citysbest.Database.SessionManager;
import com.cs442.group3.citysbest.R;
import com.cs442.group3.citysbest.Vendor.VendorStoreDetails;

public class LoginActivity extends BaseActivity {

    // Email, password edittext
    EditText txtUsername, txtPassword;

    // login button
    Button btnLogin;

    // Alert Dialog Manager
    AlertDialog alertDialog;

    // Session Manager Class
    SessionManager session;
    AccountTable accounttable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        getLayoutInflater().inflate(R.layout.activity_login, frameLayout);
        accounttable=new AccountTable(this);
        accounttable.open();
        // Session Manager
        session = new SessionManager(getApplicationContext());
        alertDialog = new AlertDialog.Builder(this).create();

        // Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();


        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);


        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                Cursor cursor=  accounttable.checkCredentials(username,password);
                if (!username.equals("")  || !password.equals("")) {

                    if (cursor != null && cursor.getCount()==1) {

                        cursor.moveToFirst();
                        String cusername = cursor.getString(cursor
                                .getColumnIndex(AccountTable.ACCOUNT_USERNAME));
                        String cpassword = cursor.getString(cursor
                                .getColumnIndex(AccountTable.ACCOUNT_PASSWORD));
                        int storeid = Integer.parseInt(cursor.getString(cursor
                                .getColumnIndex(AccountTable.ACCOUNT_STORE_ID)));
                        Log.d("storeid",storeid+"");
                        int categoryid = Integer.parseInt(cursor.getString(cursor
                                .getColumnIndex(AccountTable.ACCOUNT_CATEGORY_ID)));

                        int aid = Integer.parseInt(cursor.getString(cursor
                                .getColumnIndex(AccountTable.ACCOUNT_ID)));

                        if (username.equals(cusername) && password.equals(cpassword)) {
                            session.createLoginSession(cusername, storeid, categoryid,cpassword,aid);
                            Intent i = new Intent(getApplicationContext(), VendorStoreDetails.class);
                            startActivity(i);
                            finish();
                        } else {

                        }
                    } else {
                        alertDialog.setTitle("Login failed..");
                        alertDialog.setMessage("Username/Password is incorrect");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
                else
                {

                    alertDialog.setTitle("Required");
                    alertDialog.setMessage("Username and Password is required");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

            }
        });
    }
}