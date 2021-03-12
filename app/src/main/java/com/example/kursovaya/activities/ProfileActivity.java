package com.example.kursovaya.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kursovaya.R;

public class ProfileActivity extends AppCompatActivity {
    public static String USER_KEY = "USER_KEY";

    private ImageView prPhoto;
    private TextView prEmail;
    private TextView prPassword;
    private TextView prLogin;
    private Button prSettings;
    private Button prNotifications;
    private Button prDescriptionAndSupport;

    private String TAG = "User_data_set";

    private View.OnClickListener onSettingsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent settingsActivity = new Intent(ProfileActivity.this, SettingsActivity.class);
            startActivity(settingsActivity);
        }
    };

    private View.OnClickListener onNotificationsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //go to Notifications activity
        }
    };

    private View.OnClickListener onDescriptionAndSupportClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //go to DescriptionAndSupport activity
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        prPhoto = findViewById(R.id.pr_photo);
        prEmail = findViewById(R.id.pr_email);
        prLogin = findViewById(R.id.pr_login);
        prPassword = findViewById(R.id.pr_password);
        prSettings = findViewById(R.id.pr_settings_btn);
        prNotifications = findViewById(R.id.pr_notifications_btn);
        prDescriptionAndSupport = findViewById(R.id.pr_descriptionandsupport_btn);

        prSettings.setOnClickListener(onSettingsClickListener);
        prNotifications.setOnClickListener(onNotificationsClickListener);
        prDescriptionAndSupport.setOnClickListener(onDescriptionAndSupportClickListener);

    }
}
