package com.fishes.bigreddisaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.Context;
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

import java.net.ServerSocket;

public class MainActivity extends AppCompatActivity {
    PopupWindow popUp;
    ConstraintLayout mainLayout;
    Button yes;
    Button no;
    Button sos;
    LocationManager locationManager;
    TextView locationView;
    LocationService locationServer;
    double current_lattitude, current_longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        mainLayout = findViewById(R.id.mainLayout);
        popUp = new PopupWindow(this);
        popUp.setFocusable(true);
        sos = findViewById(R.id.button);
        locationView = findViewById(R.id.locationView);
        locationServer = new LocationService(this);
        int status = 0;
        if(locationServer.canGetLocation())
        {
            status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

            if (status == ConnectionResult.SUCCESS) {
                current_lattitude = locationServer.getLatitude();
                current_longitude = locationServer.getLongitude();
                Log.d("CURRENT LOCATION", "" + current_lattitude + "-"
                        + current_longitude);

                if (current_lattitude == 0.0 && current_longitude == 0.0) {
                    current_lattitude = 22.22;
                    current_longitude = 22.22;

                }
            } else {
                current_lattitude = 22.22;
                current_longitude = 22.22;
            }
            Log.d("CHANGED LOCATION", "" + current_lattitude + "-"
                    + current_longitude);
        }
        else
        {
            locationServer.showSettingsAlert();
        }
    }

    public void sendHelpOpenMap(View view){
        LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.popup,null);
        popUp = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popUp.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        /*Intent myIntent = new Intent(this, MapsActivity.class);
        startActivity(myIntent);*/

       /* CountDownDialog countDownDialog = new CountDownDialog();
        countDownDialog.show(getSupportFragmentManager(), "fragment_countdownTimer");*/

    }


/*    private class ServerTask extends AsyncTask<ServerSocket, String, Void> {
        @Override
        protected Void doInBackground(ServerSocket... sockets) {

        }


        protected void onProgressUpdate(String... strings) {

        }
    }*/
}