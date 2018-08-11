package org.tensorflow.demo;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;

public class HelpActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private ImageButton btnAbout;
    private ImageButton btnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        btnBack = (ImageButton)findViewById(R.id.btnBack2);
        btnAbout = (ImageButton)findViewById(R.id.btnAbout2);
        btnHelp = (ImageButton)findViewById(R.id.btnHelp2);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    /*private void startAboutActivity(){
        Intent intent = new Intent(HelpActivity.this, AboutActivity.class);
        startActivity(intent);
        finish();
    }*/

    private void goBack(){
        Intent intent = new Intent(HelpActivity.this, InputActivity.class);
        startActivity(intent);
        finish();
    }

    private void startHelpActivity(){
        Intent intent = new Intent(HelpActivity.this, HelpActivity.class);
        startActivity(intent);
        finish();
    }
}
