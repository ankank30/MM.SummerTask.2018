package com.ank30.mondaymorning;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> values;
    private Context appContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView imageView;
        public View layout;

        public ViewHolder(View view) {
            super(view);
            txtHeader = (TextView) view.findViewById(R.id.card_title_textview);
            txtFooter = (TextView) view.findViewById(R.id.card_content_textview);
            imageView = (ImageView) view.findViewById(R.id.card_imageview);
        }
    }

    public RecyclerViewAdapter(Context context, List<String> myDataset) {
        values = myDataset;
        appContext = context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.article_cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String name = values.get(position);
        holder.txtHeader.setText(name);
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove(position);
                Intent intent = new Intent(appContext, ArticleActivity.class);
                intent.putExtra("Article", position + 1);
                appContext.startActivity(intent);
            }
        });
        holder.txtFooter.setText(R.string.twoLineText);
        holder.txtFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(appContext, ArticleActivity.class);
                intent.putExtra("Article", position + 1);
                appContext.startActivity(intent);
            }
        });
        Glide
                .with(appContext)
                .load("http://mondaymorning.nitrkl.ac.in/uploads/post/Screenshot (115) (3).png")
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
