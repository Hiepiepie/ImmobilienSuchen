package com.example.immobiliensuchen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class MaklerActivity extends AppCompatActivity {
    Button neuAngebotButton,angebotAnlegenButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makler_acitivity);
        Toolbar toolbar = findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);
        neuAngebotButton = (Button) findViewById(R.id.neuAngebotButton);
        angebotAnlegenButton = (Button) findViewById(R.id.angebotVerwaltenButton);

        neuAngebotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaklerActivity.this, NeuAngebotActivity.class);
                startActivity(intent);

            }
        });
        angebotAnlegenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaklerActivity.this, AngebotVerwaltenActivity.class);
                startActivity(intent);
            }
        });
    }

}
