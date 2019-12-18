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
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class BrowseActivity extends AppCompatActivity {


    private static final String TAG = "BrowseActivity";

    private ArrayList<String> mPreis = new ArrayList<>();
    private ArrayList<ImageView> mImagesURL = new ArrayList<>();
    RecyclerView recyclerView;

    public StringBuilder sb = new StringBuilder();

    String titel, beschreibung, stadt, email;
    String art;
    double preis;
    int beitragID;


    String stadtName ;
    static ArrayList<Angebote> angebotContainer = new ArrayList<Angebote>();
    ArrayList<String> listTitel = new ArrayList<String>();
    ArrayList<Angebote> myDynamicAngebot = new ArrayList<Angebote>();
    ArrayList<String> myEmail = new ArrayList<String>();
    ArrayList<String> myBeschreibung = new ArrayList<String>();

    private void init(){



        Angebote a1 = new Angebote(1,"M","Darmstadt",370,"Titel von Haus 1",
                "Musterhaus1@gmail.com", "Beschreibung von Haus 1");
        Angebote a2 = new Angebote(2,"K","Darmstadt",350120,"Titel von Haus 2",
                "Musterhaus2@gmail.com", "Beschreibung von Haus 2");
        Angebote a3 = new Angebote(3,"K","Darmstadt",250000,"Titel von Haus 3",
                "Musterhaus3@gmail.com", "Beschreibung von Haus 3");
        Angebote a4 = new Angebote(4,"K","Darmstadt",1000000,"Titel von Haus 4",
                "Musterhaus4@gmail.com", "Beschreibung von Haus 4");
        Angebote a5 = new Angebote(5,"M","Darmstadt",370,"Titel von Haus 5",
                "Musterhaus5@gmail.com", "Beschreibung von Haus 5");
        Angebote a6 = new Angebote(6,"M","Darmstadt",370,"Titel von Haus 6",
                "Musterhaus6@gmail.com", "Beschreibung von Haus 6");
        angebotContainer.add(a1);
        angebotContainer.add(a2);
        angebotContainer.add(a3);
        angebotContainer.add(a4);
        angebotContainer.add(a5);
        angebotContainer.add(a6);

        try {

            File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "NZSE2.txt");
            FileOutputStream fos = new FileOutputStream(myFile);

            OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);
            JSONArray jsonarray = new JSONArray();
        for (int i = 0 ; i < angebotContainer.size();i++){
            JSONObject object = new JSONObject();
            Angebote a = angebotContainer.get(i);
            object.put("BeitragsID",a.BeitragID);
            object.put("Titel",a.titel);
            object.put("Art",a.art);
            object.put("Preis",a.preis);
            object.put("Stadt",a.stadt);
            object.put("Email",a.email);
            object.put("Beschreibung",a.beschreibung);
            jsonarray.put(object);
        }
        myOutWriter.append(jsonarray.toString());
        myOutWriter.close();
        fos.close();
            Toast.makeText(this, angebotContainer.size()
                            +
                            " werden gespeichert!",
                    Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

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

    protected void createTundPList(boolean boolMiet, boolean boolKauf) {
        boolean test = true;
        listTitel.clear();
        if(boolMiet){
            for (int j = 0 ; j < angebotContainer.size(); j++ ){
                if ((angebotContainer.get(j).stadt.equals(stadtName) ) && (angebotContainer.get(j).art.equals("M"))){
                    test = false;
                    listTitel.add(angebotContainer.get(j).titel);
                    mPreis.add(Double.toString(angebotContainer.get(j).preis));
                    myEmail.add(angebotContainer.get(j).email);
                    myBeschreibung.add(angebotContainer.get(j).beschreibung);
                    myDynamicAngebot.add(angebotContainer.get(j));
                }
            }
        }
        if(boolKauf){
            for (int j = 0 ; j < angebotContainer.size(); j++ ){
                if ((angebotContainer.get(j).stadt.equals(stadtName)  ) && (angebotContainer.get(j).art.equals("K"))){
                    test = false;
                    listTitel.add(angebotContainer.get(j).titel);
                    mPreis.add(Double.toString(angebotContainer.get(j).preis));
                    myEmail.add(angebotContainer.get(j).email);
                    myBeschreibung.add(angebotContainer.get(j).beschreibung);
                    myDynamicAngebot.add(angebotContainer.get(j));
                }
            }

        }
        if(test){
            Toast t;
            t = Toast.makeText(BrowseActivity.this.getApplicationContext(),
                    " Stadt nicht gefunden oder es gibt kein Angebot in dieser Stadt ", Toast.LENGTH_SHORT);
            t.show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);

        //init();
        readFile();
//      pass variable from KundenActivity
        boolean boolMiet = getIntent().getBooleanExtra("boolMiet", true);
        boolean boolKauf = getIntent().getBooleanExtra("boolKauf", false);
        stadtName = getIntent().getStringExtra("stadtname");

        listTitel.clear();

        createTundPList(boolMiet, boolKauf);

        initImageBitmaps();



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
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(listTitel,mPreis,myEmail,myBeschreibung,mImagesURL,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

