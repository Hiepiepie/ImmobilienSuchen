package com.example.immobiliensuchen;


import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

class Angebote{
    int BeitragID;
    char art;
    String stadt;
    double preis;
    String titel;
    String beschreibung;
    ImageView image;

    public Angebote(int BeitragID,char art, String stadt, double Preis, String titel, String beschreibung, ImageView images ){
        this.BeitragID= BeitragID;
        this.art = art;
        this.beschreibung= beschreibung;
        this.titel = titel;
        this.stadt = stadt;
        this.preis = Preis;
        this.image = images;
    }

}

public class MainActivity extends AppCompatActivity {

    Button kundenB, maklerB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kundenB = (Button) findViewById(R.id.KundenButton);
        maklerB = (Button) findViewById(R.id.maklerButton);

        kundenB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToKundenActivity();

            }
        });
        maklerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToMaklerActivity();
            }
        });


    }

    private void moveToMaklerActivity() {
        Intent intent = new Intent(MainActivity.this, MaklerActivity.class);
        startActivity(intent);
    }

    private void moveToKundenActivity() {
        Intent intent = new Intent(MainActivity.this, KundenActivity.class);
        startActivity(intent);
    }

}