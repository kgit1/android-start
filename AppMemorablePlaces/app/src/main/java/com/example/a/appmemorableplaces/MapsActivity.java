package com.example.a.appmemorableplaces;

import android.Manifest;
import android.content.Intent;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    String title;
    Intent intent;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Log.i("onRequest", "results");
                Location lasKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                mapMoveCamera(lasKnownLocation, "Your location");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
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

        intent = getIntent();
        int placeNumber = intent.getIntExtra("placeNumber", 0);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                title = "";
                try {
                    List<Address> listAddresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (listAddresses != null && listAddresses.size() > 0) {
                        if (listAddresses.get(0).getThoroughfare() != null) {
                            title += listAddresses.get(0).getThoroughfare();
                            if (listAddresses.get(0).getSubThoroughfare() != null) {
                                title += listAddresses.get(0).getSubThoroughfare();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (title.equals("")) {
                    title += "Memorable place";
                }
                SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
                title += " " + dateTime.format(new Date());

                //update values in another activity(we made them static to reach from here)
                MainActivity.placesNames.add(title);
                MainActivity.placesLocations.add(latLng);
                //invoke dataChange on adapter in another activity
                MainActivity.arrayAdapter.notifyDataSetChanged();

                Log.i("AddressGeo", title);
                mMap.addMarker(new MarkerOptions().position(latLng).title(title));

                Toast.makeText(getApplicationContext(), "Location Saved", Toast.LENGTH_SHORT).show();
            }
        });

        if (placeNumber == 0) {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mapMoveCamera(location, title);
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

            if (Build.VERSION.SDK_INT < 23) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    mapMoveCamera(lastKnownLocation, "Your location");
                }
            }
        } else {
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(MainActivity.placesLocations.get(placeNumber).latitude);
            location.setLongitude(MainActivity.placesLocations.get(placeNumber).longitude);

            mapMoveCamera(location, MainActivity.placesNames.get(placeNumber));
        }

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));*/
    }

    public void mapMoveCamera(Location location, String title) {
        Log.i("locationDraw", location.toString());
        LatLng userLoc = new LatLng(location.getLatitude(), location.getLongitude());
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(userLoc));
        if (!title.equals("You location")) ;
        {
            mMap.addMarker(new MarkerOptions().position(userLoc).title(title));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 12));
    }
}
