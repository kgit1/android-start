package com.example.a.appdownloadimagefromweb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ImageView downloadedImage;

    public void functionButtonDownloadImage(View view) {

        //http://orig12.deviantart.net/6492/f/2016/059/3/4/seasonal_feathers_by_nanfe-d9tggn2.jpg
        Log.i("Test", "Button 1 - clicked");

        ImageDownloader task = new ImageDownloader();
        Bitmap myImage;
        try {
            myImage = task.execute("http://orig12.deviantart.net/6492/f/2016/059/3/4/seasonal_feathers_by_nanfe-d9tggn2.jpg").get();

            downloadedImage.setImageBitmap(myImage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadedImage = (ImageView) findViewById(R.id.image1);
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //get image in one go and convert to bitmap
                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(in);

                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
