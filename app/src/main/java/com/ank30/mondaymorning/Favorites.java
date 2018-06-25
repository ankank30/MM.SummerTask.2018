package com.ank30.mondaymorning;

import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> titleList = new ArrayList<>();
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        titleList.add("Article 1");
        titleList.add("Article 2");

        recyclerView = findViewById(R.id.favoriteActivityRecyclerView);

        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        //mAdapter = new RecyclerViewAdapter(getApplicationContext(), titleList);
        //recyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
