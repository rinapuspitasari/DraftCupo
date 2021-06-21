package com.example.cupodraft.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.cupodraft.LoginActivity;
import com.example.cupodraft.MenuActivity;
import com.example.cupodraft.R;
import com.example.cupodraft.ScanReturnActivity;

public class VerifAccActivity extends AppCompatActivity {
    String number = "+6282228019562";
    String message = getString(R.string.halo_admin);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif_acc);
    }

    public void handleChat(View view) {
        /* substring(1) ini artinya kita akan menghapus karakter pertama dari sebuah string.
        Misalnya user meng-inputkan 0858 maka angka pertama dari 0858 yaitu 0 akan dihapus,
        karena kebutuhan kita titdak perlu memakai angka 0.
        */
        openWhatsApp();
    }

    /*
    Fungsi untuk membuka aplikasi whatsapp menggunakan API yang disediakan.
     */
    private void openWhatsApp() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s", number, message)));
        startActivity(i);
    }

    public void handleBack(View view) {
        startActivity(new Intent(VerifAccActivity.this, LoginActivity.class));
        finish();
    }
}