package com.fishes.bigreddisaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


import androidx.appcompat.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    PopupWindow popUp;
    ConstraintLayout mainLayout;
    Button sos;
    LocationManager locationManager;
    TextView locationView;
    LocationService locationServer;
    Double current_latitude, current_longitude;
    ClientDuplexer server;
    DatabaseReference dbLat;
    DatabaseReference dbLng;

    private final int REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        mainLayout = findViewById(R.id.mainLayout);
        popUp = new PopupWindow(this);
        popUp.setFocusable(true);
        sos = findViewById(R.id.button);
       sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                try {
                    startActivityForResult(intent, REQ_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String response = new String(result.get(0).toString());
                    //textView.setText(response);
                    if(response.contains("no") || response.contains("help") ){
                        final Intent HelpIntent = new Intent(this, Help.class);
                        startActivity(HelpIntent);
                    }
                    else if(response.contains("yes")){
                        final Intent HelpIntent = new Intent(this, Help.class);
                        startActivity(HelpIntent);
                    }
                    else{
                        Log.e("goldfish ", "big red disaster");
                    }
                }
                break;
            }
        }
    }
    public void sendHelpOpenMap(View view) {
        dbLat = FirebaseDatabase.getInstance().getReference("help/userLocation/lat");
        dbLng = FirebaseDatabase.getInstance().getReference("help/userLocation/lng");

        locationServer = new LocationService(this);
        int status;
        if (locationServer.canGetLocation()) {
            status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

            if (status == ConnectionResult.SUCCESS) {
                current_latitude = locationServer.getLatitude();
                current_longitude = locationServer.getLongitude();
                Log.d("CURRENT LOCATION", "" + current_latitude + "," + current_longitude);
                dbLng.setValue(current_longitude);
                dbLat.setValue(current_latitude);

                if (current_latitude == 0.0 && current_longitude == 0.0) {
                    Log.e("CURRENT LOCATION IS 0", "" + current_latitude + "-" + current_longitude);
                    current_latitude = 22.22;
                    current_longitude = 22.22;
                    Log.e("CHANGED LOCATION", "" + current_latitude + "-" + current_longitude);
                }
            }
        } else {
            locationServer.showSettingsAlert();
        }

        final Intent myIntent = new Intent(getApplicationContext(), MapsActivity.class);
        myIntent.putExtra("lat", current_latitude);
        myIntent.putExtra("lng", current_latitude);
        //final Intent myIntent = new Intent(this, CanYouWalk.class);
        /*myIntent.putExtra("latitude", current_latitude.toString());
        myIntent.putExtra("longitude", current_longitude.toString());*/
        startActivity(myIntent);

    }
}