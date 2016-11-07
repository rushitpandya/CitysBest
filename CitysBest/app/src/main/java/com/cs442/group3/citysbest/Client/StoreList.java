package com.cs442.group3.citysbest.Client;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cs442.group3.citysbest.R;

import java.util.ArrayList;
import java.util.List;

public class StoreList extends BaseActivity {

    protected List<String> tempList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_store_list, frameLayout);
        tempList=new ArrayList<String>();
        tempList.add("Store1");
        tempList.add("Store2");
        tempList.add("Store3");
        tempList.add("Store4");
        ListView yourListView = (ListView) findViewById(R.id.store_list);
        ListAdapter customAdapter = new ListAdapter(this, R.layout.store_list_item, tempList);
        yourListView.setAdapter(customAdapter);

        yourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getApplicationContext(),StoreDetails.class);
                startActivity(i);
            }
        });
    }
    class ListAdapter extends ArrayAdapter<String> {

        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            // TODO Auto-generated constructor stub
        }

        private List<String> items;

        public ListAdapter(Context context, int resource, List<String> items) {

            super(context, resource, items);

            this.items = items;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {

                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.store_list_item, null);

            }
                TextView tt = (TextView) v.findViewById(R.id.store_name);
                if (tt != null) {
                    tt.setText(items.get(position));
                }
            return v;

        }
    }

}
