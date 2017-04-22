package com.example.a.apptimers;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(10000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                //Counter is counting 10 seconds with tik 1 second
                System.out.println("=======");
                Log.i("Test","Counter running");
                Log.i("Test", "Seconds till finish : "+(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                //Counter finished
                Log.i("Test","Counter finished");
            }
        }.start();

        /*//allows chunks of code be run in delayed way, controls time of execution of this events
        final Handler handler = new Handler();

        //event for handler
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("Test", "Runnable has run, a second must been passed");

                //this - refers to this runnable
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);*/

        //another way to do task regularly
        //countDownTimers
        // first value - 10000 - means how long will countdown work,
        // second value - 1000 - how long 1 tik will be
        Log.i("Test", "Here");

        //Log.i("Test", "There");
    }
}
