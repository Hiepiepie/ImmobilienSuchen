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

    private RecyclerViewAdapter adapter;
    private Intent intentWithResult;
    private String prevActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra("title"));


//      pass variable from KundenActivity or MaklerActivity
        angebotContainer = getIntent().getParcelableArrayListExtra("angebotContainer");
        prevActivity = getIntent().getStringExtra("prevActivity");
        initRecyclerView();
    }



    private void initRecyclerView(){
        //Log.d(TAG, "initRecyclerView: intit RecyclerView");
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(angebotContainer,this, prevActivity);
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
                        angebotContainer.set(i,angebot);
                    }
                }
                adapter = new RecyclerViewAdapter(angebotContainer,this, prevActivity);
                recyclerView.setAdapter(adapter);
                intentWithResult = new Intent();
                intentWithResult.putExtra("angebotContainer" , angebotContainer);
                setResult(1, intentWithResult);
                break;
            case 2 :
                angebot = data.getParcelableExtra("angebot");
                for(int i = 0 ; i < angebotContainer.size() ; i++)
                {
                    if(angebotContainer.get(i).getBeitragID() == angebot.getBeitragID()){
                        angebotContainer.set(i,angebot);
                    }
                }
                Toast.makeText(this, "Angebot wird aktualisiert!",
                        Toast.LENGTH_SHORT).show();
                adapter = new RecyclerViewAdapter(angebotContainer,this, prevActivity);
                recyclerView.setAdapter(adapter);
                intentWithResult = new Intent();
                intentWithResult.putExtra("angebotContainer" , angebotContainer);
                setResult(4, intentWithResult);
                break;
            case 3 :
                Toast.makeText(this, "Änderungen abgebrochen!",
                        Toast.LENGTH_SHORT).show();
                setResult(5);
                break;
            case 4 :
                int beitragsId = data.getIntExtra("beitragsId", 0);
                for(int i = 0 ; i < angebotContainer.size() ; i++)
                {
                    if(angebotContainer.get(i).getBeitragID() == beitragsId){
                        angebotContainer.remove(i);
                    }
                }
                Toast.makeText(this, "Angebot wird gelöscht!",
                        Toast.LENGTH_SHORT).show();
                recyclerView.setAdapter(adapter);
                intentWithResult = new Intent();
                intentWithResult.putExtra("angebotContainer" , angebotContainer);
                setResult(4, intentWithResult);
                break;
        }
    }

}

