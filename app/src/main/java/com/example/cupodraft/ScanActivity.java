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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.cupodraft.api.helper.ServiceGenerator;
import com.example.cupodraft.api.model.CommonMethod;
import com.example.cupodraft.api.model.LoginResponse;
import com.example.cupodraft.api.model.ProdukModel;
import com.example.cupodraft.api.model.RegisterResponse;
import com.example.cupodraft.api.services.ApiInterface;
import com.example.cupodraft.ui.FailActivity;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanActivity extends AppCompatActivity {
    private ImageView ivBgContent;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    String nama_produk, id_produk, id_mitra, id_customer, limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
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
        limit = preferences.getString("limit","");
        Log.e("keshav", "id_user --> " +id_customer);
        Log.e("keshav", "limit --> " +limit);
    }

    private void getId(String nama_produk) {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<ProdukModel> call = service.getId(nama_produk);
        call.enqueue(new Callback<ProdukModel>() {
            @Override
            public void onResponse(Call<ProdukModel> call, Response<ProdukModel> response) {
                if(limit.matches("0")){
                    limitDialog();
                } else {
                    if (response.body().getData().length == 0) {
                        Toast.makeText(ScanActivity.this, R.string.gagal, Toast.LENGTH_SHORT).show();
                        errorDialog();
                    } else {
                        id_produk = response.body().getData()[0].getId_produk();
                        String status = response.body().getData()[0].getStatus();
                        Toast.makeText(ScanActivity.this, status, Toast.LENGTH_SHORT).show();
                        if (response.body().getData()[0].getStatus().equals("1")) {
                            Log.e("keshav", "id_produk --> " + response.body().getData()[0].getId_produk());
                            Toast.makeText(ScanActivity.this, R.string.data_produk_berhasil, Toast.LENGTH_SHORT).show();
                            inputAlertDialog();
                        } else {
                            Toast.makeText(ScanActivity.this, R.string.produk_telah_dipinjam, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
//                if(response.isSuccessful()){
//                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ScanActivity.this);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("id_produk", response.body().getData()[0].getId_produk());
//                    editor.apply();
//                    id_produk = response.body().getData()[0].getId_produk();
//                    String status = response.body().getData()[0].getStatus();
//                    Toast.makeText(ScanActivity.this, status, Toast.LENGTH_SHORT).show();
//                    if(response.body().getData()[0].getStatus().equals("1")) {
//                        Log.e("keshav", "id_produk --> " + response.body().getData()[0].getId_produk());
//                        Toast.makeText(ScanActivity.this, R.string.data_produk_berhasil, Toast.LENGTH_SHORT).show();
//                        inputAlertDialog();
//                    }
//                    else{
//                        Toast.makeText(ScanActivity.this, R.string.produk_telah_dipinjam, Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Toast.makeText(ScanActivity.this, R.string.gagal, Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<ProdukModel> call, Throwable t) {
                Toast.makeText(ScanActivity.this, R.string.gagal_koneksi, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inputAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        builder.setTitle(R.string.masukkan_kode);
        builder.setIcon(R.drawable.ic_cup1);
        builder.setMessage(R.string.kode_mitra_hanya);
        builder.setCancelable(true);

        // Set an EditText view to get user input
        final EditText input = new EditText(ScanActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.TEXT_ALIGNMENT_TEXT_START
        );
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton(
                R.string.ya,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        id_mitra = input.getText().toString();
                        Log.e("keshav", "id_mitra --> " +id_mitra);
                        dialog.cancel();
                        if (input.getText().toString().trim().equals("")) {
                            CommonMethod.showAlert("MitraID Cannot be left blank", ScanActivity.this);
                        } else{
                            showAlertDialog();
                        }
//                        finish();
                    }
                });

        builder.setNegativeButton(
                R.string.batal,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(ScanActivity.this, MenuActivity.class));
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void doPinjam() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<RegisterResponse> call = service.doPeminjaman(id_customer, id_produk, id_mitra);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                Log.e("keshav", "id_produk --> " +id_produk);
                Log.e("keshav", "id_mitra --> " +id_mitra);
                Log.e("keshav", "id_user --> " +id_customer);
                Log.e("keshav", "pinjamResponse 1 --> " + registerResponse);
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("true")){
//                        Toast.makeText(ScanActivity.this, "berhasil melakukan transaksi peminjaman", Toast.LENGTH_SHORT).show();
//                        saveCredentials();
                        Intent intent = new Intent(ScanActivity.this, DetailPinjamActivity.class);
                        intent.putExtra("id_produk", id_produk);
                        startActivity(intent);
//                        startActivity(new Intent(ScanActivity.this, DetailPinjamActivity.class));
                    } else{
                        String eror = response.body().getMessage();
                        Toast.makeText(ScanActivity.this, eror, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    String eror = response.body().getMessage();
                    Toast.makeText(ScanActivity.this, eror, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(ScanActivity.this, R.string.gagal, Toast.LENGTH_SHORT).show();
//                    limitDialog();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(ScanActivity.this, R.string.gagal_koneksi, Toast.LENGTH_SHORT).show();
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
        builder.setTitle(R.string.apakah_anda_ingin);
        builder.setIcon(R.drawable.ic_cup1);
//        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setPositiveButton(
                R.string.ya,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        doPinjam();
                        finish();
                    }
                });

        builder.setNegativeButton(
                R.string.batal,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(ScanActivity.this, MenuActivity.class));
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void limitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        builder.setTitle("Oops..");
        builder.setIcon(R.drawable.ic_cup1);
        builder.setMessage(R.string.peminjaman_sudah_habis);
        builder.setCancelable(true);

        builder.setPositiveButton(
                R.string.kembalikan,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(ScanActivity.this, ScanReturnActivity.class));
                        finish();
                    }
                });

        builder.setNegativeButton(
                R.string.batal,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(ScanActivity.this, MenuActivity.class));
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void errorDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        builder.setTitle(R.string.gagal_memindai_data);
        builder.setIcon(R.drawable.ic_cup1);
        builder.setMessage(R.string.proses_gagal);
        builder.setCancelable(true);

        builder.setPositiveButton(
                R.string.coba_lagi,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        recreate();
                    }
                });

        builder.setNegativeButton(
                R.string.batal,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(ScanActivity.this, MenuActivity.class));
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