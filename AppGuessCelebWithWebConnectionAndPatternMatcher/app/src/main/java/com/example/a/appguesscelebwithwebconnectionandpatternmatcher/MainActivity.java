package com.example.a.appguesscelebwithwebconnectionandpatternmatcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> namesArray;
    ArrayList<String> imageURLSArray;

    private ArrayList<String> namesToArray(String htmlCode) {
        Log.i("Test","Names");
        String[] cutHtml = htmlCode.split("<div class=\"see-more\">");
        ArrayList<String> names = new ArrayList<String>();
        Pattern pNamesFirst=Pattern.compile("alt=\"Image of "+"(.*?)"+"\" class=\"");
        Pattern pNamesAfterload = Pattern.compile("alt=\"Image of "+"(.*?)"+"\" title");

        Matcher matcher = pNamesFirst.matcher(cutHtml[0]);
        while(matcher.find()){
            names.add(matcher.group(1));
        }

        matcher=pNamesAfterload.matcher(cutHtml[0]);
        while (matcher.find()){
            //matcher.find() second call because pattern occurs twice for every entry
            //so with second call - we every time jump one step ahead before make write
            matcher.find();
            names.add(matcher.group(1));
        }

        Log.i("Test","Names end");
        return names;
    }

    private ArrayList<String> imageURLsToArray(String htmlCode){
        Log.i("Test","URLS");
        String []cutHtml = htmlCode.split("<div class=\"see-more\">");
        ArrayList<String> urls = new ArrayList<>();

        Pattern pImageUrls = Pattern.compile("src=\"https://images-na.ssl-images-amazon.com/images/M/"+"(.*?)"+".jpg");
        Matcher matcher = pImageUrls.matcher(cutHtml[0]);

        while(matcher.find()){
            urls.add("https://images-na.ssl-images-amazon.com/images/M/"+matcher.group(1)+".jpg");
        }

        Log.i("Test","URLS end");
        return urls;
    }


    private class DownloadPageHtmlTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.i("Test","Background");
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                StringBuilder result = new StringBuilder();
                while (data > -1) {
                    char tempChar = (char) data;
                    result.append(tempChar);
                    data = reader.read();
                }
                Log.i("Test","Background - end");
                return result.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap bitmap;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();

                bitmap = BitmapFactory.decodeStream(in);

                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        namesArray=new ArrayList<>();
        imageURLSArray=new ArrayList<>();

        try {
            String pageHtml = new DownloadPageHtmlTask().execute("http://www.imdb.com/list/ls052283250/").get();
            namesArray=namesToArray(pageHtml);
            imageURLSArray=imageURLsToArray(pageHtml);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(int i = 0; i<namesArray.size();i++) {
            Log.i("Test", i + " name: " + namesArray.get(i));
        }
        for(int i = 0; i<imageURLSArray.size();i++) {
            Log.i("Test", i + " url: " + imageURLSArray.get(i));
        }

    }
}




































