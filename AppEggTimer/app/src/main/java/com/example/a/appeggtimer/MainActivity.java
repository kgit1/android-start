package com.example.a.appeggtimer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textTime;
    ImageView imageEgg;
    Button buttonGo;
    Button buttonPause;
    Button buttonStop;
    SeekBar seekBarTimer;
    CountDownTimer countDown;
    int count = 0;
    boolean isPaused;
    boolean isCanceled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBarTimer = (SeekBar) findViewById(R.id.seekBarTimer);
        textTime = (TextView) findViewById(R.id.textTime);
        buttonGo = (Button) findViewById(R.id.buttonGo);
        buttonPause = (Button) findViewById(R.id.buttonPause);
        buttonPause.setEnabled(false);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonStop.setEnabled(false);
        imageEgg = (ImageView) findViewById(R.id.imageEgg);
        CountDownTimer countDown;
        isPaused = false;
        isCanceled = false;

        seekBarTimer.setMax(600);
        seekBarTimer.setProgress(0);

        textTime.setText(timerChanger(seekBarTimer.getProgress()));

        seekBarTimer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                count = progress;
                textTime.setText(timerChanger(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private String timerChanger(int progress) {
        String seconds = "";
        String minutes = "";
        if (progress > 0) {
            minutes = "" + progress / 60;
            seconds = "" + progress % 60;
            if (progress % 60 <= 9) {
                seconds = "0" + seconds;
            }
        } else {
            minutes = "0";
            seconds = "00";
        }
        return minutes + " : " + seconds;
    }


    public void functionButtonGo(View view) {
        if (count > 0) {
            buttonGo.setEnabled(false);
            buttonPause.setEnabled(true);
            buttonStop.setEnabled(true);
            isPaused = false;
            isCanceled = false;

            //+100 to fix light lag on start end
            countDown = new CountDownTimer(count * 1000+100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    if (isPaused || isCanceled) {
                        cancel();
                    } else {
                        seekBarTimer.setProgress((int) millisUntilFinished / 1000);
                        textTime.setText(timerChanger((int) millisUntilFinished / 1000));
                    }
                }

                @Override
                public void onFinish() {
                    Log.i("Test", "Count down finished!");
                    timerReset();
                }
            }.start();
        } else {
            Toast.makeText(getApplicationContext(), "First drag scrabbler to choose time to count down", Toast.LENGTH_LONG).show();
        }
    }

    public void functionButtonPause(View view) {
        buttonGo.setEnabled(true);
        buttonPause.setEnabled(false);
        isPaused = true;
    }

    public void functionButtonStop(View view) {
        timerReset();
    }

    public void timerReset(){
        buttonGo.setEnabled(true);
        //buttonGo.setVisibility(View.VISIBLE);
        //buttonStop.setVisibility(View.INVISIBLE);
        count = 0;
        seekBarTimer.setProgress(count);
        textTime.setText("0:00");
        isCanceled = true;
    }

    /*seekBar.setOnTouchListener(new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return blockSeekbar;
        }
    });*/

}

