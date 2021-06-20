package com.example.cupodraft.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cupodraft.LoginActivity;
import com.example.cupodraft.R;
import com.example.cupodraft.api.helper.ServiceGenerator;
import com.example.cupodraft.api.model.CommonMethod;
import com.example.cupodraft.api.model.RegisterResponse;
import com.example.cupodraft.api.model.UserResponse;
import com.example.cupodraft.api.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LupaPassActivity extends AppCompatActivity {
    Button btSend;
    EditText edtUsername;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_pass);
        btSend = findViewById(R.id.btnsend);
        edtUsername = findViewById(R.id.txt_username);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    if (CommonMethod.isNetworkAvailable(LupaPassActivity.this))
                        getUserData();
                    else
                        CommonMethod.showAlert("Internet Connectivity Failure", LupaPassActivity.this);
                }
            }
        });
    }

    private boolean checkValidation() {
        username = edtUsername.getText().toString();
        if (username.equals("")) {
            Toast.makeText(this, R.string.data_wajib_diisi, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void getUserData(){
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<UserResponse> call = service.getCustUsername(username);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
//                    Toast.makeText(LupaPassActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                    String id = response.body().getData()[0].getId_cust();
                    String user = response.body().getData()[0].getUsername();
                    Intent i = new Intent(getApplicationContext(), ForgotActivity.class);
                    i.putExtra("id", id);
                    i.putExtra("username", user);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(LupaPassActivity.this, R.string.data_tidak_ditemukan, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(LupaPassActivity.this, R.string.gagal_koneksi, Toast.LENGTH_SHORT).show();
            }
        });
    }
}