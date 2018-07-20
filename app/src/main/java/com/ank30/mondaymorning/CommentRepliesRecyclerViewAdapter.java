package com.ank30.mondaymorning;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

public class CommentRepliesRecyclerViewAdapter extends RecyclerView.Adapter<CommentRepliesRecyclerViewAdapter.ViewHolder> {

    private JSONArray values;
    private Context appContext;
    private String imagePrefix;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView, commentView, commentDate;
        public ImageView imageView;
        public View layout;

        public ViewHolder(View view) {
            super(view);
            nameView = (TextView) view.findViewById(R.id.commentName);
            commentView = (TextView) view.findViewById(R.id.commentContent);
            commentDate = (TextView) view.findViewById(R.id.commentDate);
            imageView = (ImageView) view.findViewById(R.id.commentImage);
        }
    }

    public CommentRepliesRecyclerViewAdapter(Context context, JSONArray apiRequest, String imagePrefixL) {
        imagePrefix = imagePrefixL;
        values = apiRequest;
        appContext = context;
    }

    @Override
    public CommentRepliesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return values.length();
    }
}
