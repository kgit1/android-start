package com.example.a.appnewsreader;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TestActivity extends AppCompatActivity {

    ArrayList<String> listOfTitles = new ArrayList<>();

    private class DownloadDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                Log.i("URLResult", result);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    private class NewsArrayTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            ArrayList<String> newsIndexes = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(params[0]);

                Log.i("News", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    newsIndexes.add(jsonArray.get(i).toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.i("Array",newsIndexes.toString());
            //for(int i=0;i<newsIndexes.size();i++){
               // Log.i("index","i"+i+" "+newsIndexes.get(i));
            //}
            return newsIndexes;
        }
    }

    private class NewsToDatabaseTask extends AsyncTask<ArrayList<String>, Void, String> {
        @Override
        protected String doInBackground(ArrayList<String>... params) {

            SQLiteDatabase myDatabase = getApplicationContext().openOrCreateDatabase("newsList", MODE_PRIVATE, null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS news (id INT, title VARCHAR, urlPath VARCHAR)");

            for (int i = 0; i < params[0].size(); i++) {
                Log.i("Story - " + i, dataToString("https://hacker-news.firebaseio.com/v0/item/" + params[0].get(i).toString() + ".json?print=pretty"));
            }


            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listWiew);
        listOfTitles.add("Title 1");
        listOfTitles.add("Title 2");
        listOfTitles.add("Title 3");
        ArrayAdapter adapterTitles = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listOfTitles);
        listView.setAdapter(adapterTitles);

       /* SQLiteDatabase myDatabase = this.openOrCreateDatabase("News", MODE_PRIVATE, null);

        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS news (newsId INT, title VARCHAR, urlPath VARCHAR)");

        myDatabase.execSQL("INSERT INTO news(newsId, title, urlPath) VALUES(14309756,'Inside Volta', 'https://devblogs.nvidia.com/parallelforall/inside-volta/')");

        Cursor c = myDatabase.rawQuery("SELECT * FROM news", null);
        Log.i("SQL columns", String.valueOf(c.getColumnCount()));
        Log.i("SQL column names", Arrays.toString(c.getColumnNames()));
        Log.i("SQL id index", String.valueOf(c.getColumnIndexOrThrow("newsId")));
        Log.i("SQL title index", String.valueOf(c.getColumnIndexOrThrow("title")));
        Log.i("SQL url index", String.valueOf(c.getColumnIndexOrThrow("urlPath")));


        c.moveToFirst();

        while(!c.isAfterLast()){
            Log.i("ID",String.valueOf(c.getInt(c.getColumnIndexOrThrow("newsId"))));
            Log.i("Title",c.getString(c.getColumnIndexOrThrow("title")));
            Log.i("Url",c.getString(c.getColumnIndexOrThrow("urlPath")));
            c.moveToNext();
        }

        myDatabase.close();*/

        DownloadDataTask dataTask = new DownloadDataTask();
        NewsArrayTask newsArrayTask = new NewsArrayTask();
        NewsToDatabaseTask newsToDatabaseTask = new NewsToDatabaseTask();

        String result = "";
        ArrayList<String> listNewsIndex = new ArrayList<>();
        try {
            result = dataTask.execute("https://hacker-news.firebaseio.com/v0/newstories.json?print=pretty").get();

            listNewsIndex = newsArrayTask.execute(result).get();

            newsToDatabaseTask.execute(listNewsIndex);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /*try {
            ArrayList<String> listNewsIndex = new ArrayList<>();
            listNewsIndex = newsArrayTask.execute(dataTask.execute("https://hacker-news.firebaseio.com/v0/newstories.json?print=pretty").get()).get();

            SQLiteDatabase myDatabase = getApplicationContext().openOrCreateDatabase("newsList", MODE_PRIVATE, null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS news (id INT, title VARCHAR, urlPath VARCHAR)");

            // Log.i("Story",new DownloadDataTask().execute("https://hacker-news.firebaseio.com/v0/item/14296252.json?print=pretty").get());
            for (int i = 0; i < listNewsIndex.size(); i++) {
                Log.i("Story - " + i, new DownloadDataTask().execute("https://hacker-news.firebaseio.com/v0/item/" + listNewsIndex.get(i).toString() + ".json?print=pretty").get());
                //  Log.i("Story1",new DownloadDataTask().execute("https://hacker-news.firebaseio.com/v0/item/14296252.json?print=pretty").get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/

        /*Intent intent = new Intent(getApplicationContext(),WebActivity.class);


        try {
            intent.putExtra("data",dataTask.execute("https://devblogs.nvidia.com/parallelforall/inside-volta/").get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/

        //https://hacker-news.firebaseio.com/v0/topstories
        //"https://hacker-news.firebaseio.com/v0/item/8863.json?print=pretty"
        //topstories


        //story
        //dataTask.execute("https://hacker-news.firebaseio.com/v0/item/14309756.json?print=pretty");
        //story url
        //dataTask.execute("https://devblogs.nvidia.com/parallelforall/inside-volta/");

    }

    public void functionButtonWeb(View view) {
        Intent intent = new Intent(getApplicationContext(), WebActivity.class);
        startActivity(intent);
    }

    private String dataToString(String urlPath) {
        String result = "";
        try {
            URL url = new URL(urlPath);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();

            InputStreamReader reader = new InputStreamReader(inputStream);

            int data = reader.read();

            while (data > -1) {

                char c = (char) data;
                result += c;
                data = reader.read();
            }
            //Log.i("dataToString", result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
