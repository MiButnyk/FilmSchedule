package com.example.kursovaya.activities;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.*;

import com.example.kursovaya.R;

public class AuthorizationActivity extends AppCompatActivity {

    private EditText auEmail;
    private EditText auPassword;
    private Button auLogIn;
    private Button auSignUp;

    private View.OnClickListener onLogInClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mainActivity = new Intent(AuthorizationActivity.this, MainActivity.class);
            startActivity(mainActivity);
           // to make a database of users!
        }
    };

    private View.OnClickListener onSignUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent startRegistrationActivity = new Intent(AuthorizationActivity.this, RegistrationActivity.class);
            startActivity(startRegistrationActivity);
        }
    };

    private boolean validEmail(){
        return !TextUtils.isEmpty(auEmail.getText()) && Patterns.EMAIL_ADDRESS.matcher(auEmail.getText()).matches();
    }

    private boolean validPassword(){
        return !TextUtils.isEmpty(auPassword.getText());
    }

    public void showMessage(@StringRes int str){
        Toast.makeText(AuthorizationActivity.this, str, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        auEmail = findViewById(R.id.au_email);
        auPassword = findViewById(R.id.au_password);
        auLogIn = findViewById(R.id.au_login);
        auSignUp = findViewById(R.id.au_signup);

        auLogIn.setOnClickListener(onLogInClickListener);
        auSignUp.setOnClickListener(onSignUpClickListener);
    }
}
