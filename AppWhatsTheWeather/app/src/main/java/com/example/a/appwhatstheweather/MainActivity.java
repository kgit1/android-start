package com.example.a.appwhatstheweather;

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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    //unsplash.com - free images for background
    EditText editCity;
    TextView textWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCity = (EditText) findViewById(R.id.editTextCity);
        textWeather = (TextView) findViewById(R.id.textViewWeather);
    }

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
                String weatherForTextField = "";
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultWeather = jsonObject.getString("weather");

                    JSONArray jsonArray = new JSONArray(resultWeather);
                    JSONObject tempJSON;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        tempJSON = jsonArray.getJSONObject(i);
                        weatherForTextField += "Main: " + tempJSON.getString("main")
                                + "\nDescription: " + tempJSON.getString("description");
                    }

                    tempJSON = jsonObject.getJSONObject("main");
                    weatherForTextField += "\nTemp: " + kelvinToCelsius(tempJSON.getString("temp"))
                            + "\nPressure: " + tempJSON.getString("pressure")
                            + "\nHumidity: " + tempJSON.getString("humidity")
                            + "\nTemp_min: " + kelvinToCelsius(tempJSON.getString("temp_min"))
                            + "\nTemp_max: " + kelvinToCelsius(tempJSON.getString("temp_max"));

                    tempJSON= jsonObject.getJSONObject("coord");
                    weatherForTextField+="\nLon: " + tempJSON.getString("lon")
                            +"\nLat: "+tempJSON.getString("lat");


                    weatherForTextField+="\n"+jsonObject.getString("name");

                    textWeather.setText(weatherForTextField);
                } catch (JSONException e) {
                    textWeather.setText(noneInfo());
                    e.printStackTrace();
                }
            }else{
                textWeather.setText(noneInfo());
            }
        }
    }

    //to call Toast from non-UI thread
    public void errorToast(final String errorString) {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
            }
        }, 1000);
    }

    public void functionGetWeather(View view) {
        String city = editCity.getText().toString();
        Log.i("city: ", city);
        //to encode string city name to proper url(to handle spaces in name etc)
        try {
            city= URLEncoder.encode(city,"UTF-8");
            Log.i("city encoded: ", city);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //to get rid out from screen keyboard (which can block way if proposes variants)
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editCity.getWindowToken(), 0);

        new DownloadWeatherTask().execute("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=aabddda8fca6982bdf6299a97f0f0100");
    }

    public String kelvinToCelsius(String temperature) {
        float temp = Float.parseFloat(temperature);
        temp = temp - 273.15F;
        return new String("" + Math.round(temp));
    }

    public String noneInfo(){
        return "Main: " + "none"
                + "\nDescription: " + "none"
                + "\nTemp: " + "none"
                + "\nPressure: " + "none"
                + "\nHumidity: " + "none"
                + "\nTemp_min: " + "none"
                + "\nTemp_max: " + "none";
    }
}









