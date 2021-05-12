package com.example.cupodraft.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cupodraft.LoginActivity;
import com.example.cupodraft.MenuActivity;
import com.example.cupodraft.NavActivity;
import com.example.cupodraft.R;
import com.example.cupodraft.RegActivity;
import com.example.cupodraft.ScanActivity;
import com.example.cupodraft.api.helper.ServiceGenerator;
import com.example.cupodraft.api.model.RegisterResponse;
import com.example.cupodraft.api.services.ApiInterface;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EProfileActivity extends AppCompatActivity {
    EditText tUser, tFullname, tEmail, tHp;
    TextView profileNm;
    CircleImageView profileCircleImageView;
    String profileImageUrl = "https://rest-server-cupo.000webhostapp.com/assets/images/profile/default.jpg";
    String id_customer, nama_customer, email, username, no_hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_profile);
        tFullname = findViewById(R.id.edtFullname);
        tUser = findViewById(R.id.edtUser);
        tEmail = findViewById(R.id.edtEmail);
        tHp = findViewById(R.id.edtHp);
        profileNm = findViewById(R.id.txtProfilename);
        profileCircleImageView = findViewById(R.id.imagePhoto);
        Glide.with(EProfileActivity.this)
                .load(profileImageUrl)
                .into(profileCircleImageView);
        SharedPreferences preferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);
        id_customer = preferences.getString("id_customer","");
        Intent mIntent = getIntent();
        nama_customer = mIntent.getStringExtra("fullname");
        email = mIntent.getStringExtra("email");
        username = mIntent.getStringExtra("username");
        no_hp = mIntent.getStringExtra("no_hp");
        Log.e("drawer", "nama_cus --> " +nama_customer);
        Log.e("drawer", "email_cus --> " +email);
        Log.e("drawer", "nohp_cus --> " +no_hp);
        profileNm.setText(nama_customer);
        tFullname.setText(nama_customer);
        tEmail.setText(email);
        tUser.setText(username);
        tHp.setText(no_hp);
    }

    public void updateProfile(String userId, String fullname, String user, String eemail, String no){
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<RegisterResponse> call = service.updateUser(userId, fullname, user, eemail, no);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                Log.e("keshav", "registerResponse 1 --> " + registerResponse);
                if (response.isSuccessful()) {
                    Toast.makeText(EProfileActivity.this, R.string.update_profile_berhasil, Toast.LENGTH_SHORT).show();
                    Intent myProfileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(myProfileIntent);
                } else {
                    Toast.makeText(EProfileActivity.this, R.string.gagal, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(EProfileActivity.this, R.string.gagal_koneksi, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateProfile(View view) {
        String userId = id_customer;
        String fullname = tFullname.getText().toString();
        String user = tUser.getText().toString();
        String eemail = tEmail.getText().toString();
        String no = tHp.getText().toString();
        updateProfile(userId, fullname, user, eemail, no);
    }

}