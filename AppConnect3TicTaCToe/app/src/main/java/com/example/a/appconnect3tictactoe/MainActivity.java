package com.example.a.appconnect3tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    boolean gameWon = false;
    //true = first player active, false = second
    boolean activePlayer1 = true;
    //2 - means unplayed field, 1 - mean yellow, 0 - mean red
    int[] gameArray = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    //add android:tag="name" to images to differ them by getTag() method

    public void functionDrop(View view) {
        Log.i("Test", "function drop");
        if (!gameWon) {
            ImageView counter = (ImageView) view;

            int tappedCounter = Integer.parseInt(counter.getTag().toString());
            Log.i("Test", counter.getTag().toString());

            if (gameArray[tappedCounter - 1] == 2) {
                counter.setTranslationY(-1000f);
                counter.setRotation(0f);

                if (activePlayer1) {
                    Log.i("Test", "red");
                    counter.setImageResource(R.drawable.red);
                    gameArray[tappedCounter - 1] = 0;
                    gameWon = winningState();
                    activePlayer1 = false;
                } else {
                    Log.i("Test", "yellow");
                    counter.setImageResource(R.drawable.yellow);
                    gameArray[tappedCounter - 1] = 1;
                    gameWon = winningState();
                    activePlayer1 = true;
                }
                counter.animate().translationY(0f).rotation(720f).setDuration(700);
            } else {
                Toast.makeText(MainActivity.this, "Field already played", Toast.LENGTH_SHORT).show();
            }
            System.out.println(Arrays.toString(gameArray));
        } else {
        }
    }

    public boolean winningState() {
        int[][] winningCombs = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
        for (int[] winningComb : winningCombs) {

            if (gameArray[winningComb[0]] != 2) {
                if (gameArray[winningComb[0]] == gameArray[winningComb[1]] && gameArray[winningComb[0]] == gameArray[winningComb[2]]) {
                    if (activePlayer1) {
                        Toast.makeText(MainActivity.this, "Red WINS!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Yellow WINS!", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            }

        }
        return false;
    }


    /*public boolean winningState() {
        boolean winningState = false;
        if (gameArray[0] != 2) {
            Log.i("Test", "winning state test");
            if (gameArray[0] == gameArray[1] && gameArray[1] == gameArray[2]) {
                Log.i("Test", "winning state test 1");
                winningState = true;
            }
            if (gameArray[0] == gameArray[3] && gameArray[3] == gameArray[6]) {
                Log.i("Test", "winning state test 2");
                winningState = true;
            }
            if (gameArray[0] == gameArray[4] && gameArray[4] == gameArray[8]) {
                Log.i("Test", "winning state test 3");
                winningState = true;
            }
        }
        if (gameArray[1] != 2) {
            if (gameArray[1] == gameArray[4] && gameArray[4] == gameArray[7]) {
                Log.i("Test", "winning state test 4");
                winningState = true;
            }
        }
        if (gameArray[2] != 2) {
            if (gameArray[2] == gameArray[5] && gameArray[5] == gameArray[8]) {
                Log.i("Test", "winning state test 5");
                winningState = true;
            }
            if (gameArray[2] == gameArray[4] && gameArray[4] == gameArray[6]) {
                Log.i("Test", "winning state test 6");
                winningState = true;
            }
        }
        if (gameArray[3] != 2 && gameArray[3] == gameArray[4] && gameArray[4] == gameArray[5]) {
            Log.i("Test", "winning state test 7");
            winningState = true;
        }
        if (gameArray[6] != 2 && gameArray[6] == gameArray[7] && gameArray[7] == gameArray[8]) {
            Log.i("Test", "winning state test 8");
            winningState = true;
        }
        if (winningState) {
            if (activePlayer1) {
                Toast.makeText(MainActivity.this, "Red WINS!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Yellow WINS!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        Log.i("Test", "winning state test false");
        return false;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
