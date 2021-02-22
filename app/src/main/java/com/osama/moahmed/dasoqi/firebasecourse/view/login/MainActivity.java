package com.osama.moahmed.dasoqi.firebasecourse.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.osama.moahmed.dasoqi.firebasecourse.R;
import com.osama.moahmed.dasoqi.firebasecourse.databinding.ActivityMainBinding;
import com.osama.moahmed.dasoqi.firebasecourse.view.signup.SignUpActivity;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext() , SignUpActivity.class));
            }
        });
    }


}