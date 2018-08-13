package org.tensorflow.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ResultActivity extends AppCompatActivity {

    private ImageView img;
    final String root =
            Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "tensorflow/preview.png";
    File imgFile = new File(root);

    private TextView lblDiseaseName;
    private TextView lblDiseaseDescription;
    private ImageButton btnBack;

    private String diseaseName;
    private GlobalVariables gv = new GlobalVariables();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        lblDiseaseName = (TextView)findViewById(R.id.lblName);
        lblDiseaseDescription = (TextView)findViewById(R.id.lblDesc);
        btnBack = (ImageButton)findViewById(R.id.btnBack3);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCameraActivity();
            }
        });

        diseaseName = gv.getDiseaseName();
        //Toast.makeText(this, gv.getDiseaseName() , Toast.LENGTH_SHORT).show(); //FOR DEBUGGING

        if(imgFile.exists()){
            Bitmap bmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            img = (ImageView)findViewById(R.id.imgPlant);
            img.setImageBitmap(bmp);
        }

        if(diseaseName.equalsIgnoreCase("brownspot")){
            lblDiseaseName.setText("BROWN SPOT");
            lblDiseaseDescription.setText("Brown spot is a fungal disease. Its most\nobservable damage are the numerous " +
                    "big\nspots on the leaves which can kill the whole\nleaf.");
        }

        else if(diseaseName.equalsIgnoreCase("leafscald")){
            lblDiseaseName.setText("LEAF SCALD");
            lblDiseaseDescription.setText("Scald is a fungal disease which causes the\nscalded appearance of leaves.");
        }

        else if(diseaseName.equalsIgnoreCase("tungro")){
            lblDiseaseName.setText("TUNGRO");
            lblDiseaseDescription.setText("Rice tungro disease is caused by two viruses\nand transmitted by leafhoppers. " +
                    "It is one of\nthe most destructive diseases of rice in\nSouth and Southeast Asia.");
        }

        else if(diseaseName.equalsIgnoreCase("redstripe")){
            lblDiseaseName.setText("RED STRIPE");
            lblDiseaseDescription.setText("Red stripe causes formation of lesions on\nleaves. It usually occurs when the " +
                    "plants\nreach the reproductive stage.");
        }

        else if(diseaseName.equalsIgnoreCase("bacterialblight")){
            lblDiseaseName.setText("BACTERIAL BLIGHT");
            lblDiseaseDescription.setText("Bacterial blight causes wilting of seedlings\nand yellowing and drying of leaves.");
        }
    }

    private void startCameraActivity(){
        Intent intent = new Intent(ResultActivity.this, ClassifierActivity.class);
        startActivity(intent);
        finish();
    }

}
