package com.hackx.doctormain;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hackx.doctormain.databinding.ActivityConnectBinding;
import com.hackx.doctormain.databinding.ActivityPatientsBinding;

public class PatientsActivity extends AppCompatActivity {

    private ActivityPatientsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}