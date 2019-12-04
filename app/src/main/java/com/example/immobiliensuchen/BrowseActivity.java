package com.example.immobiliensuchen;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BrowseActivity extends AppCompatActivity {
    Toolbar myAngebote;
    ListView myListview;
    private static final String FILE_NAME = "NZSE.txt";


    public StringBuilder sb = new StringBuilder();

    String titel, beschreibung, stadt, email;
    char art;
    double preis;
    int beitragID;
    public int[] images;
    String stadtName ;
    static ArrayList<Angebote> angebotContainer = new ArrayList<Angebote>();
    ArrayList<String> listTitel = new ArrayList<String>();
    ArrayList<Angebote> myDynamicAngebot = new ArrayList<Angebote>();
    ArrayAdapter<String> myAdapter ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);


        myListview = (ListView) findViewById(R.id.listAngebote);


        //init();
        angebotContainerLegen();
//         pass variable from KundenActivity
        boolean boolMiet = getIntent().getBooleanExtra("boolMiet", true);
        boolean boolKauf = getIntent().getBooleanExtra("boolKauf", false);
        stadtName = getIntent().getStringExtra("stadtname");

        listTitel.clear();
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listTitel);
        myAdapter.notifyDataSetChanged();
        createTitelList(boolMiet, boolKauf);

        for (int i = 0; i<listTitel.size(); i++){
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listTitel);
            myListview.setAdapter(myAdapter);
        }


        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(BrowseActivity.this, AngebotActivity.class);
                myIntent.putExtra("Email",myDynamicAngebot.get(position).email);
                myIntent.putExtra("Beschreibung",myDynamicAngebot.get(position).beschreibung);
                myIntent.putExtra("Preis",myDynamicAngebot.get(position).preis);
                myIntent.putExtra("Titel",myDynamicAngebot.get(position).titel);
                startActivity(myIntent);
            }
        });



    }


    protected void createTitelList(boolean boolMiet, boolean boolKauf) {
        boolean test = true;
        listTitel.clear();
        if(boolMiet){
            for (int j = 0 ; j < angebotContainer.size(); j++ ){
                if ((angebotContainer.get(j).stadt.equals(stadtName) ) && (angebotContainer.get(j).art == 'M')){
                     test = false;
                     listTitel.add(angebotContainer.get(j).titel);
                     myDynamicAngebot.add(angebotContainer.get(j));
                }
            }
        }
        if(boolKauf){
            for (int j = 0 ; j < angebotContainer.size(); j++ ){
                if ((angebotContainer.get(j).stadt.equals(stadtName)  ) && (angebotContainer.get(j).art == 'K')){
                    test = false;
                    listTitel.add(angebotContainer.get(j).titel);
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

