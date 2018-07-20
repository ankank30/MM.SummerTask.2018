package com.ank30.mondaymorning;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ResponseCache;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ArticleActivity extends AppCompatActivity {

    RecyclerView commentRecyclerView;
    RecyclerView.Adapter mAdapter;

    DownloadApi downloadApi = new DownloadApi();
    DownloadApi commentsDownloadApi = new DownloadApi();
    String apiReturned, commentApiReturned;
    int postId, i;

    String postCategories, datesAuthors, article;

    TextView postCategoriesTextView, datesNAuthorsTextView, articlesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        postCategoriesTextView = findViewById(R.id.postCategories);
        datesNAuthorsTextView = findViewById(R.id.dateNAuthorsTextView);
        articlesTextView = findViewById(R.id.articleTextView);

        Intent intent = getIntent();
        postId = intent.getIntExtra("articleID", 0);
        try {
            apiReturned = downloadApi.execute("http://mondaymorning.nitrkl.ac.in/api/post/get/" + Integer.toString(postId)).get();
            if(apiReturned.startsWith("null")) {
                apiReturned = apiReturned.substring("null".length(), apiReturned.length());
            }
            final JSONObject jsonObject = new JSONObject(apiReturned);
            setTitle(jsonObject.getJSONObject("post").getString("post_title"));
            for(i = 0; i < jsonObject.getJSONObject("post").getJSONArray("categories").length(); i++){
                postCategories += jsonObject.getJSONObject("post").getJSONArray("categories").getJSONObject(i).getString("post_category_name");
                if(i != jsonObject.getJSONObject("post").getJSONArray("categories").length() - 1){
                    postCategories += ", ";
                }
            }
            datesAuthors = jsonObject.getJSONObject("post").getString("post_publish_date") + " | ";
            for(i = 0; i < jsonObject.getJSONObject("post").getJSONArray("authors").length(); i++){
                datesAuthors += jsonObject.getJSONObject("post").getJSONArray("authors").getString(i);
                if(i != jsonObject.getJSONObject("post").getJSONArray("authors").length() - 1){
                    datesAuthors += ", ";
                }
            }
            if(datesAuthors.startsWith("null")) {
                datesAuthors = datesAuthors.substring("null".length(), datesAuthors.length());
            }
            if(postCategories.startsWith("null")) {
                postCategories = postCategories.substring("null".length(), postCategories.length());
            }
            postCategoriesTextView.setText(postCategories);
            datesNAuthorsTextView.setText(datesAuthors);
            for(i = 0; i < jsonObject.getJSONObject("post").getJSONArray("post_content").length(); i++) {
                if(jsonObject.getJSONObject("post").getJSONArray("post_content").getJSONObject(i).getInt("type") == 4) {
                    article += jsonObject.getJSONObject("post").getJSONArray("post_content").getJSONObject(i).getString("content");
                } else if (jsonObject.getJSONObject("post").getJSONArray("post_content").getJSONObject(i).getInt("type") == 0){
                    article += "<b><big> &quot " + jsonObject.getJSONObject("post").getJSONArray("post_content").getJSONObject(i).getString("content") + "</big></b>";
                }
            }
            if(article.startsWith("null")) {
                article = article.substring("null".length(), article.length());
            }
            articlesTextView.setText(Html.fromHtml(article));
            ImageView articleImage = findViewById(R.id.articleImage);
            Glide
                    .with(getApplicationContext())
                    .load(jsonObject.getString("imageUrlPrefix") + jsonObject.getJSONObject("post").getString("featured_image"))
                    .into(articleImage);
            articleImage.setBackgroundResource(R.drawable.text_view_background_gradient);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    try {
                        shareIntent.putExtra(Intent.EXTRA_TEXT, jsonObject.getJSONObject("post").getString("post_url"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(Intent.createChooser(shareIntent, "Share post"));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            commentApiReturned = commentsDownloadApi.execute("http://mondaymorning.nitrkl.ac.in/api/comment/get/" + postId).get();
            if (commentApiReturned.startsWith("null")) {
                commentApiReturned = commentApiReturned.substring("null".length(), commentApiReturned.length());
            }
            JSONObject jsonObject = new JSONObject(commentApiReturned);
            String imageUrlPrefix = jsonObject.getString("imageUrlPrefix");
            commentRecyclerView = findViewById(R.id.commentRecyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            commentRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new CommentRecyclerViewAdapter(getApplicationContext(),
                    jsonObject.getJSONArray("comments"),
                    imageUrlPrefix, postId); //Used hardcoded url because of my design is different
            commentRecyclerView.setAdapter(mAdapter);
        } catch (Exception e){
            e.printStackTrace();
        }

        ConstraintLayout loginConstraintLayout;
        if(MainActivity.loginStatus) {
            loginConstraintLayout = findViewById(R.id.loginTrueConstraintLayout);
            loginConstraintLayout.setVisibility(View.VISIBLE);
            TextView username = findViewById(R.id.commentUserName);
            username.setText(MainActivity.username);
        } else {
            loginConstraintLayout = findViewById(R.id.loginFalseConstraintLayout);
            loginConstraintLayout.setVisibility(View.VISIBLE);
        }
        DisplayMetrics displayMetrics = getApplication().getResources().getDisplayMetrics();

        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.setMinimumHeight((int) ((displayMetrics.widthPixels / displayMetrics.density) * 500) / 875);
    }

    public void loginButtonPressed(View view){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void commentPostButtonPressed(View view){
        URL url = null;
        try {
            url = new URL("http://exampleurl.com/");
            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setRequestProperty("Key","Value");
            client.setDoOutput(true);
            OutputStream outputPost = client.getOutputStream();
            //writeStream(outputPost);
            outputPost.flush();
            outputPost.close();
            //client.setFixedLengthStreamingMode(outputPost.getBytes().length);
            client.setChunkedStreamingMode(0);
            client.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //finally {
        //    if(client != null) // Make sure the connection is not null.
        //        client.disconnect();
        //}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
