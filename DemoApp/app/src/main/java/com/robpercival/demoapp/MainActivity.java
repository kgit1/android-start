package com.robpercival.demoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void clickFunction(View view){
        EditText stringEnterName = (EditText) findViewById(R.id.myTextField);
        EditText stringEnterPassword = (EditText) findViewById(R.id.myPasswordField);
        System.out.println("Clicked");
        Log.i("Info", "Button pressed");
        Log.i("Name", stringEnterName.getText().toString());
        Log.i("Password",stringEnterPassword.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
