package com.example.cupodraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cupodraft.api.helper.ServiceGenerator;
import com.example.cupodraft.api.model.KembaliResponse;
import com.example.cupodraft.api.model.PinjamResponse;
import com.example.cupodraft.api.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailReturnActivity extends AppCompatActivity {
    TextView idKembali, tglKembali, txtStatus, txtDenda, txtTerlambat, inDenda, inTerlambat;
    String id_prod, id_cust, id_kembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_return);
        idKembali = findViewById(R.id.inputIdKembali);
        tglKembali = findViewById(R.id.inputTglKembali);
        txtStatus = findViewById(R.id.inputStatus);
        txtDenda = findViewById(R.id.textView15);
        txtTerlambat = findViewById(R.id.textView14);
        inDenda = findViewById(R.id.inputDenda);
        inTerlambat = findViewById(R.id.inputTerlambat);

        SharedPreferences preferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);
        id_cust = preferences.getString("id_customer","");
//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
//        id_produk = pref.getString("id_produk","");
        id_prod = getIntent().getStringExtra("id_produk");
        Log.d("recyctest", "Test customer: "+id_cust);
        Log.d("recyctest", "Test produk: "+id_prod);
        getKembali();
    }

    private void getKembali() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<KembaliResponse> call = service.getPengembalian(id_cust, id_prod);
        call.enqueue(new Callback<KembaliResponse>() {
            @Override
            public void onResponse(Call<KembaliResponse> call, Response<KembaliResponse> response) {
                id_kembali = response.body().getData()[0].getId_kembali();
                Log.e("keshav", "kembaliResponse 1 --> " + id_kembali);
                if(response.isSuccessful()){
                    String id = response.body().getData()[0].getId_kembali();
                    String tanggalKembali = response.body().getData()[0].getTanggal_kembali();
                    String terlambat = response.body().getData()[0].getTerlambat();
                    String denda = response.body().getData()[0].getDenda();
                    if(!terlambat.equals(null)){
                        txtDenda.setVisibility(View.VISIBLE);
                        txtTerlambat.setVisibility(View.VISIBLE);
                        inDenda.setVisibility(View.VISIBLE);
                        inTerlambat.setVisibility(View.VISIBLE);
                        txtStatus.setText("Terlambat");
                        inTerlambat.setText(terlambat+" hari");
                        inDenda.setText("Rp. " + denda);
                    }
                    tglKembali.setText(tanggalKembali);
                    idKembali.setText(id);
                }else{
                    Toast.makeText(DetailReturnActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KembaliResponse> call, Throwable t) {
                Toast.makeText(DetailReturnActivity.this, "Gagal Koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handleHome(View view) {
        startActivity(new Intent(DetailReturnActivity.this, NavActivity.class));
        finish();
    }
}