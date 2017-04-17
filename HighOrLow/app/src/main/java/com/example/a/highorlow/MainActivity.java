package com.example.a.highorlow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int rand;

    public void functionHighOrLow(View view) {
        Log.i("Test", "button check clicked");
        EditText textEnter = (EditText) findViewById(R.id.textEnter);
        TextView textResult = (TextView) findViewById(R.id.textResult);
        int guess = Integer.parseInt(textEnter.getText().toString());
        if (guess == rand) {
            Log.i("Test", "button check clicked");
            textResult.setText("Congratulations!!!\n your guess number " + guess + " = " + rand + " mine number\nTry more!");
            textEnter.setText(null);
            rand = (int) (Math.random() * 20);
        } else {
            if (guess > rand) {
                Log.i("Test", guess + " > " + rand);
                textResult.setText("Fail!\n your guess number " + guess + " > " + rand + " mine number\nTry more!");
                textEnter.setText(null);
                rand = (int) (Math.random() * 20);
            } else {
                Log.i("Test", guess + " < " + rand);
                textResult.setText("Fail!\n your guess number " + guess + " < " + rand + " mine number\nTry more!");
                textEnter.setText(null);
                rand = (int) (Math.random() * 20);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rand = (int) (Math.random() * 20);
        System.out.println(rand);
        System.out.println(Math.random() * 20);
        System.out.println(Math.random());
    }
}
