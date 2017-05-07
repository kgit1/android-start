package com.example.a.appnotessaver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.util.Collections;

public class EditTextActivity extends AppCompatActivity {

    private EditText editNote;
    private int noteNumber;
    private boolean rewrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        editNote = (EditText) findViewById(R.id.editNote);

        Intent intent = getIntent();
        noteNumber = intent.getIntExtra("note", -1);
        if (noteNumber > -1) {
            editNote.setText(MainActivity.notes.get(noteNumber));
        }
        Log.i("noteNumber", "" + noteNumber);

        rewrite = intent.getBooleanExtra("rewrite", true);
        Log.i("rewrite", "" + rewrite);

        /*TextView editEntryView = new TextView(...);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(8);
        editEntryView.setFilters(filterArray);*/

        /*//to save note immediately on text change
        editNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteNumber,""+s);
                notesWriter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
    }

    @Override
    public void onBackPressed() {
        if (!editNote.getText().toString().equals("") && !editNote.getText().toString().equals(" ")) {
            if (!rewrite) {
                MainActivity.notes.add(editNote.getText().toString());
                notesWriter();
            } else {
                MainActivity.notes.set(noteNumber, editNote.getText().toString());
                notesWriter();
            }
        }
        super.onBackPressed();
    }

    private void notesWriter() {
        MainActivity.viewNotes.clear();
        MainActivity.viewNotes.addAll(MainActivity.notes);
        Collections.reverse(MainActivity.viewNotes);
        MainActivity.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.a.appnotessaver", Context.MODE_PRIVATE);

        Log.i("back", "button");
        try {
            Log.i("notes", MainActivity.notes.toString());
            Log.i("viewNotes", MainActivity.viewNotes.toString());
            sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(MainActivity.notes)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
