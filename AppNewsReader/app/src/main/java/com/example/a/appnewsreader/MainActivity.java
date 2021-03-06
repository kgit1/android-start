package com.example.a.appnewsreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> contents = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    boolean refresh= false;

    static SQLiteDatabase articlesDB;

    private class AllWorkTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            refresh=false;
            //get news indexes
            String result = dataFromUrl(params[0]);

            //get news by index
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(result);

                int numberOfResults = 20;
                if (jsonArray.length() < 20) {
                    numberOfResults = jsonArray.length();
                }

                //clear DB
                articlesDB.execSQL("DELETE FROM articles");

                //get data from news - title, by and url -> get content by url -> put all to DB
                for (int i = 0; i < numberOfResults; i++) {
                    String articleId = jsonArray.getString(i);
                    String data = dataFromUrl("https://hacker-news.firebaseio.com/v0/item/" + articleId + ".json?print=pretty");
                    JSONObject jsonObject = new JSONObject(data);

                    String by = "";
                    String articleTitle = "";
                    String articleUrl = "";

                    if (!jsonObject.isNull("title") && !jsonObject.isNull("url")) {
                        articleTitle = jsonObject.getString("title");
                        articleUrl = jsonObject.getString("url");
                        if (!jsonObject.isNull("by")) {
                            by = jsonObject.getString("by");
                        }
                        String articleContent = htmlDataFromUrl(articleUrl);
                        //Log.i("TEST","content");
                        //Log.i("HTML",articleContent);

                        String sql = "INSERT INTO articles (articleId, title, content) VALUES(?,?,?)";

                        SQLiteStatement statement = articlesDB.compileStatement(sql);

                        statement.bindString(1, articleId);
                        statement.bindString(2, articleTitle);
                        statement.bindString(3, articleContent);

                        statement.execute();
                    }
                    Log.i("Story", "Title: " + articleTitle + " - by " + by);
                    Log.i("URL", articleUrl);
                    Log.i("DONE", "task: " + i);
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            updateListView();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listWiew);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(arrayAdapter);

        articlesDB = this.openOrCreateDatabase("articles", MODE_PRIVATE, null);

        articlesDB.execSQL("CREATE TABLE IF NOT EXISTS articles (id INTEGER PRIMARY KEY, articleID INT, title VARCHAR, content VARCHAR)");

        updateListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                intent.putExtra("content", contents.get(position));

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        AllWorkTask work = new AllWorkTask();
        work.execute("https://hacker-news.firebaseio.com/v0/newstories.json?print=pretty");
        return true;

    }

    private void updateListView() {
        Cursor c = articlesDB.rawQuery("SELECT * FROM articles", null);

        int contentIndex = c.getColumnIndex("content");
        int titleIndex = c.getColumnIndex("title");

        c.moveToFirst();
        if (!c.isAfterLast()) {
            titles.clear();
            contents.clear();

            do {
                titles.add(c.getString(titleIndex));
                contents.add(c.getString(contentIndex));
            } while (c.moveToNext());

            arrayAdapter.notifyDataSetChanged();
        }
        c.close();
    }

    //get data char by char
    private String dataFromUrl(String urlPath) {
        String result = "";
        URL url;
        HttpURLConnection httpURLConnection;

        try {
            url = new URL(urlPath);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            int data = reader.read();

            while (data != -1) {
                char current = (char) data;
                result += current;
                data = reader.read();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //get data through buffer line by line
    private String htmlDataFromUrl(String urlPath) {
        //use commons-io String html = IOUtils.toString(url.openStream(), "utf-8"); ??
        StringBuilder result=new StringBuilder();
        URL url;
        HttpURLConnection httpURLConnection;
        BufferedReader bufferedReader = null;

        try {
            url = new URL(urlPath);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(reader);

            String line = null;

            while ((line = bufferedReader.readLine())!=null) {
                result.append(line+"\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //Log.i("Test","html method");
        //Log.i("HTML method", result.toString());
        return result.toString();
    }
}
