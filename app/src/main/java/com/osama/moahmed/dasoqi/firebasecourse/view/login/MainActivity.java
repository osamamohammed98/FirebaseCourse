package com.osama.moahmed.dasoqi.firebasecourse.view.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.osama.moahmed.dasoqi.firebasecourse.R;
import com.osama.moahmed.dasoqi.firebasecourse.databinding.ActivityMainBinding;
import com.osama.moahmed.dasoqi.firebasecourse.util.AppSharedData;
import com.osama.moahmed.dasoqi.firebasecourse.view.home.HomeActivity;
import com.osama.moahmed.dasoqi.firebasecourse.view.signup.SignUpActivity;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    private ProgressDialog dialog;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);


        init();
        initListener();
    }

    private void init() {
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Pleas wait ...");
    }

    private void initListener() {
        login();
        signUp();
    }

    private void signUp() {
        binding.btnSignup.setOnClickListener(v -> MainActivity.this.startActivity(new Intent(MainActivity.this.getBaseContext(), SignUpActivity.class)));
    }

    private void login() {
        binding.btnLogin.setOnClickListener(v -> {
            if (AppSharedData.isEmptyEditText(binding.etEmail)) {
                AppSharedData.setErrorET(binding.etEmail, "this filed is required");
                return;
            }

            if (!AppSharedData.isEmailMatcher(binding.etEmail)) {
                AppSharedData.setErrorET(binding.etEmail, "this email is not valid : example@example.com");
                return;
            }

            if (AppSharedData.isEmptyEditText(binding.etPassword)) {
                AppSharedData.setErrorET(binding.etPassword, "this filed is required");
                return;
            }
            if (AppSharedData.getTextFromET(binding.etPassword).length() < 8) {
                AppSharedData.setErrorET(binding.etPassword, "this password is not valid it must be more thane 8 character");
                return;
            }
            String email = AppSharedData.getTextFromET(binding.etEmail);
            String password = AppSharedData.getTextFromET(binding.etPassword);

           signIn(email , password);
        });
    }

    private void signIn(String email, String password) {
        try {
            dialog.show();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener((AuthResult authResult) -> {
                        startActivity(new Intent(getBaseContext(), HomeActivity.class));
                        finish();
                        dialog.dismiss();
                    })
                    .addOnFailureListener((Exception e) -> {
                        dialog.dismiss();
                        Log.d(TAG, "login: " + e.toString() + "\n" + e.getMessage());
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "login: " + e.toString() + "\n" + e.getMessage());
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            if (firebaseAuth.getCurrentUser() != null){
                startActivity(new Intent(getBaseContext() , HomeActivity.class));
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onStart: "+e.getMessage());
        }
    }
}