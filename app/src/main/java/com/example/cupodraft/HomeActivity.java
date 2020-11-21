package com.example.cupodraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void handleMaps(View view) {
        Intent i = new Intent(HomeActivity.this, MapsActivity.class);
        startActivity(i);
    }

    public void handleMenu(View view) {
        Intent i = new Intent(HomeActivity.this, MenuActivity.class);
        startActivity(i);
    }
}