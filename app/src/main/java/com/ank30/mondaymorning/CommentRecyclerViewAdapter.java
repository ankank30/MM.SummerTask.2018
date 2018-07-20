package com.ank30.mondaymorning;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {

    private int postId;

    RecyclerView commentRepliesRecyclerView;
    RecyclerView.Adapter mAdapter;

    DownloadApi commentsRepliesDownloadApi = new DownloadApi();
    String commentRepliesApiReturned;

    private JSONArray values;
    private Context appContext;
    private String imagePrefix;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameView, commentView, commentDate;
        public ImageView imageView;
        public View layout;
        public ConstraintLayout repliesConstraintLayout;


        private ViewHolder(View view) {
            super(view);
            nameView = (TextView) view.findViewById(R.id.commentName);
            commentView = (TextView) view.findViewById(R.id.commentContent);
            commentDate = (TextView) view.findViewById(R.id.commentDate);
            imageView = (ImageView) view.findViewById(R.id.commentImage);
            repliesConstraintLayout = (ConstraintLayout) view.findViewById(R.id.commentRepliesConstraintLayout);
            commentRepliesRecyclerView = view.findViewById(R.id.commentRepliesRecyclerView);
        }
    }

    public CommentRecyclerViewAdapter(Context context, JSONArray apiRequest, String imagePrefixL, int postIdL) {
        imagePrefix = imagePrefixL;
        values = apiRequest;
        appContext = context;
        postId = postIdL;
    }

    @Override
    public CommentRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.comments_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            holder.nameView.setText(values.getJSONObject(holder.getAdapterPosition()).getString("name"));
            holder.commentView.setText(values.getJSONObject(holder.getAdapterPosition()).getString("comment"));
            Glide
                    .with(appContext)
                    .load(imagePrefix + values.getJSONObject(holder.getAdapterPosition()).getString("picture"))
                    .into(holder.imageView);
            holder.commentDate.setText(values.getJSONObject(holder.getAdapterPosition()).getString("date"));
            if(values.getJSONObject(holder.getAdapterPosition()).getJSONArray("replies").length() != 0){
                holder.repliesConstraintLayout.setVisibility(View.VISIBLE);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(appContext);
                commentRepliesRecyclerView.setLayoutManager(layoutManager);
                mAdapter = new CommentRepliesRecyclerViewAdapter(appContext,
                        values.getJSONObject(holder.getAdapterPosition()).getJSONArray("replies"),
                        imagePrefix);
                commentRepliesRecyclerView.setAdapter(mAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return values.length();
    }
}
