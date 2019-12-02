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

    String titel, beschreibung, stadt;
    char art;
    double preis;
    int beitragID;
    public int[] images;
    // pass variable from KundenActivity
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


        init();
        search();
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
                Bundle b = new Bundle();
                b.putSerializable("angebot", myDynamicAngebot.get(position));
                myIntent.putExtra("angebot", b);
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

    void search(){
        int counter = 0;

        angebotContainer.clear();
        // read data from a file
        readFile();
        // split string
        String[] aString = sb.toString().split("[|]");

        // search for cityname and creat a list of angebot in this city
//        for( int i1 =2; i1 < aString.length ; i1+=7){
//                if(aString[i1].equals(stadtName)){
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
                            beschreibung = string;
                            counter++;
                            break;
                        }
                        case 6:{
                            Angebote a = new Angebote(beitragID,art,stadt,preis,titel,beschreibung);
                            angebotContainer.add(a);
                            counter = 0;
                            break;
                        }
                    }
                }
//            }
//        }
        // give notification when cant find any matched city name





    }
    private void init(){
        FileOutputStream fos = null;
        String text = "1|M|Hamburg|300|Titel von Haus 1|Beschreibung beschreibung beschreibung\n" +
                "beschreibung|\n" +
                "|2|K|Frankfurt|150000|Titel von Haus 2| beschreibung von haus 2|\n" +
                "|3|M|Hamburg|350|Titel von Haus 3| beschreibung von haus 3|\n" +
                "|4|M|Berlin|250|Titel von Haus 4| beschreibung von haus 4|\n" +
                "|5|K|Darmstadt|280000|Titel von Haus 5| beschreibung von haus 5|\n" +
                "|6|M|Darmstadt|350|Titel von Haus 6| beschreibung von haus 6|\n" +
                "|7|K|Kiel|1000000|Titel von Haus 7| beschreibung von haus 7|\n" +
                "|8|K|Armsterdam|250000|Titel von Haus 8| beschreibung von haus 8|\n" +
                "|9|M|Muenchen|400|Titel von Haus 9| beschreibung von haus 9|\n" +
                "|10|M|Stuttgart|500|Titel von Haus 10| beschreibung von haus 10|\n" +
                "|11|M|Koeln|450|Titel von Haus 11| beschreibung von haus 11|"
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

