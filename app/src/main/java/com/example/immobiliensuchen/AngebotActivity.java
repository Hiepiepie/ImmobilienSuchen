package com.example.immobiliensuchen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Base64;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

import java.io.File;

public class AngebotActivity extends AppCompatActivity {

    private static final String TAG = "AngebotActivity";
    private static final String pathToPicture = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ImmobilienSuche/images";
    private Angebot angebot;
    private LinearLayout gallery, information;
    private LayoutInflater layoutInflater, layoutInflater1;
    private View view;
    private TextView titelText, preisText, contactText, beschreibungText;
    private ImageView favoritView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angebot);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Angebot");

        angebot = getIntent().getParcelableExtra("angebot");

        gallery =  findViewById(R.id.gallery);
        information =  findViewById(R.id.information);

        layoutInflater  = LayoutInflater.from(this);
        layoutInflater1 = LayoutInflater.from(this);

        view = layoutInflater.inflate(R.layout.activity_angebot, information, false);

        titelText =  findViewById(R.id.titelTextView);
        preisText =  findViewById(R.id.preisTextView);
        contactText =  findViewById(R.id.emailTextView);
        beschreibungText =  findViewById(R.id.beschreibungTextView);
        favoritView = findViewById(R.id.favorit);

        titelText.setText(angebot.getTitel());
        preisText.setText("Preis : " + Double.toString(angebot.getPreis()) + " Euro");
        beschreibungText.setText("Beschreibung : \n" + angebot.getBeschreibung());
        contactText.setText("Kontakt : " + angebot.getEmail());

        for (int i = 0; i < angebot.getImages().size() ; i++){
            View view1 = layoutInflater1.inflate(R.layout.item, gallery, false);
            ImageView imageView = view1.findViewById(R.id.imageView3);
            imageView.setImageBitmap(BitmapFactory.decodeFile(pathToPicture + File.separator + angebot.getImages().get(i)));
            gallery.addView(view1);
        }

        if(angebot.getFavorit() == 1)
            favoritView.setImageResource(R.drawable.star_on);


        favoritView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(angebot.getFavorit() == 0) {
                    favoritView.setImageResource(R.drawable.star_on);
                    angebot.setFavorit(1);
                    Intent intentWithResult = new Intent();
                    intentWithResult.putExtra("angebot", angebot);
                    setResult(1, intentWithResult);
                }
                else {
                    favoritView.setImageResource(R.drawable.star_off);
                    angebot.setFavorit(0);
                    Intent intentWithResult = new Intent();
                    intentWithResult.putExtra("angebot", angebot);
                    setResult(1, intentWithResult);
                }
            }
        });

    }
    private Bitmap getBitmapFromString(String stringPicture) {
        byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

}
