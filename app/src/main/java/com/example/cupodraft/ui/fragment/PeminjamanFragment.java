package com.example.cupodraft.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cupodraft.DetailPinjamActivity;
import com.example.cupodraft.R;
import com.example.cupodraft.api.helper.ServiceGenerator;
import com.example.cupodraft.api.model.DataRecord;
import com.example.cupodraft.api.model.PinjamResponse;
import com.example.cupodraft.api.model.RecordResponse;
import com.example.cupodraft.api.services.ApiInterface;
import com.example.cupodraft.ui.RecordAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeminjamanFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecordAdapter fadapter;
    LinearLayoutManager mLayoutManager;
    ProgressBar progressBar;
    TextView notFound;
    String id_customer;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_peminjaman, container, false);
        SharedPreferences preferences = getContext().getSharedPreferences("data_login", Context.MODE_PRIVATE);
        id_customer = preferences.getString("id_customer","");
        Log.d("recyctest", "Test: "+id_customer);
        tampilRecord(root);
        return root;
    }

    private void tampilRecord(View view){
        recyclerView = view.findViewById(R.id.rv_record);
        progressBar = view.findViewById(R.id.progressBar);
        notFound = view.findViewById(R.id.nothing);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<RecordResponse> call = service.getPinjam(id_customer);
        call.enqueue(new Callback<RecordResponse>() {
            @Override
            public void onResponse(Call<RecordResponse> call, Response<RecordResponse> response) {
                RecordResponse record = response.body();
                if(response.isSuccessful()){
                    for(int i=0 ; i< record.getData().length; i++){
                        fadapter = new RecordAdapter(record, getContext());
                        recyclerView.setAdapter(fadapter);
                    }
//                    Toast.makeText(getContext(), record.getData().length, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    notFound.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecordResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal Koneksi", Toast.LENGTH_SHORT).show();
            }
        });

    }
}