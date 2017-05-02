package com.example.a.appsharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

//add ObjectSerializer class to convert arrayList to string
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mode_private - make usable only for this app
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.a.appsharedpreferences", Context.MODE_PRIVATE);
       // sharedPreferences.edit().putString("userName","rob").apply();

        //in getString() first string - key for value, second - default value if key returns nothing or wrong
        String userName = sharedPreferences.getString("userName","1");

        Log.i("shared string", userName);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("Marry");
        strings.add("has");
        strings.add("a little");
        strings.add("lamb");

        try {
            sharedPreferences.edit().putString("strings", ObjectSerializer.serialize(strings)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> restoredStrings = new ArrayList<>();
        try {
            restoredStrings= (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("strings",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("restoredString",restoredStrings.toString());
    }
}
