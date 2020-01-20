package com.example.immobiliensuchen;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

public class NeuAngebotActivity extends AppCompatActivity {

    //private int STORAGE_PERMISSION_CODE = 1;
    public StringBuilder sb = new StringBuilder();
    private static final String FILE_NAME = "/ImmobilienSuche/Angebote.txt";
    String titel, beschreibung, stadt, email;
    String art;
    double preis;
    int beitragID, favorit;
    List<Integer> imagesId;
    RadioButton verkaufen,vermieten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neu_angebot);
        Toolbar toolbar = findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);

        //Reading Data from Main Activity
        beitragID = getIntent().getIntExtra("neuBeitragsID",0) + 1;

        if(beitragID == 0) {
            setResult(3);
            finish();
        }

        Button abschickenButton = findViewById(R.id.buttonAbschicken2);
        Button abbrechenButton = findViewById(R.id.buttonCancel2);

        abschickenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesId = new ArrayList<>();
                verkaufen = (RadioButton) findViewById(R.id.verkaufenButton2);
                vermieten = (RadioButton) findViewById(R.id.vermietenButton2);
                if(verkaufen.isChecked()){
                    art = "K";
                }else {
                    art = "M";
                }
                EditText stadtEditText = (EditText) findViewById(R.id.stadtTextEdit2);
                EditText titelEditText = (EditText) findViewById(R.id.titelTextEdit2);
                EditText preisEditText = (EditText) findViewById(R.id.preisTextEdit2);
                EditText beschreibungEditText = (EditText) findViewById(R.id.beschreibungTextEdit2);
                EditText emailEditText = (EditText) findViewById(R.id.emailTextEdit2);
                String s = preisEditText.getText().toString();
                preis = Double.parseDouble(s);
                stadt = stadtEditText.getText().toString();
                titel = titelEditText.getText().toString();
                beschreibung=beschreibungEditText.getText().toString();
                email = emailEditText.getText().toString();
                favorit = 0;
                if(imagesId.size() == 0){
                    imagesId.add(R.drawable.house_example);
                    imagesId.add(R.drawable.house_example);
                    imagesId.add(R.drawable.house_example);
                }

                Angebot neuesAngebot = new Angebot(beitragID,art,stadt,preis,titel,email,beschreibung,favorit, imagesId);

                //Sending result and Extra back to Main Activity
                Intent intentWithResult = new Intent();
                intentWithResult.putExtra("neuesAngebot", neuesAngebot);
                setResult(1, intentWithResult);
                finish();
            }
        });
        abbrechenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Sending result back to Main Activity
                setResult(2);
                finish();
            }
        });
    }

   /* private void checkPermission(){
        if (ContextCompat.checkSelfPermission(NeuAngebotActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(NeuAngebotActivity.this, " You have already granted permission" , Toast.LENGTH_SHORT).show();
            saveToFile();
        } else {
            requestStoragePermission();
        }
    }
    private void requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(NeuAngebotActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(NeuAngebotActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

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

    /*public void readFile(){
        String alleausgaben = "";
        angebotContainer = new ArrayList<Angebot>();
        try{
            File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ FILE_NAME);
            FileInputStream fis = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader( new InputStreamReader(fis, StandardCharsets.UTF_8.name()));
            String line;
            while((line = myReader.readLine())!= null ){
                alleausgaben += line;
            }
            JSONArray jsonArray = new JSONArray(alleausgaben);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                beitragID = Integer.parseInt(jsonObject.getString("BeitragsID"));
                art = jsonObject.getString("Art");
                titel = jsonObject.getString("Titel");
                preis = Double.parseDouble(jsonObject.getString("Preis"));
                stadt = jsonObject.getString("Stadt");
                email = jsonObject.getString("Email");
                beschreibung = jsonObject.getString("Beschreibung");
                Angebot a = new Angebot(beitragID,art,stadt,preis,titel,email,beschreibung);
                angebotContainer.add(a);
            }

        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }*/

   /* private void saveToFile(){
        int tempID = angebotContainer.size() + 1;
        verkaufen = (RadioButton) findViewById(R.id.verkaufenButton2);
        vermieten = (RadioButton) findViewById(R.id.vermietenButton2);
        if(verkaufen.isChecked()){
            art = "K";
        }else {
            art = "M";
        }
        EditText stadtEditText = (EditText) findViewById(R.id.stadtTextEdit2);
        EditText titelEditText = (EditText) findViewById(R.id.titelTextEdit2);
        EditText preisEditText = (EditText) findViewById(R.id.preisTextEdit2);
        EditText beschreibungEditText = (EditText) findViewById(R.id.beschreibungTextEdit2);
        EditText emailEditText = (EditText) findViewById(R.id.emailTextEdit2);
        String s = preisEditText.getText().toString();
        preis = Double.parseDouble(s);
        stadt = stadtEditText.getText().toString();
        titel = titelEditText.getText().toString();
        beschreibung=beschreibungEditText.getText().toString();
        email = emailEditText.getText().toString();

        Angebot neuAngebot = new Angebot(tempID,art,stadt,preis,titel,email,beschreibung);
        angebotContainer.add(neuAngebot);

        try {
            File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ImmobilienSuche/Angebote.txt");
            FileOutputStream fos = new FileOutputStream(myFile);

            OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);
            JSONArray jsonarray = new JSONArray();

            JSONObject object = new JSONObject();

            for (int i = 0; i < angebotContainer.size(); i++) {
                Angebot a = angebotContainer.get(i);
                object.put("BeitragsID", a.BeitragID);
                object.put("Titel", a.titel);
                object.put("Art", a.art);
                object.put("Preis", a.preis);
                object.put("Stadt", a.stadt);
                object.put("Email", a.email);
                object.put("Beschreibung", a.beschreibung);
                object.put("Favorit", a.favorit);
                jsonarray.put(object);
            }

            myOutWriter.append(jsonarray.toString());
            myOutWriter.close();
            fos.close();
            Toast.makeText(this, "Neu Angebot mit BeitragID : " + tempID + " wird gespeichert!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
*/
}
