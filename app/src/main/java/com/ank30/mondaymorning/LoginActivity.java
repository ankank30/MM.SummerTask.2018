package com.ank30.mondaymorning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private String usernameEntered, passwordEntered;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final EditText usernameEditText, passwordEditText;
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usernameEntered = usernameEditText.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEntered = usernameEditText.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void loginButtonPressed(View view) {
        sharedPreferences = getSharedPreferences("MMPrefs", Context.MODE_PRIVATE);
        if (usernameEntered.equals("Ank") &&
                passwordEntered.equals("Ank")) {
            MainActivity.loginStatus = true;
            sharedPreferences.edit().putBoolean("loginStatus", true).apply();
            sharedPreferences.edit().putString("username", usernameEntered);
            MainActivity.username = usernameEntered;
        }
        finish();
    }

    public void skipButtonPressed(View view) {
        finish();
    }

    public void signUpButtonPressed(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }
}
