package com.example.kursovaya.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kursovaya.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Button profile;
    private Button chosen;
    private Button about_us;
    private FloatingActionButton fab;
    private EditText search;


    private View.OnClickListener OnFABClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (search.getVisibility() == View.GONE) {
                search.setVisibility(View.VISIBLE);
            }
            else if (TextUtils.isEmpty(search.getText())) {
                Snackbar.make(v, R.string.empty_search, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
            else {
                String normal = search.getText().toString();
                Intent intent = new Intent(MainActivity.this, ApiActivity.class);
                intent.putExtra(ApiActivity.SEARCH_KEY, normal);
                startActivity(intent);
            }
        }
    };

    private View.OnClickListener OnProfileClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        /*  Intent profileActivity = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(profileActivity);*/
        }
    };

    private View.OnClickListener OnChosenClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ChosenActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener OnAUClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent about_us = new Intent(MainActivity.this, AboutUs.class);
            startActivity(about_us);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_main);

        profile = findViewById(R.id.button1);
        chosen = findViewById(R.id.button2);
        fab = findViewById(R.id.fab);
        about_us = findViewById(R.id.button3);
        search = findViewById(R.id.search);

        profile.setOnClickListener(OnProfileClick);
        chosen.setOnClickListener(OnChosenClick);
        fab.setOnClickListener(OnFABClick);
        about_us.setOnClickListener(OnAUClick);
    }
}
