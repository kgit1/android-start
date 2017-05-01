package com.example.a.appmultipleactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this intent will get intent which brought user to this activity
        Intent intent = getIntent();

        Toast.makeText(getApplicationContext(),intent.getStringExtra("userName"),Toast.LENGTH_SHORT).show();

        ListView listView = (ListView) findViewById(R.id.listView);
        final List<String> stringsList = new ArrayList<>();
        stringsList.add("Marry");
        stringsList.add("has");
        stringsList.add("a little");
        stringsList.add("lamb");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringsList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("string",stringsList.get(position));
                startActivity(intent);
            }
        });

    }

    public void functionButtonSwitch(View view){

        //An intent is an abstract description of an operation to be performed.
        //It can be used with startActivity to launch an Activity, broadcastIntent to send it to any interested
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

        //start another activity here
        startActivity(intent);
    }

    public void functionItemClicked(View view){
        TextView tempTextView = (TextView)findViewById(view.getId());

        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("textName", tempTextView.getText());
        startActivity(intent);
    }
}
