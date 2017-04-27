package com.example.a.appwhatstheweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    //unsplash.com - free images for background
    EditText editCity;
    TextView textWeather;

    private class DownloadWeatherTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data>-1){
                    char current = (char)data;
                    result.append(current);
                    data=reader.read();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result.toString();
        }

        //executed on doInBackground() finish
        //string result = returned from doInBackground string result
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String resultWeather = jsonObject.getString("weather");
                String weatherForTextField="";

                JSONArray jsonArray = new JSONArray(resultWeather);
                for(int i =0;i<jsonArray.length();i++){
                    JSONObject temp = jsonArray.getJSONObject(i);
                    weatherForTextField += "Main: " + temp.getString("main")+"\nDescription: " + temp.getString("description");
                }
                textWeather.setText(weatherForTextField);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCity = (EditText) findViewById(R.id.editTextCity);
        textWeather=(TextView) findViewById(R.id.textViewWeather);


    }

    public void functionGetWeather(View view){
        String city = editCity.getText().toString();
        Log.i("city: ", city);
        new DownloadWeatherTask().execute("http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=aabddda8fca6982bdf6299a97f0f0100");

    }
}
