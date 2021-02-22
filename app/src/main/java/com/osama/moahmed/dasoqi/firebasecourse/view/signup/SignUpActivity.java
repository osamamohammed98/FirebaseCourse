package com.osama.moahmed.dasoqi.firebasecourse.view.signup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.auth.FirebaseAuth;
import com.osama.moahmed.dasoqi.firebasecourse.R;
import com.osama.moahmed.dasoqi.firebasecourse.databinding.ActivitySignUpBinding;
import com.osama.moahmed.dasoqi.firebasecourse.util.AppSharedData;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SignUpActivity.this, R.layout.activity_sign_up);

        init();
        initListener();

    }

    private void init() {
        auth = FirebaseAuth.getInstance();
    }

    private void initListener() {
        onRegisterClick();
        onLoginClick();
        selectImage();
    }

    private void selectImage() {
        binding.ivUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(SignUpActivity.this);
            }
        });
    }

    private void onLoginClick() {
        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void onRegisterClick() {


        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setErrorNull(binding.etPassword, binding.etEmail, binding.etPhone, binding.etFullName);

                if (AppSharedData.isEmptyEditText(binding.etFullName)) {
                    AppSharedData.setErrorET(binding.etFullName, "this filed is required");
                    return;
                }

                if (AppSharedData.isEmptyEditText(binding.etPhone)) {
                    AppSharedData.setErrorET(binding.etPhone, "this filed is required");
                    return;
                }

                if (AppSharedData.isEmptyEditText(binding.etEmail)) {
                    AppSharedData.setErrorET(binding.etEmail, "this filed is required");
                    return;
                }

                if (!AppSharedData.getTextFromET(binding.etEmail).contains("@")) {
                    AppSharedData.setErrorET(binding.etEmail, "this email is not valid : example@example.com");
                    return;
                }

                if (AppSharedData.isEmptyEditText(binding.etPassword)) {
                    AppSharedData.setErrorET(binding.etPassword, "this filed is required");
                    return;
                }
                if (AppSharedData.getTextFromET(binding.etPassword).length() < 7) {
                    AppSharedData.setErrorET(binding.etPassword, "this password is not valid it must be more thane 8 character");
                    return;
                }


            }
        });


    }

    private void setErrorNull(EditText etPassword, EditText etEmail, EditText etPhone, EditText etFullName) {
        etEmail.setError(null);
        etPassword.setError(null);
        etPhone.setError(null);
        etFullName.setError(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                binding.ivUserImage.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}