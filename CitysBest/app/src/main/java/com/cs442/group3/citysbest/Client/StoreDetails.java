package com.cs442.group3.citysbest.Client;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.cs442.group3.citysbest.Database.CategoryTable;
import com.cs442.group3.citysbest.Database.StoreTable;
import com.cs442.group3.citysbest.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class StoreDetails extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    StoreTable storeTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_store_details, frameLayout);

        storeTable = new StoreTable(getApplicationContext());
        storeTable.open();

        Intent receiver = getIntent();
        int s_id = receiver.getIntExtra("s_id", 400);
        int c_id = receiver.getIntExtra("c_id", 400);
        TextView storeName = (TextView) findViewById(R.id.store_name1);
        storeName.setText(storeTable.getStoreName(s_id));
       // storeTable.open();
        Log.d("name",storeTable.getStoreName(s_id));
        Log.d("id",s_id+"");
        TextView storeContact = (TextView) findViewById(R.id.store_contact_no1);
        storeContact.setText(storeTable.getStoreContact(s_id));
       // storeTable.open();

        TextView storeAddress = (TextView) findViewById(R.id.store_address1);
        storeAddress.setText(storeTable.getStoreAddress(s_id));
        //storeTable.open();

        TextView storeTiming = (TextView) findViewById(R.id.store_timings1);
        storeTiming.setText(storeTable.getStoreTime(s_id));

        TextView cname = (TextView) findViewById(R.id.categoryname1);
        CategoryTable categorytable=new CategoryTable(this);
        categorytable.open();
        cname.setText(categorytable.getCategory(c_id));
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProductFragment(), "Products");
        adapter.addFragment(new OfferFragment(), "Offers");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
