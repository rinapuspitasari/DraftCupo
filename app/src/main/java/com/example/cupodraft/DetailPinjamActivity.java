package com.example.cupodraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DetailPinjamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pinjam);
    }

    public void handleHome(View view) {
        startActivity(new Intent(DetailPinjamActivity.this, NavActivity.class));
        finish();
    }
}