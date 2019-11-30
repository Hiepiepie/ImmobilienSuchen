package com.example.immobiliensuchen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AngebotVerwaltenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angebot_verwalten);
        Toolbar toolbar = findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);



    }

}
