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
import com.example.cupodraft.NavActivity;
import com.example.cupodraft.R;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EProfileActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 1;
    EditText tUser, tFullname, tEmail;
    TextView profileNm;
    CircleImageView profileCircleImageView;
    String profileImageUrl = "https://rest-server-cupo.000webhostapp.com/assets/images/profile/default.jpg";
    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_profile);
        tFullname = findViewById(R.id.edtFullname);
        tUser = findViewById(R.id.edtUser);
        tEmail = findViewById(R.id.edtEmail);
        profileNm = findViewById(R.id.txtProfilename);
        profileCircleImageView = findViewById(R.id.imagePhoto);
        Glide.with(EProfileActivity.this)
                .load(profileImageUrl)
                .into(profileCircleImageView);
        SharedPreferences preferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);
        String nama_customer = preferences.getString("nama_customer","");
        String email = preferences.getString("email", "");
        String username = preferences.getString("username", "");
        Log.e("drawer", "nama_cus --> " +nama_customer);
        Log.e("drawer", "email_cus --> " +email);
        profileNm.setText(nama_customer);
        tFullname.setText(nama_customer);
        tEmail.setText(email);
        tUser.setText(username);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                try {
                    imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    profileCircleImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e("tag", e.getMessage());
                }
            }
        }
    }

    public  void inputPassword(){

    }

    public void changeAvatar(View view) {
        Intent foto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(foto, GALLERY_REQUEST_CODE);
    }
}