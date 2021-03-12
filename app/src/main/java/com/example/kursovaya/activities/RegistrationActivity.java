package com.example.kursovaya.activities;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kursovaya.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText reEmail;
    private EditText reLogin;
    private EditText rePassword;
    private EditText rePasswordAgain;
    private Button reSignUp;

    View.OnClickListener onReSignUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(validEmail() && validLogin() && validPassword()){
                Intent startProfileActivity = new Intent(RegistrationActivity.this, ProfileActivity.class);
            }
            else if(!validEmail()){
                showMessage(R.string.reg_email_input_error);
            }
            else if(!validPassword()){
                showMessage(R.string.reg_password_input_error);
            }
            else if(!validPasswordAgain()){
                showMessage(R.string.reg_password_again_input_error);
            }
        }
    };
    private boolean validEmail(){
        return !TextUtils.isEmpty(reEmail.getText()) && Patterns.EMAIL_ADDRESS.matcher(reEmail.getText()).matches();
    }

    private boolean validPassword(){
        return !TextUtils.isEmpty(rePassword.getText());
    }

    private boolean validPasswordAgain(){
        String password = rePassword.getText().toString();
        String passwordAgain = rePasswordAgain.getText().toString();
        return password.equals(passwordAgain);
    }

    private boolean validLogin(){
        return !TextUtils.isEmpty(reLogin.getText());
    }

    public void showMessage(@StringRes int str){
        Toast.makeText(RegistrationActivity.this, str, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        reEmail = findViewById(R.id.re_email);
        reLogin = findViewById(R.id.re_login);
        rePassword = findViewById(R.id.re_password);
        rePasswordAgain = findViewById(R.id.re_password_again);
        reSignUp = findViewById(R.id.re_signup);

        reSignUp.setOnClickListener(onReSignUpClickListener);
    }
}
