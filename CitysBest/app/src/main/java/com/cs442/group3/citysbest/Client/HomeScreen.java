package com.cs442.group3.citysbest.Client;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cs442.group3.citysbest.Database.CategoryTable;

import com.cs442.group3.citysbest.Database.StoreTable;
import com.cs442.group3.citysbest.R;

import java.util.List;

public class HomeScreen extends BaseActivity   {

    CategoryTable categoryTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home_screen, frameLayout);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        AndroidImageAdapter adapterView = new AndroidImageAdapter(this);
        mViewPager.setAdapter(adapterView);

        categoryTable = new CategoryTable(getApplicationContext());
        categoryTable.open();
      /*  if(categoryTable.CheckDb()  < 1)
        //{
            byte[] food="[B@4554f22".getBytes();
            byte[] insurance="[B@509f4a5".getBytes();
            byte[] car="[B@a65a07a".getBytes();
            byte[] clothing="[B@b484c88".getBytes();
            byte[] electronic="[B@c86b02b".getBytes();
            byte[] shoe="[B@e4a3621".getBytes();
            byte[] coffee="[B@9d4a46".getBytes();

            categoryTable.AddNew("Food",food);
            categoryTable.AddNew("Clothes",clothing);
            categoryTable.AddNew("Shoes",shoe);
            categoryTable.AddNew("Coffee",coffee);
            categoryTable.AddNew("Electronics",electronic);
            categoryTable.AddNew("Cars & Repairing",car);
            categoryTable.AddNew("Insurance",insurance);
        }*/

        Cursor cursor = categoryTable.getAllCategoriesCursor();
        cursor.moveToFirst();
        ListView yourListView = (ListView) findViewById(R.id.client_category);

        CategoryCursorAdapter cursorAdapter = new CategoryCursorAdapter(this, cursor, 0);
        yourListView.setAdapter(cursorAdapter);
        yourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
                // categoryTable.open();
                Intent i = new Intent(getApplicationContext(),StoreList.class);
                i.putExtra("c_id", position+1);
                startActivity(i);
                //Log.d("CATEGORY-CHECK", categoryTable.getCategory(position+1));
                //startActivity(i);
            }
        });

    }
}
