package com.cs442.group3.citysbest.Client;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cs442.group3.citysbest.R;

public class HomeScreen extends BaseActivity   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home_screen, frameLayout);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        AndroidImageAdapter adapterView = new AndroidImageAdapter(this);
        mViewPager.setAdapter(adapterView);
        ListView yourListView = (ListView) findViewById(R.id.client_category);
        yourListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getApplicationContext(),StoreList.class);
                startActivity(i);
            }
        });

    }
}
