package com.example.cupodraft.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cupodraft.HomeActivity;
import com.example.cupodraft.MapsActivity;
import com.example.cupodraft.MenuActivity;
import com.example.cupodraft.R;

public class HomeFragment extends Fragment {
    Button btnMenu, btnMaps;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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