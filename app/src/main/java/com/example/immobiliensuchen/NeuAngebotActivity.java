package com.example.immobiliensuchen;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.graphics.Bitmap;
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
import android.net.Uri;

import android.content.Intent;
import java.io.FileDescriptor;
import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
    ImageView addPhoto;
    List<Bitmap> photos;
    private LayoutInflater layoutInflater;
    private LinearLayout gallery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neu_angebot);
        Toolbar toolbar = findViewById(R.id.Angebote);
        setSupportActionBar(toolbar);
        setTitle("Neues Angebot erstellen");

        addPhoto = findViewById(R.id.addphoto);
        gallery =  findViewById(R.id.gallery);
        layoutInflater = LayoutInflater.from(this);

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

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(NeuAngebotActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        //addPhoto.setImageBitmap(selectedImage);

                        View view = layoutInflater.inflate(R.layout.item, gallery, false);

                        ImageView imageView = view.findViewById(R.id.imageView3);
                        imageView.setImageBitmap(selectedImage);
                        gallery.addView(view);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                View view = layoutInflater.inflate(R.layout.item, gallery, false);

                                ImageView imageView = view.findViewById(R.id.imageView3);
                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                gallery.addView(view);
                                //addPhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                   break;
            }
        }
    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Picture..");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
