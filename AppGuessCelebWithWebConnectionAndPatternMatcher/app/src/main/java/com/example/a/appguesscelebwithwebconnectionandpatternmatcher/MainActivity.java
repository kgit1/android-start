package com.example.a.appguesscelebwithwebconnectionandpatternmatcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> namesArray;
    ArrayList<String> imageURLSArray;
    ImageView image;
    TextView textTimer;
    TextView textResult;
    TextView textScore;
    TextView textGameEnd;
    Button buttonNewGame;
    GridLayout grid;
    int answerIndex;
    int randomButtonIndex;

    int timerLength;
    CountDownTimer countDownTimer;
    int tasks;
    int rightTasks;
    int wrongTasks;


    private ArrayList<String> namesToArray(String htmlCode) {
        Log.i("Test", "Names");
        String[] cutHtml = htmlCode.split("<div class=\"see-more\">");
        ArrayList<String> names = new ArrayList<String>();
        Pattern pNamesFirst = Pattern.compile("alt=\"Image of " + "(.*?)" + "\" class=\"");
        Pattern pNamesAfterload = Pattern.compile("alt=\"Image of " + "(.*?)" + "\" title");

        Matcher matcher = pNamesFirst.matcher(cutHtml[0]);
        while (matcher.find()) {
            names.add(matcher.group(1));
        }

        matcher = pNamesAfterload.matcher(cutHtml[0]);
        while (matcher.find()) {
            //matcher.find() second call because pattern occurs twice for every entry
            //so with second call - we every time jump one step ahead before make write
            matcher.find();
            names.add(matcher.group(1));
        }

        Log.i("Test", "Names end");
        return names;
    }

    private ArrayList<String> imageURLsToArray(String htmlCode) {
        Log.i("Test", "URLS");
        String[] cutHtml = htmlCode.split("<div class=\"see-more\">");
        ArrayList<String> urls = new ArrayList<>();

        Pattern pImageUrls = Pattern.compile("src=\"https://images-na.ssl-images-amazon.com/images/M/" + "(.*?)" + ".jpg");
        Matcher matcher = pImageUrls.matcher(cutHtml[0]);

        while (matcher.find()) {
            urls.add("https://images-na.ssl-images-amazon.com/images/M/" + matcher.group(1) + ".jpg");
        }

        Log.i("Test", "URLS end");
        return urls;
    }


    private class DownloadPageHtmlTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                Log.i("Test", "Background");
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                StringBuilder result = new StringBuilder();
                while (data > -1) {
                    char tempChar = (char) data;
                    result.append(tempChar);
                    data = reader.read();
                }
                Log.i("Test", "Background - end");
                return result.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap bitmap;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();

                bitmap = BitmapFactory.decodeStream(in);

                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        namesArray = new ArrayList<>();
        imageURLSArray = new ArrayList<>();
        image = (ImageView) findViewById(R.id.imageView);
        textTimer = (TextView) findViewById(R.id.textTimer);
        textResult = (TextView) findViewById(R.id.textResult);
        textScore = (TextView) findViewById(R.id.textScore);
        textGameEnd = (TextView) findViewById(R.id.textGameEnd);
        buttonNewGame = (Button) findViewById(R.id.buttonNewGame);
        grid = (GridLayout) findViewById(R.id.grid);


        try {
            String pageHtml = new DownloadPageHtmlTask().execute("http://www.imdb.com/list/ls052283250/").get();
            namesArray = namesToArray(pageHtml);
            imageURLSArray = imageURLsToArray(pageHtml);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < namesArray.size(); i++) {
            Log.i("Test", i + " name: " + namesArray.get(i));
        }
        for (int i = 0; i < imageURLSArray.size(); i++) {
            Log.i("Test", i + " url: " + imageURLSArray.get(i));
        }

        gameStart();
        game();
    }

    public void game() {
        draw();
        functionCounter(timerLength);

        textResult.setText((rightTasks + wrongTasks) + "/" + tasks);
        textScore.setText(""+rightTasks);
    }

    public void gameEnd() {
        textResult.setText((rightTasks + wrongTasks) + "/" + tasks);
        textScore.setText(""+rightTasks);
        textGameEnd.setVisibility(View.VISIBLE);
        buttonNewGame.setVisibility(View.VISIBLE);
        for (int i = 0; i < grid.getChildCount(); i++) {
            grid.getChildAt(i).setEnabled(false);
        }
        textGameEnd.setText("Game Ended\n You score\n is: " + rightTasks);
    }

    public void gameStart() {
        timerLength = 15;
        tasks = 15;
        rightTasks = 0;
        wrongTasks = 0;

        textGameEnd.setVisibility(View.INVISIBLE);
        buttonNewGame.setVisibility(View.INVISIBLE);
        for (int i = 0; i < grid.getChildCount(); i++) {
            grid.getChildAt(i).setEnabled(true);
        }
    }

    public void functionAnswer(View view) {
        Button button = (Button) findViewById(view.getId());
        if (namesArray.get(answerIndex).equals(button.getText())) {
            rightTasks++;
        } else {
            wrongTasks++;
        }
        countDownTimer.cancel();
        if (wrongTasks + rightTasks < tasks) {
            game();
        } else {
            gameEnd();
        }
    }

    public void functionNewGame(View view){
        gameStart();
        game();
    }

    public void draw() {
        answerIndex = randomNumber(99);
        randomButtonIndex = randomNumber(3);
        //image = (ImageView) findViewById(R.id.imageView);
        try {
            image.setImageBitmap(new DownloadImageTask().execute(imageURLSArray.get(answerIndex)).get());
            Button tempButton;
            for (int i = 0; i < grid.getChildCount(); i++) {
                tempButton = (Button) grid.getChildAt(i);
                if (i == randomButtonIndex) {
                    tempButton.setText(namesArray.get(answerIndex));
                } else {
                    tempButton.setText(namesArray.get(randomNumber(99)));
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public int randomNumber(int randomMax) {
        return (int) (Math.random() * randomMax);
    }

    public void functionCounter(int length) {
        countDownTimer = new CountDownTimer((1000 * length) + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textTimer.setText(counterText((int) millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                if (wrongTasks + rightTasks <= tasks) {
                    wrongTasks++;
                    textTimer.setText("00");
                    game();
                }
            }
        }.start();
    }

    public String counterText(int time) {
        String timeString = "";
        if (time <= 9) {
            timeString = "0" + time;
        } else {
            timeString = "" + time;
        }

        return timeString;
    }
}




































