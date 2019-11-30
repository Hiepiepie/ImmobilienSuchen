package com.example.immobiliensuchen;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
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

    Intent myIntent = getIntent();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);

        myAngebote = (Toolbar) findViewById(R.id.Angebote);
        myListview = (ListView) findViewById(R.id.listAngebote);

        init();
        readFile();

    }
    void search(){
        String stadtName = myIntent.getStringExtra("stadtname");
        boolean boolMiet = myIntent.getBooleanExtra("boolMiet", false);
        boolean boolKauf = myIntent.getBooleanExtra("boolKauf", false);


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

