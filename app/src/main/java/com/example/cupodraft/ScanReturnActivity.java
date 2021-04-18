package com.example.cupodraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.cupodraft.api.helper.ServiceGenerator;
import com.example.cupodraft.api.model.CommonMethod;
import com.example.cupodraft.api.model.PinjamResponse;
import com.example.cupodraft.api.model.ProdukModel;
import com.example.cupodraft.api.model.RegisterResponse;
import com.example.cupodraft.api.services.ApiInterface;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanReturnActivity extends AppCompatActivity {
    private ImageView ivBgContent;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    String nama_produk, id_produk, id_customer, id_mitra, id_pinjam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_return);
        ivBgContent = findViewById(R.id.ivBgContent);
        scannerView = findViewById(R.id.scannerView);

        ivBgContent.bringToFront();

        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        String message = "Produk :\n" + result.getText();
                        nama_produk = result.getText();
                        getId(nama_produk);
//                        showAlertDialog();
                    }
                });
            }
        });
        checkCameraPermission();
        SharedPreferences preferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);
        id_customer = preferences.getString("id_customer","");
//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
//        id_produk = pref.getString("id_produk","");
//        id_pinjam = pref.getString("id_pinjam", "");
//        SharedPreferences preff = getSharedPreferences("peminjaman", MODE_PRIVATE);
//        id_pinjam = preff.getString("id_pinjam", "");
        Log.d("recyctest", "Test customer untuk pengembalian: "+id_customer);
//        Log.d("recyctest", "Test produk untuk peminjaman: "+id_produk);
//        Log.d("recyctest", "Test id_pinjam untuk peminjaman: "+id_pinjam);
    }

    private void getId(String nama_produk) {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<ProdukModel> call = service.getId(nama_produk);
        call.enqueue(new Callback<ProdukModel>() {
            @Override
            public void onResponse(Call<ProdukModel> call, Response<ProdukModel> response) {
                id_produk = response.body().getData()[0].getId_produk();
                Log.e("keshav", "produkResponse 1 --> " + id_produk);
                if(response.isSuccessful()){
                    Log.e("keshav", "id_produk --> " + response.body().getData()[0].getId_produk());
                    Toast.makeText(ScanReturnActivity.this, "berhasil get id produk", Toast.LENGTH_SHORT).show();
                    inputAlertDialog();
                }else{
                    Toast.makeText(ScanReturnActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProdukModel> call, Throwable t) {
                Toast.makeText(ScanReturnActivity.this, "Gagal Koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inputAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        builder.setTitle("Masukkan Kode Mitra");
        builder.setIcon(R.drawable.ic_cup1);
        builder.setMessage("Kode Mitra hanya diketahui oleh pihak mitra cupo :)");
        builder.setCancelable(true);

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton(
                "YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        id_mitra = input.getText().toString();
                        Log.e("keshav", "id_mitra --> " +id_mitra);
                        dialog.cancel();
                        if (input.getText().toString().trim().equals("")) {
                            CommonMethod.showAlert("MitraID Cannot be left blank", ScanReturnActivity.this);
                        } else{
                            showAlertDialog();
                        }
//                        finish();
                    }
                });

        builder.setNegativeButton(
                "BATAL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(ScanReturnActivity.this, MenuActivity.class));
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getPinjam() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<PinjamResponse> call = service.getPeminjaman(id_customer, id_produk);
        call.enqueue(new Callback<PinjamResponse>() {
            @Override
            public void onResponse(Call<PinjamResponse> call, Response<PinjamResponse> response) {
                if(response.isSuccessful()){
                    id_pinjam = response.body().getData()[0].getId_pinjam();
                    Log.e("keshav", "pinjamResponse 1 --> " + id_pinjam);
                    doPengembalian();
                }else{
                    Toast.makeText(ScanReturnActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PinjamResponse> call, Throwable t) {
                Toast.makeText(ScanReturnActivity.this, "Gagal Koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doPengembalian(){
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<RegisterResponse> call = service.doPengembalian(id_customer, id_produk, id_mitra, id_pinjam);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.e("keshav", "id_produk --> " +id_produk);
                Log.e("keshav", "id_mitra --> " +id_mitra);
                Log.e("keshav", "id_user --> " +id_customer);
                Log.e("keshav", "id_pinjam --> " +id_pinjam);
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("true")){
                        Toast.makeText(ScanReturnActivity.this, "berhasil melakukan transaksi pengembalian", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ScanReturnActivity.this, DetailReturnActivity.class);
                        intent.putExtra("id_produk", id_produk);
                        startActivity(intent);
//                        startActivity(new Intent(ScanReturnActivity.this, DetailReturnActivity.class));
                    } else{
                        String eror = response.body().getMessage();
                        Log.e("keshav", "Eror --> " +eror);
                        Toast.makeText(ScanReturnActivity.this, eror, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ScanReturnActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(ScanReturnActivity.this, "Gagal Koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkCameraPermission(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mCodeScanner.startPreview();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                                   PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }

    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        builder.setTitle("Apakah Anda ingin mengembalikan cup ini?");
        builder.setIcon(R.drawable.ic_cup1);
//        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setPositiveButton(
                "YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
//                        startActivity(new Intent(ScanReturnActivity.this, DetailReturnActivity.class));
                        getPinjam();
                        finish();
                    }
                });

        builder.setNegativeButton(
                "BATAL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(ScanReturnActivity.this, MenuActivity.class));
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCameraPermission();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}