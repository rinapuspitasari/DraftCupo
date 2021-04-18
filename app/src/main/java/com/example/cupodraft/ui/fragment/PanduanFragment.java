package com.example.cupodraft.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cupodraft.R;

public class PanduanFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_panduan, container, false);
        final TextView textView = root.findViewById(R.id.text_panduan);
        textView.setText("ini halaman panduan");
        return root;
    }
}