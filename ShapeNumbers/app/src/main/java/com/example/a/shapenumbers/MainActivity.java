package com.example.a.shapenumbers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public void functionCheckNumber(View view) {
        Log.i("Test", "check clicked");
        EditText textEnter = (EditText) findViewById(R.id.textEnter);
        TextView textResult = (TextView) findViewById(R.id.textResult);
        String result = "";
        if (numberCheckTriangular(Integer.parseInt(textEnter.getText().toString()))) {
            result += "\nNumber " + textEnter.getText().toString() + " is triangular";
        } else {
            result += "\nNumber " + textEnter.getText().toString() + " is not triangular";
        }
        if (numberCheckSquare(Integer.parseInt(textEnter.getText().toString()))) {
            result += "\nNumber " + textEnter.getText().toString() + " is square";
        } else {
            result += "\nNumber " + textEnter.getText().toString() + " is not square";
        }
        if (numberCheckOddOrEven(Integer.parseInt(textEnter.getText().toString()))) {
            result += "\nNumber " + textEnter.getText().toString() + " is even";
        } else {
            result += "\nNumber " + textEnter.getText().toString() + " is odd";
        }
        textResult.setText(result);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //check if number triangular -
    //triangular numbers - sequence of numbers obtained by adding numbers one by one
    //0, 0 + 1 = 1, 1 + 2 = , 3 + 3 = 6, 6 + 4 = 10, 10 + 5 = 15
    //0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 66, 78, 91, 105, 120, 136, 153, 171, 190, 210, 231, 253, 276, 300, 325, 351, 378, 406
    //can be obtained by  n(n+1)/2
    public boolean numberCheckTriangular(int number) {
        int temp = 0;
        for (int i = 1; temp < number; i++) {
            temp = temp + i;
            if (temp == number) {
                return true;
            }
        }
        return false;
    }

    public boolean numberCheckSquare(int number) {
        double temp = Math.sqrt(number);
        if (temp == Math.floor(temp)) {
            return true;
        }
        return false;
    }

    public boolean numberCheckOddOrEven(int number){
        if(number%2==0){
           return true;
        }
        return false;
    }
}
