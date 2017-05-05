package com.example.a.appnotessaver;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> viewNotes;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = this.getSharedPreferences("com.example.a.appnotessaver", Context.MODE_PRIVATE);
        //sharedPreferences.edit().clear().apply();

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditTextActivity.class);
                intent.putExtra("note", (notes.size() - 1 - position));
                Log.i("intent", "" + (notes.size() - 1 - position));
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_input_delete)
                        .setTitle("Choose action")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("which", "" + position);
                                notes.remove(notes.size() - 1 - position);
                                try {
                                    sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(notes)).apply();

                                    viewNotes.clear();
                                    viewNotes.addAll(notes);
                                    Collections.reverse(viewNotes);
                                    arrayAdapter.notifyDataSetChanged();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("which", "" + position);
                            }
                        })
                        .show();
                //put true here to work properly with usual click listener
                return true;
            }
        });

        try {
            notes.addAll((ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<String>()))));
            Log.i("shared decode", notes.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewNotes = new ArrayList<>();
        viewNotes.addAll(notes);
        Collections.reverse(viewNotes);

        /*if (viewNotes.size() == 0) {
            viewNotes.add("new");
        }*/

        Log.i("adapter", "invoke");
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, viewNotes);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.new_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, EditTextActivity.class);
        intent.putExtra("rewrite", false);
        startActivity(intent);
        return true;
    }
}
