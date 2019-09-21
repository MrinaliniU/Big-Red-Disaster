package com.fishes.bigreddisaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.PrintWriter;
import java.util.Scanner;

public class CanYouWalk extends AppCompatActivity {

    Button yes;
    Button no;
    ClientDuplexer server;
    String lat, lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_can_you_walk);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Log.e("Oh No", "Oh NO");
                lng = "0";
                lat = "0";
            } else {
                lng= extras.getString("longitude");
                lat = extras.getString("latitude");
            }
        } else {
            lng= (String) savedInstanceState.getSerializable("longitude");
            lat = (String) savedInstanceState.getSerializable("latitude");
        }

        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final Intent mapIntent = new Intent(getApplicationContext(), MapsActivity.class);
                mapIntent.putExtra("lat", lat);
                mapIntent.putExtra("lng", lng);
                // ~~ construct message

                String message = lat + ":" + lng + ":" + "T";
                Log.e("Lat", message);
                //server.sendMessage(message);
               // String resp = server.recieveMessage();
                // ~~ end construction
                startActivity(mapIntent);

            }

        });
        no.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // ~~ construct message

                String message = lat + ":" + lng + ":" + "F";
                server.sendMessage(message);
                // ~~ end construction

            }
        });
    }
}
