package com.example.a.appmemorableplaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

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

        Log.i("names",placesNames.toString());

        placesNames.add("Add new memorable place");
        placesLocations.add(new LatLng(0, 0));

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, placesNames);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("placeNumber",position);
                startActivity(intent);
            }
        });
    }
}
