package com.fishes.bigreddisaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.net.ServerSocket;

public class MainActivity extends AppCompatActivity {
    PopupWindow popUp;
    ConstraintLayout mainLayout;
    Button yes;
    Button no;
    Button sos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);
        popUp = new PopupWindow(this);
        popUp.setFocusable(true);
        sos = findViewById(R.id.button);
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