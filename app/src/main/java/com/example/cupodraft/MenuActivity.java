package com.example.cupodraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void handleScan(View view) {
        Intent i = new Intent(MenuActivity.this, ScanActivity.class);
        startActivity(i);
    }
}