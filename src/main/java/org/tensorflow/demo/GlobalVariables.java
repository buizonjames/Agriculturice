package org.tensorflow.demo;

import android.app.Application;

public class GlobalVariables extends Application {

    public static String diseaseName;
    private static String diseaseDesc;

    public String getDiseaseName(){
        return diseaseName;
    }

    public String getDiseaseDesc(){
        return diseaseDesc;
    }

    public void setDiseaseName(String dn){
        diseaseName = dn;
    }

    public void setDiseaseDesc(String dd){
        diseaseDesc = dd;
    }
}
