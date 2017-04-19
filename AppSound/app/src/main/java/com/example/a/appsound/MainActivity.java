package com.example.a.appsound;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    //soundbible.com - to download various sound files

    MediaPlayer mediaPlayer;

    public void functionPlayButton(View view){
        mediaPlayer = MediaPlayer.create(this, R.raw.laugh);
        mediaPlayer.start();
    }

    public void functionPauseButton(View view){
        mediaPlayer.pause();
    }

    public void functionStopButton(View view){
        mediaPlayer.stop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBarVolume);

        //listenning when user changing position on seekbar
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
