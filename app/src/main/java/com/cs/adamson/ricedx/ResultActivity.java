package com.cs.adamson.ricedx;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends NavActivity {

    String JsonURLPost = "http://132.148.23.68/api/RiceMD/ProcessImg";
    String b64String;
    TextView diseasetxt, descriptiontxt, treatmenttxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_result, frameLayout);
        setTitle("Result");
        diseasetxt = (TextView)findViewById(R.id.diseasetxt);
        descriptiontxt = (TextView)findViewById(R.id.descriptiontxt);
        treatmenttxt = (TextView)findViewById(R.id.treatmenttxt);

        ImageView imgView = (ImageView)findViewById(R.id.resultView);
        Bundle extras = getIntent().getExtras();
        Bitmap bmp = extras.getParcelable("photo");
        imgView.setImageBitmap(bmp);
        b64String = extras.getString("B64");
        uploadImage();
    }


    private void uploadImage(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, JsonURLPost, null,
                new Response.Listener<JSONArray>(){ //Added null to satisfy the required parameters of the constructor
                    @Override
                    public void onResponse(JSONArray response){
                        loading.dismiss();
                        for(int i = 0; i < response.length(); i++){
                            try{
                                JSONObject iter = response.getJSONObject(i);
                                diseasetxt.append(iter.getString("disease_name")+ "\n");
                                descriptiontxt.append(iter.getString("disease_description")+ "\n\n");
                                treatmenttxt.append(iter.getString("treatment_description")+ "\n\n");
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        loading.dismiss();
                        Toast.makeText(ResultActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            public byte[] getBody(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("b64", b64String);
                JSONObject JsonObj = new JSONObject(params);
                String JsonString = "=" + JsonObj.toString();
                Log.d("base64", JsonString);
                return JsonString.getBytes();
            }
        };
        requestQueue.add(jsonArrayRequest);
    }
}
