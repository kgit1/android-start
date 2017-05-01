package com.example.a.apphikerswatch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    TextView textView;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.i("permission: ", "6");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //location manager initialize
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        textView = (TextView) findViewById(R.id.textView);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("addressData: ", location.toString());
                textView.setText(locationData(location));
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

        //if SDK < 23(Marshmallow) - we don't need permission and can ask location immediately
        if (Build.VERSION.SDK_INT < 23) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }//if SDK > 23
        else {
            //check do we have permission
            //if haven't - ask
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //ask permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }//if have - run code to ask location
            else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(lastKnownLocation!=null) {
                    textView.setText(locationData(lastKnownLocation));
                }
            }
        }
    }


    public String locationData(Location location) {
        String addressData = "";

        if (location != null) {
            addressData += "Latitude: " + location.getLatitude()
                    + "\nLongitude: " + location.getLongitude()
                    + "\nAccuracy: " + location.getAccuracy()
                    + "\nAttitude: " + location.getAltitude();


            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                Address address = addresses.get(0);
                if (address.getSubThoroughfare() != null) {
                    addressData += "\nAddress: " + address.getSubThoroughfare();
                }
                if (address.getThoroughfare() != null) {
                    addressData += ",\n" + address.getThoroughfare();
                }
                if (address.getLocality() != null) {
                    addressData += "\n" + address.getLocale();
                }
                if (address.getCountryName() != null) {
                    addressData += "\n" + address.getCountryName();
                }
                if (address.getCountryCode() != null) {
                    addressData += " " + address.getCountryCode();
                }
                if (address.getPostalCode() != null) {
                    addressData += "\n" + address.getPostalCode();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.i("addressData: ", addressData);
        return addressData;
    }
}

































































