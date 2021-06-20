package com.example.cupodraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cupodraft.api.helper.ServiceGenerator;
import com.example.cupodraft.api.model.CommonMethod;
import com.example.cupodraft.api.model.RegisterResponse;
import com.example.cupodraft.api.services.ApiInterface;
import com.example.cupodraft.ui.VerifAccActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegActivity extends AppCompatActivity {
    EditText namaInput, usernameInput, emailInput, passwordInput, conPassInput, hpInput;
    Button btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String fullname, username, email, no_hp, password, conPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        namaInput = findViewById(R.id.text_fullname);
        usernameInput = findViewById(R.id.text_username);
        emailInput = findViewById(R.id.text_email);
        hpInput = findViewById(R.id.text_hp);
        passwordInput = findViewById(R.id.text_password);
        conPassInput = findViewById(R.id.text_confirm_password);
        btnRegister = findViewById(R.id.button_ok);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    if (CommonMethod.isNetworkAvailable(RegActivity.this))
                        doRegister(fullname, username, email, no_hp, password);
                    else
                        CommonMethod.showAlert("Internet Connectivity Failure", RegActivity.this);
                }
            }
        });
    }

    private boolean checkValidation() {
        fullname = namaInput.getText().toString();
        username = usernameInput.getText().toString();
        email = emailInput.getText().toString();
        no_hp = hpInput.getText().toString();
        password = passwordInput.getText().toString();
        conPass = conPassInput.getText().toString();

        Log.e("Keshav", "fullname is -> " + fullname);
        Log.e("Keshav", "username is -> " + username);
        Log.e("Keshav", "email is -> " + email);
        Log.e("Keshav", "no hp is -> " + no_hp);
        Log.e("Keshav", "password is -> " + password);
        Log.e("Keshav", "password 2 is -> " + conPass);

        if(fullname.isEmpty()){
            namaInput.setError(getString(R.string.nama_tidak_boleh));
            return false;
        } else if(username.isEmpty()){
            usernameInput.setError(getString(R.string.username_tidak));
            return false;
        } else if(email.isEmpty()){
            emailInput.setError(getString(R.string.email_tidak_boleh));
            return false;
        } else if(!email.matches(emailPattern)) {
            emailInput.setError(getString(R.string.emai_harus));
            return false;
        } else if(no_hp.isEmpty()){
            hpInput.setError(getString(R.string.no_hp_tidak));
            return false;
        } else if(password.isEmpty()){
            passwordInput.setError(getString(R.string.password_tidak));
            return false;
        } else if (!conPass.equals(password)){
            conPassInput.setError(getString(R.string.password_harus_sama));
            return false;
        }
        return true;
    }


    public void doRegister(String fullname, String username, String email, String no_hp, String password){
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<RegisterResponse> call = service.doRegister(fullname, username, email, no_hp, password);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                Log.e("keshav", "registerResponse 1 --> " + registerResponse);
                if (response.isSuccessful()) {
                    Toast.makeText(RegActivity.this, R.string.register_berhasil, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(RegActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(RegActivity.this, response.body().getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegActivity.this, VerifAccActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegActivity.this, R.string.gagal, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegActivity.this, R.string.gagal_koneksi, Toast.LENGTH_SHORT).show();
            }
        });
    }
}