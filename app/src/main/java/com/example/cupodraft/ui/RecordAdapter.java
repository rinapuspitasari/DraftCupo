package com.example.cupodraft.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cupodraft.DetailPinjamActivity;
import com.example.cupodraft.DetailReturnActivity;
import com.example.cupodraft.R;
import com.example.cupodraft.ScanReturnActivity;
import com.example.cupodraft.api.model.RecordResponse;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ListViewHolder>{
    private RecordResponse itemResponse;
    private Context context;

    public RecordAdapter(RecordResponse itemResponse, Context context) {
        this.itemResponse = itemResponse;
        this.context = context;
    }

    @NonNull
    @Override
    public RecordAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.ListViewHolder holder, int position) {
        holder.tvName.setText(itemResponse.getData()[position].getId_pinjam());
        holder.tvTgl.setText(itemResponse.getData()[position].getTanggal_pinjam());
        holder.tvStatus.setText(itemResponse.getData()[position].getStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemResponse.getData()[position].getStatus().equals("Belum Kembali")){
                    itemResponse.getData()[position].getTanggal_haruskembali();
                    Intent intent = new Intent(holder.itemView.getContext(), DetailPinjamActivity.class);
                    intent.putExtra("id_produk", itemResponse.getData()[position].getId_produk());
                    holder.itemView.getContext().startActivity(intent);
                } else{
                    Intent intent = new Intent(holder.itemView.getContext(), DetailReturnActivity.class);
                    intent.putExtra("id_produk", itemResponse.getData()[position].getId_produk());
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemResponse.getData().length;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTgl, tvStatus;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTgl = itemView.findViewById(R.id.tv_tgl);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
