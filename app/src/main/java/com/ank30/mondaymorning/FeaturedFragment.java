package com.ank30.mondaymorning;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FeaturedFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> titleList = new ArrayList<>();
    RecyclerView.Adapter mAdapter;
    DownloadApi downloadApi = new DownloadApi();
    String testResult;

    public FeaturedFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);

        try {
            testResult = downloadApi.execute("http://www.mondaymorning.nitrkl.ac.in/api/post/get/featured").get();
            Log.i("ApiResult", testResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        titleList.add("Article 1");
        titleList.add("Article 2");

        recyclerView = view.findViewById(R.id.mainActivityRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewAdapter(getActivity(), titleList);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

}
