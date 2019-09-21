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
import java.util.concurrent.ExecutionException;

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

        // ~~init server
        try {
            AsyncTask serverConn = new InitServerTask().execute("10.0.2.2:1337");
            this.server = (ClientDuplexer) serverConn.get();
        }catch (Exception ioe){
            System.err.println(ioe.getMessage());
        }
        // ~~ end init

        this.lat = getIntent().getDoubleExtra("latitude", 0);
        this.lng = getIntent().getDoubleExtra("longitude",0);



        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View v){
                final Intent mapIntent = new Intent(getApplicationContext(), MapsActivity.class);
                String response = "";
                String[] tokens;
                AsyncTask resp=null;
                new DuplexTask(lat,lng,true,true).execute(server); // Send message
                try {
                    resp = new DuplexTask(0, 0, false, false).execute(server);

                }catch(Exception e){
                    System.err.println(e.getMessage());
                }try {
                    response = (String) resp.get();
                }catch(Exception e) {
                    System.err.println(e.getMessage());
                }
                Log.e("RESPONSE", response);
                tokens = response.split(":"); // Contains data from the db.

                startActivity(mapIntent);
            }
        });
        no.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View v){
                new DuplexTask(lat,lng,false,true).execute(server); // Send message
            }
        });
    }

    private static class InitServerTask extends AsyncTask<String, String, ClientDuplexer> {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected ClientDuplexer doInBackground(String... ipPort) {
            String[] tokens = ipPort[0].split(":");
            ClientDuplexer server = null;
            try {
                server = new ClientDuplexer(new Socket(tokens[0],Integer.parseInt(tokens[1])));

            }catch(IOException ioe){
                System.err.println(ioe.getMessage());
            }
            return server;
        }
    }

    /**
     * Allows for asynchronus send/recieve from the client
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static class DuplexTask extends AsyncTask<ClientDuplexer,String,String> {
        double lat;
        double lng;
        boolean mobile;
        boolean sending;

        DuplexTask(double lat, double lng, boolean mobile, boolean sending){
            this.lat = lat;
            this.lng = lng;
            this.mobile = mobile;
            this.sending = sending;
        }

        @Override
        protected String doInBackground(ClientDuplexer... server) {
            if(sending){
                if(server[0] != null){
                    String message = lat + ":" + lng + ":" + (mobile ? "T" : "F");
                    server[0].sendMessage(message);
                }else{
                    return null;
                }
            }else{
                if(server[0] != null) {
                    String response = server[0].recieveMessage();
                    return response;
                }
            }
            return null;
        }
    }

}

