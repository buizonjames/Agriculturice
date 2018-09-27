package org.tensorflow.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SolutionActivity extends AppCompatActivity {

    private TextView lblSolution;
    private String diseaseName;
    private GlobalVariables gv = new GlobalVariables();

    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        lblSolution = (TextView)findViewById(R.id.txtSolution);
        lblSolution.setMovementMethod(new ScrollingMovementMethod());

        btnBack = (ImageButton)findViewById(R.id.btnBack4);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startResultActivity();
            }
        });

        diseaseName = gv.getDiseaseName();

        if(diseaseName.equalsIgnoreCase("brownspot")){
            lblSolution.setText("Improving soil fertility is the first step in\nmanaging brown spot. To do this:\n\n" +
                    "\t\u2022 Monitor soil nutrients regularly\n\n" +
                    "\t\u2022 Apply required fertilizers\n\n" +
                    "\t\u2022 For soils that are low in silicon, apply \n\t  calcium silicate slag before planting\n\n" +
                    "Fertilizers, however, can be costly and may\ntake many cropping seasons before\nbecoming effective. More economical \nmanagement options include:\n\n" +
                    "\t\u2022 Use resistant varieties\n\n" +
                    "\t\u2022 Use fungicides (e.g., iprodione,\n\t  propiconazole, azoxystrobin, \n\t  trifloxystrobin, and carbendazim) as seed \n\t  treatments\n\n" +
                    "\t\u2022 Treat seeds with hot water (53-54°C) for \n\t  10-12 minutes before planting, to control \n\t  primary infection at the seedling stage." +
                    "To \n\t  increase effectiveness of the treatment, \n\t  pre-soak seeds in cold water for eight\n\t  hours");
        }

        else if(diseaseName.equalsIgnoreCase("leafscald")){
            lblSolution.setText("\t\u2022 Use resistant varieties\n\n" +
                    "\t\u2022 Contact your local agriculture office for an \n\t  up-to-date list of available varieties\n\n" +
                    "\t\u2022 Avoid high use of fertilizer. Apply Nitrogen \n\t  in split\n\n" +
                    "\t\u2022 Use benomyl, carbendazim, quitozene, and \n\t  thiophanate-methyl to treat seeds\n\n" +
                    "\t\u2022 In the field, spraying of benomyl, fentin \n\t  acetate, edifenphos, and validamycin\n\t  significantly reduce the incidence of leaf\n\t  scald. Foliar application of captafol, \n\t  mancozeb, and copper oxychloride also\n\t  reduces the incidence and severity of the\n\t  fungal disease.");
        }

        else if(diseaseName.equalsIgnoreCase("redstripe")){
            lblSolution.setText("\t\u2022 Use resistant varieties\n\n" +
                    "\t\u2022 Contact your local agriculture office for an\n\t  up-to-date list of available varieties\n\n" +
                    "\t\u2022 Apply Nitrogen based on actual crop \n\t  requirements\n\n" +
                    "\t\u2022 Ensure optimum seeding rate and wider \n\t  plant spacing also appear to reduce the \n\t  disease\n\n" +
                    "\t\u2022 Use benzimidazole fungicides (benomyl, \n\t  carbendazim, and thiophanate-methyl) to \n\t  treat seeds");
        }

        else if(diseaseName.equalsIgnoreCase("tungro")){
            lblSolution.setText("Once a rice plant is infected by tungro, it cannot be cured.\n\n" +
                    "Preventive measures are more effective for the control of tungro than direct disease control measures." +
                    "Using insecticides to control leafhoppers is often not effective, because green leafhoppers continuously move to surrounding fields" +
                    "and spread tungro rapidly in very short feeding times.\n\n" +
                    "The most practical measures at present, include:\n\n" +
                    "\t\u2022 Grow tungro or leafhopper resistant \n\t  varieties. This is the most economical \n\t  means of managing the disease." +
                    "There are \n\t  tungro-resistant varieties available for the \n\t  Philippines, Malaysia, Indonesia, India, and \n\t  Bangladesh." +
                    "Contact your local \n\t  agriculture office for up-to-date lists of \n\t  varieties available.\n\n" +
                    "\t\u2022 Practice synchronous planting with \n\t  surrounding farms." +
                    "Delayed or late planting, \n\t  relative to the average date in a given area, \n\t  makes the field susceptible to Tungro." +
                    "\n\t  Late-planted fields also pose a risk to early \n\t  planting in the next season.\n\n" +
                    "\t\u2022 Adjust planting times to when green \n\t  leafhopper are not in season or abundant, \n\t  if known\n\n" +
                    "\t\u2022 Plow infected stubbles immediately after \n\t  harvest to reduce inoculum sources and \n\t  destroy the eggs" +
                    "and breeding sites of \n\t  green leaf hoppers");
        }

        else if(diseaseName.equalsIgnoreCase("bacterialblight")){
            lblSolution.setText("\t\u2022 Use balanced amounts of plant nutrients \n\t  especially nitrogen\n\n" +
                    "\t\u2022 Ensure good drainage of fields (in \n\t  conventionally floodes crops) and nurseries\n\n" +
                    "\t\u2022 Keep fields clean. Remove weed hosts and \n\t  plow under rice stubble, straw, rice ratoons \n\t  and volunteer seedlings, " +
                    "which can serve as \n\t  hosts of bacteria\n\n" +
                    "\t\u2022 Allow fallow fields to dry to suppress \n\t  disease agents in the soil and plant residues");
        }
    }

    private void startResultActivity(){
        Intent intent = new Intent(SolutionActivity.this, ResultActivity.class);
        startActivity(intent);
        finish();
    }
}
