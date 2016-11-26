package com.cs442.group3.citysbest.Client;

/**
 * Created by Rash on 11/24/2016.
 */
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs442.group3.citysbest.Database.CategoryTable;
import com.cs442.group3.citysbest.Database.OfferTable;
import com.cs442.group3.citysbest.Database.Utils;
import com.cs442.group3.citysbest.R;

/**
 * Created by Rash on 11/24/2016.
 */

public class OfferCursorAdapter extends CursorAdapter {

    public OfferCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.offer_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // get the stuff from the cursor

        String offerName = cursor.getString(cursor.getColumnIndex(OfferTable.OFFER_NAME));

        TextView offerNameView = (TextView) view.findViewById(R.id.offername1);

        offerNameView.setText(offerName);

        String offerPrice = (cursor.getFloat(cursor.getColumnIndex(OfferTable.OFFER_OLDPRICE))- cursor.getFloat(cursor.getColumnIndex(OfferTable.OFFER_NEWPRICE)))+"";

        TextView offerPriceView = (TextView) view.findViewById(R.id.offerprice1);

        offerPriceView.setText("Savings "+offerPrice+" $");

        ImageView offerImageView = (ImageView) view.findViewById(R.id.offerimage1);

        byte[] image;
        image=cursor.getBlob(cursor.getColumnIndex(OfferTable.OFFER_IMAGE));
        offerImageView.setImageBitmap(Utils.getImage(image));

        //TextView phoneNo = (TextView) view.findViewById(R.id.store_miles);
        //phoneNo.setText(String.format("%.0f", Integer.parseInt(cursor.getString(cursor.getColumnIndex(StoreTable.STORE_CONTACT)))));
        //phoneNo.setText(cursor.getString(cursor.getColumnIndex(StoreTable.STORE_HOURS)));

    }


}
