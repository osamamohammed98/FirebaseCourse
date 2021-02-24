package com.osama.moahmed.dasoqi.firebasecourse.view.signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.osama.moahmed.dasoqi.firebasecourse.R;
import com.osama.moahmed.dasoqi.firebasecourse.databinding.ActivitySignUpBinding;
import com.osama.moahmed.dasoqi.firebasecourse.util.AppSharedData;
import com.osama.moahmed.dasoqi.firebasecourse.util.Constance;
import com.osama.moahmed.dasoqi.firebasecourse.view.home.HomeActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import static com.osama.moahmed.dasoqi.firebasecourse.util.Constance.ProfileImage;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference databaseReferenceUserRoot;
    private StorageReference referenceUserImage;
    private ProgressDialog dialog;
    private static final String TAG = "SignUpActivity";
    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SignUpActivity.this, R.layout.activity_sign_up);

        init();
        initListener();

    }

    private void init() {
        auth = FirebaseAuth.getInstance();
        databaseReferenceUserRoot = FirebaseDatabase.getInstance().getReference().child(Constance.UserRoot);
        referenceUserImage = FirebaseStorage.getInstance().getReference().child(ProfileImage);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Pleas wait ...");
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
                if (AppSharedData.getTextFromET(binding.etPassword).length() < 8) {
                    AppSharedData.setErrorET(binding.etPassword, "this password is not valid it must be more thane 8 character");
                    return;
                }

                String email = AppSharedData.getTextFromET(binding.etEmail);
                String password = AppSharedData.getTextFromET(binding.etPassword);
                String fullName = AppSharedData.getTextFromET(binding.etFullName);
                String phone = AppSharedData.getTextFromET(binding.etPhone);

                if (imageUri == null) {
                    register(email, password, fullName, phone);
                } else {
                    registerWithImage(email, password, fullName, phone, imageUri);
                }


            }
        });


    }

    private void registerWithImage(final String email, final String password, final String fullName, final String phone, final Uri imageUri) {

        try {
            dialog.show();
            StorageReference storageReference = referenceUserImage.child(auth.getCurrentUser().getUid());
            storageReference.child(imageUri.getLastPathSegment() + ".jpg").putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Log.d(TAG, "onFailure: " + e.toString());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            dialog.dismiss();
            Log.d(TAG, "onFailure: " + e.toString());
        }


    }

    private void register(final String email, final String password, final String fullName, final String phone) {
        try {
            dialog.show();
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            String userId = auth.getCurrentUser().getUid();
                            //"User/userId/{}"
                            Map<String, Object> map = new HashMap<>();
                            map.put("userID", userId);
                            map.put("fullName", fullName);
                            map.put("phone", phone);
                            map.put("password", password);
                            map.put("email", email);
                            databaseReferenceUserRoot.child(userId).setValue(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            startActivity(new Intent(getBaseContext(), HomeActivity.class));
                                            dialog.dismiss();
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignUpActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            Log.d(TAG, "onFailure: " + e.toString());
                                        }
                                    });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            Log.d(TAG, "onFailure: " + e.toString());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(SignUpActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            Log.d(TAG, "onFailure: " + e.toString());
        }
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
                imageUri = resultUri;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}