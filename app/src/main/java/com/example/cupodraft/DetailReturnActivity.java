package com.example.cupodraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DetailReturnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_return);
    }

    public void handleHome(View view) {
        startActivity(new Intent(DetailReturnActivity.this, NavActivity.class));
        finish();
    }
}