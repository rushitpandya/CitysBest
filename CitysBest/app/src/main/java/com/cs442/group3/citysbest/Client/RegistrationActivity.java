package com.cs442.group3.citysbest.Client;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.cs442.group3.citysbest.Database.AccountTable;
import com.cs442.group3.citysbest.Database.StoreTable;
import com.cs442.group3.citysbest.Database.Utils;
import com.cs442.group3.citysbest.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class
RegistrationActivity extends BaseActivity {

    byte[] inputData;
    Bitmap bitmap;
    Uri selectedImage;
    private static final String TAG = "RegistrationActivity";
    private static int RESULT_LOAD_IMG = 1;
    @InjectView(R.id.input_name)
    EditText _nameText;
    @InjectView(R.id.input_address)
    EditText _addressText;
    @InjectView(R.id.input_contactno)
    EditText _numberText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.input_hours) EditText _hoursText;
    @InjectView(R.id.btn_signup)
    Button _signupButton;
    @InjectView(R.id.link_login)
    TextView _loginLink;
    @InjectView(R.id.spinner_category) Spinner spinner_category;
    int c_id;
    public void loadImageFromGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                selectedImage = data.getData();
                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                imgView.setImageURI(selectedImage);
            }
            else
            {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_registration, frameLayout);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            c_id=(int)id+1;
            Log.d("id",c_id+"");
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }
        _signupButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

         String name = _nameText.getText().toString();
         String email = _emailText.getText().toString();
         String password = _passwordText.getText().toString();
         String contactno=_numberText.getText().toString();
         String address=_addressText.getText().toString();
         String hours=_hoursText.getText().toString();
         InputStream iStream = null;
        try {
            iStream = getContentResolver().openInputStream(selectedImage);
             inputData = Utils.getBytes(iStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
                        StoreTable storetable= new StoreTable(getApplicationContext());
                        storetable.open();
                        int s_id=(int)storetable.addNewStore(name,address,Double.parseDouble(contactno),c_id,hours,inputData);
                        Log.d("s_id",s_id+"");
                        AccountTable accountTable=new AccountTable(getApplicationContext());
                        accountTable.open();
                        accountTable.addNewAccount(email,password,s_id,c_id);
                        onSignupSuccess();
                        progressDialog.dismiss();

    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        Toast.makeText(this, "Registered Successfully!!Please Login with your email and password", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, null);
        Intent i=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);
    }

    public void onSignupFailed() {
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String contactno=_numberText.getText().toString();
        String address=_addressText.getText().toString();
        String hours=_hoursText.getText().toString();

        if (name.isEmpty()) {
            _nameText.setError("Store Name is required");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (hours.isEmpty()) {
            _hoursText.setError("Store Timings are required. Enter 24 hours if no specific");
            valid = false;
        } else {
            _hoursText.setError(null);
        }

        if (contactno.isEmpty() || contactno.length()!=10) {
            _numberText.setError("Enter a Valid Contact Number");
            valid = false;
        } else {
            _numberText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter a Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()  ) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 ) {
            _passwordText.setError("more than  alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        if (selectedImage ==null  ) {
            Toast.makeText(this, "Please Select a Image", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {

        }

        return valid;
    }
}
