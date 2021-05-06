package com.example.cupodraft.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cupodraft.LoginActivity;
import com.example.cupodraft.R;
import com.example.cupodraft.api.helper.ServiceGenerator;
import com.example.cupodraft.api.model.RegisterResponse;
import com.example.cupodraft.api.model.UserResponse;
import com.example.cupodraft.api.services.ApiInterface;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView profileNm, tUser, tFullname, tEmail, tHp;
    CircleImageView profileCircleImageView;
    String profileImageUrl = "https://rest-server-cupo.000webhostapp.com/assets/images/profile/default.jpg";
    String id_customer, nama_customer, email, username, no_hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tFullname = findViewById(R.id.txtFullname);
        tUser = findViewById(R.id.txtUser);
        tEmail = findViewById(R.id.txtEmail);
        tHp = findViewById(R.id.txtHp);
        profileNm = findViewById(R.id.txtProfilename);
        profileCircleImageView = findViewById(R.id.imagePhoto);
        Glide.with(ProfileActivity.this)
                .load(profileImageUrl)
                .into(profileCircleImageView);
        SharedPreferences preferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);
        id_customer = preferences.getString("id_customer","");
        getProfile();
    }

    public void getProfile(){
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<UserResponse> call = service.getUser(id_customer);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Get Data Profile Berhasil", Toast.LENGTH_SHORT).show();
                    nama_customer = response.body().getData()[0].getFullname();
                    email = response.body().getData()[0].getEmail();
                    username = response.body().getData()[0].getUsername();
                    no_hp = response.body().getData()[0].getNo_hp();
                    profileNm.setText(nama_customer);
                    tFullname.setText(nama_customer);
                    tEmail.setText(email);
                    tUser.setText(username);
                    tHp.setText(no_hp);
                } else {
                    Toast.makeText(ProfileActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Gagal Koneksi Ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateProfile(View view) {
        Intent i = new Intent(ProfileActivity.this, EProfileActivity.class);
        i.putExtra("fullname", nama_customer);
        i.putExtra("username", username);
        i.putExtra("email", email);
        i.putExtra("no_hp", no_hp);
        startActivity(i);
    }

    public void logoutUser(View view) {
        SharedPreferences handler = getSharedPreferences("data_login", Context.MODE_PRIVATE);
        handler.edit().clear().commit();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    public void changePass(View view) {
        Intent i = new Intent(ProfileActivity.this, EPassActivity.class);
        startActivity(i);
    }
}