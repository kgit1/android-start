package com.example.a.appcurrency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText textLogin;
    EditText textPassword;
    boolean catOrDogClicked;
    boolean scaleClicked;
    ImageView imageCatOrDog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLogin = (EditText) findViewById(R.id.textLogin);
        textPassword= (EditText) findViewById(R.id.textPassword);
        catOrDogClicked = false;
        scaleClicked = false;
        imageCatOrDog = (ImageView) findViewById(R.id.imageCatOrNot);

        Button buttonLogin= (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new ButtonLoginListener());

        Button buttonCatOrDog = (Button) findViewById(R.id.buttonCatDog);
        buttonCatOrDog.setOnClickListener(new ButtonCatOrDogListener());

        Button buttonScaleCatOrDog= (Button) findViewById(R.id.buttonScale);
        buttonScaleCatOrDog.setOnClickListener(new ButtonScaleCatOrDogListener());
    }

    private class ButtonCatOrDogListener extends AppCompatActivity implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Log.i("Test", "swith listener");
          if(!catOrDogClicked){
               imageCatOrDog.setImageResource(R.drawable.dogy);
              catOrDogClicked=true;
           }else{
               imageCatOrDog.setImageResource(R.drawable.cat1);
              catOrDogClicked=false;
           }
        }
    }

    private class ButtonScaleCatOrDogListener extends AppCompatActivity implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Log.i("Test", "scale listener");
            if(!scaleClicked){
                imageCatOrDog.setScaleType(ImageView.ScaleType.CENTER);
                scaleClicked = true;
            }else{
                imageCatOrDog.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                scaleClicked = false;
            }
        }
    }

    private class ButtonLoginListener extends AppCompatActivity implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Log.i("Test","login listener");
            String login = "root";
            String password = "admin";

            if(login.equals(textLogin.getText().toString())){
                if(password.equals(textPassword.getText().toString())){
                    Toast.makeText(MainActivity.this, "Login: " + textLogin.getText().toString(),Toast.LENGTH_SHORT).show();

                    Toast.makeText(MainActivity.this, "Password: "+textPassword.getText().toString(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Error, password - \""+textPassword.getText().toString()+"\" is incorrect",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(MainActivity.this,"Error, login - \""+textLogin.getText().toString()+"\" is incorrect",Toast.LENGTH_LONG).show();
            }
        }
    }


    /*public void functionButtonLogin(View view){
        Log.i("Test"," button login clicked");
        EditText textLogin = (EditText) findViewById(R.id.textLogin);
        EditText textPassword = (EditText) findViewById(R.id.textPassword);
    }

    public void functionButtonScale(View view){
        Log.i("Test", "button scale clicked");
        ImageView imageView= (ImageView) findViewById(R.id.imageCatOrNot);
        imageView.setImageResource(R.drawable.dogy);
    }*/

}
