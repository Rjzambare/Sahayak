package com.hackx.usermain.report;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hackx.usermain.R;
import com.hackx.usermain.databinding.ActivityReportBinding;
import com.hackx.usermain.databinding.ActivityViewPdfactivityBinding;

public class ViewPDFActivity extends AppCompatActivity {

    private ActivityViewPdfactivityBinding binding;
    private static final String TAG = "ViewPDFActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewPdfactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String text = "hello";
    }
}