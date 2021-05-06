package com.example.cupodraft.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cupodraft.LoginActivity;
import com.example.cupodraft.R;
import com.example.cupodraft.RegActivity;
import com.example.cupodraft.api.helper.ServiceGenerator;
import com.example.cupodraft.api.model.CommonMethod;
import com.example.cupodraft.api.model.RegisterResponse;
import com.example.cupodraft.api.services.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EPassActivity extends AppCompatActivity {
    EditText pasLama, pasBaru;
    String password, passwordBaru;
    Button btn;
    String id_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_pass);
        pasLama = findViewById(R.id.edtPas);
        pasBaru = findViewById(R.id.edtPasBaru);
        btn = findViewById(R.id.btnChangePas);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    if (CommonMethod.isNetworkAvailable(EPassActivity.this))
                        changePass();
                    else
                        CommonMethod.showAlert("Internet Connectivity Failure", EPassActivity.this);
                }
            }
        });
        SharedPreferences preferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);
//        if(preferences!=null){
            id_customer = preferences.getString("id_customer","");
//        } else{
            id_customer = getIntent().getStringExtra("id");
//        }
    }

    private boolean checkValidation() {
        password = pasLama.getText().toString();
        passwordBaru = pasBaru.getText().toString();
        if (passwordBaru.trim().length() < 8) {
            Toast.makeText(this, "Minimal 8 karakter!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void changePass(){
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<RegisterResponse> call = service.updatePass(id_customer, passwordBaru);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                Log.e("keshav", "registerResponse 1 --> " + registerResponse);
                if (response.isSuccessful()) {
                    Toast.makeText(EPassActivity.this, "Update Password Berhasil, Silahkan login menggunakan password yang baru:)", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i); finish();
                } else {
                    Toast.makeText(EPassActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(EPassActivity.this, "Gagal Koneksi Ke Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}