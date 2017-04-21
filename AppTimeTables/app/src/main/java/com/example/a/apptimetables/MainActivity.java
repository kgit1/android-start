package com.example.a.apptimetables;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ListView listView;
    SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.idList);

        list = new ArrayList<>(asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(20);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int seekBarMinValue = 1;
                if (progress >= seekBarMinValue) {
                    ArrayList<String> tempList = new ArrayList<String>(list);
                    for (int i = 0; i < tempList.size(); i++) {

                        //tempList.indexOf(element);
                        tempList.set(i, "" + Integer.parseInt(tempList.get(i)) * progress);
                        Log.i("Test", "element - " + tempList.get(i));
                        Log.i("Test", "progress " + progress);
                    }
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, tempList);
                    listView.setAdapter(adapter);
                } else {
                    seekBar.setProgress(seekBarMinValue);
                }




               /* for (String element : tempList) {

                    //tempList.indexOf(element);
                    tempList.set(tempList.indexOf(element), "" + (Integer.parseInt(element) * progress));
                    Log.i("Test", "element - " + element);
                    Log.i("Test","progress " + progress);
                }
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, tempList);
                listView.setAdapter(adapter);
            }*/
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
