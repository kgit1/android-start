package com.example.a.appmapsuserslocation;

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
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    double lat;
    double lon;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        lat = 0;
        lon = 0;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location: ", location.toString());
                //to draw marker on map on user move
                lat = location.getLatitude();
                lon = location.getLongitude();
                LatLng userLoc = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(userLoc).title("User-" + lat + ":lon"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLoc));

                //to transform latitude and longitude to actual address
                //A class for handling geocoding and reverse geocoding. Geocoding is the process of
                // transforming a street address or other description of a location into
                // a (latitude, longitude) coordinate. Reverse geocoding is the process of
                // transforming a (latitude, longitude) coordinate into a (partial) address.
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses != null && addresses.size() > 0) {
                        Log.i("Address", addresses.get(0).toString());
                        List<String> address = new ArrayList<>();
                        Pattern p1 = Pattern.compile("0:\"" + "(.*?)" + "\"");
                        Pattern p2 = Pattern.compile("countryCode=" + "(.*?)" + ",");
                        Pattern p3 = Pattern.compile("countryName=" + "(.*?)" + ",");
                        Pattern p4 = Pattern.compile("postalCode=" + "(.*?)" + ",");
                        Pattern p5 = Pattern.compile("latitude=" + "(.*?)" + ",");
                        Pattern p6 = Pattern.compile("longitude=" + "(.*?)" + ",");

                        Matcher match = p1.matcher(addresses.get(0).toString());
                        while (match.find()) {
                            address.add("Address: " + match.group(1));
                        }
                        match = p2.matcher(addresses.get(0).toString());
                        while (match.find()) {
                            address.add("countryCode: " + match.group(1));
                        }
                        match = p3.matcher(addresses.get(0).toString());
                        while (match.find()) {
                            address.add("countryName: " + match.group(1));
                        }
                        match = p4.matcher(addresses.get(0).toString());
                        while (match.find()) {
                            address.add("postalCod: " + match.group(1));
                        }
                        match = p5.matcher(addresses.get(0).toString());
                        while (match.find()) {
                            address.add("latitude=: " + match.group(1));
                        }
                        match = p6.matcher(addresses.get(0).toString());
                        while (match.find()) {
                            address.add("longitude=: " + match.group(1));
                        }

                        String forToast = "";
                        for (int i = 0; i < address.size(); i++) {
                            forToast += "\n" + address.get(i);
                        }
                        Log.i("forToast", forToast);

                        Toast.makeText(getApplicationContext(), forToast, Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

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

        //if SDK < 23(Marshmallow) - we dont need permission and can ask location immediately
        if (Build.VERSION.SDK_INT < 23) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            //if SDK > 23
        } else {
            //check do we have permission
            //if haven't - ask
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //ask permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }//if have - run code to ask location
            else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                //to draw marker on map on start, not only when user moves
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                Log.i("lastLoc", lastKnownLocation.toString());
                lat = lastKnownLocation.getLatitude();
                lon = lastKnownLocation.getLongitude();
                LatLng userLoc = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(userLoc).title("User-lat:" + lat + ":lon" + lon));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLoc));
            }
        }
    }
}
