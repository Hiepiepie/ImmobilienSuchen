package com.example.immobiliensuchen;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class KundenActivity extends AppCompatActivity {
    private Button suchenButton;
    public static String stadtName;
    public boolean checkboxKaufen = true;
    public boolean checkboxMieten = true ;

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
                onCheckboxClicked();   // set the value of bool , control if searching for Miet oder Kauf Object

                openBrowseActivity();
            }
        });
    }
    public void openBrowseActivity() {
        Intent intent = new Intent(KundenActivity.this, BrowseActivity.class);
        intent.putExtra("stadtname", stadtName);
        intent.putExtra("boolMiet", checkboxMieten);
        intent.putExtra("bookKauf", checkboxKaufen);
        startActivity( intent);

    }
    public void onCheckboxClicked() {
        // Is the view now checked?
        CheckBox cb1 = (CheckBox) findViewById(R.id.KaufCheckbox);
        CheckBox cb2 = (CheckBox) findViewById(R.id.MieteCheckbox);

        // Check which checkbox was clicked

        if (cb1.isChecked()) checkboxKaufen = true;
        if (cb2.isChecked()) checkboxMieten = true;



    }
}
