package com.example.immobiliensuchen;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import java.util.List;

import android.content.Intent;

public class NeuAngebotActivity extends AppCompatActivity {

    //private int STORAGE_PERMISSION_CODE = 1;
    public StringBuilder sb = new StringBuilder();
    private static final String FILE_NAME = "/ImmobilienSuche/Angebote.txt";
    String titel, beschreibung, stadt, email;
    String art;
    double preis;
    int beitragID, favorit;
    List<Integer> imagesId;
    RadioButton verkaufen,vermieten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neu_angebot);
        Toolbar toolbar = findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);
        setTitle("Neues Angebot erstellen");

        //Reading Data from Main Activity
        beitragID = getIntent().getIntExtra("neuBeitragsID",0) + 1;

        if(beitragID == 0) {
            setResult(3);
            finish();
        }

        Button abschickenButton = findViewById(R.id.buttonAbschicken2);
        Button abbrechenButton = findViewById(R.id.buttonCancel2);

        abschickenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesId = new ArrayList<>();
                verkaufen = (RadioButton) findViewById(R.id.verkaufenButton2);
                vermieten = (RadioButton) findViewById(R.id.vermietenButton2);
                if(verkaufen.isChecked()){
                    art = "K";
                }else {
                    art = "M";
                }
                EditText stadtEditText = (EditText) findViewById(R.id.stadtTextEdit2);
                EditText titelEditText = (EditText) findViewById(R.id.titelTextEdit2);
                EditText preisEditText = (EditText) findViewById(R.id.preisTextEdit2);
                EditText beschreibungEditText = (EditText) findViewById(R.id.beschreibungTextEdit2);
                EditText emailEditText = (EditText) findViewById(R.id.emailTextEdit2);
                String s = preisEditText.getText().toString();
                preis = Double.parseDouble(s);
                stadt = stadtEditText.getText().toString();
                titel = titelEditText.getText().toString();
                beschreibung=beschreibungEditText.getText().toString();
                email = emailEditText.getText().toString();
                favorit = 0;
                if(imagesId.size() == 0){
                    imagesId.add(R.drawable.house_example);
                    imagesId.add(R.drawable.house_example);
                    imagesId.add(R.drawable.house_example);
                }

                Angebot neuesAngebot = new Angebot(beitragID,art,stadt,preis,titel,email,beschreibung,favorit, imagesId);

                //Sending result and Extra back to Main Activity
                Intent intentWithResult = new Intent();
                intentWithResult.putExtra("neuesAngebot", neuesAngebot);
                setResult(1, intentWithResult);
                finish();
            }
        });
        abbrechenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Sending result back to Main Activity
                setResult(2);
                finish();
            }
        });
    }

}
