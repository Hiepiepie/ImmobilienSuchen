package com.example.immobiliensuchen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AngebotActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angebot);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout gallery = (LinearLayout) findViewById(R.id.gallery);
        LinearLayout information = (LinearLayout) findViewById(R.id.information);
        LayoutInflater li  = LayoutInflater.from(this);
        LayoutInflater li2 = LayoutInflater.from(this);

        View v = li2.inflate(R.layout.activity_angebot, information, false);
        TextView titelText = (TextView) findViewById(R.id.titelTextView);
        TextView preisText = (TextView) findViewById(R.id.preisTextView);
        TextView contactText = (TextView) findViewById(R.id.emailTextView);
        TextView beschreibungText = (TextView) findViewById(R.id.beschreibungTextView);

        contactText.setText(getIntent().getStringExtra("Email"));
        beschreibungText.setText(getIntent().getStringExtra("Beschreibung"));

        titelText.setText(getIntent().getStringExtra("Titel"));
        String s = getIntent().getStringExtra("Preis");
        preisText.setText(s);








        for (int i= 0 ; i< 5 ; i++){
            View view = li.inflate(R.layout.item, gallery, false);
            ImageView imageView = view.findViewById(R.id.imageView3);
            imageView.setImageResource(R.drawable.home);
            gallery.addView(view);
        }

    }

}
