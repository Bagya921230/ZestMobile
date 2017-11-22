package com.inovaitsys.inozest.Fragments;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inovaitsys.inozest.MainActivity;
import com.inovaitsys.inozest.R;
import com.inovaitsys.inozest.customview.ServiceCustomView;
import com.inovaitsys.inozest.listeners.GridItemClickListener;
import com.inovaitsys.inozest.listeners.SnackBarClickListner;

import java.util.ArrayList;
import java.util.List;

import static com.inovaitsys.inozest.R.id.map;


public class AddReviewFragment extends Fragment implements View.OnClickListener,SnackBarClickListner {

    public static final String TAG = AddReviewFragment.class.getSimpleName();

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DrawerLayout mDrawerLayout;
    private MaterialDialog barProgressDialog;
    private Button reviewBtn;
    private TextView ratingText;

    public AddReviewFragment() {
        // Required empty public constructor
    }


    public static AddReviewFragment newInstance(DrawerLayout drawer){
        AddReviewFragment fragment = new AddReviewFragment();
        fragment.mDrawerLayout = drawer;

        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_review, container, false);

        reviewBtn = (Button) view.findViewById(R.id.addReviewBtn);


        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .content(getString(R.string.processing))
                .progress(true, 0)
                .cancelable(false);
        barProgressDialog = builder.build();


        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        ratingText = (TextView) view.findViewById(R.id.ratingText);

        RatingBar rr = (RatingBar) view.findViewById(R.id.ratingBar1);
//        rr.setRating((float) 2);
        ratingText.setText((int) rr.getRating() + " stars");
        rr.setStepSize((float) 0.5);
        DrawableCompat.setTint(rr.getProgressDrawable(), ContextCompat.getColor(getActivity(), R.color.grey));
        rr.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                ratingText.setText(rating + " stars");
                LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();//#FFDF00
                layerDrawable.getDrawable(2).setColorFilter(ContextCompat.getColor(getActivity(), R.color.gold), PorterDuff.Mode.SRC_ATOP);

            }
        });


        loadUserPrefs();
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                launchMainActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    openBranchFragment();

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
//        userName = sp.getString(getString(R.string.user_name), "");
//        sessionId = sp.getString(getString(R.string.session_id),"");
//        isLoggedIn = sp.getBoolean(getString(R.string.is_logged_in),false);
    }


    @Override
    public void onSnanckBarClickListner() {

    }


    public void openBranchFragment() {


        Fragment branchFragment = new BranchFragment().newInstance(mDrawerLayout,toolbar);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_right);
        fragmentTransaction.replace(R.id.main_layout_frsgment, branchFragment, BranchFragment.TAG);
        fragmentTransaction.addToBackStack(BranchFragment.TAG);
        fragmentTransaction.commit();
    }
}
