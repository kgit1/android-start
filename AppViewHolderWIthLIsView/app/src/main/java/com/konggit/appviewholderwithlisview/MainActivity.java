package com.konggit.appviewholderwithlisview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //build-in adapter
        ListView listView1 = (ListView) findViewById(R.id.listView1);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listFiller(30));
        listView1.setAdapter(adapter);

        //custom adapter
        ListView listView2 =(ListView) findViewById(R.id.listView2);
        ViewHolderAdapter viewHolderAdapter = new ViewHolderAdapter(getApplicationContext(),listFiller(50));
        listView2.setAdapter(viewHolderAdapter);

    }

    private List<String> listFiller(int numberOfEntries) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < numberOfEntries; i++) {
            list.add(i + 1 + " item = " + (int) (Math.random() * 200));
        }
        return list;
    }




}
