package com.example.cupodraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.cupodraft.api.helper.ServiceGenerator;
import com.example.cupodraft.api.model.ProdukModel;
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
    String nama_produk;

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
    }

    private void getId(String nama_produk) {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<ProdukModel> call = service.getId(nama_produk);
        call.enqueue(new Callback<ProdukModel>() {
            @Override
            public void onResponse(Call<ProdukModel> call, Response<ProdukModel> response) {
                ProdukModel produkModel = response.body();
                Log.e("keshav", "produkResponse 1 --> " + produkModel);
                if(response.isSuccessful()){
//                    SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    SharedPreferences.Editor editor = preference.edit();
//                    editor.putString("token",response.body().getData().getToken());
//                    editor.putString("fullname",response.body().getData().getFull_name());
//                    editor.putString("email",response.body().getData().getFull_name());
//                    editor.apply();
                    Log.e("keshav", "id_produk --> " + response.body().getData().toString());
                    Toast.makeText(ScanReturnActivity.this, "berhasil get id produk", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(getApplicationContext(), NavActivity.class);
//                    startActivity(i);
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
                        startActivity(new Intent(ScanReturnActivity.this, DetailReturnActivity.class));
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