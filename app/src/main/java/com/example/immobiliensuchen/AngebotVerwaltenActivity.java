package com.example.immobiliensuchen;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AngebotVerwaltenActivity extends AppCompatActivity {

    private static final String TAG = "AngebotVerwaltenActivity";

    private ArrayList<ImageView> mImagesURL = new ArrayList<>();
    RecyclerView recyclerView;

    String titel, beschreibung, stadt, email;
    String art;
    double preis;
    int beitragID;

    static ArrayList<Angebot> angebotContainer = new ArrayList<Angebot>();
    private ArrayList<String> listTitel = new ArrayList<String>();
    private ArrayList<String> listEmail = new ArrayList<String>();
    private ArrayList<String> listBeschreibung = new ArrayList<String>();
    private ArrayList<String> listPreis = new ArrayList<>();
    private ArrayList<String> listArt = new ArrayList<String>();
    private ArrayList<String> listStadt = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angebot_verwalten);
        Toolbar toolbar = findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);

        //Reading Data from Main Activity
        angebotContainer = this.getIntent().getParcelableArrayListExtra("angebotContainer");

        /*// create a list of titel
        for (int i = 0; i < angebotContainer.size() ; i++){
            listTitel.add(angebotContainer.get(i).getTitel());
            listPreis.add(Double.toString(angebotContainer.get(i).getPreis()));
            listEmail.add(angebotContainer.get(i).getEmail());
            listBeschreibung.add(angebotContainer.get(i).getBeschreibung());
            listArt.add(angebotContainer.get(i).getArt());
            listStadt.add(angebotContainer.get(i).getStadt());
        }*/
        initImageBitmaps();
        initRecyclerView();
    }

   /* public void readFile(){
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
                Angebot a = new Angebot(beitragID,art,stadt,preis,titel,email,beschreibung);
                angebotContainer.add(a);
            }


        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }*/

    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps");
        for ( int i = 0 ; i< listTitel.size(); i++) {
            ImageView j = new ImageView(this);
            j.setImageResource(R.drawable.home);
            mImagesURL.add(j);
        }
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init RecyclerView");
        recyclerView = findViewById(R.id.recyclerView2);
        RecyclerViewAdapter2 adapter = new RecyclerViewAdapter2(listTitel, listPreis, listEmail, listBeschreibung, listArt, listStadt,mImagesURL,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
