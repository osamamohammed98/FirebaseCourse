package com.osama.moahmed.dasoqi.firebasecourse.view.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.osama.moahmed.dasoqi.firebasecourse.R;
import com.osama.moahmed.dasoqi.firebasecourse.databinding.ActivityHomeBinding;
import com.osama.moahmed.dasoqi.firebasecourse.util.Constance;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference databaseReferenceUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);

        init();

    }

    private void init() {
        auth = FirebaseAuth.getInstance();
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference().child(Constance.UserRoot);

        binding.rvListUser.setHasFixedSize(true);
        binding.rvListUser.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        binding.rvListUser.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }



}