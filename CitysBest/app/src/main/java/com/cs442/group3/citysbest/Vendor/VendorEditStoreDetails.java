package com.cs442.group3.citysbest.Vendor;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs442.group3.citysbest.Client.LoginActivity;
import com.cs442.group3.citysbest.Database.CategoryTable;
import com.cs442.group3.citysbest.Database.SessionManager;
import com.cs442.group3.citysbest.Database.StoreTable;
import com.cs442.group3.citysbest.Database.Utils;
import com.cs442.group3.citysbest.R;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VendorEditStoreDetails extends VendorBaseActivity {

    byte[] inputData;
    Bitmap bitmap;
    Uri selectedImage;
    @InjectView(R.id.vendor_name)
    EditText _nameText;
    @InjectView(R.id.vendor_address)
    EditText _addressText;
    @InjectView(R.id.vendor_contactno) EditText _contact;
    @InjectView(R.id.vendor_hours) EditText _hoursText;
    @InjectView(R.id.v_img)
    ImageView _imgView;
    @InjectView(R.id.vUpload)
    Button _imgButton;
    @InjectView(R.id.vendor_store_update)
    Button _saveButton;
    @InjectView(R.id.vendor_spinner_category)
    Spinner vspinner;
    private static int RESULT_LOAD_IMG1 = 1;
    int c_id,s_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_vendor_edit_store_details, frameLayout1);
        Intent i=getIntent();
        ButterKnife.inject(this);
        CategoryTable categorytable=new CategoryTable(this);
        categorytable.open();
        List<String> categories=categorytable.getAllCategories();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vspinner.setAdapter(dataAdapter);
        _nameText.setText(i.getStringExtra("sname"));
        _addressText.setText(i.getStringExtra("saddress"));
        _hoursText.setText(i.getStringExtra("shours"));
        _contact.setText(i.getStringExtra("scontact"));
        vspinner.setSelection((i.getIntExtra("c_id",0))-1);
        _imgView.setImageBitmap(Utils.getImage(i.getByteArrayExtra("img")));
        inputData=i.getByteArrayExtra("img");
        SessionManager sm=new SessionManager(this);
        HashMap<String,String> storedetails=sm.getUserDetails();
        s_id=Integer.parseInt(storedetails.get(SessionManager.KEY_STORE_ID));
        vspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public void onSignupSuccess() {
        _saveButton.setEnabled(true);
        Toast.makeText(this, "Store Details Updated", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, null);
        Intent i=new Intent(this,VendorStoreDetails.class);
        startActivity(i);
    }

    public void onSignupFailed() {
        _saveButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String name = _nameText.getText().toString();
        String contactno=_contact.getText().toString();
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
            _contact.setError("Enter a Valid Contact Number");
            valid = false;
        } else {
            _contact.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter a Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (inputData ==null  ) {
            Toast.makeText(this, "Please Select a Image", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {

        }

        return valid;
    }

    public void UpdateStoreDetails(View v)
    {
        if (!validate()) {
            onSignupFailed();
            return;
        }
        String name = _nameText.getText().toString();
        String contactno=_contact.getText().toString();
        String address=_addressText.getText().toString();
        String hours=_hoursText.getText().toString();
        InputStream iStream = null;
        try {
            iStream = getContentResolver().openInputStream(selectedImage);
            inputData = Utils.getBytes(iStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        _saveButton.setEnabled(false);

        StoreTable storetable=new StoreTable(this);
        storetable.open();
       long c =storetable.updateStoreDetails(name,address,Double.parseDouble(contactno),c_id,hours,inputData,s_id);
       //c.moveToFirst();
        if(c !=  -1) {
            onSignupSuccess();
        }
        else
        {
            onSignupFailed();
        }
    }

    public void loadImageFromGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == RESULT_LOAD_IMG1 && resultCode == RESULT_OK
                    && null != data) {
                selectedImage = data.getData();
                ImageView imgView = (ImageView) findViewById(R.id.v_img);
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


    }
