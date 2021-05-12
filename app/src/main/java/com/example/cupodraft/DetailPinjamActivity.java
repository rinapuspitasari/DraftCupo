package com.example.cupodraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cupodraft.api.helper.ServiceGenerator;
import com.example.cupodraft.api.model.PinjamResponse;
import com.example.cupodraft.api.model.ProdukModel;
import com.example.cupodraft.api.model.RegisterResponse;
import com.example.cupodraft.api.services.ApiInterface;
import com.example.cupodraft.ui.FailActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.cupodraft.R.string.gagal_koneksi;

public class DetailPinjamActivity extends AppCompatActivity {
    TextView idPinjam, tglPinjam, tglKembali, txtWait;
    String id_customer, id_produk, id_pinjam, tglBaru, tgl;
    ProgressBar progressBar;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pinjam);
        idPinjam = findViewById(R.id.inputId);
        tglPinjam = findViewById(R.id.inputTgl);
        tglKembali = findViewById(R.id.inputKembali);
        progressBar = findViewById(R.id.progressBar);
        constraintLayout = findViewById(R.id.layout);
        txtWait = findViewById(R.id.wait);
        SharedPreferences preferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);
        id_customer = preferences.getString("id_customer","");
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
                if(response.isSuccessful()){
                    if(response.body().getData()[0].getIs_acc() != null){
                        progressBar.setVisibility(View.GONE);
                        txtWait.setVisibility(View.GONE);
                        constraintLayout.setVisibility(View.VISIBLE);
                        String tanggalPinjam = response.body().getData()[0].getTanggal_pinjam();
                        String tanggal = response.body().getData()[0].getTanggal_haruskembali();
                        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            tglBaru=dateFormat.format(df.parse(tanggal));
                            tgl=dateFormat.format(df.parse(tanggalPinjam));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tglPinjam.setText(tglBaru);
                        tglKembali.setText(tgl);
                        idPinjam.setText(id_pinjam);
                        //Toast.makeText(DetailPinjamActivity.this, "Silahkan dikembalikan sebelum tanggal "+tanggal+ " yaa :)", Toast.LENGTH_SHORT).show();
                    } else{
                        progressBar.setVisibility(View.VISIBLE);
                        constraintLayout.setVisibility(View.INVISIBLE);
                        txtWait.setVisibility(View.VISIBLE);
//                        recreate();
                        getPinjam();
//                        Toast.makeText(DetailPinjamActivity.this, "Proses peminjaman sedang diproses", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(DetailPinjamActivity.this, R.string.gagal, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), FailActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<PinjamResponse> call, Throwable t) {
                Toast.makeText(DetailPinjamActivity.this, gagal_koneksi, Toast.LENGTH_SHORT).show();
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