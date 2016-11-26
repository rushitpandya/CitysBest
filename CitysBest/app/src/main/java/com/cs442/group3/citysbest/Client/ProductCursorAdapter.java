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
import com.cs442.group3.citysbest.Database.ProductTable;
import com.cs442.group3.citysbest.Database.Utils;
import com.cs442.group3.citysbest.R;

/**
 * Created by Rash on 11/24/2016.
 */

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.product_list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // get the stuff from the cursor

        String productName = cursor.getString(cursor.getColumnIndex(ProductTable.PRODUCT_NAME));
        
        TextView productNameView = (TextView) view.findViewById(R.id.productname1);

        productNameView.setText(productName);

        String productPrice = cursor.getString(cursor.getColumnIndex(ProductTable.PRODUCT_PRICE));

        TextView productPriceView = (TextView) view.findViewById(R.id.productprice1);

        productPriceView.setText(productPrice+" $");

        ImageView productImageView = (ImageView) view.findViewById(R.id.productimage1);

        byte[] image;
        image=cursor.getBlob(cursor.getColumnIndex(ProductTable.PRODUCT_IMAGE));
        productImageView.setImageBitmap(Utils.getImage(image));

        //TextView phoneNo = (TextView) view.findViewById(R.id.store_miles);
        //phoneNo.setText(String.format("%.0f", Integer.parseInt(cursor.getString(cursor.getColumnIndex(StoreTable.STORE_CONTACT)))));
        //phoneNo.setText(cursor.getString(cursor.getColumnIndex(StoreTable.STORE_HOURS)));

    }


}

