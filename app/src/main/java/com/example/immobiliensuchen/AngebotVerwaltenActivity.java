package com.example.immobiliensuchen;

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

public class AngebotVerwaltenActivity extends AppCompatActivity {

    private static final String TAG = "AngebotVerwaltenActivity";

    private ArrayList<ImageView> mImagesURL = new ArrayList<>();
    RecyclerView recyclerView;


    String titel, beschreibung, stadt, email;
    String art;
    double preis;
    int beitragID;



    static ArrayList<Angebote> angebotContainer = new ArrayList<Angebote>();
    ArrayList<String> listTitel = new ArrayList<String>();
    ArrayList<String> myEmail = new ArrayList<String>();
    ArrayList<String> myBeschreibung = new ArrayList<String>();
    private ArrayList<String> mPreis = new ArrayList<>();
    ArrayList<String> myArt = new ArrayList<String>();
    ArrayList<String> myStadt = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angebot_verwalten);
        Toolbar toolbar = findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);

        readFile();

        // create a list of titel
        for (int i = 0; i < angebotContainer.size() ; i++){
            listTitel.add(angebotContainer.get(i).titel);
            mPreis.add(Double.toString(angebotContainer.get(i).preis));
            myEmail.add(angebotContainer.get(i).email);
            myBeschreibung.add(angebotContainer.get(i).beschreibung);
            myArt.add(angebotContainer.get(i).art);
            myStadt.add(angebotContainer.get(i).stadt);
        }
        initImageBitmaps();

    }

    public void readFile(){
        String alleausgaben = "";
        angebotContainer= new ArrayList<>();
        try{
            File myFile = new File(Environment.getExternalStorageDirectory().getPath()+"/"+ "NZSE2.txt");
            FileInputStream fis = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader( new InputStreamReader(fis, StandardCharsets.UTF_8.name()));
            String line;
            while((line = myReader.readLine())!= null ){
                alleausgaben += line;
            }
            JSONArray jsonArray = new JSONArray(alleausgaben);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                beitragID = Integer.parseInt(jsonObject.getString("BeitragsID"));
                art = jsonObject.getString("Art");
                titel = jsonObject.getString("Titel");
                preis = Double.parseDouble(jsonObject.getString("Preis"));
                stadt = jsonObject.getString("Stadt");
                email = jsonObject.getString("Email");
                beschreibung = jsonObject.getString("Beschreibung");
                Angebote a = new Angebote(beitragID,art,stadt,preis,titel,email,beschreibung);
                angebotContainer.add(a);
            }


        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps");
        for ( int i = 0 ; i< listTitel.size(); i++) {
            ImageView j = new ImageView(this);
            j.setImageResource(R.drawable.home);
            mImagesURL.add(j);
        }

        initRecyclerView();
    }
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: intit RecyclerView");
        recyclerView = findViewById(R.id.recyclerView2);
        RecyclerViewAdapter2 adapter = new RecyclerViewAdapter2(listTitel,mPreis,myEmail,myBeschreibung,myArt,myStadt,mImagesURL,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
