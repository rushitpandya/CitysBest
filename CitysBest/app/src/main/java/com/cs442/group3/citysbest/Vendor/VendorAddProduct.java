package com.cs442.group3.citysbest.Vendor;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cs442.group3.citysbest.Database.OfferTable;
import com.cs442.group3.citysbest.Database.ProductTable;
import com.cs442.group3.citysbest.Database.SessionManager;
import com.cs442.group3.citysbest.Database.Utils;
import com.cs442.group3.citysbest.R;

import java.io.InputStream;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VendorAddProduct extends VendorBaseActivity {

    byte[] inputData;
    @InjectView(R.id.v_add_product_name)
    EditText p_name;
    @InjectView(R.id.v_add_product_price)
    EditText p_price;
    @InjectView(R.id.v_add_product_image)
    ImageView _imgView;
    @InjectView(R.id.p_image_upload)
    Button _imgButton;
    @InjectView(R.id.v_add_new_product_btn)
    Button add;
    int s_id;
    private static int RESULT_LOAD_IMG1 = 1;
    Uri selectedImage;
    ProductTable producttable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_vendor_add_product, frameLayout1);
        ButterKnife.inject(this);
        SessionManager sm=new SessionManager(this);
        HashMap<String,String> storedetails=sm.getUserDetails();
        s_id=Integer.parseInt(storedetails.get(SessionManager.KEY_STORE_ID));

    }

    public void AddNewProduct(View v)
    {
        if (!validate()) {
            onSignupFailed();
            return;
        }
        String s_p_name = p_name.getText().toString();
        String s_p_price=p_price.getText().toString();
        InputStream iStream = null;
        try {
            iStream = getContentResolver().openInputStream(selectedImage);
            inputData = Utils.getBytes(iStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        add.setEnabled(false);
        producttable=new ProductTable(getApplicationContext());
        producttable.open();
        long c =producttable.addNewProduct(s_p_name,s_p_price,inputData,s_id);
        //c.moveToFirst();
        if(c !=  -1) {
            onSignupSuccess();
        }
        else
        {
            onSignupFailed();
        }


    }

    public void onSignupSuccess() {
        add.setEnabled(true);
        Toast.makeText(this, "Product Details Added", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, null);
        Intent i=new Intent(this,VendorProductDetails.class);
        startActivity(i);
    }

    public void onSignupFailed() {
        add.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String s_p_name = p_name.getText().toString();
        String s_p_price=p_price.getText().toString();

        if (s_p_name.isEmpty()) {
            p_name.setError("Product Name is required");
            valid = false;
        } else {
            p_name.setError(null);
        }

        if (s_p_price.isEmpty()) {
            p_price.setError("Product Price are required");
            valid = false;
        } else {
            p_price.setError(null);
        }

        if (selectedImage ==null  ) {
            Toast.makeText(this, "Please Select a Image", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {

        }

        return valid;
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
                ImageView imgView = (ImageView) findViewById(R.id.v_add_product_image);
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
