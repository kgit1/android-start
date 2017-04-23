package com.example.a.appbrainteaser;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//style for fullscreen at the bottom
public class MainActivity extends AppCompatActivity {

    RelativeLayout rLayout;
    TextView textCounter;
    TextView textEquation;
    TextView textResults;
    TextView textAnswer;
    TextView textTasks;
    Button buttonStart;
    int answer;
    int answerGiven;
    int tasks;
    int wrongTasks;
    int rightTasks;
    boolean started;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rLayout =(RelativeLayout)findViewById(R.id.relativeLayout);
        rLayout.setVisibility(View.INVISIBLE);

        textCounter = (TextView) findViewById(R.id.textCounter);
        textEquation = (TextView) findViewById(R.id.textEquation);
        textResults = (TextView) findViewById(R.id.textResults);
        textAnswer = (TextView) findViewById(R.id.textAnswer);
        textTasks = (TextView) findViewById(R.id.textTasks);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        started = false;

        reset();

        countDownTimer = new CountDownTimer(0, 0) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        };
    }

    public void functionButtonStart(View view) {
        Log.i("Test", " start click");
        buttonStart.setVisibility(View.INVISIBLE);
        rLayout.setVisibility(View.VISIBLE);
        reset();
        started = true;
        functionWork();
    }

    public void functionCounter() {
        countDownTimer = new CountDownTimer(16000 + 100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("Test", "tik " + millisUntilFinished / 1000);
                textCounter.setText("" + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                Log.i("Test", "finish");
                textCounter.setText("" + 0);
                wrongTasks++;
                if (tasks - (wrongTasks + rightTasks) > 0) {
                    functionWork();
                } else {
                    buttonStart.setVisibility(View.VISIBLE);
                }
            }
        }.start();
    }

    public void functionWork() {
        countDownTimer.cancel();
        functionEquation();
        functionCounter();
        functionResult();
    }

    public void functionEquation() {
        int randomNumber1 = randomNumber(50);
        int randomNumber2 = randomNumber(50);
        int randomIndex = randomNumber(4);
        answer = randomNumber1 + randomNumber2;
        textEquation.setText("" + randomNumber1 + " + " + randomNumber2);
        textAnswer.setText("" + answer);
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout);
        TextView temp;
        for (int i = 0; i < 4; i++) {
            temp = (TextView) grid.getChildAt(i);
            if (i == randomIndex) {
                temp.setText("" + answer);
            } else {
                temp.setText("" + randomNumbers(50));
            }
        }
    }

    public void functionResult() {
        textTasks.setText((rightTasks + wrongTasks) + "/" + tasks);
        textResults.setText(rightTasks + "/" + (rightTasks + wrongTasks));
    }

    public void functionGetAnswer(View view) {
        if (started) {
            TextView textView = (TextView) findViewById(view.getId());
            answerGiven = Integer.parseInt(textView.getText().toString());
            if (answer == answerGiven) {
                rightTasks++;
            } else {
                wrongTasks++;
            }
            if (tasks - (wrongTasks + rightTasks) >= 0) {
                functionWork();
            } else {
                countDownTimer.cancel();
                started = false;
                buttonStart.setVisibility(View.VISIBLE);
            }
            Log.i("Test", "answer clicked - " + answerGiven);
        }
    }

    public int randomNumber(int topRandom) {
        return (int) (Math.random() * topRandom);
    }

    public int randomNumbers(int topRandom) {
        return randomNumber(topRandom) + randomNumber(topRandom);
    }

    public void reset() {
        textCounter.setText("");
        textEquation.setText("");
        textResults.setText("");
        textAnswer.setText("");
        tasks = 15;
        wrongTasks = 0;
        rightTasks = 0;
    }
}

/*
Update Answer I added android:windowIsTranslucent in case you have white screen in start of activity

just create new Style in values/styles.xml

<?xml version="1.0" encoding="utf-8"?>
<resources>

    <style name="AppTheme" parent="Theme.AppCompat.NoActionBar">
    <!-- to hide white screen in start of window -->
    <item name="android:windowIsTranslucent">true</item>
    </style>

</resources>
from your AndroidManifest.xml add style to your activity

android:theme="@style/AppTheme"*/
//===========  OR  =========================================================

/*Just add the following attribute to your current theme:

<item name="android:windowFullscreen">true</item>
For example:

<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
    <!-- Customize your theme here. -->
    <item name="colorPrimary">@color/orange</item>
    <item name="colorPrimaryDark">@android:color/holo_orange_dark</item>
    <item name="android:windowNoTitle">true</item>
    <item name="android:windowFullscreen">true</item>
</style>*/
//===========  OR  =========================================================

/*In onCreate call

requestWindowFeature(Window.FEATURE_NO_TITLE); // for hiding title

getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
