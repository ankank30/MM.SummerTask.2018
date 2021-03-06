package com.ank30.mondaymorning;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private JSONArray values;
    private Context appContext;
    private String imagePrefix, authors, categories;
    private CardView cardView;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader, txtFooter, dateTextView, authorTextView, categoryNameTextView;
        public ImageView imageView;
        public View layout;

        public ViewHolder(View view) {
            super(view);
            txtHeader = (TextView) view.findViewById(R.id.card_title_textview);
            txtFooter = (TextView) view.findViewById(R.id.card_content_textview);
            imageView = (ImageView) view.findViewById(R.id.card_imageview);
            dateTextView = (TextView) view.findViewById(R.id.dateTextView);
            authorTextView = (TextView) view.findViewById(R.id.authorTextView);
            categoryNameTextView = (TextView) view.findViewById(R.id.categoriesNameTextView);
            cardView = (CardView) view.findViewById(R.id.articleCardView);
        }
    }

    public RecyclerViewAdapter(Context context, JSONArray apiRequest, String imagePrefixL) {
        imagePrefix = imagePrefixL;
        values = apiRequest;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            holder.txtHeader.setText(values.getJSONObject(holder.getAdapterPosition()).getString("post_title"));
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(appContext, ArticleActivity.class);
                    try {
                        intent.putExtra("articleID", values.getJSONObject(holder.getAdapterPosition()).getInt("post_id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    appContext.startActivity(intent);
                }
            });
            holder.dateTextView.setText(values.getJSONObject(holder.getAdapterPosition()).getString("post_publish_date"));
            for(int i = 0; i < values.getJSONObject(holder.getAdapterPosition()).getJSONArray("authors").length(); i++){
                authors += values.getJSONObject(holder.getAdapterPosition()).getJSONArray("authors").getString(i);
                if(i != (values.getJSONObject(holder.getAdapterPosition()).getJSONArray("authors").length() - 1)){
                    authors += ", ";
                }
            }
            if(authors.startsWith("null")) {
                authors = authors.substring("null".length(), authors.length());
            }
            holder.authorTextView.setText(authors);
            authors = null;
            holder.txtFooter.setText(values.getJSONObject(holder.getAdapterPosition()).getString("post_excerpt"));
            for(int i = 0; i < values.getJSONObject(holder.getAdapterPosition()).getJSONArray("categories").length(); i++){
                categories += values.getJSONObject(holder.getAdapterPosition()).getJSONArray("categories").getJSONObject(i).getString("post_category_name");
                if(i != (values.getJSONObject(holder.getAdapterPosition()).getJSONArray("categories").length() - 1)){
                    categories += ",\n";
                }
            }
            if(categories.startsWith("null")) {
                categories = categories.substring("null".length(), categories.length());
            }
            Glide
                    .with(appContext)
                    .load(imagePrefix + values.getJSONObject(holder.getAdapterPosition()).getString("featured_image"))
                    .into(holder.imageView);
            holder.categoryNameTextView.setText(categories);
            categories = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return values.length();
    }
}
