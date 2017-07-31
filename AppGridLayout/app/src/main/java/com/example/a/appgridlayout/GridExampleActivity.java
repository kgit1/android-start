package com.example.a.appgridlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class GridExampleActivity extends AppCompatActivity {

    private GridViewAdapter adapter;
    private ArrayList<String> listCountry;
    private ArrayList<String> listCountryCode;

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_example);

        prepareLists();

        adapter = new GridViewAdapter(listCountry,listCountryCode,getApplicationContext());

        gridView =(GridView) findViewById(R.id.grivView);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), adapter.getItem(position).toString(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void prepareLists(){

        listCountry = new ArrayList<>();
        listCountryCode = new ArrayList<>();

        listCountry.add("Afghanistan");
        listCountryCode.add("93");

        listCountry.add("Albania");
        listCountryCode.add("355");

        listCountry.add("Algeria");
        listCountryCode.add("213");

        listCountry.add("American Samoa");
        listCountryCode.add("1-684");

        listCountry.add("Andorra");
        listCountryCode.add("376");

        listCountry.add("Angola");
        listCountryCode.add("244");

        listCountry.add("Anguilla");
        listCountryCode.add("1-264");

        listCountry.add("Antarctica");
        listCountryCode.add("672");

        listCountry.add("Antigua and Barbuda");
        listCountryCode.add("1-268");

        listCountry.add("Argentina");
        listCountryCode.add("54");

    }

}
