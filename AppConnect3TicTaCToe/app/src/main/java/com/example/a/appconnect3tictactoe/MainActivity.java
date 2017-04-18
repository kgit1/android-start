package com.example.a.appconnect3tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void functionDrop(View view){
        Log.i("Test","function drop");
        ImageView counter = (ImageView) view;

        counter.setTranslationY(-1000f);
        counter.setRotation(0f);

        counter.setImageResource(R.drawable.red);

        counter.animate().translationY(0f).rotation(720f).setDuration(1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
