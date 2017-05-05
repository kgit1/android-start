package com.example.a.appnotessaver;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class EditTextActivity extends AppCompatActivity {

    private EditText editNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        editNote = (EditText) findViewById(R.id.editNote);

        Intent intent = getIntent();
        String extra = intent.getStringExtra("note");
        editNote.setText(extra);
        Log.i("extra", "" + extra);

        /*TextView editEntryView = new TextView(...);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(8);
        editEntryView.setFilters(filterArray);*/
    }

    @Override
    public void onBackPressed() {
        if (!editNote.getText().toString().equals("") && !editNote.getText().toString().equals(" ")) {
            MainActivity.notes.add(editNote.getText().toString());
            MainActivity.viewNotes.clear();
            MainActivity.viewNotes.addAll(MainActivity.notes);
            Collections.reverse(MainActivity.viewNotes);
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.a.appnotessaver", Context.MODE_PRIVATE);

            Log.i("back", "button");
            try {
                Log.i("notes", MainActivity.notes.toString());
                Log.i("viewNotes", MainActivity.viewNotes.toString());

                MainActivity.arrayAdapter.notifyDataSetChanged();
                sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onBackPressed();

        /* new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.alert_dark_frame)
                .setTitle("Choose action")
                .setMessage("Save changes or Back to list")
                .setPositiveButton("Save", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editNote.getText().toString().equals("") && !editNote.getText().toString().equals(" ")) {
                            MainActivity.notes.add(editNote.getText().toString());
                            MainActivity.viewNotes.clear();
                            MainActivity.viewNotes.addAll(MainActivity.notes);
                            Collections.reverse(MainActivity.viewNotes);
                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.a.appnotessaver", Context.MODE_PRIVATE);

                            Log.i("back", "button");
                            try {
                                Log.i("notes",MainActivity.notes.toString());
                                Log.i("viewNotes",MainActivity.viewNotes.toString());

                                MainActivity.arrayAdapter.notifyDataSetChanged();
                                sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Back","Answer");

                    }

                }).show();*/
    }
}
