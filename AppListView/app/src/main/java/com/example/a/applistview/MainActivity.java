package com.example.a.applistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create list view object
        ListView listView = (ListView) findViewById(R.id.idList);

        //create and fill array
        // ArrayList<String> family = new ArrayList<>(asList("pap","mom","me"));
        final ArrayList<String> family = new ArrayList<>();
        family.add("dad");
        family.add("mom");
        family.add("me");
        family.add("brother");
        family.add("sister");

        //with help of array adapter transform list to list view
        //first transform to ArrayAdapter with constuctor which takes context, layout tipe of list
        // and finally array
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, family);
        //that set adapter to list view object
        listView.setAdapter(arrayAdapter);

        //add listener to list view - on item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //AdapterView parent - represents listView object, but have wild card<?> because
            // can represent another type of view like gridView, scrollView etc
            //View - the view within the AdapterView that was clicked (this will be
            // a view provided by the adapter)
            //position - the position of the view in the adapter.
            //id - the row id of the item that was clicked.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Test", "position " + family.get(position) + " id " + id);
                //Toast.makeText(MainActivity.this, "Hello, " + family.get(position), Toast.LENGTH_SHORT).show();
                //or better way - get context with method - getApplicationContext()
                Toast.makeText(getApplicationContext(), "Hello, " + family.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
