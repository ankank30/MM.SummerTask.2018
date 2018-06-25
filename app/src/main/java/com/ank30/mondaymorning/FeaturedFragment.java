package com.ank30.mondaymorning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;

public class FeaturedFragment extends Fragment {

    RecyclerView recyclerView;
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
        ImageView slideImageView = view.findViewById(R.id.slideImageView);
        TextView slideTitleTextView = view.findViewById(R.id.titleSlideTextView);
        TextView slideAuthorTextView = view.findViewById(R.id.authorSlideTextView);
        TextView slideDateTextView = view.findViewById(R.id.dateSlideTextView);

        try {
            testResult = downloadApi.execute("http://mondaymorning.nitrkl.ac.in/api/post/get/featured").get();
            if(testResult.startsWith("null")) {
                testResult = testResult.substring("null".length(), testResult.length());
            }
            JSONObject jsonObject = new JSONObject(testResult);
            Log.i("ApiResult", jsonObject.getJSONObject("slider").toString());
            String imageUrlPrefix = jsonObject.getJSONObject("slider").getString("imageUrlPrefix");
            //Will modify later
            for(int i = 0; i < jsonObject.getJSONObject("slider").getJSONArray("posts").length(); i++){
                slideTitleTextView.setText(jsonObject
                        .getJSONObject("slider")
                        .getJSONArray("posts")
                        .getJSONObject(i)
                        .getString("post_title"));
                slideAuthorTextView.setText(jsonObject
                        .getJSONObject("slider")
                        .getJSONArray("posts")
                        .getJSONObject(i)
                        .getJSONArray("authors")
                        .getString(0));
                slideDateTextView.setText(jsonObject
                        .getJSONObject("slider")
                        .getJSONArray("posts")
                        .getJSONObject(i)
                        .getString("post_publish_date"));
                Glide
                        .with(getActivity())
                        .load(imageUrlPrefix +
                                jsonObject
                                        .getJSONObject("slider")
                                        .getJSONArray("posts")
                                        .getJSONObject(i)
                                        .getString("featured_image"))
                        .into(slideImageView);
            }
            recyclerView = view.findViewById(R.id.mainActivityRecyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            mAdapter = new RecyclerViewAdapter(getActivity(),
                    jsonObject.getJSONObject("top4").getJSONArray("posts"),
                    "http://mondaymorning.nitrkl.ac.in/uploads/post/big/"); //Used hardcode url because of my design is different
            recyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

}
