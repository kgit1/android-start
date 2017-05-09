package com.example.a.appsqlitedemo;

import android.content.ContentValues;
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

        try {
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

            // delete table
            myDatabase.execSQL("DROP TABLE IF EXISTS users");
            // create table(to autoincrement id - just make field - primary key)
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (userID INTEGER PRIMARY KEY,name VARCHAR, age INT(3))");

            // to truncate table - delete all from table and than vacuum it to rid out of empty space
            myDatabase.execSQL("DELETE FROM USERS");
            myDatabase.execSQL("VACUUM");

            // insert data to database
            myDatabase.execSQL("INSERT INTO users VALUES(null,'Rob',34)");
            myDatabase.execSQL("INSERT INTO users(name,age) VALUES ('Tommy',4)");
            myDatabase.execSQL("INSERT INTO users(name,age) VALUES ('Amilu',21),('Bomarshe',52),('Clementine',17)");
            myDatabase.execSQL("INSERT INTO users(name,age) VALUES ('Robin',11),('Robin',33),('Robin',33),('Robin',35),('Roben',32),('Robert',37), ('Ramon', 28),('Ramona',25)");
            ContentValues cv = new ContentValues();
            cv.put("name","Ithan");
            cv.put("age",35);
            myDatabase.insert("users", null, new ContentValues(cv));

            // delete data from database
            // myDatabase.execSQL("DELETE FROM users WHERE name = 'Robin' AND age = 33");
            myDatabase.execSQL("DELETE FROM users WHERE userId IN (SELECT userId FROM users WHERE name = 'Robin' AND age=33 LIMIT 1)");

            // update data
            myDatabase.execSQL("UPDATE users SET age = 99 WHERE name LIKE 'Bo%'");

            // cursor object allows us to loop through all results of some query
            Cursor c = myDatabase.rawQuery("SELECT * FROM users", null);

            Log.i("SQL columns", String.valueOf(c.getColumnCount()));
            Log.i("SQL column names", Arrays.toString(c.getColumnNames()));
            Log.i("SQL age index", String.valueOf(c.getColumnIndexOrThrow("age")));
            Log.i("SQL name index", String.valueOf(c.getColumnIndexOrThrow("name")));

            // c = myDatabase.rawQuery("SELECT * FROM users WHERE age>4",null);
            // c = myDatabase.rawQuery("SELECT name FROM users WHERE age>4",null);
            // c = myDatabase.rawQuery("SELECT name FROM users WHERE age>4 AND name LIKE 'R%'",null);
            // c = myDatabase.rawQuery("SELECT name FROM users WHERE age>4 AND name LIKE 'Ro%'",null);
            // c = myDatabase.rawQuery("SELECT name FROM users WHERE age>4 AND name LIKE '%o%'",null);
            // c = myDatabase.rawQuery("SELECT * FROM users WHERE age>4 AND name LIKE '%o%' ORDER BY name ASC",null);
            // c = myDatabase.rawQuery("SELECT * FROM users WHERE age>4 AND name LIKE '%o%' ORDER BY age DESC", null);
            // c = myDatabase.rawQuery("SELECT * FROM users WHERE age>4 AND name LIKE '%o%' ORDER BY age DESC LIMIT 2", null);
            c = myDatabase.rawQuery("SELECT * FROM users ORDER BY name ASC, age DESC", null);
            // Log.i("SQL",c.toString());
            Log.i("SQL results", String.valueOf(c.getCount()));
            Log.i("SQL", "ORDER - name = ASC age = DESC");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                //Log.i("SQL name", c.getString(c.getColumnIndexOrThrow("name")));
                //Log.i("SQL age", c.getString(c.getColumnIndexOrThrow("age")));
                Log.i("SQL", "userID: " + c.getInt(c.getColumnIndexOrThrow("userID")) + " name: " + c.getString(c.getColumnIndexOrThrow("age")) + " age: " + c.getString(c.getColumnIndexOrThrow("name")));
                c.moveToNext();
            }

            /*SQLiteDatabase eventsDatabase = this.openOrCreateDatabase("Events", MODE_PRIVATE,null);
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
            }*/


            /*// to check which tables in database
            // Cursor info = myDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'",null);
            Cursor info = myDatabase.rawQuery("SELECT name FROM sqlite_master\n" +
                    "            WHERE type IN ('table','view') AND name NOT LIKE 'sqlite_%'\n" +
                    "            UNION ALL\n" +
                    "            SELECT name FROM sqlite_temp_master\n" +
                    "            WHERE type IN ('table','view')\n" +
                    "            ORDER BY 1",null);

            Log.i("INFO", String.valueOf(info.getCount()));

            info.moveToFirst();
            while(!info.isAfterLast()){
                Log.i("INFO table name",info.getString(0));
                info.moveToNext();
            }*/


            c.close();
            myDatabase.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
