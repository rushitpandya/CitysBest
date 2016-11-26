package com.cs442.group3.citysbest.Client;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cs442.group3.citysbest.Database.StoreTable;
import com.cs442.group3.citysbest.R;

import java.util.ArrayList;
import java.util.List;

public class StoreList extends BaseActivity {

    protected List<String> tempList ;
    private CursorAdapter myAdapter;
    StoreTable storeTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_store_list, frameLayout);
        storeTable = new StoreTable(getApplicationContext());
        storeTable.open();

        Intent resultant = getIntent();
        final int c_id = resultant.getIntExtra("c_id", 400);

        // Getting the Cursor from the getStoresFromCategory method I created
        Cursor myStoresCursor = storeTable.getStoresFromCategoryID(c_id);
        myAdapter = new StoreCursorAdapter(this, myStoresCursor, 0);

        ListView yourListView = (ListView) findViewById(R.id.store_list);
        yourListView.setAdapter(myAdapter);

        yourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getApplicationContext(),StoreDetails.class);
                i.putExtra("s_id", Integer.parseInt(id+""));
                i.putExtra("c_id", c_id);
                startActivity(i);
                //Log.d("Store-Test", storeTable.getStore(position+1));
            }
        });
    }


}
