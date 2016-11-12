package com.cs442.group3.citysbest.Vendor;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cs442.group3.citysbest.Database.ProductTable;
import com.cs442.group3.citysbest.Database.SessionManager;
import com.cs442.group3.citysbest.Database.StoreTable;
import com.cs442.group3.citysbest.Database.Utils;
import com.cs442.group3.citysbest.R;

import java.io.InputStream;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VendorEditProduct extends VendorBaseActivity {

    byte[] inputData;
    @InjectView(R.id.v_edit_product_name1)
    EditText p_name;
    @InjectView(R.id.v_edit_product_price1)
    EditText p_price;
    @InjectView(R.id.v_edit_product_image1)
    ImageView _imgView;
    @InjectView(R.id.p_edit_image_upload1)
    Button _imgButton;
    @InjectView(R.id.v_edit_product_btn1)
    Button add;
    int s_id;
    private static int RESULT_LOAD_IMG1 = 1;
    Uri selectedImage;
    ProductTable producttable;
    int p_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_vendor_edit_product, frameLayout1);
        Intent i=getIntent();
        p_id=i.getIntExtra("id",0);
        ButterKnife.inject(this);
        SessionManager sm=new SessionManager(this);
        HashMap<String,String> storedetails=sm.getUserDetails();
        s_id=Integer.parseInt(storedetails.get(SessionManager.KEY_STORE_ID));
        producttable=new ProductTable(this);
        producttable.open();
        Cursor c=producttable.getProduct(p_id);
        c.moveToFirst();
        producttable.close();
        p_name.setText(c.getString(c.getColumnIndex(ProductTable.PRODUCT_NAME)));
        p_price.setText(c.getString(c.getColumnIndex(ProductTable.PRODUCT_PRICE)));
        inputData=c.getBlob(c.getColumnIndex(ProductTable.PRODUCT_IMAGE));
        _imgView.setImageBitmap(Utils.getImage(c.getBlob(c.getColumnIndex(ProductTable.PRODUCT_IMAGE))));
        c.close();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("inLoop","");

                if (!validate()) {
                    onSignupFailed();
                    return;
                }
                String name = p_name.getText().toString();
                String price=p_price.getText().toString();
                InputStream iStream = null;
                try {
                    iStream = getContentResolver().openInputStream(selectedImage);
                    inputData = Utils.getBytes(iStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                add.setEnabled(false);
                ProductTable producttable=new ProductTable(getApplicationContext());
                producttable.open();
                long c =producttable.updateProductDetails(name,price,inputData,p_id,s_id);
                //c.moveToFirst();
                producttable.close();
                if(c !=  -1) {
                    onSignupSuccess();
                }
                else
                {
                    onSignupFailed();
                }
            }
        });
    }

    public void onSignupSuccess() {
        add.setEnabled(true);
        Toast.makeText(this, "Product Details Updated", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, null);
        Intent i=new Intent(this,VendorProductDetails.class);
        startActivity(i);
    }

    public void onSignupFailed() {
        add.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String pname = p_name.getText().toString();
        String pprice=p_price.getText().toString();


        if (pname.isEmpty()) {
            p_name.setError("Product Name is required");
            valid = false;
        } else {
            p_name.setError(null);
        }

        if (pprice.isEmpty()) {
            p_price.setError("Product Price");
            valid = false;
        } else {
            p_price.setError(null);
        }

        if (inputData ==null  ) {
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
                ImageView imgView = (ImageView) findViewById(R.id.v_edit_product_image1);
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
