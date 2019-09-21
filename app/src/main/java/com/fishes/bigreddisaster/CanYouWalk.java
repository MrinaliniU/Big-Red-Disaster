package com.fishes.bigreddisaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.PrintWriter;
import java.util.Scanner;

public class CanYouWalk extends AppCompatActivity {

    Button yes;
    Button no;
    ClientDuplexer server;
    double lat;
    double lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_can_you_walk);
        Bundle extra = getIntent().getExtras();
        this.server = (ClientDuplexer)extra.get("server");
        this.lat = (double)extra.get("latitude");
        this.lng = (double)extra.get("longitude");



        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final Intent mapIntent = new Intent(getApplicationContext(), MapsActivity.class);
                // ~~ construct message

                String message = lat + ":" + lng + ":" + "T";
                server.sendMessage(message);
                String resp = server.recieveMessage();
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
