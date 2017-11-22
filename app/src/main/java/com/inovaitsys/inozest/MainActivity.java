package com.inovaitsys.inozest;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.inovaitsys.inozest.Fragments.BranchListFragment;
import com.inovaitsys.inozest.adapters.RestaurantAdapter;
import com.inovaitsys.inozest.listeners.GridItemClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GridItemClickListener {

    private Toolbar toolbar;
    private AppBarLayout appbar;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private RestaurantAdapter mAdapter;
    private ArrayList<ImageResponse> restaurantList;
    private RelativeLayout page_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appbar = (AppBarLayout)findViewById(R.id.main_app_bar);
        page_container = (RelativeLayout) findViewById(R.id.page_container);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);

        createRestaurantList();

        

        initNavigationDrawer();

        if (Build.VERSION.SDK_INT >= 23) {
            //do your check here

            permissions();
        }



    }

    public void permissions() {

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        } else {
            Log.e("DB", "PERMISSION GRANTED");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1: {


                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    storePreferences(true);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void createRestaurantList() {


        restaurantList = new ArrayList<ImageResponse>();

        ImageResponse res1 = new ImageResponse();
        res1.setName("Sweet foods");
        res1.setUrl("https://d3ccf09m20lfo4.cloudfront.net/store/1b513f5f93201008a50551ed5a33ea9bd2719d0fc3df12c4ba160746723d");
        ImageResponse res2 = new ImageResponse();
        res2.setName("Perera n Sons");
        res2.setUrl("https://worldfoodchampionships.com/images/world-food-championships-fb-icon.png");
        ImageResponse res3 = new ImageResponse();
        res3.setName("Dinemore");
        res3.setUrl("https://s-media-cache-ak0.pinimg.com/736x/ae/cf/ae/aecfae6a710852406e4c335616f67a7d--chipotle-mexican-grill-chipotle-menu.jpg");

        restaurantList.add(res1);
        restaurantList.add(res2);
        restaurantList.add(res3);
        restaurantList.add(res1);
        restaurantList.add(res2);
        restaurantList.add(res3);
        restaurantList.add(res1);
        restaurantList.add(res2);
        restaurantList.add(res3);

        mAdapter = new RestaurantAdapter(MainActivity.this, restaurantList, MainActivity.this);

        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

    }


    public void initNavigationDrawer() {



        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        Menu menu = (Menu) navigationView.getMenu();



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        if(getApplicationContext()==MainActivity.this){

                            drawerLayout.closeDrawers();
                        }else{
                            Intent mainIntent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(mainIntent);
                        }
                        break;
                    case R.id.nav_logout:

                        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(loginIntent);
                        break;

                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);



        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                navigationView.bringToFront();
                drawerLayout.requestLayout();
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onItemClick(Object object) {

    }

    @Override
    public void onItemClick(String id) {
        openBranchListFragment();
    }

    @Override
    public void onItemClick(String id, String msgId) {

    }

    public void openBranchListFragment() {


        Fragment branchListFragment = new BranchListFragment().newInstance(drawerLayout,toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.page_container, branchListFragment, BranchListFragment.TAG);
        fragmentTransaction.addToBackStack(BranchListFragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }


    private void storePreferences(boolean havePermission) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(getString(R.string.have_permission), havePermission);
        editor.apply();

        //saveSessionID(sessionId);
    }
}
