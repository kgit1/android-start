package com.robpercival.demoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void clickFunction(View view){
        EditText stringEnterName = (EditText) findViewById(R.id.myTextField);
        EditText stringEnterPassword = (EditText) findViewById(R.id.myPasswordField);

        Toast.makeText(MainActivity.this, "Login: "+stringEnterName.getText().toString(),
                Toast.LENGTH_SHORT).show();

        Toast.makeText(MainActivity.this, "Password: "+stringEnterPassword.getText().toString(),
                Toast.LENGTH_SHORT).show();

        Toast.makeText(MainActivity.this, "Hi there, Queen of Meereen, Queen of the Andals" +
                "(, the Rhoynar) and the First Men, Lady Regnant of the Seven Kingdoms, Khaleesi"+
                "of the Great Grass Sea, Mhysa, Breaker of Chains, the Unburnt, Mother of Dragons",
                Toast.LENGTH_LONG).show();

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
