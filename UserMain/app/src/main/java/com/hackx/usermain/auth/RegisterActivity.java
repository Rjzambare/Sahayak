package com.hackx.usermain.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hackx.usermain.MainActivity;
import com.hackx.usermain.databinding.ActivityRegisterBinding;
import com.hackx.usermain.model.User;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private static final String TAG = "RegisterActivity";

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        binding.btnRegister.setOnClickListener(v -> {
            User user = new User();

            user.setName(binding.name.getText().toString());
            user.setPhoneNumber(binding.phoneNumber.getText().toString());
            user.setEmail(binding.email.getText().toString());
            user.setId(UUID.randomUUID().toString());
            user.setUniqueID(UUID.randomUUID().toString());

            firebaseAuth
                    .createUserWithEmailAndPassword(user.getEmail(), binding.password.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            firebaseFirestore
                                    .collection("users")
                                    .document(user.getEmail())
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: " + e.getMessage());
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.getMessage());
                        }
                    });
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}