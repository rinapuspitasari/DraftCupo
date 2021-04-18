package com.example.cupodraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
    TextView idPinjam, tglPinjam, tglKembali;
    String id_customer, id_produk, id_pinjam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pinjam);
        idPinjam = findViewById(R.id.inputId);
        tglPinjam = findViewById(R.id.inputTgl);
        tglKembali = findViewById(R.id.inputKembali);
        SharedPreferences preferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);
        id_customer = preferences.getString("id_customer","");
//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
//        id_produk = pref.getString("id_produk","");
        id_produk = getIntent().getStringExtra("id_produk");
        Log.d("recyctest", "Test customer: "+id_customer);
        Log.d("recyctest", "Test produk: "+id_produk);
        getPinjam();
    }

    private void getPinjam() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<PinjamResponse> call = service.getPeminjaman(id_customer, id_produk);
        call.enqueue(new Callback<PinjamResponse>() {
            @Override
            public void onResponse(Call<PinjamResponse> call, Response<PinjamResponse> response) {
                id_pinjam = response.body().getData()[0].getId_pinjam();
                Log.e("keshav", "pinjamResponse 1 --> " + id_pinjam);
//                SharedPreferences preferences = getApplicationContext().getSharedPreferences("peminjaman", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString("id_produk", response.body().getData()[0].getId_produk());
//                editor.putString("id_customer", response.body().getData()[0].getId_user());
//                editor.putString("id_pinjam", response.body().getData()[0].getId_pinjam());
//                editor.apply();

                if(response.isSuccessful()){
                    String tanggalPinjam = response.body().getData()[0].getTanggal_pinjam();
                    String tanggal = response.body().getData()[0].getTanggal_haruskembali();
                    tglPinjam.setText(tanggalPinjam);
                    tglKembali.setText(tanggal);
                    idPinjam.setText(id_pinjam);
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

    public void handleKembali(View view) {
        Intent intent = new Intent(DetailPinjamActivity.this, ScanReturnActivity.class);
//        intent.putExtra("id_pinjam", id_pinjam);
        startActivity(intent);
    }
}