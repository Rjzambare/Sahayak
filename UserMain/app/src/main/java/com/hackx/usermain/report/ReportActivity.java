package com.hackx.usermain.report;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hackx.usermain.R;
import com.hackx.usermain.adapter.ReportAdapter;
import com.hackx.usermain.databinding.ActivityBotBinding;
import com.hackx.usermain.databinding.ActivityReportBinding;
import com.hackx.usermain.model.Report;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private ActivityReportBinding binding;
    private static final String TAG = "BotActivity";

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private ReportAdapter adapter;
    private List<Report> reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        reports = new ArrayList<>();
        adapter = new ReportAdapter(reports);

        getD();
    }

    private void getD() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        String email = firebaseAuth.getCurrentUser().getEmail();
        firebaseFirestore
                .collection("users")
                .document(email)
                .collection("reports")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            String id = document.getId();
                            String name = document.getString("name");
                            reports.add(new Report(id, name));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                });

        recyclerView.setLayoutManager(new LinearLayoutManager(ReportActivity.this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}