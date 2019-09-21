package com.fishes.bigreddisaster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
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
        try {
            AsyncTask serverConn = new ServerTask().execute("10.0.2.2:1337");
            this.server = (ClientDuplexer) serverConn.get();
        }catch (Exception ioe){
            System.err.println(ioe.getMessage());
        }
        this.lat = getIntent().getDoubleExtra("latitude",0);
        this.lng = getIntent().getDoubleExtra("longitude",0);
        System.out.println(server);



        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View v){
                final Intent mapIntent = new Intent(getApplicationContext(), MapsActivity.class);
                // ~~ construct message
                String message = lat + ":" + lng + ":" + "T";
                server.sendMessage(message);
                String response = null;
                try {
                    AsyncTask respConn = new ResponseTask().execute(server);
                    response = (String) respConn.get();
                }catch (Exception ioe){
                    System.err.println(ioe.getMessage());
                }
                System.out.println(response);
                // ~~ end construction
                startActivity(mapIntent);
            }
        });
        no.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View v){
                // ~~ construct message

                String message = lat + ":" + lng + ":" + "F";
                server.sendMessage(message);
                // ~~ end construction

            }
        });
    }
    private static class ServerTask extends AsyncTask<String, String, ClientDuplexer> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected ClientDuplexer doInBackground(String... ipPort) {
            String[] tokens = ipPort[0].split(":");
            ClientDuplexer server = null;
            Log.e("IPCONFIG", tokens[0] + " " + tokens[1]);
            try {
                server = new ClientDuplexer(new Socket(tokens[0],Integer.parseInt(tokens[1])));
                server.sendMessage("test:test:t");
            }catch(IOException ioe){
                System.err.println(ioe.getMessage());
            }
            return server;
        }


        @Override
        protected void onPostExecute(ClientDuplexer server){

        }

    }
    private static class ResponseTask extends AsyncTask<ClientDuplexer,String,String> {



        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(ClientDuplexer... server) {
            return server[0].recieveMessage();
        }
    }
}

