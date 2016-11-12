package com.cs442.group3.citysbest.Vendor;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cs442.group3.citysbest.Database.OfferTable;
import com.cs442.group3.citysbest.Database.ProductTable;
import com.cs442.group3.citysbest.Database.SessionManager;
import com.cs442.group3.citysbest.Database.Utils;
import com.cs442.group3.citysbest.R;

import java.util.HashMap;

public class VendorOfferDetails extends VendorBaseActivity {
    OfferTable offertable;
    int s_id;
    byte[] image1,image2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_vendor_offer_details, frameLayout1);
        SessionManager sm=new SessionManager(this);
        HashMap<String,String> storedetails=sm.getUserDetails();
        s_id=Integer.parseInt(storedetails.get(SessionManager.KEY_STORE_ID));

        offertable=new OfferTable(this);
        offertable.open();

        /*Cursor cursor1=offertable.getProducts();
        Log.d("count:",cursor1.getCount()+"");
*/

        Cursor cursor=offertable.getAllOffers(s_id);
        cursor.moveToFirst();

        int Id,pid;

        TableLayout table=(TableLayout)findViewById(R.id.producttbl1);
        if(cursor != null) {
            while(cursor.isAfterLast()==false) {
                pid = cursor.getInt(cursor
                        .getColumnIndex(OfferTable.OFFER_ID));
                Id=pid;
                Log.d("PID",pid+"");
                String offername = cursor.getString(cursor
                        .getColumnIndex(OfferTable.OFFER_NAME));
                String offeroldprice = (cursor.getDouble(cursor
                        .getColumnIndex(OfferTable.OFFER_OLDPRICE)))+"";
                String offernewprice = (cursor.getDouble(cursor
                        .getColumnIndex(OfferTable.OFFER_NEWPRICE)))+"";
                String offerdate = (cursor.getString(cursor
                        .getColumnIndex(OfferTable.OFFER_DATE)));
                int index=Id;

                Log.d("name",offername);
                image1=cursor.getBlob(cursor.getColumnIndex(OfferTable.OFFER_IMAGE));

                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                TableRow row = new TableRow(this);
                row.setLayoutParams(lp);

                TableRow.LayoutParams llp1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
                LinearLayout ll1 = new LinearLayout(this);
                ll1.setOrientation(LinearLayout.VERTICAL);
                ll1.setLayoutParams(llp1);

                LinearLayout.LayoutParams imgparams1 = new LinearLayout.LayoutParams(250, 250);
                ImageView o_img1 = new ImageView(this);
                imgparams1.gravity = Gravity.CENTER_HORIZONTAL;
                o_img1.setLayoutParams(imgparams1);
                o_img1.setImageBitmap(Utils.getImage(image1));

                LinearLayout.LayoutParams nameparams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView name = new TextView(this);
                name.setText(offername);
                nameparams1.gravity = Gravity.CENTER_HORIZONTAL;
                name.setLayoutParams(nameparams1);

                LinearLayout.LayoutParams priceparams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView price = new TextView(this);
                price.setText(offernewprice+" $. You save "+(cursor.getDouble(cursor
                        .getColumnIndex(OfferTable.OFFER_OLDPRICE))-cursor.getDouble(cursor
                        .getColumnIndex(OfferTable.OFFER_NEWPRICE)))+" $");
                priceparams1.gravity = Gravity.CENTER_HORIZONTAL;
                price.setLayoutParams(priceparams1);

                LinearLayout.LayoutParams dateparams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView date = new TextView(this);
                date.setText("Expires on "+offerdate);
                dateparams1.gravity = Gravity.CENTER_HORIZONTAL;
                date.setLayoutParams(dateparams1);

                LinearLayout.LayoutParams cllp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout cll1 = new LinearLayout(this);
                cllp1.gravity=Gravity.CENTER_HORIZONTAL;
                cll1.setLayoutParams(cllp1);
                cll1.setOrientation(LinearLayout.HORIZONTAL);

                Button edit1 = new Button(this);
                edit1.setText("Edit");
                edit1.setId(index);
                edit1.setOnClickListener(EditClickListener);
                Log.d("Edit Id:",edit1.getId()+"");
                LinearLayout.LayoutParams editbuttonllp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                edit1.setLayoutParams(editbuttonllp1);
                //edit1.setId();
                Button delete1 = new Button(this);
                delete1.setText("Delete");
                delete1.setId(index);
                delete1.setOnClickListener(DeleteClickListener);
                Log.d("Delete Id:",delete1.getId()+"");
                LinearLayout.LayoutParams deletebuttonllp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                delete1.setLayoutParams(deletebuttonllp1);

                cll1.addView(edit1);
                cll1.addView(delete1);

                ll1.addView(o_img1);
                ll1.addView(name);
                ll1.addView(price);
                ll1.addView(date);
                ll1.addView(cll1);

                row.addView(ll1);


                cursor.moveToNext();
                if( cursor.isAfterLast()==false) {
                    Log.d("Here","ads");

                    int oid = cursor.getInt(cursor
                            .getColumnIndex(OfferTable.OFFER_ID));
                    Id=oid;
                    int index1=Id;
                    Log.d("PID",oid+"");
                    String offername2 = cursor.getString(cursor
                            .getColumnIndex(OfferTable.OFFER_NAME));
                    String offeroldprice2 = (cursor.getDouble(cursor
                            .getColumnIndex(OfferTable.OFFER_OLDPRICE)))+"";
                    String offernewprice2 = (cursor.getDouble(cursor
                            .getColumnIndex(OfferTable.OFFER_NEWPRICE)))+"";
                    String offerdate2 = (cursor.getString(cursor
                            .getColumnIndex(OfferTable.OFFER_DATE)));

                    Log.d("name",offername);
                    image2=cursor.getBlob(cursor.getColumnIndex(OfferTable.OFFER_IMAGE));



                    TableRow.LayoutParams llp2 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f);
                    LinearLayout ll2 = new LinearLayout(this);
                    ll2.setLayoutParams(llp2);
                    ll2.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout.LayoutParams imgparams2 = new LinearLayout.LayoutParams(250, 250);
                    ImageView o_img2 = new ImageView(this);
                    imgparams2.gravity = Gravity.CENTER_HORIZONTAL;
                    o_img2.setLayoutParams(imgparams2);
                    //o_img2.setImageResource(R.drawable.ic_menu_camera);
                    o_img2.setImageBitmap(Utils.getImage(image2));

                    LinearLayout.LayoutParams nameparams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView name2 = new TextView(this);
                    name2.setText(offername2);
                    nameparams2.gravity = Gravity.CENTER_HORIZONTAL;
                    name2.setLayoutParams(nameparams2);

                    LinearLayout.LayoutParams priceparams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView price2 = new TextView(this);
                    price2.setText(offernewprice2+" $. You save "+(cursor.getDouble(cursor
                            .getColumnIndex(OfferTable.OFFER_OLDPRICE))-cursor.getDouble(cursor
                            .getColumnIndex(OfferTable.OFFER_NEWPRICE)))+" $");

                    priceparams2.gravity = Gravity.CENTER_HORIZONTAL;
                    price2.setLayoutParams(priceparams2);

                    LinearLayout.LayoutParams dateparams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView date2 = new TextView(this);
                    date2.setText("Expires on "+offerdate2);
                    dateparams2.gravity = Gravity.CENTER_HORIZONTAL;
                    date2.setLayoutParams(dateparams2);

                    LinearLayout.LayoutParams cllp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    LinearLayout cll2 = new LinearLayout(this);
                    cllp2.gravity=Gravity.CENTER_HORIZONTAL;
                    cll2.setLayoutParams(cllp2);
                    cll2.setOrientation(LinearLayout.HORIZONTAL);

                    Button edit2 = new Button(this);
                    edit2.setText("Edit");
                    edit2.setId(index1);
                    edit2.setOnClickListener(EditClickListener);
                    Log.d("Edit Id:",edit2.getId()+"");
                    LinearLayout.LayoutParams editbuttonllp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                    edit2.setLayoutParams(editbuttonllp2);
                    //edit1.setId();
                    Button delete2 = new Button(this);
                    delete2.setText("Delete");
                    delete2.setId(index1);
                    delete2.setOnClickListener(DeleteClickListener);
                    Log.d("Delete Id:",delete2.getId()+"");
                    LinearLayout.LayoutParams deletebuttonllp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                    delete2.setLayoutParams(deletebuttonllp2);

                    cll2.addView(edit2);
                    cll2.addView(delete2);

                    ll2.addView(o_img2);
                    ll2.addView(name2);
                    ll2.addView(price2);
                    ll2.addView(date2);
                    ll2.addView(cll2);


                    row.addView(ll2);
                    cursor.moveToNext();
                }

                table.addView(row);
                Log.d("lase","snk");
             /*if(!cursor.isLast())
             {

                 Log.d("lase1","snk");
             }*/

            }
        }

    }

    private View.OnClickListener EditClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(getApplicationContext(),VendorEditOffer.class);
            i.putExtra("id",v.getId());
            startActivity(i);

        }
    };


    private View.OnClickListener DeleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            OfferTable offertable=new OfferTable(getApplicationContext());
            offertable.open();
            offertable.deleteOffer(v.getId());
            Intent in=new Intent(getApplicationContext(),VendorOfferDetails.class);
            startActivity(in);

        }
    };
    
    public void AddOffer(View v)
    {
        Intent i=new Intent(this,VendorAddOffer.class);
        startActivity(i);
    }

}
