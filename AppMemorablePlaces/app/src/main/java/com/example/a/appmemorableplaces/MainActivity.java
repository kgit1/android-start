package com.example.a.appmemorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> placesNames = new ArrayList<>();
    static ArrayList<LatLng> placesLocations = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        LatLng latLng = (LatLng) intent.getParcelableExtra("location");
        if (latLng != null) {
            Toast.makeText(getApplicationContext(), "LocOnMain" + latLng.toString(), Toast.LENGTH_SHORT);
        }

        ListView listView = (ListView) findViewById(R.id.listOfPlaces);

        Log.i("names", placesNames.toString());

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.google.android.gms.maps.model.LatLng", Context.MODE_PRIVATE);
        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();

        placesNames.clear();
        placesLocations.clear();
        latitudes.clear();
        longitudes.clear();

        try {
            placesNames.addAll((ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("placesNames", ObjectSerializer.serialize(new ArrayList<String>()))));
            latitudes.addAll((ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitudes", ObjectSerializer.serialize(new ArrayList<String>()))));
            Log.i("Lat: ", latitudes.toString());
            longitudes.addAll((ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longitudes", ObjectSerializer.serialize(new ArrayList<String>()))));
            Log.i("Lon: ", longitudes.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (placesNames.size() > 0 && latitudes.size() > 0 && longitudes.size() > 0) {
            if (placesNames.size() == latitudes.size() && latitudes.size() == longitudes.size()) {
                for (int i = 0; i < latitudes.size(); i++) {
                    placesLocations.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))));
                }
            }
        } else {
            placesNames.add("Add new memorable place");
            placesLocations.add(new LatLng(0, 0));
        }


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, placesNames);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("placeNumber", position);
                startActivity(intent);
            }
        });
    }
}
