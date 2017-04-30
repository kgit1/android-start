package com.example.a.applocationdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.i("request", "1");
        //if we have response and response tell that we have permission
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i("request", "2");
            //here we need one more check if we do have permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.i("request", "3");
                //if we do have permission - call requestLocationUpdates() method for locationManager
                //which will add listener to it, and give details for listener
                // LocationManager.GPS_PROVIDER - as location data provider(GPS because we choose ACCESS_FINE_LOCATION
                // 0 - for min time between ask on location changes - we can put some value to save battery, like to ask only every 10 seconds or more
                // 0 - for min distance of location change to give signal to method
                // locationListener

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get users location
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Log.i("Location", locationManager.toString());
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.i("Location1", location.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //if device running SDK <23(Marshmallow)
        if (Build.VERSION.SDK_INT < 23) {
            Log.i("request", "6");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            //we need to ask user permission for location explicitly
            //first check if we already have permission, call object ContextCompat -
            // Helper for accessing features in {@link android.content.Context}
            // introduced after API level 4 in a backwards compatible fashion.
            //invoke method checkSelfPermissions() which needs Context and permission name
            // Context - this, permission name - Manifest.permission.ACCESS_FINE_LOCATION
            //if no permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("request", "4");
                //ask for it
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                Log.i("request", "5");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }
}
