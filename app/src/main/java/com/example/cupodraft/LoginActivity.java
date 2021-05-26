package com.example.cupodraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cupodraft.api.helper.ServiceGenerator;
import com.example.cupodraft.api.model.CommonMethod;
import com.example.cupodraft.api.model.LoginResponse;
import com.example.cupodraft.api.services.ApiInterface;
import com.example.cupodraft.ui.EProfileActivity;
import com.example.cupodraft.ui.LupaPassActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtUser,edtPassword;
    String username, password, id_customer, fullname, token, email, limit, no_hp;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUser = findViewById(R.id.txtUsername);
        edtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.button3);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    if (CommonMethod.isNetworkAvailable(LoginActivity.this))
                        doLogin(username, password);
                    else
                        CommonMethod.showAlert("Internet Connectivity Failure", LoginActivity.this);
                }
            }
        });

//        SharedPreferences handler = getSharedPreferences("data_login", Context.MODE_PRIVATE);
//        if(handler.getBoolean("data_login", true)) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//            finish();
//        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public boolean checkValidation() {
        username = edtUser.getText().toString();
        password = edtPassword.getText().toString();

        Log.e("Keshav", "username is -> " + username);
        Log.e("Keshav", "password is -> " + password);

        if (edtUser.getText().toString().trim().equals("")) {
            CommonMethod.showAlert("UserId Cannot be left blank", LoginActivity.this);
            return false;
        } else if (edtPassword.getText().toString().trim().equals("")) {
            CommonMethod.showAlert("password Cannot be left blank", LoginActivity.this);
            return false;
        }
        return true;
    }

    private void saveCredentials() {
        SharedPreferences handler = this.getSharedPreferences("data_login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = handler.edit();
        editor.putString("username", this.edtUser.getText().toString());
        editor.putString("password", this.edtPassword.getText().toString());
        editor.putString("id_customer", this.id_customer);
        editor.putString("nama_customer", this.fullname);
        editor.putString("token", this.token);
        editor.putString("email", this.email);
        editor.putString("limit", this.limit);
        editor.putString("no_hp", this.no_hp);
        editor.apply();
    }

    private void doLogin(String username, String password) {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<LoginResponse> call = service.doLogin(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                Log.e("keshav", "loginResponse 1 --> " + loginResponse);
                if(response.isSuccessful()){
                    id_customer = response.body().getData().getId_cust();
                    fullname = response.body().getData().getFull_name();
                    token = response.body().getData().getToken();
                    email = response.body().getData().getEmail();
                    limit = response.body().getData().getLimit_pinjam();
                    no_hp = response.body().getData().getNo_hp();
                    saveCredentials();
                    Log.e("keshav", "token --> " + response.body().getData().getToken());
                    Toast.makeText(LoginActivity.this, R.string.berhasil_login, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), NavActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, R.string.username_atau_, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, R.string.gagal_koneksi, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handleReg(View view) {
        Intent i = new Intent(LoginActivity.this, RegActivity.class);
        startActivity(i);
    }

    public void handleLupa(View view) {
        Intent i = new Intent(LoginActivity.this, LupaPassActivity.class);
        startActivity(i);
    }
}