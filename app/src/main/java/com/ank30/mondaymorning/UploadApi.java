package com.ank30.mondaymorning;

import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class UploadApi extends AsyncTask<String, Void, String>{

    private String resultString;

    @Override
    protected String doInBackground(String... params) {

        try{

            URL url = new URL("http://mondaymorning.nitrkl.ac.in/login/verifylogin/");

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("username", params[0]);
            postDataParams.put("password", params[1]);
            Log.e("params",postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            int responseCode=conn.getResponseCode();

//            String cookie = conn.getHeaderField("Cookie");
            Map<String, List<String>> response = conn.getHeaderFields();
            return response.toString();
        }
        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
      //  return "Fail";
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}
