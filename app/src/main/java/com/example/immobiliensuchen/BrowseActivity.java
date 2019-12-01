package com.example.immobiliensuchen;


import android.os.Bundle;
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

public class BrowseActivity extends AppCompatActivity {
    Toolbar myAngebote;
    ListView myListview;
    private static final String FILE_NAME = "NZSE.txt";
    Angebote[] angebotContainer ;

    public StringBuilder sb = new StringBuilder();

    String titel, beschreibung, stadt;
    char art;
    double preis;
    int beitragID;
    public int[] images;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);

        myAngebote = (Toolbar) findViewById(R.id.Angebote);
        myListview = (ListView) findViewById(R.id.listAngebote);

        init();
        search();


    }
    void search(){
        int counter = 0;
        // pass variable from KundenActivity
        String stadtName = getIntent().getStringExtra("stadtname");
        boolean boolMiet = getIntent().getBooleanExtra("boolMiet", true);
        boolean boolKauf = getIntent().getBooleanExtra("boolKauf", true);
        readFile();
        String[] aString = sb.toString().split("|");
        int i1;
        // search for cityname and creat a list of angebot in this city
        for( i1 =1; i1 < aString.length ; i1+=6){
            if(aString[i1] == stadtName){
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
                            counter = 0;
                            break;
                        }
                    }
                }
            }
        }
        if(i1 > aString.length){
            Toast t;
            t = Toast.makeText(BrowseActivity.this.getApplicationContext(),
                    " Stadt nicht gefunden oder kein Angebot in diese Stadt ", Toast.LENGTH_SHORT);
            t.show();

        }




    }
    private void init(){
        FileOutputStream fos = null;
        String text = "1|M|Hamburg|300|Titel von Haus 1|Beschreibung beschreibung beschreibung\n" +
                "beschreibung|\n" +
                "2|K|Frankfurt|150000|Titel von Haus 2| beschreibung von haus 2|\n" +
                "3|M|Hamburg|350|Titel von Haus 3| beschreibung von haus 3|\n" +
                "4|M|Berlin|250|Titel von Haus 4| beschreibung von haus 4|\n" +
                "5|K|Darmstadt|280000|Titel von Haus 5| beschreibung von haus 5|\n" +
                "6|M|Darmstadt|350|Titel von Haus 6| beschreibung von haus 6|\n" +
                "7|K|Kiel|1000000|Titel von Haus 7| beschreibung von haus 7|\n" +
                "8|K|Armsterdam|250000|Titel von Haus 8| beschreibung von haus 8|\n" +
                "9|M|Muenchen|400|Titel von Haus 9| beschreibung von haus 9|\n" +
                "10|M|Stuttgart|500|Titel von Haus 10| beschreibung von haus 10|\n" +
                "11|M|Koeln|450|Titel von Haus 11| beschreibung von haus 11|\n"
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

