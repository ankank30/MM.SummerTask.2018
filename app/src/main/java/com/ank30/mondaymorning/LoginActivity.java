package com.ank30.mondaymorning;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity {

    private String usernameEntered, passwordEntered;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final EditText usernameEditText, passwordEditText;
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usernameEntered = usernameEditText.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEntered = passwordEditText.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void loginButtonPressed(View view) {
       UploadApi api = new UploadApi();
       String cookie = null;
        try {
            cookie = api.execute(usernameEntered, passwordEntered).get();
            if (cookie.startsWith("null")) {
                cookie = cookie.substring("null".length(), cookie.length());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        DownloadApi downloadAPI = new DownloadApi();
        try {
            String result= downloadAPI.execute("http://mondaymorning.nitrkl.ac.in/users/getlogininfo/", cookie).get();
            Log.i("result12", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        /*
        // int a;
        URL url = null;
        try {
            url = new URL("http://mondaymorning.nitrkl.ac.in/api/user/signin");

            HttpURLConnection client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setRequestProperty("Accept","text/plain");
            client.setDoOutput(true);
            a = client.getResponseCode();
            Log.i("Response", Integer.toString(a));
            OutputStream outputPost = client.getOutputStream();
            //writeStream(outputPost);
            outputPost.flush();
            outputPost.close();
            //client.setFixedLengthStreamingMode(outputPost.getBytes().length);
            client.setChunkedStreamingMode(0);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //finally {
        //    if(client != null) // Make sure the connection is not null.
        //        client.disconnect();
        //}

        /*
        String urlString = usernameEntered; // URL to call
        String data = passwordEntered;//data to post
        OutputStream out = null;
        String response;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL("");
            urlConnection = (HttpURLConnection) url.openConnection();
            out = new java.io.PrintStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            out.close();
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
        //finally {
        //    if(client != null) // Make sure the connection is not null.
        //        client.disconnect();
        //}
        /*
        sharedPreferences = getSharedPreferences("MMPrefs", Context.MODE_PRIVATE);
        if (usernameEntered.equals("Ank") &&
                passwordEntered.equals("Ank")) {
            MainActivity.loginStatus = true;
            sharedPreferences.edit().putBoolean("loginStatus", true).apply();
            sharedPreferences.edit().putString("username", usernameEntered);
            MainActivity.username = usernameEntered;
        }
        finish();
        */
    }

    public void skipButtonPressed(View view) {
        finish();
    }

    public void signUpButtonPressed(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }
}
