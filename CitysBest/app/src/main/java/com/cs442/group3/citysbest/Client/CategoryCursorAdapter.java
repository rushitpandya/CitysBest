package com.cs442.group3.citysbest.Client;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs442.group3.citysbest.Database.CategoryTable;
import com.cs442.group3.citysbest.Database.StoreTable;
import com.cs442.group3.citysbest.Database.Utils;
import com.cs442.group3.citysbest.R;

/**
 * Created by Rash on 11/23/2016.
 */

public class CategoryCursorAdapter extends CursorAdapter {

    public CategoryCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.category_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // get the stuff from the cursor

        String categoryName = cursor.getString(cursor.getColumnIndex(CategoryTable.CATEGORY_NAME));

        TextView categoryNameView = (TextView) view.findViewById(R.id.categoryname1);

        categoryNameView.setText(categoryName);

        ImageView categoryImageView = (ImageView) view.findViewById(R.id.categoryimage1);

        byte[] image;
        image=cursor.getBlob(cursor.getColumnIndex(CategoryTable.CATEGORY_IMAGE));
        categoryImageView.setImageBitmap(Utils.getImage(image));

        //TextView phoneNo = (TextView) view.findViewById(R.id.store_miles);
        //phoneNo.setText(String.format("%.0f", Integer.parseInt(cursor.getString(cursor.getColumnIndex(StoreTable.STORE_CONTACT)))));
        //phoneNo.setText(cursor.getString(cursor.getColumnIndex(StoreTable.STORE_HOURS)));

    }
}
