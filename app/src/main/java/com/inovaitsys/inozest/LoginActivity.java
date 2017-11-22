package com.inovaitsys.inozest;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.inovaitsys.inozest.Fragments.BranchFragment;
import com.inovaitsys.inozest.Fragments.ForgotPasswordFragment;
import com.inovaitsys.inozest.Fragments.RegistrationFragment;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginBtn;
    private TextView signupText;
    private TextView forgotPassText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        signupText = (TextView) findViewById(R.id.signup);
        forgotPassText = (TextView) findViewById(R.id.forgotPasswordTextView);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        loginBtn.setOnClickListener(this);
        signupText.setOnClickListener(this);
        forgotPassText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.loginBtn:

                launchMainActivity();

                break;
            case R.id.signup:
                openRegistrationFragment();
                break;

            case R.id.forgotPasswordTextView:
                openForgotPasswordFragment();
        }

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
//        super.onBackPressed();
    }

    private void openForgotPasswordFragment() {


        Fragment forgotPasswordFragment = new ForgotPasswordFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.main_layout_frsgment, forgotPasswordFragment, ForgotPasswordFragment.TAG);
        fragmentTransaction.addToBackStack(ForgotPasswordFragment.TAG);
        fragmentTransaction.commit();
    }

    private void openRegistrationFragment() {


        Fragment registrationFragment = new RegistrationFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.main_layout_frsgment, registrationFragment, RegistrationFragment.TAG);
        fragmentTransaction.addToBackStack(RegistrationFragment.TAG);
        fragmentTransaction.commit();
    }


    public void launchMainActivity() {

        Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainActivity);

    }

}
