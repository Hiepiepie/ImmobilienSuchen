package com.example.immobiliensuchen;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends AppCompatActivity {

    private static final String TAG = "BrowseActivity";

    private RecyclerView recyclerView;

    private ArrayList<Angebot> angebotContainer;
    private Angebot angebot;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);

//      pass variable from KundenActivity
        angebotContainer = getIntent().getParcelableArrayListExtra("angebotContainer");
        initRecyclerView();
    }



    private void initRecyclerView(){
        //Log.d(TAG, "initRecyclerView: intit RecyclerView");
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(angebotContainer,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case 1 :
                angebot = data.getParcelableExtra("angebot");
                for(int i = 0 ; i < angebotContainer.size() ; i++)
                {
                    if(angebotContainer.get(i).getBeitragID() == angebot.getBeitragID()){
                        angebotContainer.get(i).setFavorit(angebot.getFavorit());
                    }
                }
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(angebotContainer,this);
                recyclerView.setAdapter(adapter);
                Intent intentWithResult = new Intent();
                intentWithResult.putExtra("angebotContainer" , angebotContainer);
                setResult(1, intentWithResult);
                break;
        }
    }

}

