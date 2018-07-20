package com.ank30.mondaymorning;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONObject;

public class CategoryLoader extends AppCompatActivity{

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    DownloadApi downloadApi = new DownloadApi();
    String apiReturned, option;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_loader);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        option = intent.getStringExtra("option");

        getSupportActionBar().setTitle("Monday Morning");

        try {
            apiReturned = downloadApi.execute("http://mondaymorning.nitrkl.ac.in/api/post/get/category/" + option).get();
            if (apiReturned.startsWith("null")) {
                apiReturned = apiReturned.substring("null".length(), apiReturned.length());
            }
            JSONObject jsonObject = new JSONObject(apiReturned);
            String imageUrlPrefix = jsonObject.getString("imageUrlPrefix");
            recyclerView = findViewById(R.id.recyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            mAdapter = new RecyclerViewAdapter(getApplicationContext(),
                    jsonObject.getJSONArray("posts"),
                    "http://mondaymorning.nitrkl.ac.in/uploads/post/big/"); //Used hardcoded url because of my design is different
            recyclerView.setAdapter(mAdapter);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
