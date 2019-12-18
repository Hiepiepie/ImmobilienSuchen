package com.example.immobiliensuchen;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BrowseActivity extends AppCompatActivity {
    Toolbar myAngebote;
    RecyclerView myList;
    private static final String FILE_NAME = "NZSE.txt";

    private static final String TAG = "BrowseActivity";

    private ArrayList<String> mPreis = new ArrayList<>();
    private ArrayList<String> mImagesURL = new ArrayList<>();
    RecyclerView recyclerView;

    public StringBuilder sb = new StringBuilder();

    String titel, beschreibung, stadt, email;
    char art;
    double preis;
    int beitragID;


    String stadtName ;
    static ArrayList<Angebote> angebotContainer = new ArrayList<Angebote>();
    ArrayList<String> listTitel = new ArrayList<String>();
    ArrayList<Angebote> myDynamicAngebot = new ArrayList<Angebote>();
    ArrayList<String> myEmail = new ArrayList<String>();
    ArrayList<String> myBeschreibung = new ArrayList<String>();

    private void init(){
        FileOutputStream fos = null;
        String text = "1|M|Hamburg|300|Titel von Haus 1|muster@gmail.com|Beschreibung beschreibung beschreibung\n" +
                "beschreibung|\n" +
                "|2|K|Frankfurt|150000|Titel von Haus 2| muster@gmail.com| beschreibung von haus 2|\n" +
                "|3|M|Hamburg|350|Titel von Haus 3| muster@gmail.com| beschreibung von haus 3|\n" +
                "|4|M|Berlin|250|Titel von Haus 4| muster@gmail.com|beschreibung von haus 4|\n" +
                "|5|K|Darmstadt|280000|Titel von Haus 5| muster@gmail.com| beschreibung von haus 5|\n" +
                "|6|M|Darmstadt|350|Titel von Haus 6| muster@gmail.com| beschreibung von haus 6|\n" +
                "|7|K|Kiel|1000000|Titel von Haus 7| muster@gmail.com|beschreibung von haus 7|\n" +
                "|8|K|Amsterdam|250000|Titel von Haus 8| muster@gmail.com| beschreibung von haus 8|\n" +
                "|9|M|Muenchen|400|Titel von Haus 9| muster@gmail.com|beschreibung von haus 9|\n" +
                "|10|M|Stuttgart|500|Titel von Haus 10| muster@gmail.com|beschreibung von haus 10|\n" +
                "|11|M|Koeln|450|Titel von Haus 11| muster@gmail.com|beschreibung von haus 11|\n"
                ;
        try {
            fos = openFileOutput(FILE_NAME,MODE_PRIVATE );
            fos.write(text.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void readFile(){
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            while (( text = br.readLine())!= null){
                sb.append(text).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    void angebotContainerLegen(){
        int counter = 0;

        angebotContainer.clear();
        // read data from a file
        readFile();
        // split string
        String[] aString = sb.toString().split("[|]");

        for (String string : aString){
            switch (counter){
                case 0: {
                    beitragID = Integer.parseInt(string);
                    counter++;

                    break;
                }
                case 1:{
                    art = string.charAt(0);
                    counter++;
                    break;
                }
                case 2:{
                    stadt = string;
                    counter++;
                    break;
                }
                case 3:{
                    preis = Double.parseDouble(string);
                    counter++;
                    break;
                }
                case 4:{
                    titel = string;
                    counter++;
                    break;
                }
                case 5: {
                    email = string;
                    counter++;
                    break;
                }
                case 6: {
                    beschreibung = string;
                    counter++;
                    break;
                }
                case 7:{
                    Angebote a = new Angebote(beitragID,art,stadt,preis,titel,email,beschreibung);
                    angebotContainer.add(a);
                    counter = 0;
                    break;
                }
            }
        }

    }
    protected void createTundPList(boolean boolMiet, boolean boolKauf) {
        boolean test = true;
        listTitel.clear();
        if(boolMiet){
            for (int j = 0 ; j < angebotContainer.size(); j++ ){
                if ((angebotContainer.get(j).stadt.equals(stadtName) ) && (angebotContainer.get(j).art == 'M')){
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
                if ((angebotContainer.get(j).stadt.equals(stadtName)  ) && (angebotContainer.get(j).art == 'K')){
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
        angebotContainerLegen();
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
            mImagesURL.add("https://www.pexels.com/photo/white-and-brown-concrete-building-2287310/");
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

