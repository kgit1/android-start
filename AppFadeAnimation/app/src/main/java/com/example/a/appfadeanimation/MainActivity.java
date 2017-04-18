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
        // imageBart2.setTranslationY(-2000f);

        if (!clicked) {
            functionRestartImage(imageBart2);
            imageBart2.setAlpha(0f);
            //animate() needs to know what and how long to do
            //alpha - transparency
            imageBart.animate().alpha(0f).setDuration(2000);
            imageBart2.animate().alpha(1f).setDuration(2000);
            clicked = true;
        } else {
            functionRestartImage(imageBart2);
            imageBart.animate().alpha(1f).setDuration(2000);
            //translation moving object on amount of pixels
            //scale changing images scale 0-1
            imageBart2.animate().translationYBy(2000f).translationX(1000f).scaleX(0.5f).scaleY(0.5f).setDuration(2000);
            clicked = false;
        }
    }

    private void functionRestartImage(ImageView image) {
        image.setTranslationY(0f);
        image.setTranslationX(0f);
        image.setScaleX(1f);
        image.setScaleY(1f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
