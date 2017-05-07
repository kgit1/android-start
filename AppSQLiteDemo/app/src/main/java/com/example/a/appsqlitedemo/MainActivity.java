package com.example.a.appsqlitedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))");

            //no truncate table - so delete all from table and than vacuum it to rid out of empty space
            myDatabase.execSQL("DELETE FROM USERS");
            myDatabase.execSQL("VACUUM");

            myDatabase.execSQL("INSERT INTO users(name,age) VALUES('Rob',34)");
            myDatabase.execSQL("INSERT INTO users(name,age) VALUES ('Tommy',4)");
            myDatabase.execSQL("INSERT INTO users(name,age) VALUES ('a',11),('b',32),('c',37)");

            //cursor object allows us to loop through all results of some query
            Cursor c = myDatabase.rawQuery("SELECT * FROM users", null);

            Log.i("SQL columns",String.valueOf(c.getColumnCount()));
            Log.i("SQL column names",Arrays.toString(c.getColumnNames()));
            Log.i("SQL age index",String.valueOf(c.getColumnIndexOrThrow("age")));
            Log.i("SQL name index",String.valueOf(c.getColumnIndexOrThrow("name")));

            c = myDatabase.rawQuery("SELECT * FROM users where age>4",null);
            Log.i("SQL",c.toString());
            c.moveToFirst();
            while(!c.isAfterLast()){
                Log.i("name", c.getString(c.getColumnIndexOrThrow("name")));
                Log.i("age", c.getString(c.getColumnIndexOrThrow("age")));
                c.moveToNext();
            }

            SQLiteDatabase eventsDatabase = this.openOrCreateDatabase("Events", MODE_PRIVATE,null);
            eventsDatabase.execSQL("CREATE TABLE IF NOT EXISTS events (event VARCHAR, year INT(5))");

            eventsDatabase.execSQL("INSERT INTO events(year, event) VALUES (1952, 'RIDGSTONE')");
            eventsDatabase.execSQL("INSERT INTO events(year, event) VALUES (1700, 'BlancoNegro')");
            eventsDatabase.execSQL("INSERT INTO events(year, event) VALUES (1810, 'Apperet')");
            eventsDatabase.execSQL("INSERT INTO events(year, event) VALUES (1989, 'Martin')");

            c = eventsDatabase.rawQuery("SELECT * FROM events", null);
            int eventsColumn = c.getColumnIndexOrThrow("event");
            int yearsColumn = c.getColumnIndexOrThrow("year");

            c.moveToFirst();
            while(!c.isAfterLast()){
                Log.i("year", c.getString(yearsColumn));
                Log.i("event", c.getString(eventsColumn));
                c.moveToNext();
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
