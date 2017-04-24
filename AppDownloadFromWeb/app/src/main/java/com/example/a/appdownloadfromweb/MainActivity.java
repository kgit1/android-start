package com.example.a.appdownloadfromweb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//add in AndroidManifest.xml to provide internet use permission
// <uses-permission android:name="android.permission.INTERNET"/>

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //AsyncTask allows to run another task behind(in new background thread) MainActivity thread
    // which displays app to user
    // in AsyncTask<?,?,?>
    //first (String) - variable which we send to DownloadTask class to instruct it what to do
    //second - name of method which shows the progress of this task
    //third - variable which can be returned by DownloadTask class
    public class DownloadTask extends AsyncTask<String, Void, String> {

        //call execute method from DownloadTask class - will execute this method
        @Override
        protected String doInBackground(String... params) {

            String result = "";
            URL url;
            HttpURLConnection httpConnection = null;

            for (String param : params) {
                Log.i("Test", param);
            }

            try {

                //create URL from string
                url = new URL(params[0]);

                //open HttpURLConnection with URL and openConnection() method
                httpConnection = (HttpURLConnection) url.openConnection();

                //open inputStream from HttpURLConnection with getInputStream() method
                InputStream in = httpConnection.getInputStream();
                //read stream with InputStreamReader
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                //at the end of content data from stream - will be -1
                while (data != -1) {
                    char current = (char) data;
                    result += current;

                    data = reader.read();
                }

                return result;
            } catch (Exception e) {
                e.printStackTrace();

                return "Failed";
            }
            //call get() after execute() method - wil provide returned data
            //return result;
        }
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask dTask = new DownloadTask();
        String result = "";
        try {
            //result = dTask.execute("https://www.ecowebhosting.co.uk/").get();
            result = new DownloadTask().execute("https://www.ecowebhosting.co.uk/").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.i("Result", result);
    }
}
