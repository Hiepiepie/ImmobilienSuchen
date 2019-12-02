package com.example.immobiliensuchen;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class KundenActivity extends AppCompatActivity {
    private Button suchenButton;
    public static String stadtName;
    public boolean radioKaufen = false;
    public boolean radioMieten = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kunden);
        Toolbar toolbar = findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);
        suchenButton = (Button) findViewById(R.id.SuchenButton);
        suchenButton.setOnClickListener(new View.OnClickListener() {
            @Override
          public void onClick(View v) {
                EditText temp =  findViewById(R.id.StadtEditText);
                stadtName = temp.getText().toString();         // get Cityname
                if(stadtName == ""){
                    Toast t;
                    t = Toast.makeText(KundenActivity.this.getApplicationContext(),
                            "Stadt Name kann nicht leer sein. Bitte etwas eingeben ", Toast.LENGTH_SHORT);
                    t.show();
                }
                onRadioButtonClicked();   // set the value of bool , control if searching for Miet oder Kauf Object

                openBrowseActivity();
            }
        });
    }
    public void openBrowseActivity() {
        Intent intent = new Intent(KundenActivity.this, BrowseActivity.class);
        intent.putExtra("stadtname", stadtName);
        intent.putExtra("boolMiet", radioMieten);
        intent.putExtra("boolKauf", radioKaufen);
        startActivity(intent);

    }
    public void onRadioButtonClicked() {
        // Is the view now checked?
        RadioButton cb1 = (RadioButton) findViewById(R.id.KaufButton);
        RadioButton cb2 = (RadioButton) findViewById(R.id.MietButton);

        // Check which checkbox was clicked

        if (cb1.isChecked()) {
            radioKaufen = true;
            radioMieten=false;
        }
        if (cb2.isChecked()) {
            radioKaufen= false;
            radioMieten = true;
        }



    }
}
