package com.example.a.appcurrencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView number;
    TextView answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonConvert = (Button) findViewById(R.id.buttonConvert);
        buttonConvert.setOnClickListener(new ButtonConverListener());

        number= (TextView) findViewById(R.id.textNumber);
        answer = (TextView) findViewById(R.id.textAnswer);
    }

    private class ButtonConverListener extends AppCompatActivity implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Log.i("Test"," converter listener");
            if(!TextUtils.isEmpty(number.getText())){
            answer.setText((Double.parseDouble(number.getText().toString())/27)+"");
            }else {
            answer.setText("Enter value to convert");
            }
        }
    }

}
