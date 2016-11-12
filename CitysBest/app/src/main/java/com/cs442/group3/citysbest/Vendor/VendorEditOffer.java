package com.cs442.group3.citysbest.Vendor;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442.group3.citysbest.Database.OfferTable;
import com.cs442.group3.citysbest.Database.ProductTable;
import com.cs442.group3.citysbest.Database.SessionManager;
import com.cs442.group3.citysbest.Database.Utils;
import com.cs442.group3.citysbest.R;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VendorEditOffer extends VendorBaseActivity {

    byte[] inputData;
    @InjectView(R.id.v_edit_offer_name)
    EditText o_name;
    @InjectView(R.id.v_edit_offer_newprice)
    EditText o_newprice;
    @InjectView(R.id.v_edit_offer_oldprice)
    EditText o_oldprice;
    @InjectView(R.id.dateview1)
    TextView _dateView;

    @InjectView(R.id.v_add_offer_image1)
    ImageView _imgView;
    @InjectView(R.id.o_add_image_upload1)
    Button _imgButton;
    @InjectView(R.id.v_edit_new_offer_btn)
    Button add;
    int s_id;
    private static int RESULT_LOAD_IMG1 = 1;
    Uri selectedImage;
    OfferTable offertable;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    Date date;
    int o_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_vendor_edit_offer, frameLayout1);
        ButterKnife.inject(this);
        SessionManager sm=new SessionManager(this);
        HashMap<String,String> storedetails=sm.getUserDetails();
        s_id=Integer.parseInt(storedetails.get(SessionManager.KEY_STORE_ID));
        Intent i=getIntent();
        o_id=i.getIntExtra("id",0);
        offertable=new OfferTable(this);
        offertable.open();
        Cursor c=offertable.getOffer(o_id);
        c.moveToFirst();
        offertable.close();
        o_name.setText(c.getString(c.getColumnIndex(OfferTable.OFFER_NAME)));
        o_oldprice.setText(c.getString(c.getColumnIndex(OfferTable.OFFER_OLDPRICE)));
        o_newprice.setText(c.getString(c.getColumnIndex(OfferTable.OFFER_NEWPRICE)));
        _dateView.setText(c.getString(c.getColumnIndex(OfferTable.OFFER_DATE)));
        inputData=c.getBlob(c.getColumnIndex(OfferTable.OFFER_IMAGE));
        _imgView.setImageBitmap(Utils.getImage(c.getBlob(c.getColumnIndex(OfferTable.OFFER_IMAGE))));
        c.close();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {

                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        _dateView.setText(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
        Calendar cal = Calendar.getInstance();
        cal.set(year, month , day);
        date=cal.getTime();
    }


    public void EditOffer(View v)
    {
        if (!validate()) {
            onSignupFailed();
            return;
        }
        String s_o_name = o_name.getText().toString();
        String s_o_newprice=o_newprice.getText().toString();
        String s_o_oldprice=o_oldprice.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String date1 = sdf.format(date);
        Log.d("Date",date1);
        InputStream iStream = null;
        try {
            iStream = getContentResolver().openInputStream(selectedImage);
            inputData = Utils.getBytes(iStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        add.setEnabled(false);
        offertable=new OfferTable(getApplicationContext());
        offertable.open();
        long c =offertable.updateOfferDetails(s_o_name,s_o_newprice,s_o_oldprice,date1,inputData,s_id,o_id);
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
        Toast.makeText(this, "Offer Details Added", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, null);
        Intent i=new Intent(this,VendorOfferDetails.class);
        startActivity(i);
    }

    public void onSignupFailed() {
        add.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String s_o_name = o_name.getText().toString();
        String s_o_newprice=o_newprice.getText().toString();
        String s_o_oldprice=o_oldprice.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf.format(date);

        if (s_o_name.isEmpty()) {
            o_name.setError("Offer Name is required");
            valid = false;
        } else {
            o_name.setError(null);
        }

        if (s_o_newprice.isEmpty()) {
            o_newprice.setError("Offer Old Price is required");
            valid = false;
        } else {
            o_newprice.setError(null);
        }

        if (s_o_oldprice.isEmpty()) {
            o_oldprice.setError("Offer New Price is required");
            valid = false;
        } else {
            o_oldprice.setError(null);
        }

        if (inputData == null ) {
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
                //ImageView imgView = (ImageView) findViewById(R.id.v_add_product_image);
                _imgView.setImageURI(selectedImage);
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
