package com.example.immobiliensuchen;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class AngebotAktualisierenActivity extends AppCompatActivity {

    private Angebot angebot;
    private String titel, beschreibung, stadt, email, art;
    double preis;
    int beitragID, favorit;

    private RadioButton verkaufen,vermieten;
    private ImageView deleteView;
    private EditText stadtEditText, titelEditText, beschreibungEditText,emailEditText, preisEditText;


    @Override
    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_angebot);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Angebot editieren");

        verkaufen = findViewById(R.id.verkaufenButton);
        vermieten = findViewById(R.id.vermietenButton);
        deleteView = findViewById(R.id.delete);

        Button abschickenButton = (Button) findViewById(R.id.buttonAbschicken);
        Button abbrechenButton = (Button) findViewById(R.id.buttonCancel);

        //Reading Data from Main Activity
        angebot = getIntent().getParcelableExtra("angebot");

        stadtEditText = (EditText) findViewById(R.id.stadtTextEdit);
        titelEditText = (EditText) findViewById(R.id.titelTextEdit);
        preisEditText = (EditText) findViewById(R.id.preisTextEdit);
        beschreibungEditText = (EditText) findViewById(R.id.beschreibungTextEdit);
        emailEditText = (EditText) findViewById(R.id.emailTextEdit);

        beschreibungEditText.setText(angebot.getBeschreibung(), TextView.BufferType.EDITABLE);
        stadtEditText.setText(angebot.getStadt(), TextView.BufferType.EDITABLE);
        titelEditText.setText(angebot.getTitel(), TextView.BufferType.EDITABLE);
        emailEditText.setText(angebot.getEmail(), TextView.BufferType.EDITABLE);
        preisEditText.setText(Double.toString(angebot.getPreis()), TextView.BufferType.EDITABLE);

        if(angebot.getArt().equals("K")){
            verkaufen.setChecked(true);
            vermieten.setChecked(false);
        }else if (angebot.getArt().equals("M")){
            verkaufen.setChecked(false);
            vermieten.setChecked(true);
        }

        abschickenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verkaufen.isChecked()){
                    art = "K";
                }else {
                    art = "M";
                }

                String s = preisEditText.getText().toString();
                beitragID = angebot.getBeitragID();
                preis = Double.parseDouble(s);
                stadt = stadtEditText.getText().toString();
                titel = titelEditText.getText().toString();
                beschreibung=beschreibungEditText.getText().toString();
                email = emailEditText.getText().toString();
                favorit = angebot.getFavorit();
                Angebot neuesAngebot = new Angebot(beitragID,art,stadt,preis,titel,email,beschreibung,favorit, angebot.getImages());

                //Sending result and Extra back to Main Activity
                Intent intentWithResult = new Intent();
                intentWithResult.putExtra("angebot", neuesAngebot);
                setResult(2, intentWithResult);
                finish();
            }
        });
        abbrechenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(3);
                finish();
            }
        });

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(AngebotAktualisierenActivity.this);

                // Set a title for alert dialog
                builder.setTitle("Select your answer.");

                // Ask the final question
                builder.setMessage("Are you sure to hide?");

                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when user clicked the Yes button
                        // Set the TextView visibility GONE
                        Intent intentWithResult = new Intent();
                        intentWithResult.putExtra("beitragsId",angebot.getBeitragID());
                        setResult(4, intentWithResult);
                        finish();
                    }
                });

                // Set the alert dialog no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when No button clicked
//                        Toast.makeText(getApplicationContext(),
//                                "abgebrochen",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });
    }
}
