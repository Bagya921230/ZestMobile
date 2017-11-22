package com.inovaitsys.inozest.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.inovaitsys.inozest.MainActivity;
import com.inovaitsys.inozest.R;
import com.inovaitsys.inozest.customview.ServiceCustomView;
import com.inovaitsys.inozest.helper.GMapV2Direction;
import com.inovaitsys.inozest.listeners.GridItemClickListener;
import com.inovaitsys.inozest.listeners.SnackBarClickListner;

import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.techery.properratingbar.ProperRatingBar;
import io.techery.properratingbar.RatingListener;

import static android.content.Context.LOCATION_SERVICE;
import static com.inovaitsys.inozest.R.id.addReviewBtn;
import static com.inovaitsys.inozest.R.id.inputMainLayout;
import static com.inovaitsys.inozest.R.id.map;


public class BranchFragment extends Fragment implements View.OnClickListener, SnackBarClickListner, GridItemClickListener, OnMapReadyCallback, GoogleMap.OnMarkerDragListener, LocationSource.OnLocationChangedListener {

    public static final String TAG = BranchFragment.class.getSimpleName();

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DrawerLayout mDrawerLayout;
    private MaterialDialog barProgressDialog;
    private GoogleMap mMap;
    private LinearLayout listContainer, listContainer1;
    private List<String> serviceStringList, daysStringList;
    private Button reviewBtn;
    private LinearLayout mainContainerDialog;
    private ProperRatingBar starRatingBar;
    private TextView ratingText;
    private Address address;
    private LatLng latLng;
    private TextView addressText;
    private TextView durationText;
    private LatLng MyLatLng;
    private LocationManager locationManager;
    private View TODO;
    private Polyline polylin;
    private String locationProvider;
    private boolean permissionGranted = false;

    public BranchFragment() {
        // Required empty public constructor
    }


    public static BranchFragment newInstance(DrawerLayout drawer,Toolbar toolbar) {
        BranchFragment fragment = new BranchFragment();
        fragment.mDrawerLayout = drawer;
        fragment.toolbar = toolbar;

        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_branch, container, false);

        listContainer = (LinearLayout) view.findViewById(R.id.listContainer);
        listContainer1 = (LinearLayout) view.findViewById(R.id.listContainer1);
        reviewBtn = (Button) view.findViewById(R.id.addReviewBtn);
        addressText = (TextView) view.findViewById(R.id.address);
//        durationText = (TextView) view.findViewById(R.id.duration);
        reviewBtn.setOnClickListener(this);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .content(getString(R.string.processing))
                .progress(true, 0)
                .cancelable(false);
        barProgressDialog = builder.build();


//        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitle("Thimbirigasyaya Branch");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBranchListFragment();
            }
        });

        loadUserPrefs();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);

        serviceStringList = new ArrayList<String>();
        serviceStringList.add("Dine in");
        serviceStringList.add("Take Away");
        serviceStringList.add("Online Ordering");
        serviceStringList.add("Delivery");
        serviceStringList.add("Dine in");
        serviceStringList.add("Take Away");
        serviceStringList.add("Online Ordering");
        serviceStringList.add("Delivery");

        createServices(serviceStringList);

        daysStringList = new ArrayList<String>();
        daysStringList.add("Monday");
        daysStringList.add("Tuesday");
        daysStringList.add("Wednesday");
        daysStringList.add("Thursday");
        daysStringList.add("Friday");
        daysStringList.add("Satursday");
        createDays(daysStringList);

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        locationProvider = locationManager.getBestProvider(criteria, true);

        return view;


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        super.onPrepareOptionsMenu(menu);
    }

    private void createServices(List<String> serviceList) {


        for (final String service : serviceList) {

            final ServiceCustomView serviceCustomView = new ServiceCustomView(getActivity());
            serviceCustomView.setServiceName(service);


            listContainer.addView(serviceCustomView);

        }

    }

    private void createDays(List<String> dayList) {


        for (final String service : dayList) {

            final ServiceCustomView serviceCustomView = new ServiceCustomView(getActivity());
            serviceCustomView.setServiceName(service);


            listContainer1.addView(serviceCustomView);

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.addReviewBtn:
//                Toast.makeText(getActivity(), "add review clicked", Toast.LENGTH_LONG).show();
                openAddReviewFragment();
//                showInputDialog();

                break;

        }

    }

    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    openBranchListFragment();

                    return true;

                }

                return false;
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
        snackBarTag = 1;
        if (Utility.checkConnection(getActivity())) {

            new getMessages().execute();

        } else {

            Utility.showNoInternetSnakBar(mainLayout, getActivity(), this);
        }
        */
    }


    private void loadUserPrefs() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        permissionGranted = sp.getBoolean(getString(R.string.have_permission),false);
    }

    @Override
    public void onItemClick(Object object) {

    }

    @Override
    public void onItemClick(String id) {


    }

    @Override
    public void onItemClick(String id, String msgId) {

    }

    @Override
    public void onSnanckBarClickListner() {

    }


    public void openBranchListFragment() {


        Fragment branchListFragment = new BranchListFragment().newInstance(mDrawerLayout,toolbar);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_right);
        fragmentTransaction.replace(R.id.main_layout_frsgment, branchListFragment, BranchListFragment.TAG);
        fragmentTransaction.addToBackStack(BranchListFragment.TAG);
        fragmentTransaction.commit();
    }


    public void openAddReviewFragment() {


        Fragment addReviewFragment = new AddReviewFragment().newInstance(mDrawerLayout);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.reviewLayout, addReviewFragment, AddReviewFragment.TAG);
        fragmentTransaction.addToBackStack(AddReviewFragment.TAG);
        fragmentTransaction.commit();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        latLng = new LatLng(6.8903927, 79.8677872);
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Delivery Radius:2km")
                .draggable(false)
        );


        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        mMap.animateCamera(zoom);



        Geocoder geoCoder = new Geocoder(getActivity(), Locale.ENGLISH);
        try {
            boolean x= geoCoder.isPresent();
            address = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
            String addressString = address.getAddressLine(0);
            addressString += ", " + address.getAddressLine(1);
            addressText.setText("Branch Address: " + addressString);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 23) {
            if(permissionGranted==true){
                mMap.setMyLocationEnabled(true);
                Location myLocation = locationManager.getLastKnownLocation(locationProvider);
                if (myLocation != null) {
                    MyLatLng = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
                }
            }
        }else{
            mMap.setMyLocationEnabled(true);
            Location myLocation = locationManager.getLastKnownLocation(locationProvider);
            if (myLocation != null) {
                MyLatLng = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());
            }

        }




