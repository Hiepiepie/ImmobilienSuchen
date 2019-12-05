package com.example.immobiliensuchen;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NeuAngebotActivity extends AppCompatActivity {
    public StringBuilder sb = new StringBuilder();
    private static final String FILE_NAME = "NZSE.txt";
    static ArrayList<Angebote> angebotContainer = new ArrayList<Angebote>();
    String titel, beschreibung, stadt, email;
    char art;
    double preis;
    int beitragID;
    public int[] images;
    RadioButton verkaufen,vermieten;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neu_angebot);
        Toolbar toolbar = findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);
        angebotContainerLegen();





    }
    private String createText(){
        String text;

        int tempID = angebotContainer.size() + 1;
        verkaufen = (RadioButton) findViewById(R.id.verkaufenButton);
        vermieten = (RadioButton) findViewById(R.id.vermietenButton);
        if(verkaufen.isChecked()){
            art = 'K';
        }else {
            art = 'M';
        }

        EditText stadtEditText = (EditText) findViewById(R.id.stadtTextEdit);
        EditText titelEditText = (EditText) findViewById(R.id.titelTextEdit);
        EditText preisEditText = (EditText) findViewById(R.id.preisTextEdit);
        EditText beschreibungEditText = (EditText) findViewById(R.id.beschreibungTextEdit);
        EditText emailEditText = (EditText) findViewById(R.id.emailTextEdit);


        String s = preisEditText.getText().toString();
        preis = Double.parseDouble(s);
        stadt = stadtEditText.getText().toString();
        titel = titelEditText.getText().toString();
        beschreibung=beschreibungEditText.getText().toString();
        email = emailEditText.getText().toString();
        // create first angebot
        text = angebotContainer.get(0).BeitragID + "|" + angebotContainer.get(0).art + "|" + angebotContainer.get(0).preis + "|" +
                angebotContainer.get(0).titel + "|" + angebotContainer.get(0).email + "|" + angebotContainer.get(0).beschreibung + "|";
        // create second angebot until last angebot
        for (int i = 1; i < angebotContainer.size(); i++){
            text = text + "|" + angebotContainer.get(i).BeitragID + "|" + angebotContainer.get(i).art + "|" + angebotContainer.get(i).preis + "|" +
                    angebotContainer.get(i).titel + "|" + angebotContainer.get(i).email + "|" + angebotContainer.get(i).beschreibung + "|";
        }
        // create new angebot
        text = text + "|" + tempID +"|"+ art +"|"+ stadt +"|"+ preis +"|"+ titel +"|"+ email +"|"+ beschreibung +"|" ;

        return text;

    }
    private void saveToFile(){
        FileOutputStream fos = null;
        String text = createText();

        File file = new File(FILE_NAME);
        boolean deleted = file.delete();



        if (deleted) {
            try {
                fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                fos.write(text.getBytes());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
