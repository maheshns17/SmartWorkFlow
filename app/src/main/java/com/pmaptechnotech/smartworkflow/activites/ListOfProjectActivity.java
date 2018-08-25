package com.pmaptechnotech.smartworkflow.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pmaptechnotech.smartworkflow.R;
import com.pmaptechnotech.smartworkflow.adapters.ListofProjectAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class ListOfProjectActivity extends AppCompatActivity {
private android.content.Context context;

    // ArrayList for person names
    ArrayList<String> personNames = new ArrayList<>(Arrays.asList("project 1", "project 2", "project 3", "project 4", "project 5", "project 6", "project 7"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_project);
        context= ListOfProjectActivity.this;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view4);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of ListofProjectAdapter to send the reference and data to Adapter
        ListofProjectAdapter listofProjectAdapter = new ListofProjectAdapter(ListOfProjectActivity.this, personNames);
        recyclerView.setAdapter(listofProjectAdapter); // set the Adapter to RecyclerView
    }
}