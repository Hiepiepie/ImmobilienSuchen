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

public class NeuAngebotActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;
    public StringBuilder sb = new StringBuilder();
    private static final String FILE_NAME = "NZSE.txt";
    static ArrayList<Angebote> angebotContainer = new ArrayList<Angebote>();
    String titel, beschreibung, stadt, email;
    String art;
    double preis;
    int beitragID;
    public int[] images;
    RadioButton verkaufen,vermieten;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neu_angebot);
        Toolbar toolbar = findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);


        Button abschickenButton = (Button) findViewById(R.id.buttonAbschicken2);
        Button abbrechenButton = (Button) findViewById(R.id.buttonCancel2);

        abschickenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
                finish();
            }
        });
        abbrechenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void checkPermission(){
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
    }


    public void readFile(){
        String alleausgaben = "";
        angebotContainer= new ArrayList<>();
        try{
            File myFile = new File(Environment.getExternalStorageDirectory().getPath()+"/"+ "NZSE2.txt");
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
                Angebote a = new Angebote(beitragID,art,stadt,preis,titel,email,beschreibung);
                angebotContainer.add(a);
            }


        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void saveToFile(){
        readFile();
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

        try {

            File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "NZSE2.txt");
            FileOutputStream fos = new FileOutputStream(myFile);

            OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);
            JSONArray jsonarray = new JSONArray();

                JSONObject object = new JSONObject();

                object.put("BeitragsID",tempID);
                object.put("Titel",titel);
                object.put("Art",art);
                object.put("Preis",preis);
                object.put("Stadt",stadt);
                object.put("Email",email);
                object.put("Beschreibung",beschreibung);
                jsonarray.put(object);

            myOutWriter.append(jsonarray.toString());
            myOutWriter.close();
            fos.close();
            Toast.makeText(this, angebotContainer.size()
                            +
                            " werden gespeichert!",
                    Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}
