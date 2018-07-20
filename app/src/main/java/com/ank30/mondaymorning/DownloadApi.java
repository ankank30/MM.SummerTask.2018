package com.ank30.mondaymorning;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadApi extends AsyncTask<String, Void, String>{

    private String resultString = "";

    @Override
    protected String doInBackground(String... apiUrls) {

        try {
            URL url = new URL(apiUrls[0]);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if(apiUrls.length>1)
            {
                urlConnection.setRequestProperty("Cookie", apiUrls[1]);
            }

            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int data = inputStreamReader.read();
            char currentChar;
            while(data != -1){
                currentChar = (char) data;
                resultString += currentChar;
                data = inputStreamReader.read();
            }
            return resultString;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Fail";
    }

}
