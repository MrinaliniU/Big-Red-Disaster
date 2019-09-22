package com.fishes.bigreddisaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    PopupWindow popUp;
    ConstraintLayout mainLayout;
    Button sos;
    LocationManager locationManager;
    TextView locationView;
    LocationService locationServer;
    Double current_latitude, current_longitude;
    ClientDuplexer server;
    DatabaseReference userHep;
    Map<String, UserData> users;

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
    }

    public void sendHelpOpenMap(View view) {
        userHep = FirebaseDatabase.getInstance().getReference("help");

        locationServer = new LocationService(this);
        int status;
        if (locationServer.canGetLocation()) {
            status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

            if (status == ConnectionResult.SUCCESS) {
                current_latitude = locationServer.getLatitude();
                current_longitude = locationServer.getLongitude();
                Log.d("CURRENT LOCATION", "" + current_latitude + "," + current_longitude);
                users = new HashMap<>();
               UserData user =  new UserData(current_latitude + "," + current_longitude, "Not an Emergency");
               users.put("uid"+user.getUid(),user);
                userHep.setValue(user);

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
        final Intent myIntent = new Intent(this, CanYouWalk.class);
        myIntent.putExtra("latitude", current_latitude.toString());
        myIntent.putExtra("longitude", current_longitude.toString());
        startActivity(myIntent);

    }
}