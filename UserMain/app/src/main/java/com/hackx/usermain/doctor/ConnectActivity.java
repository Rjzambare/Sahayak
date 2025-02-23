package com.hackx.usermain.doctor;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hackx.usermain.R;
import com.hackx.usermain.databinding.ActivityConnectBinding;
import com.hackx.usermain.databinding.ActivityDoctorBinding;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;

public class ConnectActivity extends AppCompatActivity {

    private ActivityConnectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConnectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

    public void onButtonClick(View view) throws MalformedURLException {
        String text = binding.conferenceName.getText().toString();

        if (text.length() > 0) {
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(text)
                    .setFeatureFlag("welcomepage.enabled", false)
                    .setAudioMuted(true)
                    .setVideoMuted(true)
                    .build();
            JitsiMeetActivity.launch(this, options);
        }
    }
}