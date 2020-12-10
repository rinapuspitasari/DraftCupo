package com.example.cupodraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void handleMain(View view) {
        Intent i = new Intent(LoginActivity.this, NavActivity.class);
        startActivity(i);
    }

    public void handleReg(View view) {
        Intent i = new Intent(LoginActivity.this, RegActivity.class);
        startActivity(i);
    }

    public void handleLupa(View view) {
    }
}