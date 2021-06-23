package com.example.cupodraft;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    Boolean Registered;
    String id_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check();
        SharedPreferences preferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);
        id_customer = preferences.getString("id_customer","");
        Log.e("keshav", "id_user --> " +preferences.getString("id_customer",""));
        Log.e("keshav", "registered --> " +preferences.getBoolean("Registered",false));
    }
    private void check() {

        if (checkInternetConnection()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
//                    startActivity(i);
//                    finish();

        SharedPreferences handler = getSharedPreferences("data_login", Context.MODE_PRIVATE);
        Registered = handler.getBoolean("Registered", false);
        if (!id_customer.isEmpty()) {
            startActivity(new Intent(MainActivity.this, NavActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        } else{
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
                }
            }, 1000);

        } else {
            AlertDialog.Builder batal = new AlertDialog.Builder(MainActivity.this);
            batal.setTitle(R.string.notification)
                    .setMessage(R.string.no_internet)
                    .setPositiveButton(R.string.try_again, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            check();
                        }
                    })
                    .setCancelable(false);
            batal.show();
        }
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
