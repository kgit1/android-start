package com.example.a.appsound;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    //soundbible.com - to download various sound files

    //media player object - to create media player for sound files
    MediaPlayer mediaPlayer;

    //audio manager object - to interact with android aurio system to change volume
    AudioManager audioManager;
    AudioManager audioManagerFile;
    boolean started = false;
    boolean pause = false;

    SeekBar fileControl;

    TextView textVolume;
    TextView textProgress;
    String volume;
    String progress;
    int mediaLength;
    int currentLength;

    Button buttonPlay;
    Button buttonPause;
    Button buttonRepeat;


    public void functionPlayButton(View view) throws IOException {
        if (!mediaPlayer.isPlaying() && started) {
            mediaPlayer.start();
            buttonPause.setEnabled(true);
        } else if (!started) {
            mediaPlayer = MediaPlayer.create(this, R.raw.laugh);
            mediaPlayer.start();
            buttonPause.setEnabled(true);
            started = true;
        }
    }

    public void functionPauseButton(View view) {
        if (pause) {
            mediaPlayer.start();
            buttonPlay.setEnabled(true);
            pause = false;
        } else {
            mediaPlayer.pause();
            buttonPlay.setEnabled(false);
            pause = true;
        }
    }

    public void functionStopButton(View view) {
        mediaPlayer.stop();
        buttonPlay.setEnabled(true);
        buttonPause.setEnabled(false);
        fileControl.setProgress(1);
        //When you are done with it, you should always call release() to make sure any system
        // resources allocated to it are properly released.
        //As you may know, when the user changes the screen orientation (or changes the device
        // configuration in another way), the system handles that by restarting the activity
        // (by default), so you might quickly consume all of the system resources as the user
        // rotates the device back and forth
        // mediaPlayer.release();
        //mediaPlayer = null;

        started = false;
    }

    public void functionRepeatButton(View view) {
        if (mediaPlayer.isLooping()) {
            Log.i("Test", "isnt looping");
            mediaPlayer.setLooping(false);
            buttonRepeat.setActivated(true);
        } else {
            Log.i("Test", "is looping");
            mediaPlayer.setLooping(true);
            buttonRepeat.setActivated(false);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.laugh);

        textVolume = (TextView) findViewById(R.id.textVolume);
        textProgress = (TextView) findViewById(R.id.textProgress);

        buttonPlay = (Button) findViewById(R.id.buttonStart);
        buttonPause = (Button) findViewById(R.id.buttonPause);
        buttonPause.setEnabled(false);
        buttonRepeat = (Button) findViewById(R.id.buttonRepeat);

        ////////AUDIO FILE CONTROL///////////////////////////
        //get media file duration
        mediaLength = mediaPlayer.getDuration();
        progress = "";
        progress = "Progress: " + 0.00 + " - " + mediaLength;
        textProgress.setText(progress);


        fileControl = (SeekBar) findViewById(R.id.seekBarAudioLength);
        fileControl.setMax(mediaLength);

        //create timer object to complete task on timer tik
        new Timer().scheduleAtFixedRate(new TimerTask() {
                                            @Override
                                            public void run() {
                                                //get current position of the file playback
                                                //currentLength = mediaPlayer.getCurrentPosition();
                                                if (mediaPlayer.isPlaying()) {
                                                    fileControl.setProgress(mediaPlayer.getCurrentPosition());
                                                }
                                                //progress="";
                                                //progress="Progress: "+currentLength+" - " + mediaLength;
                                            }
                                        }
                //every second
                , 0, 80);

        fileControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Test", "fileBar changed");
                Log.i("Test", Integer.toString(progress));
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
            }
        });

        /////////////////////////////////////////////////////


        ////////VOLUME CONTROL///////////////////////////////
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
                Log.i("Test", "volumeBar changed");
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
        //////////////////////////////////////////////////////

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                buttonPause.setEnabled(false);
            }
        });
    }

}
