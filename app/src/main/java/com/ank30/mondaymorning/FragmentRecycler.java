package com.ank30.mondaymorning;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.zip.Inflater;

public class FragmentRecycler extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    DownloadApi downloadApi = new DownloadApi();
    String apiReturned;

    ViewPager selectionViewpager;

    String department = null;

    FloatingActionButton fab;

    public FragmentRecycler(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        selectionViewpager = view.findViewById(R.id.selectionViewPager);
        try {
            apiReturned = downloadApi.execute("http://mondaymorning.nitrkl.ac.in/api/post/get/tab/departments").get();
            if (apiReturned.startsWith("null")) {
                apiReturned = apiReturned.substring("null".length(), apiReturned.length());
            }
            JSONObject jsonObject = new JSONObject(apiReturned);
            String imageUrlPrefix = jsonObject.getString("imageUrlPrefix");
            recyclerView = view.findViewById(R.id.mainActivityRecyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            mAdapter = new RecyclerViewAdapter(getActivity(),
                    jsonObject.getJSONArray("posts"),
                    "http://mondaymorning.nitrkl.ac.in/uploads/post/big/"); //Used hardcode url because of my design is different
            recyclerView.setAdapter(mAdapter);
        } catch (Exception e){
            e.printStackTrace();
        }
        fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout constraintLayout = view.findViewById(R.id.selectionConstraintLayout);
                constraintLayout.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    public void selectionPressed(View view){
        department = (String) view.getTag();
    }

    @Override
    public void onStop() {
        super.onStop();
        fab.setVisibility(View.INVISIBLE);
        Log.i("ClassFrag", "RecyclerFragment onStop Run");
    }
}
