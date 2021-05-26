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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cupodraft.LoginActivity;
import com.example.cupodraft.MapsActivity;
import com.example.cupodraft.MenuActivity;
import com.example.cupodraft.NavActivity;
import com.example.cupodraft.R;
import com.example.cupodraft.api.helper.ServiceGenerator;
import com.example.cupodraft.api.model.DataTotal;
import com.example.cupodraft.api.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.cupodraft.R.string.BerhasilLogin;

public class HomeFragment extends Fragment {
    ImageView btnMenu, btnMaps;
    TextView txtTotal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences preferences = getContext().getSharedPreferences("data_login", Context.MODE_PRIVATE);
        String id_customer = preferences.getString("id_customer","");
        Log.d("recyctest", "Test: "+id_customer);
//        Toast.makeText(getContext(), BerhasilLogin, Toast.LENGTH_SHORT).show();
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        tampilHome(view);
        return view;
    }

    private void tampilHome(View view) {
        btnMenu = view.findViewById(R.id.btnMenu);
        btnMaps = view.findViewById(R.id.btnMaps);
        txtTotal = view.findViewById(R.id.totalPinjam);
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
        getTotal();
    }

    public void getTotal(){
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<DataTotal> call = service.getTotal();
        call.enqueue(new Callback<DataTotal>() {
            @Override
            public void onResponse(Call<DataTotal> call, Response<DataTotal> response) {
                if (response.isSuccessful()) {
                    String total = response.body().getData();
//                    Toast.makeText(getContext(), total, Toast.LENGTH_SHORT).show();
                    Log.d("totaltest", "Test: "+total);
                    txtTotal.setText(total+ " " + getString(R.string._0_cup_dipinjam));

                } else {
                    Toast.makeText(getContext(), R.string.gagal, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataTotal> call, Throwable t) {
                Toast.makeText(getContext(), R.string.gagal_koneksi, Toast.LENGTH_SHORT).show();
            }
        });
    }
}