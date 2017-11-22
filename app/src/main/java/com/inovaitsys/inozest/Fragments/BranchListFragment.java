package com.inovaitsys.inozest.Fragments;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.inovaitsys.inozest.ImageResponse;
import com.inovaitsys.inozest.MainActivity;
import com.inovaitsys.inozest.R;
import com.inovaitsys.inozest.adapters.BranchAdapter;
import com.inovaitsys.inozest.adapters.RestaurantAdapter;
import com.inovaitsys.inozest.listeners.GridItemClickListener;
import com.inovaitsys.inozest.listeners.SnackBarClickListner;

import java.util.ArrayList;


public class BranchListFragment extends Fragment implements View.OnClickListener,SnackBarClickListner,GridItemClickListener {

    public static final String TAG = BranchListFragment.class.getSimpleName();

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DrawerLayout mDrawerLayout;
    private MaterialDialog barProgressDialog;
    private ArrayList<ImageResponse> branchList;
    private BranchAdapter mAdapter;

    public BranchListFragment() {
        // Required empty public constructor
    }


    public static BranchListFragment newInstance(DrawerLayout drawer,Toolbar toolbar){
        BranchListFragment fragment = new BranchListFragment();
        fragment.mDrawerLayout = drawer;
        fragment.toolbar = toolbar;

        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_branch_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(layoutManager);




        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .content(getString(R.string.processing))
                .progress(true, 0)
                .cancelable(false);
        barProgressDialog = builder.build();

//        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(Color.BLACK);


//        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.branches));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadUserPrefs();

        createBranchList();


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
                launchMainActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createBranchList() {


        branchList = new ArrayList<ImageResponse>();

        ImageResponse res1 = new ImageResponse();
        res1.setName("Branch A");
        res1.setUrl("https://static.pexels.com/photos/262918/pexels-photo-262918.jpeg");
        ImageResponse res2 = new ImageResponse();
        res2.setName("Branch B");
        res2.setUrl("https://static.pexels.com/photos/51115/restaurant-wine-glasses-served-51115.jpeg");
        ImageResponse res3 = new ImageResponse();
        res3.setName("Branch C");
        res3.setUrl("https://static.pexels.com/photos/299550/pexels-photo-299550.jpeg");

        branchList.add(res1);
        branchList.add(res2);
        branchList.add(res3);
        branchList.add(res1);
        branchList.add(res2);
        branchList.add(res3);
        branchList.add(res1);
        branchList.add(res2);
        branchList.add(res3);

        mAdapter = new BranchAdapter(getActivity(), branchList, BranchListFragment.this);

        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

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

                    launchMainActivity();

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
    public void onItemClick(Object object) {

    }

    @Override
    public void onItemClick(String id) {

        openBranchFragment();

    }

    @Override
    public void onItemClick(String id, String msgId) {

    }

    @Override
    public void onSnanckBarClickListner() {

    }

    public void launchMainActivity() {

        Intent mainActivity = new Intent(getActivity(), MainActivity.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainActivity);
        getActivity().overridePendingTransition(R.anim.enter_left, R.anim.exit_right);

    }

    public void openBranchFragment() {


        Fragment branchFragment = new BranchFragment().newInstance(mDrawerLayout,toolbar);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.main_layout_frsgment, branchFragment, BranchFragment.TAG);
        fragmentTransaction.addToBackStack(BranchFragment.TAG);
        fragmentTransaction.commit();
    }


}
