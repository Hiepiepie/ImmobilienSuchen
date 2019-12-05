package com.example.immobiliensuchen;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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


public class KundenActivity extends AppCompatActivity {
    private int STORAGE_PERMISSION_CODE = 1;
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

                checkPermission();

            }
        });
    }
    private void doThingsIfPermissionGranted(){
        EditText temp =  findViewById(R.id.StadtEditText);
        stadtName = temp.getText().toString();         // get Cityname
        if(stadtName.equals("")){
            Toast t;
            t = Toast.makeText(KundenActivity.this.getApplicationContext(),
                    "Stadt Name kann nicht leer sein. Bitte etwas eingeben ", Toast.LENGTH_SHORT);
            t.show();
        }
        onRadioButtonClicked();   // set the value of bool , control if searching for Miet oder Kauf Object

        openBrowseActivity();
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(KundenActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(KundenActivity.this, " You have already granted permission" , Toast.LENGTH_SHORT).show();
            doThingsIfPermissionGranted();
        } else {
            requestStoragePermission();
        }
    }
    private void requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(KundenActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                            doThingsIfPermissionGranted();
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
