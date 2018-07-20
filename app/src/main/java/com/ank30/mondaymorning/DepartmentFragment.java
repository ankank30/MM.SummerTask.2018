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

public class DepartmentFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    DownloadApi downloadApi = new DownloadApi();
    String apiReturned;

    FloatingActionButton fabMenu;

    public DepartmentFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
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
                    "http://mondaymorning.nitrkl.ac.in/uploads/post/big/"); //Used hardcoded url because of my design is different
            recyclerView.setAdapter(mAdapter);
        } catch (Exception e){
            e.printStackTrace();
        }
        final ConstraintLayout constraintLayout = view.findViewById(R.id.selectionConstraintLayout);
        fabMenu = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fabMenu.setVisibility(View.VISIBLE);
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constraintLayout.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        fabMenu.setVisibility(View.INVISIBLE);
    }
}
