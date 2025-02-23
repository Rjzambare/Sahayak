package com.hackx.textconversion;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateRemoteModel;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseTranslator translator;

    private FirebaseModelManager modelManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        modelManager = FirebaseModelManager.getInstance();

        downloadModel(FirebaseTranslateLanguage.MR);
        downloadModel(FirebaseTranslateLanguage.HI);

        translateText(FirebaseTranslateLanguage.MR, "तुमचे स्वागत आहे!", FirebaseTranslateLanguage.EN);
        translateText(FirebaseTranslateLanguage.HI, "आपका स्वागत है!", FirebaseTranslateLanguage.EN);
    }

    private void downloadModel(int sourceLanguage) {

        Log.d(TAG, "downloadModel: st" + sourceLanguage);

        FirebaseTranslateRemoteModel model =
                new FirebaseTranslateRemoteModel.Builder(sourceLanguage).build();

        FirebaseModelDownloadConditions conditions =
                new FirebaseModelDownloadConditions.Builder()
                        .requireWifi()
                        .build();

        modelManager.download(model, conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        Log.d(TAG, "Model downloaded successfully: " + sourceLanguage);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Model download failed: " + e.getMessage());
                    }
                });

        Log.d(TAG, "downloadModel: ed" + sourceLanguage);
    }

    private void translateText(int sourceLanguage, String text, int targetLanguage) {
        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(sourceLanguage)
                        .setTargetLanguage(targetLanguage)
                        .build();

        FirebaseTranslator translator =
                FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions =
                new FirebaseModelDownloadConditions.Builder()
                        .requireWifi()
                        .build();

        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        translator.translate(text)
                                .addOnSuccessListener(new OnSuccessListener<String>() {
                                    @Override
                                    public void onSuccess(@NonNull String translatedText) {
                                        Log.d(TAG, "Translated Text: " + translatedText);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, "Translation failed: " + e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Model download failed: " + e.getMessage());
                    }
                });
    }
}
