package com.example.a.appvideo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;
import android.widget.MediaController;

public class MainActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add videoView
        videoView = (VideoView)findViewById(R.id.videoView);
        //define path of resource for videoView
        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.capibara);

        //create media controls
        MediaController mediaController = new MediaController(this);

        //attach media controller to videoView
        mediaController.setAnchorView(videoView);

        //set mediaController to videoView
        videoView.setMediaController(mediaController);

        //start playing video
        videoView.start();

        //to do some activity on video complition(end of playback)
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i("Test", "Video restarted");
                videoView.start();
            }
        });
    }
}
