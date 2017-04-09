package com.example.a.demoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//appCompatActivity makes sure that app compatable with as many old android versions as needed
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
