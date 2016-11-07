package com.cs442.group3.citysbest.Vendor;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.cs442.group3.citysbest.Client.BaseActivity;
import com.cs442.group3.citysbest.Client.HomeScreen;
import com.cs442.group3.citysbest.Client.LoginActivity;
import com.cs442.group3.citysbest.Client.RegistrationActivity;
import com.cs442.group3.citysbest.R;

public class VendorBaseActivity extends AppCompatActivity {

    protected FrameLayout frameLayout1;

    /**
     * ListView to add navigation drawer item in it.
     * We have made it protected to access it in child class. We will just use it in child class to make item selected according to activity opened.
     */

    protected ListView mDrawerList1;

    /**
     * List item array for navigation drawer items.
     * */
    protected String[] listArray1 = { "Store Details", "Product Details", "Offers Details", "Manage Details", "Log Out" };

    /**
     * Static variable for selected item position. Which can be used in child activity to know which item is selected from the list.
     * */
    protected static int position1;

    /**
     *  This flag is used just to check that launcher activity is called first time
     *  so that we can open appropriate Activity on launch and make list item position selected accordingly.
     * */
    private static boolean isLaunch1 = true;

    /**
     *  Base layout node of this Activity.
     * */
    private DrawerLayout mDrawerLayout1;

    /**
     * Drawer listner class for drawer open, close etc.
     */
    private ActionBarDrawerToggle actionBarDrawerToggle1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_base);

        frameLayout1 = (FrameLayout)findViewById(R.id.content_frame1);
        mDrawerLayout1 = (DrawerLayout) findViewById(R.id.drawer_layout1);
        mDrawerList1 = (ListView) findViewById(R.id.left_drawer1);

        mDrawerList1.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_vendor_list_item, listArray1));
        mDrawerList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                openActivity(position);
            }
        });

        // enable ActionBar app icon to behave as action to toggle nav drawer

        // ActionBarDrawerToggle ties together the the proper interactions between the sliding drawer and the action bar app icon
        actionBarDrawerToggle1 = new ActionBarDrawerToggle(
                this,						/* host Activity */
                mDrawerLayout1, 				/* DrawerLayout object */
                     /* nav drawer image to replace 'Up' caret */
                R.string.app_name,       /* "open drawer" description for accessibility */
                R.string.app_name)      /* "close drawer" description for accessibility */
        {
            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(listArray1[position1]);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };

        mDrawerLayout1.setDrawerListener(actionBarDrawerToggle1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if(isLaunch1){

            isLaunch1 = false;
            openActivity(0);
        }
    }

    protected void openActivity(int position) {

        mDrawerLayout1.closeDrawer(mDrawerList1);
        VendorBaseActivity.position1 = position; //Setting currently selected position in this field so that it will be available in our child activities.

        switch (position) {
            case 0:
                startActivity(new Intent(this, VendorStoreDetails.class));
                break;
            case 1:
                startActivity(new Intent(this, VendorProductDetails.class));
                break;
            case 2:
                startActivity(new Intent(this, VendorOfferDetails.class));
                break;
            case 3:
                startActivity(new Intent(this, ManageAccountDetails.class));
                break;
            case 4:
                startActivity(new Intent(this, VendorLogout.class));
                break;

            default:
                break;
        }

        //Toast.makeText(this, "Selected Item Position::"+position, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (actionBarDrawerToggle1.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout1.isDrawerOpen(mDrawerList1);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /* We can override onBackPressed method to toggle navigation drawer*/
    @Override
    public void onBackPressed() {
        if(mDrawerLayout1.isDrawerOpen(mDrawerList1)){
            mDrawerLayout1.closeDrawer(mDrawerList1);
        }else {
            mDrawerLayout1.openDrawer(mDrawerList1);
        }
    }
}
