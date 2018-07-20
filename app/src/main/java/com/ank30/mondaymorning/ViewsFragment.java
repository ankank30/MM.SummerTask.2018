package com.ank30.mondaymorning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

public class ViewsFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    DownloadApi downloadApi = new DownloadApi();
    String apiReturned;

    FloatingActionButton fabMenu;

    public ViewsFragment(){
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
            apiReturned = downloadApi.execute("http://mondaymorning.nitrkl.ac.in/api/post/get/tab/views").get();
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
                //Here we can change strings of fab buttons to modify task
                Snackbar.make(view, "Ending tag not known", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
