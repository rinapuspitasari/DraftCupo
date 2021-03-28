package com.example.cupodraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cupodraft.api.helper.ServiceGenerator;
import com.example.cupodraft.api.model.PinjamResponse;
import com.example.cupodraft.api.model.ProdukModel;
import com.example.cupodraft.api.model.RegisterResponse;
import com.example.cupodraft.api.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPinjamActivity extends AppCompatActivity {

    String id_customer, id_produk, id_pinjam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pinjam);
        SharedPreferences preferences = getSharedPreferences("data_pinjam", Context.MODE_PRIVATE);
        id_customer = preferences.getString("id_cust","");
        id_produk = preferences.getString("id_prod","");
        Log.d("recyctest", "Test customer: "+id_customer);
        Log.d("recyctest", "Test produk: "+id_produk);
//        getPinjam();
    }

    private void getPinjam() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<PinjamResponse> call = service.getPeminjaman(id_customer, id_produk);
        call.enqueue(new Callback<PinjamResponse>() {
            @Override
            public void onResponse(Call<PinjamResponse> call, Response<PinjamResponse> response) {
                id_pinjam = response.body().getData()[0].getId_pinjam();
                Log.e("keshav", "pinjamResponse 1 --> " + id_pinjam);
                if(response.isSuccessful()){
                    String tanggal = response.body().getData()[0].getTanggal_haruskembali();
                    Toast.makeText(DetailPinjamActivity.this, "Silahkan dikembalikan sebelum tanggal "+tanggal+ " yaa :)", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DetailPinjamActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PinjamResponse> call, Throwable t) {
                Toast.makeText(DetailPinjamActivity.this, "Gagal Koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handleHome(View view) {
        startActivity(new Intent(DetailPinjamActivity.this, NavActivity.class));
        finish();
    }
}