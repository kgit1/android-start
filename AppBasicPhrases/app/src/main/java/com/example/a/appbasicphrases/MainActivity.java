package com.example.a.appbasicphrases;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //page for free phrases
    //ielanguages.com/french3s.html
    MediaPlayer mediaPlayer;

    public void functionButtonPlayPhrases(View view){
        Button button = (Button) findViewById(view.getId());
        Log.i("Test","button " + view.getId()+"clicked");
        Log.i("Test","button - " + button.getText().toString()+" - clicked");
        if(mediaPlayer==null){
            mediaPlayer = new MediaPlayer();
        }
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        switch(button.getText().toString()){
            case "1":
                mediaPlayer=MediaPlayer.create(this,R.raw.doyouspeakenglish);
                mediaPlayer.start();
                break;
            case "2":
                mediaPlayer=MediaPlayer.create(this,R.raw.goodevening);
                mediaPlayer.start();
                break;
            case "3":
                mediaPlayer=MediaPlayer.create(this,R.raw.hello);
                mediaPlayer.start();
                break;
            case "4":
                mediaPlayer=MediaPlayer.create(this,R.raw.howareyou);
                mediaPlayer.start();
                break;
            case "5":
                mediaPlayer=MediaPlayer.create(this,R.raw.ilivein);
                mediaPlayer.start();
                break;
            case "6":
                mediaPlayer=MediaPlayer.create(this,R.raw.mynameis);
                mediaPlayer.start();
                break;
            case "7":
                mediaPlayer=MediaPlayer.create(this,R.raw.please);
                mediaPlayer.start();
                break;
            case "8":
                mediaPlayer=MediaPlayer.create(this,R.raw.welcome);
                mediaPlayer.start();
                break;
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                @Override
                                                public void onCompletion(MediaPlayer mp) {
                                                    mediaPlayer.stop();
                                                    mediaPlayer.release();
                                                    mediaPlayer = null;
                                                    Log.i("Test","mediaplayer nulled");
                                                }
                                            }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer= MediaPlayer.create(this,R.raw.doyouspeakenglish);
    }
}
