package com.example.a.appsound;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    //soundbible.com - to download various sound files

    //media player object - to create media player for sound files
    MediaPlayer mediaPlayer;

    //audio manager object - to interact with android aurio system to change volume
    AudioManager audioManager;
    boolean started = false;
    boolean pause = false;

    public void functionPlayButton(View view) {
        if (pause) {
            mediaPlayer.start();
        } else if (!started) {
            mediaPlayer = MediaPlayer.create(this, R.raw.laugh);
            mediaPlayer.start();
            started = true;
        }
    }

    public void functionPauseButton(View view) {
        if (pause) {
            mediaPlayer.start();
            pause = false;
        } else {
            mediaPlayer.pause();
            pause = true;
        }
    }

    public void functionStopButton(View view) {
        mediaPlayer.stop();
        started = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get system audio volume level
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //create max volume value - by getting it from system audio manager music stream
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //create seekBar object for volume control
        SeekBar volumeControl = (SeekBar) findViewById(R.id.seekBarVolume);
        //set max needed value for volume seek bar
        //put max system value get earlie in maxVolume value
        volumeControl.setMax(maxVolume);

        //set current value to volume seek bar
        volumeControl.setProgress(currentVolume);

        //listenning when user changing position on seekbar
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Test", "progressBar changed");
                Log.i("Test", Integer.toString(progress));

                //set volume value from seek bar to audio manager
                //first value - stream which is used
                //second value - volume value - progress from seek bar
                //third value - boolean flags if needed, so 0 for now
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
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
