package com.cs442.group3.citysbest.Client;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs442.group3.citysbest.Database.StoreTable;
import com.cs442.group3.citysbest.Database.Utils;
import com.cs442.group3.citysbest.R;

/**
 * Created by mohsin on 11/13/16.
 */

public class StoreCursorAdapter extends CursorAdapter {

    public StoreCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.store_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // get the stuff from the cursor

        String storeName = cursor.getString(cursor.getColumnIndex(StoreTable.STORE_NAME));

        TextView storeNameView = (TextView) view.findViewById(R.id.store_name);

        storeNameView.setText(storeName);

        ImageView storeImageView = (ImageView) view.findViewById(R.id.storeimage);

        byte[] image;
        image=cursor.getBlob(cursor.getColumnIndex(StoreTable.STORE_IMAGE));
        storeImageView.setImageBitmap(Utils.getImage(image));

        TextView phoneNo = (TextView) view.findViewById(R.id.store_miles);
        //phoneNo.setText(String.format("%.0f", Integer.parseInt(cursor.getString(cursor.getColumnIndex(StoreTable.STORE_CONTACT)))));
        phoneNo.setText(cursor.getString(cursor.getColumnIndex(StoreTable.STORE_HOURS)));

    }
}