/*
        GMapV2Direction md = new GMapV2Direction();
        Document doc = md.getDocument(MyLatLng, latLng,
                GMapV2Direction.MODE_DRIVING);

        ArrayList<LatLng> directionPoint = md.getDirection(doc);
        PolylineOptions rectLine = new PolylineOptions().width(3).color(
                Color.RED);

        for (int i = 0; i < directionPoint.size(); i++) {
            rectLine.add(directionPoint.get(i));
        }
        String duration = md.getDurationText(doc);
        if(polylin!=null){
            polylin.remove();
        }
//        polylin = mMap.addPolyline(rectLine);
        durationText.setText(duration);


        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(6.8903927, 79.8677872))
                .strokeColor(Color.TRANSPARENT)
                .radius(20)
                .fillColor(R.color.transparent_red));

        mMap.setOnMarkerDragListener(this);
        */

    }


    @Override
    public void onMarkerDragStart(Marker marker) {

        if (polylin != null) {
            polylin.remove();
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {

        addressText.setText("Loading Address...");


    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

        latLng = marker.getPosition();
        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            address = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
            String addressString = address.getAddressLine(0);
            addressString += ", " + address.getAddressLine(1);
            addressText.setText(addressString);

            Log.d("get address pinpoint", "drag end: " + addressString + " shown:" + marker.isVisible());
        } catch (IOException e) {
            e.printStackTrace();
        }

        GMapV2Direction md = new GMapV2Direction();
        Document doc = md.getDocument(marker.getPosition(), MyLatLng,
                GMapV2Direction.MODE_DRIVING);

        ArrayList<LatLng> directionPoint = md.getDirection(doc);
        PolylineOptions rectLine = new PolylineOptions().width(3).color(
                Color.RED);

        for (int i = 0; i < directionPoint.size(); i++) {
            rectLine.add(directionPoint.get(i));
        }
        String duration = md.getDurationText(doc);

//        polylin = mMap.addPolyline(rectLine);
        durationText.setText(duration);

    }

    @Override
    public void onLocationChanged(Location location) {

        MyLatLng = new LatLng(location.getLatitude(),location.getLongitude());

    }


}
