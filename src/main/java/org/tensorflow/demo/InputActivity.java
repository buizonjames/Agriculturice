package org.tensorflow.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;

public class InputActivity extends AppCompatActivity {

    private ImageButton btnCamera;
    private ImageButton btnBack;
    private ImageButton btnHelp;
    private ImageButton btnAbout;
    private ImageButton btnGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnHelp = (ImageButton) findViewById(R.id.btnHelp);
        btnAbout = (ImageButton) findViewById(R.id.btnAbout);
        btnGallery = (ImageButton) findViewById(R.id.btnGallery);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCameraActivity();
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHelpActivity();
            }
        });

    }

    private void startCameraActivity(){
        Intent intent = new Intent(InputActivity.this, ClassifierActivity.class);
        startActivity(intent);
        finish();
    }

    /*private void startAboutActivity(){
        Intent intent = new Intent(InputActivity.this, AboutActivity.class);
        startActivity(intent);
        finish();
    }*/

    private void startHelpActivity(){
        Intent intent = new Intent(InputActivity.this, HelpActivity.class);
        startActivity(intent);
        finish();
    }
}
