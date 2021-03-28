package com.example.cupodraft.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cupodraft.LoginActivity;
import com.example.cupodraft.MapsActivity;
import com.example.cupodraft.MenuActivity;
import com.example.cupodraft.NavActivity;
import com.example.cupodraft.R;

public class HomeFragment extends Fragment {
    Button btnMenu, btnMaps;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences preferences = getContext().getSharedPreferences("data_login", Context.MODE_PRIVATE);
        String id_customer = preferences.getString("id_customer","");
        Log.d("recyctest", "Test: "+id_customer);
        Toast.makeText(getContext(), id_customer, Toast.LENGTH_SHORT).show();
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        tampilHome(view);
        return view;
    }

    private void tampilHome(View view) {
        btnMenu = view.findViewById(R.id.btnMenu);
        btnMaps = view.findViewById(R.id.btnMaps);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MenuActivity.class);
                startActivity(i);
            }
        });
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MapsActivity.class);
                startActivity(i);
            }
        });
    }
}