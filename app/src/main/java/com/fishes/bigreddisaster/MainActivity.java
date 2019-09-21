package com.fishes.bigreddisaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {
    PopupWindow popUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popUp = new PopupWindow(this);
        Resources res = getResources();
        Drawable shape = ResourcesCompat.getDrawable(res, R.drawable.buttonshape, getTheme());

        Button tv = findViewById(R.id.button);
        tv.setBackground(shape);
    }

    public void sendHelpOpenMap(View view){
        /*Intent myIntent = new Intent(this, MapsActivity.class);
        startActivity(myIntent);*/

       /* CountDownDialog countDownDialog = new CountDownDialog();
        countDownDialog.show(getSupportFragmentManager(), "fragment_countdownTimer");*/

    }
}