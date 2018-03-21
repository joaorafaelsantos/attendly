package com.example.paulo.attendly;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class DanielActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daniel);

        //        connectFirebase.writeData();
//        connectFirebase.readData();
    }

    private static final int PICK_IMAGE_REQUEST = 234;

    private static Button btnChoose;
    private static Button btnUpload;

    private static ImageView imageView;

    private Uri filePath;

    private void choosePicture(){

        btnChoose = findViewById(R.id.btnChoose);





    }

}
