package com.example.a.appmultipleactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();

        Toast.makeText(getApplicationContext(),intent.getStringExtra("textName"),Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),intent.getStringExtra("string"),Toast.LENGTH_SHORT).show();
    }

    public void functionButtonSwitchToMain(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("userName","Rob");
        startActivity(intent);
    }
}
