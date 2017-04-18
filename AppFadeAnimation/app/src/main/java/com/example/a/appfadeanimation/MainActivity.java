package com.example.a.appfadeanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    boolean clicked = false;

    public void functionFade(View view) throws InterruptedException {
        Log.i("Test", "fade clicked");

        ImageView imageBart = (ImageView) findViewById(R.id.imageBart);
        ImageView imageBart2 = (ImageView) findViewById(R.id.imageBart2);

        if (!clicked) {
            //animate() needs to know what and how long to do
            //alpha - transparency
            imageBart.animate().alpha(0f).setDuration(2000);
            imageBart2.animate().alpha(1f).setDuration(2000);
            clicked = true;
        } else {
            imageBart.animate().alpha(1f).setDuration(2000);
            imageBart2.animate().alpha(0f).setDuration(2000);
            clicked = false;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
