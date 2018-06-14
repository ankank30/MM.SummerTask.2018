package com.ank30.mondaymorning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentRecycler extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> titleList = new ArrayList<>();
    RecyclerView.Adapter mAdapter;

    public FragmentRecycler(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);

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
