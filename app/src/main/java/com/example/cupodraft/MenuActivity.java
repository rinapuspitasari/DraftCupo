package com.example.cupodraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        SharedPreferences preferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);
        String id_customer = preferences.getString("id_customer","");
        Log.d("recyctest", "Test: "+id_customer);
    }

    public void handleScan(View view) {
        Intent i = new Intent(MenuActivity.this, ScanActivity.class);
        startActivity(i);
    }

    public void handleScanBack(View view) {
        Intent i = new Intent(MenuActivity.this, ScanReturnActivity.class);
        startActivity(i);
    }
}