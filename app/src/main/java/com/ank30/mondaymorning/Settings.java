package com.ank30.mondaymorning;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    Switch loadImageSwitch;

    ConstraintLayout constraintLayout;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadImageSwitch = findViewById(R.id.loadImageSwitch);
        loadImageSwitch.setChecked(MainActivity.loadImage);

        loadImageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MainActivity.loadImage = true;
                } else {
                    MainActivity.loadImage = false;
                }
            }
        });

        constraintLayout = findViewById(R.id.constraintLayout);

        if (MainActivity.loginStatus) {
            constraintLayout = findViewById(R.id.constraintLayout);
            constraintLayout.setVisibility(View.VISIBLE);
        }

        CardView cardView = findViewById(R.id.passwordCardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = findViewById(R.id.changePasswordEditText);
                editText.setVisibility(View.VISIBLE);
            }
        });
    }

    public void logoutButtonPressed(View view) {
        MainActivity.loginStatus = false;
        SharedPreferences sharedPreferences = getSharedPreferences("MMPrefs", Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("loginStatus", false).apply();
        constraintLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
