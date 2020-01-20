package com.example.immobiliensuchen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class AngebotAktualisierenActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    public StringBuilder sb = new StringBuilder();
    private static final String FILE_NAME = "NZSE.txt";
    static ArrayList<Angebot> angebotContainer = new ArrayList<Angebot>();
    String titel, beschreibung, stadt, email;
    String art;
    String preis;


    public RadioButton verkaufen,vermieten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ein_angebot);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        verkaufen = findViewById(R.id.verkaufenButton);
        vermieten = findViewById(R.id.vermietenButton);

        Button abschickenButton = (Button) findViewById(R.id.buttonAbschicken);
        Button abbrechenButton = (Button) findViewById(R.id.buttonCancel);

        //Reading Data from Main Activity
        getText();

        abschickenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkPermission();
                finish();
            }
        });
        abbrechenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /*private void checkPermission(){
        if (ContextCompat.checkSelfPermission(AngebotAktualisierenActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(AngebotAktualisierenActivity.this, " You have already granted permission" , Toast.LENGTH_SHORT).show();

        } else {
            requestStoragePermission();
        }
    }
    private void requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(AngebotAktualisierenActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(AngebotAktualisierenActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission GRANTED", Toast.LENGTH_SHORT ).show();
            } else
            {
                Toast.makeText(this,"Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }*/


    @SuppressLint("SetTextI18n")
    private void getText(){
        EditText stadtEditText = (EditText) findViewById(R.id.stadtTextEdit);
        EditText titelEditText = (EditText) findViewById(R.id.titelTextEdit);
        EditText preisEditText = (EditText) findViewById(R.id.preisTextEdit);
        EditText beschreibungEditText = (EditText) findViewById(R.id.beschreibungTextEdit);
        EditText emailEditText = (EditText) findViewById(R.id.emailTextEdit);

        stadt = getIntent().getStringExtra("Stadt");
        email = getIntent().getStringExtra("Email");
        titel = getIntent().getStringExtra("Titel");
        preis = getIntent().getStringExtra("Preis");
        beschreibung = getIntent().getStringExtra("Beschreibung");
        art = getIntent().getStringExtra("Art");

        beschreibungEditText.setText(beschreibung, TextView.BufferType.EDITABLE);
        stadtEditText.setText(stadt, TextView.BufferType.EDITABLE);
        titelEditText.setText(titel, TextView.BufferType.EDITABLE);
        emailEditText.setText(email, TextView.BufferType.EDITABLE);
        preisEditText.setText(preis, TextView.BufferType.EDITABLE);

        if(art.equals("K")){
            verkaufen.setChecked(true);
            vermieten.setChecked(false);
        }else
        if (art.equals("M")){
            verkaufen.setChecked(false);
            vermieten.setChecked(true);
        }
    }
}
