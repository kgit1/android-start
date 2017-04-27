package com.example.a.appweatherwithjson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    private class DownloadPageTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuilder pageHtml=new StringBuilder();

            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream in = httpURLConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data>-1){
                    char current = (char) data;
                    pageHtml.append(current);
                    data=reader.read();
                }

            } catch (MalformedURLException e) {
                Log.i("Test", "interrupted");
                e.printStackTrace();
            } catch (IOException e) {
                Log.i("Test", "interrupted");
                e.printStackTrace();
            }
            return pageHtml.toString();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadPageTask task = new DownloadPageTask();
        String pageHtml="";
        try {
            pageHtml = task.execute("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=aabddda8fca6982bdf6299a97f0f0100").get();
        } catch (InterruptedException e) {
            Log.i("Test", "interrupted");
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.i("Test", "execution");
            e.printStackTrace();
        }
        Log.i("Test", pageHtml);
    }
}

















