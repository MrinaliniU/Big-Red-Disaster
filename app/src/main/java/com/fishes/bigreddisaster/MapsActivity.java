package com.fishes.bigreddisaster;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    String lat, lng;

    private GoogleMap mMap;
    DatabaseReference mRef;
    String s1Lat, s1Lng, s2Lat, s2Lng , s3Lat, s3Lng ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Log.e("Oh No", "Oh NO");
                lng = "0";
                lat = "0";
            } else {
                lng= extras.getString("lng");
                lat = extras.getString("lat");
            }
        } else {
            lng= (String) savedInstanceState.getSerializable("lng");
            lat = (String) savedInstanceState.getSerializable("lat");
        }

        mRef = FirebaseDatabase.getInstance().getReference("Shelters");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                s1Lat = String.valueOf(dataSnapshot.child("Shelter One").child("lat").getValue());
                s1Lng = String.valueOf(dataSnapshot.child("Shelter One").child("lng").getValue());

                s2Lat = String.valueOf(dataSnapshot.child("Shelter Two").child("lat").getValue());
                s2Lng = String.valueOf(dataSnapshot.child("Shelter Two").child("lng").getValue());

                s3Lat = String.valueOf(dataSnapshot.child("Shelter Three").child("lat").getValue());
                s3Lng = String.valueOf(dataSnapshot.child("Shelter Three").child("lng").getValue());

                LatLng shelterOne = new LatLng(Double.parseDouble(s1Lat), Double.parseDouble(s1Lng));
                LatLng shelterTwo = new LatLng(Double.parseDouble(s2Lat), Double.parseDouble(s2Lng));
                LatLng shelterThree = new LatLng(Double.parseDouble(s3Lat), Double.parseDouble(s3Lng));

                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(250)).position(shelterOne).title("Shelter One"));
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(250)).position(shelterTwo).title("Shelter Two"));
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(250)).position(shelterThree).title("Shelter Three"));
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Map Activity", "Failed to read value.", error.toException());
            }
        });

        // Add a marker in Sydney and move the camera
        //LatLng currentLocation = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

        //DEMO
        LatLng currentLocation = new LatLng(Double.parseDouble("42.4671367"), Double.parseDouble("-76.5075061"));
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("You Are Here!"));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
    }
}
