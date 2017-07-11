package com.konggit.appviewholderlistview;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);
        DatAdapter adapter = new DatAdapter(getApplicationContext(), getDataSet());
        listView.setAdapter(adapter);

        ListView listView2 =(ListView) findViewById(R.id.listView2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.list_item, R.id.listItem,getDataSet());
        listView2.setAdapter(arrayAdapter);
        //listView.setMinimumHeight(this.getWindowManager().getDefaultDisplay().getMetrics(););

        listView.setMinimumHeight(1794);
        Log.i("height",String.valueOf(Resources.getSystem().getDisplayMetrics().heightPixels));

    }

    private String[] getDataSet(){

        String[] dataSet = new String[100];
        for(int i = 0;i<100;i++){

            dataSet[i] = "item " + i;

        }

        return dataSet;
    }
}
