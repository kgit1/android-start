package com.example.a.appwhatstheweather;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private class DownloadWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data > -1) {
                    char current = (char) data;
                    result.append(current);
                    data = reader.read();
                }
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.i("Connect", "MalformedURLException");
                //special method to draw toast because of exception if toast runs not on UI thread
                errorToast("Cant get weather: wrong city name");
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("Connect", "IOException");
                errorToast("Cant get weather: wrong city name");
            }
            return null;
        }

        //executed on doInBackground() finish
        //string result = returned from doInBackground string result
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                Log.i("resultString", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultWeather = jsonObject.getString("weather");
                    String resultCoord = jsonObject.getString("coord");
                    String resultMainInfo = jsonObject.getString("main");
                    String resultVisibility = jsonObject.getString("visibility");
                    String resultWind = jsonObject.getString("wind");
                    String weatherForTextField = "";

                    JSONArray jsonArray = new JSONArray(resultWeather);
                    JSONObject tempJSON;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        tempJSON = jsonArray.getJSONObject(i);
                        weatherForTextField += "Main: " + tempJSON.getString("main")
                                + "\nDescription: " + tempJSON.getString("description");
                    }

                        tempJSON = new JSONObject(resultMainInfo);
                        weatherForTextField += "\nTemp: " + tempJSON.getString("temp")
                                + "\nPressure: " + tempJSON.getString("pressure")
                                + "\nHumidity: " + tempJSON.getString("humidity")
                                + "\nTemp_min: " + fahrenheitToCelsius(tempJSON.getString("temp_min"))
                                + "\nTemp_max: " + tempJSON.getString("temp_max");


                    textWeather.setText(weatherForTextField);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void errorToast(final String errorString) {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
            }
        }, 1000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCity = (EditText) findViewById(R.id.editTextCity);
        textWeather = (TextView) findViewById(R.id.textViewWeather);


    }

    public void functionGetWeather(View view) {
        String city = editCity.getText().toString();
        Log.i("city: ", city);

        //to get rid out from screen keyboard (which can block way if proposes variants)
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editCity.getWindowToken(), 0);

        new DownloadWeatherTask().execute("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=aabddda8fca6982bdf6299a97f0f0100");
    }

    public String fahrenheitToCelsius(String temperature){
        double temp = Double.parseDouble(temperature);
        temp = (temp - 32) * 5 / 9;
        return new String(""+temp);
    }
}









