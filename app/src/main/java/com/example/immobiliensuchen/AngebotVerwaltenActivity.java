package com.example.immobiliensuchen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AngebotVerwaltenActivity extends AppCompatActivity {

    public StringBuilder sb = new StringBuilder();
    private static final String FILE_NAME = "NZSE.txt";
    ArrayList<String> listTitel = new ArrayList<String>();
    static ArrayList<Angebote> angebotContainer = new ArrayList<Angebote>();
    String titel, beschreibung, stadt, email;
    char art;
    double preis;
    int beitragID;
    public int[] images;
    ArrayAdapter<String> myAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angebot_verwalten);
        Toolbar toolbar = findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);
        ListView alleAngebote = (ListView) findViewById(R.id.alleAngebote);
        angebotContainerLegen();

        // create a list of titel
        for (int i = 0; i < angebotContainer.size() ; i++){
            listTitel.add(angebotContainer.get(i).titel);

        }
        // create rows
        for (int i = 0; i<listTitel.size(); i++){
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listTitel);
            alleAngebote.setAdapter(myAdapter);
        }
        // on row's item clicked
        alleAngebote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent myIntent = new Intent(AngebotVerwaltenActivity.this, einAngebotActivity.class);
                myIntent.putExtra("Email",angebotContainer.get(position).email);
                myIntent.putExtra("Beschreibung",angebotContainer.get(position).beschreibung);
                myIntent.putExtra("Preis",angebotContainer.get(position).preis);
                myIntent.putExtra("Titel",angebotContainer.get(position).titel);
                myIntent.putExtra("type", angebotContainer.get(position).art);
                myIntent.putExtra("stadt", angebotContainer.get(position).stadt);

                startActivity(myIntent);

            }
        });





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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

}
