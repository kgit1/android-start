package com.example.a.appvideo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;
import android.widget.MediaController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add videoView
        VideoView videoView = (VideoView)findViewById(R.id.videoView);
        //define path of resource for videoView
        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.demovideo);

        //create media controls
        android.widget.MediaController mediaController = new MediaController(this);

        //attach media controller to videoView
        mediaController.setAnchorView(videoView);

        //set mediaController to videoView
        videoView.setMediaController(mediaController);

        //start playing video
        videoView.start();
    }
}
