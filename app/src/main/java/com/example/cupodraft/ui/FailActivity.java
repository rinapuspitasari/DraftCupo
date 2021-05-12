package com.example.cupodraft.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cupodraft.NavActivity;
import com.example.cupodraft.R;

public class FailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);
    }

    public void handleHome(View view) {
        Intent i = new Intent(getApplicationContext(), NavActivity.class);
        startActivity(i);
        finish();
    }
}