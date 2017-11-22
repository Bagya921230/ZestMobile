package com.inovaitsys.inozest.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.inovaitsys.inozest.LoginActivity;
import com.inovaitsys.inozest.R;
import com.inovaitsys.inozest.listeners.SnackBarClickListner;


public class RegistrationFragment extends Fragment implements View.OnClickListener,SnackBarClickListner {

    public static final String TAG = RegistrationFragment.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private MaterialDialog barProgressDialog;


    public RegistrationFragment() {
        // Required empty public constructor
    }


    public static RegistrationFragment newInstance(DrawerLayout drawer){
        RegistrationFragment fragment = new RegistrationFragment();
        fragment.mDrawerLayout = drawer;

        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .content(getString(R.string.processing))
                .progress(true, 0)
                .cancelable(false);
        barProgressDialog = builder.build();

        TextInputEditText password = (TextInputEditText) view.findViewById(R.id.passwordInputEditText);
        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());


//        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


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

                    Intent loginIntent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(loginIntent);
                    getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.enter_left, R.anim.exit_right);

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


}
