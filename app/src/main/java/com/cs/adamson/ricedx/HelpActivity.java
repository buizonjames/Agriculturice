package com.cs.adamson.ricedx;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HelpActivity extends NavActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_help, frameLayout);
        setTitle("Help");

        listView = (ListView)findViewById(R.id.helplv);

        String[] values = new String[]{"Manual", "1. Click the Camera button Located on the top right of the screen",
            "2. Capture Image of the Rice Plant's Leaf", "3. The App will show the Result shortly", "", "",
            "Copyright © RiceDX 2018"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        navigationView.getMenu().getItem(2).setChecked(true);
    }
}
